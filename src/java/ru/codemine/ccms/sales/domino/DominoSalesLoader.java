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
import java.util.Map.Entry;
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

    private Map<LocalDate, Map<String, Sales>> getSalesMap()
    {
        File path = new File(settingsService.getStorageEmailPath());
        FilenameFilter filter = new EndsWithFilenameFilter(".xls", EndsWithFilenameFilter.NEVER);
        Map<LocalDate, Map<String, Sales>> result = new HashMap();
        for (File file : path.listFiles(filter))
        {
            try
            {

                log.info("Обработка файла: " + file.getName());

                FileInputStream fs = new FileInputStream(file);
                HSSFWorkbook workbook = new HSSFWorkbook(fs);
                HSSFSheet sheet = workbook.getSheetAt(0);
                boolean dateFound = false;
                boolean colFound = false;
                Integer colNumber = 0;
                LocalDate fileDate = null;
                Map<String, Sales> parsedFileMap = new HashMap();

                for (Row row : sheet)
                {
                    Cell firstCell = row.getCell(0);

                    
                    //
                    // Поиск даты документа
                    //
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
                            log.info("Дата файла: " + fileDate);
                        }

                    } 
                    
                    //
                    // Поиск столбца с фактической суммой
                    //
                    
                    else if (firstCell.getCellType() == Cell.CELL_TYPE_STRING && firstCell.getStringCellValue().startsWith("№ п/п"))
                    {
                        for (Cell headersCell : row)
                        {
                            if (headersCell.getCellType() == Cell.CELL_TYPE_STRING && headersCell.getStringCellValue().startsWith("Сумма фактическая"))
                            {
                                colFound = true;
                                colNumber = headersCell.getColumnIndex();
                                log.info("Колонка значений: " + colNumber);
                            }
                        }
                    } 
                    
                    //
                    // Поиск и обработка записей магазинов
                    //
                    
                    else if (dateFound && colFound
                            && firstCell.getCellType() == Cell.CELL_TYPE_STRING
                            && firstCell.getStringCellValue().startsWith("Подразделение:"))
                    {
                        Sales sale = new Sales();
                        
                        // Поиск имени магазина
                        String namesStr = firstCell.getStringCellValue().replace("Подразделение: ", "");
                        String delimiter = " - ";
                        if(namesStr.indexOf(delimiter) > 0 && namesStr.indexOf(delimiter) != namesStr.lastIndexOf(delimiter))
                        {
                            String name = namesStr.substring(
                                namesStr.indexOf(delimiter) + delimiter.length(),
                                namesStr.lastIndexOf(delimiter));
                            
                            // Разбор записи магазина
                            boolean shopFinished = false;
                            int rcrd = 1;
                            int cashb_rcrd = 0;
                            while(!shopFinished)    
                            {
                                Row r = sheet.getRow(row.getRowNum() + rcrd);
                                if(r.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC) // строка с продажей или возвратом
                                {
                                    Double val = r.getCell(colNumber).getNumericCellValue();
                                    //log.debug("i is: " + i + ", val is: " + val);
                                    if(val > 0)
                                        sale.setValue(sale.getValue() + val);           //выручка
                                    else
                                    {
                                        sale.setCashback(sale.getCashback() - val);     //возврат
                                        cashb_rcrd++;
                                    }
                                }
                                else if(r.getCell(0).getStringCellValue().startsWith("Итого по:"))
                                {
                                    //log.debug("finished shop record, i is: " + i);
                                    sale.setChequeCount(rcrd - cashb_rcrd - 1);
                                    shopFinished = true;
                                }
                                
                                rcrd++;
                            }
                            
                            parsedFileMap.put(name, sale);
                            //Double value = row.getCell(colNumber).getNumericCellValue();
                            //parsedFileMap.put(name, value);
                            //log.debug("Recieved file map: " + parsedFileMap);
                        }
                        
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
                        log.warn("Дата не найдена в файле " + file.getName());
                    }
                    if (!colFound)
                    {
                        log.warn("Колонка с данными не найдена в файле " + file.getName());
                    }
                    log.error("Невозможно обработать файл " + file.getName());
                }

            }
            catch (Exception e)
            {
                //e.printStackTrace();
                log.error("Невозможно обработать файл, возникла ошибка: " + e.getMessage());
            }

        } // foreach file in path

        return result;
    }   // getSalesMap()

    @Override
    public void processSales(List<Shop> shops)
    {
        emailExtractor.saveAllAttachment();

        Map<LocalDate, Map<String, Sales>> processMap = getSalesMap();

        if (!processMap.isEmpty())
        {
            for(Entry<LocalDate, Map<String, Sales>> entry : processMap.entrySet())
            {
                LocalDate date = entry.getKey();
                Map<String, Sales> valuesMap = entry.getValue();
                
                for (Shop shop : shops)
                {
                    Sales saleFromMap = valuesMap.get(shop.getDominoName());
                    if(saleFromMap != null)
                    {
                        SalesMeta sm = salesService.getByShopAndDate(shop, date.withDayOfMonth(1), date.dayOfMonth().withMaximumValue());
                        Sales sales = sm.getByDate(date);
                        sales.setValue(saleFromMap.getValue());
                        sales.setCashback(saleFromMap.getCashback());
                        sales.setChequeCount(saleFromMap.getChequeCount());
                        salesService.update(sm);
                    }
                    else
                    {
                        log.warn("Нет данных для загрузки по магазину " + shop.getName() + ", нет данных за " + date.toString("dd.MM.YYYY"));
                        //log.warn("Иия Домино: " + shop.getDominoName());
                    }
                }
            }
            
            log.info("Процесс загрузки выручек завершен");
        }
        else
        {
            log.info("Не найдено выручек для загрузки");
        }
        
    }
}
