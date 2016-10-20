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
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String getExpencesPage(@RequestParam(required = false)  Integer shopid, 
                                  @RequestParam(required = false) String dateYear,
                                  @RequestParam(required = false) String mode,
                                  ModelMap model)
    {
        if(dateYear == null) dateYear = LocalDate.now().toString("YYYY");
        if(shopid == null) shopid = shopService.getAll().get(0).getId();
        
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.YYYY");
        LocalDate startDate = formatter.parseLocalDate("01.01." + dateYear);
        LocalDate endDate = formatter.parseLocalDate("31.12." + dateYear);
        
        Shop shop = shopService.getById(shopid);
        List<Shop> shopList = shopService.getAllOpen();
        
        List<ExpenceType> allExpTypes = expenceTypeService.getAll();
        Set<ExpenceType> addedExpTypes = salesService.getExpenceTypesByPeriod(shop, startDate, endDate);
                
        model.addAllAttributes(utils.prepareModel());
        model.addAttribute("shop", shop);
        model.addAttribute("shopList", shopList);
        model.addAttribute("selectedYear", dateYear);
        model.addAttribute("yearList", utils.getYearStrings());
        model.addAttribute("allExpTypes", allExpTypes);
        model.addAttribute("addedExpTypes", addedExpTypes);
        model.addAttribute("expenceTypesForm", new ExpenceTypesForm());
        
        return "print".equals(mode) ? "printforms/expences" : "pages/shops/expences";
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
    
    
    //       //
    // Отчет //
    //       //
    
    
    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "/reports/expences", method = RequestMethod.GET)
    public String getExpencesReportPage(ModelMap model, @RequestParam(required = false) String dateYear)
    {
        if(dateYear == null) dateYear = LocalDate.now().toString("YYYY");

        model.addAllAttributes(utils.prepareModel());
        model.addAttribute("selectedYear", dateYear);
        model.addAttribute("yearList", utils.getYearStrings());

        return "/reports/expences";
        
    }
    
    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "/reports/expences/data", method = RequestMethod.GET)
    public @ResponseBody List<Map<String, Object>> getExpencesReportData(@RequestParam String dateYear)
    {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.YYYY");
        LocalDate startDate = formatter.parseLocalDate("01.01." + dateYear);
        LocalDate endDate = formatter.parseLocalDate("31.12." + dateYear);
        
        List<Shop> shopList = shopService.getAllOpen();
        List<Map<String, Object>> recordList = new ArrayList<>();
        
        for(Shop shop : shopList)
        {
            Map<String, Object> record = new HashMap<>();
            
            Double sales = salesService.getSalesValueByPeriod(shop, startDate, endDate);
            Double expences = salesService.getTotalExpenceValueForPeriod(shop, startDate, endDate);
            Double cleanSales = (sales == null || expences == null) ? 0 : sales - expences;
            Double midExpences = salesService.getMidExpences(shop, 6);
            Double midCleanSales = salesService.getMidCleanSales(shop, 6);
            
            record.put("shopname", shop.getName());
            record.put("sales", sales);
            record.put("expences", expences);
            record.put("cleanSales", cleanSales);
            record.put("midExpences", midExpences);
            record.put("midCleanSales", midCleanSales);
            
            recordList.add(record);
        }
        
        return recordList;
    }
    
    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "/reports/expences/details", method = RequestMethod.GET)
    public @ResponseBody List<Map<String, Object>> getExpencesReportDetails(
            @RequestParam String dateYear, 
            @RequestParam String shopname)
    {
        List<Map<String, Object>> records = new ArrayList<>(); 
        Shop shop = shopService.getByName(shopname);
        
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.YYYY");
        LocalDate startDate = formatter.parseLocalDate("01.01." + dateYear);
        LocalDate endDate = formatter.parseLocalDate("31.12." + dateYear);
        
        List<SalesMeta> smList = salesService.getByPeriod(shop, startDate, endDate);
        Set<ExpenceType> expenceTypes = salesService.getExpenceTypesByPeriod(shop, startDate, endDate);
        
        Map<String, Object> totalExpences = new HashMap<>();
        Map<String, Object> totalSales = new HashMap<>();
        Map<String, Object> totalCleanSales = new HashMap<>();
        
        for(ExpenceType type : expenceTypes)
        {
            Map<String, Object> record = new HashMap<>();
            for(SalesMeta sm : smList)
            {
                Double val = sm.getExpences().get(type);
                String month = sm.getStartDate().toString("M");
                record.put("c" + month, val);
                
                totalExpences.put("c" + month, sm.getExpencesTotal());
                totalSales.put("c" + month, sm.getSalesTotal());
                totalCleanSales.put("c" + month, sm.getSalesTotal() - sm.getExpencesTotal());
            }
            record.put("expencetype", type.getName());
            record.put("expencenote", type.getComment());
            record.put("totals", salesService.getTotalExpenceValueForPeriod(shop, startDate, endDate, type));
            records.add(record);
        }
        
        
        totalExpences.put("expencetype", "ИТОГО:");
        totalSales.put("expencetype", "Выручка");
        totalCleanSales.put("expencetype", "Чистая прибыль");
        
        Double totalExpencesValue = salesService.getTotalExpenceValueForPeriod(shop, startDate, endDate);
        Double totalSalesValue = salesService.getSalesValueByPeriod(shop, startDate, endDate);
        totalExpences.put("totals", totalExpencesValue);
        totalSales.put("totals", totalSalesValue);
        totalCleanSales.put("totals", totalSalesValue - totalExpencesValue);
        
        records.add(totalExpences);
        records.add(totalSales);
        records.add(totalCleanSales);
        
        return records;
    }
    
    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "/reports/graph/expences")
    public String getExpencesGraph(
            @RequestParam(required = false) Integer shopid, 
            @RequestParam(required = false) String dateStartStr,
            @RequestParam(required = false) String dateEndStr,
            ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel());
        
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.YYYY");
        LocalDate dateStart = dateStartStr == null 
                ? LocalDate.now().withMonthOfYear(1).withDayOfMonth(1)
                : formatter.parseLocalDate(dateStartStr).withDayOfMonth(1);
        LocalDate dateEnd = dateEndStr == null 
                ? LocalDate.now().dayOfMonth().withMaximumValue() 
                : formatter.parseLocalDate(dateEndStr).dayOfMonth().withMaximumValue();
        
        if(dateEnd.isBefore(dateStart)) dateEnd = dateStart;
        
        model.addAttribute("dateStartStr", dateStart.toString("dd.MM.YYYY"));
        model.addAttribute("dateEndStr", dateEnd.toString("dd.MM.YYYY"));
        
        List<Shop> shopList = shopService.getAllOpen();
        List<String> graphDataSalesTotal = new ArrayList<>();
        List<String> graphDataExpencesTotal = new ArrayList<>();
        
        Shop shop = shopid == null ? shopList.get(0) : shopService.getById(shopid);
        
        model.addAttribute("shop", shop);
        model.addAttribute("shopList", shopList);
        
        List<SalesMeta> smList = salesService.getByPeriod(shop, dateStart, dateEnd);
        
        for (SalesMeta sm : smList)
        {

            graphDataSalesTotal.add(sm.getGraphDataSalesTotal());
            graphDataExpencesTotal.add(sm.getGraphDataExpencesTotal());
        }

        model.addAttribute("graphDataSalesTotal", graphDataSalesTotal);
        model.addAttribute("graphDataExpencesTotal", graphDataExpencesTotal);

        return "reports/expences-graph";
    }
    
    
    //                 //
    //  Виды расходов  //
    //                 //
    
    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "/management/expencetypes", method = RequestMethod.GET)
    public String getExpenceTypesPage(ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel());
        model.addAttribute("allExpenceTypes", expenceTypeService.getAll());
        model.addAttribute("expenceTypeFrm", new ExpenceType());
        
        return "management/expencetypes";
    }
    
    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "/management/expencetypes", method = RequestMethod.POST)
    public String addExpenceType(@Valid @ModelAttribute("expenceTypeFrm") ExpenceType expenceType,
            BindingResult result,
            ModelMap model)
    {
        if(result.hasErrors())
        {
            model.addAllAttributes(utils.prepareModel());
            model.addAttribute("allExpenceTypes", expenceTypeService.getAll());
        
            return "management/expencetypes";
        }
        
        expenceTypeService.create(expenceType);
        
        return "redirect:/management/expencetypes";
    }
    
    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "management/expencetypes/edit", method = RequestMethod.GET)
    public String getExpenceTypeEditPage(@RequestParam("id") Integer id, ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel());
        model.addAttribute("expenceType", expenceTypeService.getById(id));
        
        return "management/expencetypeedit";
    }
    
    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "management/expencetypes/edit", method = RequestMethod.POST)
    public String expenceTypeEdit(@Valid @ModelAttribute ExpenceType type, 
            BindingResult result,
            ModelMap model)
    {
        if(result.hasErrors())
        {
            model.addAllAttributes(utils.prepareModel());
            
            return "management/expencetypeedit";
        }
        
        expenceTypeService.update(type);
        
        return "redirect:/management/expencetypes";
    }
    
    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "/management/expencetypes/delete", method = RequestMethod.POST)
    public String deleteExpenceType(@RequestParam("id") Integer id)
    {
        expenceTypeService.deleteById(id);
        
        return "redirect:/management/expencetypes";
    }
}
