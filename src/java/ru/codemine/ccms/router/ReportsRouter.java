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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import ru.codemine.ccms.entity.Counter;
import ru.codemine.ccms.entity.Sales;
import ru.codemine.ccms.entity.SalesMeta;
import ru.codemine.ccms.entity.Shop;
import ru.codemine.ccms.forms.SalesForm;
import ru.codemine.ccms.service.CounterService;
import ru.codemine.ccms.service.EmployeeService;
import ru.codemine.ccms.service.SalesService;
import ru.codemine.ccms.service.ShopService;

/**
 *
 * @author Alexander Savelev
 */
@Controller
public class ReportsRouter //TODO this class needs refactoring
{

    private static final Logger log = Logger.getLogger("ReportsRouter");

    @Autowired
    private ShopService shopService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CounterService counterService;
    @Autowired
    private SalesService salesService;

    @Secured("ROLE_USER")
    @RequestMapping(value = "/reports/sales-pass", method = RequestMethod.GET)
    public String getSalesPassReport(
            @RequestParam(required = false) String dateMonth,
            @RequestParam(required = false) String dateYear,
            @RequestParam(required = false) boolean graph,
            @RequestParam(required = false) Integer shopid,
            ModelMap model)
    {
        
        model.addAttribute("mainMenuActiveItem", "reports");

        model.addAttribute("currentUser", employeeService.getCurrentUser());

        model.addAttribute("monthList", formMonthValues());
        model.addAttribute("yearList", formYearValues());

        model.addAttribute("selectedMonth", dateMonth == null ? LocalDate.now().toString("MMMM") : dateMonth);
        model.addAttribute("selectedYear", dateYear == null ? LocalDate.now().toString("YYYY") : dateYear);

        if (!graph) //Таблица
        {
            model.addAttribute("title", "Отчет по проходимости и продажам - ИнфоПортал");
            model.addAttribute("sideMenuActiveItem", "general");
            model.addAttribute("salesMap", formShopSalesMap(dateMonth, dateYear));
            return "reports/sales-pass";
        } else //графики
        {
            model.addAttribute("title", "Графики проходимости и продаж - ИнфоПортал");
            model.addAttribute("sideMenuActiveItem", "graph");
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

    }

    private Map<Shop, SalesMeta> formShopSalesMap(String dateMonth, String dateYear)
    {
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
        Map<Shop, SalesMeta> salesMap = new LinkedHashMap();
        for (Shop shop : shopList)
        {
            SalesMeta sm = salesService.getByShopAndDate(shop, selectedStartDate, selectedEndDate);

            if (shop.isCountersEnabled())
            {
                for (Sales sales : sm.getSales())
                {
                    Counter counter = counterService.getByShopAndDate(shop, sales.getDate().toDateTime(LocalTime.MIDNIGHT));
                    sales.setPassability(counter == null ? 0 : counter.getIn());
                }
            }
            salesMap.put(shop, sm);
        }

        return salesMap;
    }

    //private List<SalesForm> formGraphSalesList(String dateMonth, String dateYear)
    //{
    //    List<SalesForm> result = new ArrayList<>();
    //    LocalDate selectedStartDate;
    //    LocalDate selectedEndDate;
    //    DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMM YYYY");
    //    if(dateMonth != null && dateYear != null) //Период задан пользователем
    //    {
    //        selectedStartDate = formatter.parseLocalDate("01 " + dateMonth + " " + dateYear);
    //        selectedEndDate = selectedStartDate.dayOfMonth().withMaximumValue();
    //    }
    //    else //Период - текущий месяц, по умолчанию
    //    {
    //        selectedStartDate = LocalDate.now().withDayOfMonth(1);
    //        selectedEndDate = selectedStartDate.dayOfMonth().withMaximumValue();
    //    }
    //    
    //    for(LocalDate date = selectedStartDate; date.isBefore(selectedEndDate); date = date.plusDays(1))
    //    {
    //        Integer counterPassability = counterService.getSumIn(date.toDateTime(LocalTime.MIDNIGHT));
    //        Integer manualPassability = 0;
    //        
    //        
    //        SalesForm sf = new SalesForm();
    //        sf.setDate(date.toString("dd.MM.YYYY"));
    //        sf.setPassability(counterPassability + manualPassability);
    //   }
    //    
    //    return null;
    //}
    private List<String> formMonthValues()
    {
        List<String> result = new ArrayList<>();
        result.add("Январь");
        result.add("Февраль");
        result.add("Март");
        result.add("Апрель");
        result.add("Май");
        result.add("Июнь");
        result.add("Июль");
        result.add("Август");
        result.add("Сентябрь");
        result.add("Октябрь");
        result.add("Ноябрь");
        result.add("Декабрь");

        return result;
    }

    private List<String> formYearValues()
    {
        List<String> result = new ArrayList<>();
        result.add("2015");
        result.add("2016");
        result.add("2017");
        result.add("2018");
        result.add("2019");
        result.add("2020");

        return result;
    }
}
