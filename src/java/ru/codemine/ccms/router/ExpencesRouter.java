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
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.codemine.ccms.entity.ExpenceType;
import ru.codemine.ccms.entity.SalesMeta;
import ru.codemine.ccms.entity.Shop;
import ru.codemine.ccms.forms.ExpenceTypesForm;
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
        if(dateYear == null) dateYear = LocalDate.now().toString("YYYY");
        
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.YYYY");
        LocalDate startDate = formatter.parseLocalDate("01.01." + dateYear);
        LocalDate endDate = formatter.parseLocalDate("31.12." + dateYear);
        
        Shop shop = shopService.getById(shopid);
        
        List<ExpenceType> allExpTypes = expenceTypeService.getAll();
        Set<ExpenceType> addedExpTypes = salesService.getExpenceTypesByPeriod(shop, startDate, endDate);
                
        model.addAllAttributes(utils.prepareModel("Установка расходов по магазину - " + shop.getName() + " - ИнфоПортал", "shops", "expences"));
        model.addAttribute("shop", shop);
        model.addAttribute("selectedYear", dateYear);
        model.addAttribute("yearList", utils.getYearStrings());
        model.addAttribute("allExpTypes", allExpTypes);
        model.addAttribute("addedExpTypes", addedExpTypes);
        model.addAttribute("expenceTypesForm", new ExpenceTypesForm());
        
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
            record.put("expencetype", type.getName());
            record.put("expencenote", type.getComment());
            record.put("totals", salesService.getTotalExpenceValueForPeriod(shop, startDate, endDate, type));
            records.add(record);
        }
        
        
        return records;
    }
    
    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "/expences/addtype", method = RequestMethod.POST)
    public String addExpenceTypeToShop(
                                    @RequestParam Integer shopid, 
                                    @RequestParam String dateYear,
                                    @ModelAttribute ExpenceTypesForm expenceTypesForm)
    {
        Shop shop = shopService.getById(shopid);
        
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.YYYY");
        LocalDate startDate = formatter.parseLocalDate("01.01." + dateYear);
        LocalDate endDate = formatter.parseLocalDate("31.01." + dateYear);
        
        SalesMeta januarySalesMeta = salesService.getByShopAndDate(shop, startDate, endDate);
        
        for(ExpenceType type : expenceTypesForm.getTypes())
        {
            if(!januarySalesMeta.getExpences().containsKey(type))
                januarySalesMeta.getExpences().put(type, 0.0);
        }
        
        salesService.update(januarySalesMeta);
        
        return "redirect:/expences?shopid=" + shop.getId() + "&dateYear=" + dateYear;
    }
    
    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "/expences/savecell", method = RequestMethod.POST)
    public @ResponseBody String expencesSavecell( HttpServletRequest request,
                                    @RequestParam Integer shopid, 
                                    @RequestParam String dateYear)
    {
        Shop shop = shopService.getById(shopid);
        
        String expenceTypeName = request.getParameterMap().get("extypename")[0];
        if(expenceTypeName == null) return "{\"result\": \"error\"}";
        
        ExpenceType expenceType = expenceTypeService.getByName(expenceTypeName);
        if(expenceType == null) return "{\"result\": \"error\"}";
        
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.M.YYYY");
        
        for(Map.Entry<String, String[]> entry : request.getParameterMap().entrySet())
        {
            if(entry.getKey().startsWith("c"))
            {
                String cellname = entry.getKey();
                String cellval = entry.getValue()[0];
                if(cellval == null) return "{\"result\": \"error\"}";
                
                LocalDate startDate = formatter.parseLocalDate("01." + cellname.substring(1) + "." + dateYear);
                LocalDate endDate = startDate.dayOfMonth().withMaximumValue();
                
                Double value = Double.parseDouble(cellval);
                
                SalesMeta sm = salesService.getByShopAndDate(shop, startDate, endDate);
                sm.getExpences().put(expenceType, value);
                salesService.update(sm);
            }
        }
        
        
        return "{\"result\": \"success\"}";
    }
    
}
