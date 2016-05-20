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
import java.util.Map.Entry;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
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
import ru.codemine.ccms.entity.Counter;
import ru.codemine.ccms.entity.Sales;
import ru.codemine.ccms.entity.SalesMeta;
import ru.codemine.ccms.entity.Shop;
import ru.codemine.ccms.sales.SalesLoader;
import ru.codemine.ccms.service.CounterService;
import ru.codemine.ccms.service.SalesService;
import ru.codemine.ccms.service.ShopService;
import ru.codemine.ccms.utils.Utils;

/**
 *
 * @author Alexander Savelev
 */
@Controller
public class ReportsRouter //TODO this class NEEDS refactoring
{

    private static final Logger log = Logger.getLogger("ReportsRouter");

    @Autowired private ShopService shopService;
    @Autowired private CounterService counterService;
    @Autowired private SalesService salesService;
    @Autowired private SalesLoader salesLoader;
    @Autowired private Utils utils;

    @Secured("ROLE_USER")
    @RequestMapping(value = "/reports/sales-pass", method = RequestMethod.GET)
    public String getSalesPassReport(
            @RequestParam(required = false) String dateMonth,
            @RequestParam(required = false) String dateYear,
            ModelMap model)
    {

        model.addAllAttributes(utils.prepareModel("Отчет по проходимости и продажам - ИнфоПортал", 
                "reports", "general", 
                dateMonth, dateYear));
        
        LocalDate selectedDate;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMM YYYY");
        if (dateMonth != null && dateYear != null) 
        {
            selectedDate = formatter.parseLocalDate("01 " + dateMonth + " " + dateYear);
        } else 
        {
            selectedDate = LocalDate.now().withDayOfMonth(1);
        }
        
        List<String> subgridColNames = new ArrayList<>();
        subgridColNames.add("Сведения");
        for(int i = 1; i <= selectedDate.dayOfMonth().withMaximumValue().getDayOfMonth(); i++)
        {
            subgridColNames.add(String.valueOf(i) + selectedDate.toString(".MM.YY"));
        }
        subgridColNames.add("Итого");
        
        model.addAttribute("subgridColNames", subgridColNames);
        
        return "reports/sales-pass";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/reports/graph/sales-pass", method = RequestMethod.GET)
    public String getSalesPassGraph(
            @RequestParam(required = false) String dateMonth,
            @RequestParam(required = false) String dateYear,
            @RequestParam(required = false) Integer shopid,
            ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel("Графики проходимости и продаж - ИнфоПортал", 
                "reports", "graph", 
                dateMonth, dateYear));
        
        List<Shop> shopList = shopService.getAllOpen();
        List<String> graphDataDayTotal = new ArrayList<>();
        List<String> graphDataPassability = new ArrayList<>();
        List<String> graphDataDayTotalStack = new ArrayList<>();
        List<String> graphDataPlan = new ArrayList<>();

        Shop shop;

        if (shopid == null)
        {
            shop = shopList.get(0);
        } else
        {
            shop = shopService.getById(shopid);
        }

        model.addAttribute("shop", shop);
        model.addAttribute("shopList", shopList);

        LocalDate selectedStartDate;
        LocalDate selectedEndDate;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMM YYYY");
        if (dateMonth != null && dateYear != null) //Период задан пользователем
        {
            selectedStartDate = formatter.parseLocalDate("01 " + dateMonth + " " + dateYear);
            selectedEndDate = selectedStartDate.dayOfMonth().withMaximumValue();
        } else //Период - текущий месяц, по умолчанию
        {
            selectedStartDate = LocalDate.now().withDayOfMonth(1);
            selectedEndDate = selectedStartDate.dayOfMonth().withMaximumValue();
        }

        SalesMeta salesMeta = salesService.getByShopAndDate(shop, selectedStartDate, selectedEndDate);
        Double dayTotalStack = 0.0;
        for (Sales sales : salesMeta.getSales())
        {
            if (shop.isCountersEnabled())
            {
                Counter counter = counterService.getByShopAndDate(shop, sales.getDate().toDateTime(LocalTime.MIDNIGHT));
                sales.setPassability(counter == null ? 0 : counter.getIn());
            }

            dayTotalStack += sales.getDayTotal();

            graphDataDayTotal.add(sales.getGraphDataDayTotal());
            graphDataPassability.add(sales.getGraphDataPassability());
            //move below lines to SalesMeta?
            graphDataDayTotalStack.add("[\"" + sales.getDate().toString("dd.MM.yyyy") + "\", " + dayTotalStack + "]");
            graphDataPlan.add("[\"" + sales.getDate().toString("dd.MM.yyyy") + "\", " + salesMeta.getPlan() + "]");
        }

        model.addAttribute("salesMeta", salesMeta);
        model.addAttribute("graphDataDayTotal", graphDataDayTotal);
        model.addAttribute("graphDataPassability", graphDataPassability);
        model.addAttribute("graphDataDayTotalStack", graphDataDayTotalStack);
        model.addAttribute("graphDataPlan", graphDataPlan);

        return "reports/sales-pass-graph";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/forceSalesAutoload", method = RequestMethod.POST)
    public String forceSalesAutoload(ModelMap model)
    {
        salesLoader.processSales(shopService.getAllOpen());
        return "redirect:/reports/sales-pass";
    }
    
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/reports/sales-pass/data")
    public @ResponseBody List<Map<String, Object>> getSalesPassDataJson(
            @RequestParam(required = false) String dateMonth,
            @RequestParam(required = false) String dateYear,ModelMap model)
    {
        List<Map<String, Object>> resultList = new ArrayList<>();
        
        LocalDate selectedStartDate;
        LocalDate selectedEndDate;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMM YYYY");
        if (dateMonth != null && dateYear != null) //Период задан пользователем
        {
            selectedStartDate = formatter.parseLocalDate("01 " + dateMonth + " " + dateYear);
            selectedEndDate = selectedStartDate.dayOfMonth().withMaximumValue();
        } else //Период - текущий месяц, по умолчанию
        {
            selectedStartDate = LocalDate.now().withDayOfMonth(1);
            selectedEndDate = selectedStartDate.dayOfMonth().withMaximumValue();
        }

        List<Shop> shopList = shopService.getAllOpen();
        
        //Map<Shop, SalesMeta> shopSalesMap = utils.getShopSalesMap(dateMonth, dateYear);
        
        for(Shop shop : shopList)
        {
            Map<String, Object> data = new HashMap();
            SalesMeta sm = salesService.getByShopAndDate(shop, selectedStartDate, selectedEndDate);
            if (shop.isCountersEnabled())
            {
                for (Sales sales : sm.getSales())
                {
                    Counter counter = counterService.getByShopAndDate(shop, sales.getDate().toDateTime(LocalTime.MIDNIGHT));
                    sales.setPassability(counter == null ? 0 : counter.getIn());
                }
            }
            
            data.put( "shopname",      shop.getName());
            data.put( "passability",   sm.getPassabilityTotals());
            data.put( "cheque",        sm.getChequeTotals());
            data.put( "value",         sm.getValueTotals());
            data.put( "cashback",      sm.getCashbackTotals());
            data.put( "periodtotal",   sm.getPeriodTotals());
            data.put( "midPrice",      sm.getChequeTotals() == 0 ? 0 : sm.getPeriodMidPrice());
            data.put( "plan",          sm.getPlan());
            data.put( "plancoverage",  sm.getPlan() == 0 ? 0 : sm.getPlanCoverage());
            
            resultList.add(data);
        }
        
        
        return resultList;
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/reports/sales-pass/details")
    public @ResponseBody List<Map<String, Object>> getSalesPassDetailsJson(
            @RequestParam String dateMonth,
            @RequestParam String dateYear,
            @RequestParam String shopname,
            ModelMap model)
    {
        List<Map<String, Object>> resultList = new ArrayList<>();
        
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMM YYYY");
        LocalDate startDate = formatter.parseLocalDate("01 " + dateMonth + " " + dateYear);
        
        Integer daysCount = startDate.dayOfMonth().withMaximumValue().getDayOfMonth();
        
        Shop shop = shopService.getByName(shopname);
        SalesMeta salesMeta = salesService.getByShopAndDate(shop, startDate, startDate.dayOfMonth().withMaximumValue());
        
        List<String> recordNames = new ArrayList<>();
        recordNames.add("Проходимость");
        recordNames.add("Кол-во покупок");
        recordNames.add("Отчет ККМ");
        recordNames.add("Возвраты");
        recordNames.add("Выручка");
        recordNames.add("Средний чек");
        
        Integer counterTotals = 0;
        
        for(int row = 1; row <= recordNames.size(); row++)
        {
            Map<String, Object> record = new HashMap();
            record.put("info", recordNames.get(row - 1));
            
            for(int cell = 1; cell <= daysCount; cell++)
            {
                String cellname = "c" + String.valueOf(cell);
                Sales sale = salesMeta.getByDate(startDate.withDayOfMonth(cell));
                switch(row)
                {
                    case 1:                         //Проходимость
                        if(shop.isCountersEnabled())
                        {
                            Counter counter = counterService.getByShopAndDate(shop, startDate.withDayOfMonth(cell).toDateTime(LocalTime.MIDNIGHT));
                            Integer counterValue = counter == null ? 0 : counter.getIn();
                            record.put(cellname, counterValue);
                            counterTotals += counterValue;
                            if (cell == daysCount) record.put("totals", counterTotals); 
                        }
                        else
                        {
                            record.put(cellname, sale.getPassability());
                            counterTotals += sale.getPassability();
                            if (cell == daysCount) record.put("totals", counterTotals); 
                        }
                        break;
                    case 2:                         //Кол-во покупок
                        record.put(cellname, sale.getChequeCount());
                        if (cell == daysCount) record.put("totals", salesMeta.getChequeTotals());
                        break;
                    case 3:                         //Отчет ККМ
                        record.put(cellname, sale.getValue());
                        if (cell == daysCount) record.put("totals", salesMeta.getValueTotals());
                        break;
                    case 4:                         //Возвраты
                        record.put(cellname, sale.getCashback());
                        if (cell == daysCount) record.put("totals", salesMeta.getCashbackTotals());
                        break;
                    case 5:                         //Выручка
                        record.put(cellname, sale.getDayTotal());
                        if (cell == daysCount) record.put("totals", salesMeta.getPeriodTotals());
                        break;
                    case 6:                         //Средний чек
                        Integer chequeCount = sale.getChequeCount();
                        Double midPrice = chequeCount == 0 ? 0 : sale.getMidPrice();
                        record.put(cellname, midPrice);
                        if (cell == daysCount) record.put("totals", salesMeta.getPeriodMidPrice());
                        break;
                }
            }
            
            
            
            resultList.add(record);
        }
        
        return resultList;
    }

}
