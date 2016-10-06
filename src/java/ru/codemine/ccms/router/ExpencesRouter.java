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

package ru.codemine.ccms.router;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.codemine.ccms.entity.ExpenceType;
import ru.codemine.ccms.entity.SalesMeta;
import ru.codemine.ccms.entity.Shop;
import ru.codemine.ccms.service.ExpenceTypeService;
import ru.codemine.ccms.service.SalesService;
import ru.codemine.ccms.service.ShopService;
import ru.codemine.ccms.utils.Utils;

/**
 *
 * @author Alexander Savelev
 */

@Controller
public class ExpencesRouter 
{
    private static final Logger log = Logger.getLogger("ExpencesRouter");
    
    @Autowired private ShopService shopService;
    @Autowired private SalesService salesService;
    @Autowired private ExpenceTypeService expenceTypeService;
    @Autowired private Utils utils;
    
    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "/expences", method = RequestMethod.GET)
    public String getExpencesPage(@RequestParam(required = true)  Integer shopid, 
                                  @RequestParam(required = false) String dateYear,
                                  ModelMap model)
    {
        Shop shop = shopService.getById(shopid);
        
        model.addAttribute("selectedYear", dateYear == null ? LocalDate.now().toString("YYYY") : dateYear);
        
        model.addAllAttributes(utils.prepareModel("Установка расходов по магазину - " + shop.getName() + " - ИнфоПортал", "shops", "expences"));
        model.addAttribute("shop", shop);
        model.addAttribute("dateYear", dateYear);
        model.addAttribute("yearList", utils.getYearStrings());
        
        return "pages/shops/expences";
    }
    
    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "/expences/data", method = RequestMethod.GET)
    public @ResponseBody List<Map<String, Object>> getExpencesData(
                                    @RequestParam Integer shopid, 
                                    @RequestParam String dateYear)
    {
        List<Map<String, Object>> records = new ArrayList<>(); 
        Shop shop = shopService.getById(shopid);
        
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.YYYY");
        LocalDate startDate = formatter.parseLocalDate("01.01." + dateYear);
        LocalDate endDate = formatter.parseLocalDate("31.12." + dateYear);
        
        List<SalesMeta> smList = salesService.getByPeriod(shop, startDate, endDate);
        Set<ExpenceType> expenceTypes = salesService.getExpenceTypesByPeriod(shop, startDate, endDate);
        
        for(ExpenceType type : expenceTypes)
        {
            Map<String, Object> record = new HashMap<>();
            for(SalesMeta sm : smList)
            {
                Double val = sm.getExpences().get(type);
                String month = sm.getStartDate().toString("M");
                record.put("c" + month, val);
            }
            record.put("expencetype", type.getDescription());
            record.put("totals", salesService.getTotalExpenceValueForPeriod(shop, startDate, endDate, type));
            records.add(record);
        }
        
        
        return records;
    }
    
}