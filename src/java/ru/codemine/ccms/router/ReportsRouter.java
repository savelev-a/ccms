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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.codemine.ccms.service.ShopService;
import ru.codemine.ccms.utils.Utils;

/**
 *
 * @author Alexander Savelev
 */
@Controller
public class ReportsRouter 
{

    private static final Logger log = Logger.getLogger("ReportsRouter");

    @Autowired private ShopService shopService;
    @Autowired private Utils utils;

    
    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "/reports/shopproviders", method = RequestMethod.GET)
    public String getShopProvidersReport(ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel("Отчет по провайдерам - ИнфоПортал", "reports", ""));
        model.addAttribute("allshops", shopService.getAll());
        
        return "reports/shopprov";
    }
//    
//    @Secured("ROLE_OFFICE")
//    @RequestMapping(value = "/reports/expences", method = RequestMethod.GET)
//    public String getExpencesReport(ModelMap model,
//            @RequestParam(required = false) String dateMonth,
//            @RequestParam(required = false) String dateYear, 
//            @RequestParam(required = false) String mode)
//    {
//        model.addAllAttributes(utils.prepareModel("Отчет по расходам - ИнфоПортал", "reports", "all", dateMonth, dateYear));
//        model.addAttribute("allshops", shopService.getAll());
//        
//        LocalDate selectedDate;
//        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMM YYYY");
//        if (dateMonth != null && dateYear != null) 
//        {
//            selectedDate = formatter.parseLocalDate("01 " + dateMonth + " " + dateYear);
//        } else 
//        {
//            selectedDate = LocalDate.now().withDayOfMonth(1);
//        }
//        
//        List<String> subgridColNames = new ArrayList<>();
//        subgridColNames.add("Тип расходов");
//        for(int i = 1; i <= selectedDate.dayOfMonth().withMaximumValue().getDayOfMonth(); i++)
//        {
//            subgridColNames.add(String.valueOf(i) + selectedDate.toString(".MM.YY"));
//        }
//        subgridColNames.add("Итого");
//        
//        model.addAttribute("subgridColNames", subgridColNames);
//        
//        return "reports/expences";
//    }
//    
//    
//    @Secured("ROLE_OFFICE")
//    @RequestMapping(value = "/reports/expences/data")
//    public @ResponseBody List<Map<String, Object>> getExpencesDataJson(
//            @RequestParam(required = false) String dateMonth,
//            @RequestParam(required = false) String dateYear,ModelMap model)
//    {
//        List<Map<String, Object>> resultList = new ArrayList<>();
//        
//        LocalDate selectedStartDate;
//        LocalDate selectedEndDate;
//        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMM YYYY");
//        if (dateMonth != null && dateYear != null) //Период задан пользователем
//        {
//            selectedStartDate = formatter.parseLocalDate("01 " + dateMonth + " " + dateYear);
//            selectedEndDate = selectedStartDate.dayOfMonth().withMaximumValue();
//        } else //Период - текущий месяц, по умолчанию
//        {
//            selectedStartDate = LocalDate.now().withDayOfMonth(1);
//            selectedEndDate = selectedStartDate.dayOfMonth().withMaximumValue();
//        }
//
//        List<Shop> shopList = shopService.getAllOpen();
//        
//        for(Shop shop : shopList)
//        {
//            Map<String, Object> data = new HashMap();
//            
//            SalesMeta sm = salesService.getByShopAndDate(shop, selectedStartDate, selectedEndDate);
//                       
//            data.put( "shopname",       shop.getName());
//            data.put( "sales",          sm.getPeriodTotals());
//            data.put( "expencesRec",    expenceService.getRecurrentExpencesValueForMonth(shop, selectedEndDate));
//            data.put( "expencesOne",    expenceService.getOneshotExpencesValueForMonth(shop, selectedEndDate));
//            data.put( "expencesTotal",  expenceService.getExpencesValueForMonth(shop, selectedEndDate));
//            data.put( "cleanSales",     sm.getPeriodTotals() - expenceService.getExpencesValueForMonth(shop, selectedEndDate));
//            data.put( "plan",           sm.getPlan());  
//            data.put( "plancoverage",   sm.getPlan() == 0 ? 0 : sm.getPlanCoverage());
//           
//            resultList.add(data);
//        }
//        
//        
//        return resultList;
//    }
//    
//    @Secured("ROLE_OFFICE")
//    @RequestMapping(value = "/reports/expences/details")
//    public @ResponseBody List<Map<String, Object>> getExpensesDetailsJson(
//            @RequestParam String dateMonth,
//            @RequestParam String dateYear,
//            @RequestParam String shopname,
//            ModelMap model)
//    {
//        List<Map<String, Object>> resultList = new ArrayList<>();
//        
//        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMM YYYY");
//        LocalDate startDate = formatter.parseLocalDate("01 " + dateMonth + " " + dateYear);
//        
//        Integer daysCount = startDate.dayOfMonth().withMaximumValue().getDayOfMonth();
//        
//        Shop shop = shopService.getByName(shopname);
//        SalesMeta salesMeta = salesService.getByShopAndDate(shop, startDate, startDate.dayOfMonth().withMaximumValue());
//        
//        List<String> recordNames = new ArrayList<>();
//        List<ExpenceType> expenceTypes = expenceTypeService.getAll();
//        for(ExpenceType et : expenceTypes)
//        {
//            recordNames.add(et.getDescription());
//        }
//        recordNames.add("Итого расходов");
//        recordNames.add("Выручка");
//        recordNames.add("Чистая прибыль");
//        
//        for(int row = 1; row <= recordNames.size(); row++)
//        {
//            Map<String, Object> record = new HashMap();
//            record.put("info", recordNames.get(row - 1));
//            
//            // Среднее значение периодических расходов по типу
//            Double expencesByTypeVal = (row < recordNames.size() - 2) 
//                    ? expenceService.getExpencesMidValueForMonth(shop, startDate, expenceTypes.get(row - 1))
//                    : 0.0;
//            
//            for(int cell = 1; cell <= daysCount; cell++)
//            {
//                LocalDate date = startDate.withDayOfMonth(cell);
//                String cellname = "c" + String.valueOf(cell);
//                Sales sale = salesMeta.getByDate(date);
//                
//                if(row < recordNames.size() - 2)                        //Строки с типами расходов
//                {
//                    record.put(cellname, expencesByTypeVal); 
//                    if (cell == daysCount) record.put("totals", expenceService.getExpencesValueForMonth(shop, startDate, expenceTypes.get(row - 1)));
//                }
//                else if(row == recordNames.size() - 2)                  //Строка Итого расходов
//                {
//                    Double total = 0.0;
//                    for(int i = 0; i < expenceTypes.size(); i++)
//                    {
//                        Map<String, Object> r = resultList.get(i);
//                        Double val = (Double)r.get(cellname);
//                        if(val != null) total += val;
//                    }
//                    record.put(cellname, total);
//                    if (cell == daysCount) record.put("totals", expenceService.getExpencesValueForMonth(shop, startDate));
//                }
//                else if(row == recordNames.size() - 1)                  //Строка Выручка
//                {
//                    record.put(cellname, sale.getDayTotal());
//                    if (cell == daysCount) record.put("totals", salesMeta.getPeriodTotals());
//                }
//                else if(row == recordNames.size())                      // Строка Чистая прибыль
//                {
//                    Double salesVal = (Double)resultList.get(row - 2).get(cellname);
//                    Double expVal = (Double)resultList.get(row - 3).get(cellname);
//                    if(salesVal == null) salesVal = 0.0;
//                    if(expVal == null) expVal = 0.0;
//                    Double cleanVal = salesVal - expVal;
//                    
//                    record.put(cellname, cleanVal);
//                    if (cell == daysCount) record.put("totals", salesMeta.getPeriodTotals() - expenceService.getExpencesValueForMonth(shop, startDate));
//                }
//            }
//            
//                
//            resultList.add(record);
//        }
//        
//        return resultList;
//    }
//    
//    @Secured("ROLE_OFFICE")
//    @RequestMapping(value = "/reports/graph/expences", method = RequestMethod.GET)
//    public String getExpencesGraph(
//            @RequestParam(required = false) Integer shopid, 
//            @RequestParam(required = false) String dateYear,
//            ModelMap model)
//    {
//        model.addAllAttributes(utils.prepareModel("Графики выручки и расходов (годовой) - ИнфоПортал", 
//                "reports", "graph", 
//                null, dateYear));
//        
//        List<Shop> shopList = shopService.getAllOpen();
//        
//        List<String> graphDataSalesTotal = new ArrayList<>();
//        List<String> graphDataExpencesTotal = new ArrayList<>();
//
//        Shop shop;
//
//        if (shopid == null)
//        {
//            shop = shopList.get(0);
//        } else
//        {
//            shop = shopService.getById(shopid);
//        }
//
//        model.addAttribute("shop", shop);
//        model.addAttribute("shopList", shopList);
//        
//        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.YYYY");
//        if(dateYear == null) dateYear = LocalDate.now().toString("YYYY");
//        for(int i = 1; i <= 12; i++)
//        {
//            LocalDate monthStartDate = formatter.parseLocalDate("01." + (i > 9 ? i : ("0" + i)) + "." + dateYear);
//            LocalDate monthEndDate = monthStartDate.dayOfMonth().withMaximumValue();
//            
//            SalesMeta salesMeta = salesService.getByShopAndDate(shop, monthStartDate, monthEndDate);
//            Double salesTotal = salesMeta.getPeriodTotals();
//            
//            Double expenceTotal = expenceService.getExpencesValueForMonth(shop, monthStartDate);
//            
//            graphDataSalesTotal.add("[\"" + monthStartDate.toString("MMMM") + "\", " + salesTotal + "]");
//            graphDataExpencesTotal.add("[\"" + monthStartDate.toString("MMMM") + "\", " + expenceTotal + "]");
//        }
//        
//        model.addAttribute("graphDataSalesTotal", graphDataSalesTotal);
//        model.addAttribute("graphDataExpencesTotal", graphDataExpencesTotal);
//
//        return "reports/expences-graph";
//    }
//    

}
