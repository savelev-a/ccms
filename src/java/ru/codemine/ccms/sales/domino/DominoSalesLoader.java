/*
 * Copyright (C) 2016 Alexander Savelev
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package ru.codemine.ccms.sales.domino;

import com.mchange.io.impl.EndsWithFilenameFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.ccms.entity.Sales;
import ru.codemine.ccms.entity.SalesMeta;
import ru.codemine.ccms.entity.Shop;
import ru.codemine.ccms.mail.EmailAttachmentExtractor;
import ru.codemine.ccms.sales.SalesLoader;
import ru.codemine.ccms.service.SalesService;
import ru.codemine.ccms.service.SettingsService;

/**
 *
 * @author Alexander Savelev
 */
@Component
public class DominoSalesLoader implements SalesLoader
{

    private static final Logger log = Logger.getLogger("DominoSalesLoader");

    @Autowired
    private EmailAttachmentExtractor emailExtractor;

    @Autowired
    private SettingsService settingsService;
    
    @Autowired
    private SalesService salesService;

    private Map<LocalDate, Map<String, Double>> getSalesMaps()
    {
        File path = new File(settingsService.getStorageEmailPath());
        FilenameFilter filter = new EndsWithFilenameFilter(".xls", EndsWithFilenameFilter.NEVER);
        Map<LocalDate, Map<String, Double>> result = new HashMap<>();
        try
        {
            for (File file : path.listFiles(filter))
            {

                log.info("Starting file processing: " + file.getName());

                FileInputStream fs = new FileInputStream(file);
                HSSFWorkbook workbook = new HSSFWorkbook(fs);
                HSSFSheet sheet = workbook.getSheetAt(0);
                boolean dateFound = false;
                boolean colFound = false;
                Integer colNumber = 0;
                LocalDate fileDate = null;
                Map<String, Double> parsedFileMap = new HashMap();

                for (Row row : sheet)
                {
                    Cell firstCell = row.getCell(0);

                    if (firstCell.getCellType() == Cell.CELL_TYPE_STRING
                            && firstCell.getStringCellValue().startsWith("Отчет по реализации и возвратам"))
                    {
                        String bothDatesStr = firstCell.getStringCellValue().replace("Отчет по реализации и возвратам с ", "");
                        String[] datesStr = bothDatesStr.split(" по ");
                        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.YYYY");
                        LocalDate startDate = formatter.parseLocalDate(datesStr[0]);
                        LocalDate endDate = formatter.parseLocalDate(datesStr[1]);
                        if (startDate != null && startDate.isEqual(endDate))
                        {
                            dateFound = true;
                            fileDate = startDate;
                            log.debug("date found! it is: " + fileDate);
                        }

                    } else if (firstCell.getCellType() == Cell.CELL_TYPE_STRING && firstCell.getStringCellValue().startsWith("№ п/п"))
                    {
                        for (Cell headersCell : row)
                        {
                            if (headersCell.getCellType() == Cell.CELL_TYPE_STRING && headersCell.getStringCellValue().startsWith("Сумма фактическая"))
                            {
                                colFound = true;
                                colNumber = headersCell.getColumnIndex();
                                log.debug("headers found! it in col number " + colNumber);
                            }
                        }
                    } else if (dateFound && colFound
                            && firstCell.getCellType() == Cell.CELL_TYPE_STRING
                            && firstCell.getStringCellValue().startsWith("Итого по:"))
                    {
                        String namesStr = firstCell.getStringCellValue().replace("Итого по: ", "");
                        //String[] names = namesStr.split(" - ");
                        //String name = names[1];  - Не подходит для некоторых названий
                        String delimiter = " - ";
                        String name = namesStr.substring(
                                namesStr.indexOf(delimiter) + delimiter.length(),
                                namesStr.lastIndexOf(delimiter));

                        Double value = row.getCell(colNumber).getNumericCellValue();
                        parsedFileMap.put(name, value);
                        log.debug("Recieved file map: " + parsedFileMap);
                    }

                } // foreach row in sheet

                if (dateFound && colFound)
                {
                    result.put(fileDate, parsedFileMap);
                    log.info("...ok");
                    Files.delete(file.toPath());
                } else
                {
                    if (!dateFound)
                    {
                        log.warn("No date found in file " + file.getName());
                    }
                    if (!colFound)
                    {
                        log.warn("No column 'total' found in file " + file.getName());
                    }
                    log.error("cannot process file " + file.getName());
                }

            }// foreach file in path

        } catch (Exception e)
        {
            log.error("Cannot process files, error is: " + e.getMessage());
        }

        return result;
    }   // getSalesMap()

    @Override
    public void processSales(List<Shop> shops)
    {
        emailExtractor.saveAllAttachment();

        Map<LocalDate, Map<String, Double>> processMap = getSalesMaps();
        log.debug("ready: " + processMap);

        if (!processMap.isEmpty())
        {
            LocalDate date = processMap.keySet().iterator().next();
            Map<String, Double> valuesMap = processMap.get(date);
            for (Shop shop : shops)
            {
                Double salesVal = valuesMap.get(shop.getDominoName());
                if(salesVal != null)
                {
                    SalesMeta sm = salesService.getByShopAndDate(shop, date.withDayOfMonth(1), date.dayOfMonth().withMaximumValue());
                    Sales sales = sm.getByDate(date);
                    sales.setValue(salesVal);
                    salesService.update(sm);
                }
                else
                {
                    log.warn("Cannot get sales value for shop " + shop.getName() + ", not fount in table!");
                    log.warn("Domino name is: " + shop.getDominoName());
                }
            }
            
            log.info("Completed auto update sales");
        }
        else
        {
            log.info("No new sales found to load");
        }
        
    }
}
