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
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.codemine.ccms.entity.Counter;
import ru.codemine.ccms.entity.Sales;
import ru.codemine.ccms.entity.SalesMeta;
import ru.codemine.ccms.entity.Shop;
import ru.codemine.ccms.exceptions.ResourceNotFoundException;
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
public class SalesRouter
{
    private static final Logger log = Logger.getLogger("SalesRouter");
    
    @Autowired private SalesService salesService;
    @Autowired private ShopService shopService;
    @Autowired private EmployeeService employeeService;
    @Autowired private CounterService counterService;
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/sales", method = RequestMethod.GET)
    public String getSalesPage(
            @RequestParam Integer shopid, 
            @RequestParam(required = false) String dateMonth,
            @RequestParam(required = false) String dateYear,
            ModelMap model)
    {
        Shop shop = shopService.getById(shopid);
        
        model.addAttribute("title", "Выручка магазина - " + shop.getName() + " - ИнфоПортал");
        model.addAttribute("mainMenuActiveItem", "shops");
        model.addAttribute("sideMenuActiveItem", "sales");
        model.addAttribute("currentUser", employeeService.getCurrentUser());
        model.addAttribute("shop", shop);
        
        model.addAttribute("monthList", formMonthValues());
        model.addAttribute("yearList", formYearValues());
        
        model.addAttribute("selectedMonth", dateMonth == null ? LocalDate.now().toString("MMMM") : dateMonth);
        model.addAttribute("selectedYear", dateYear == null? LocalDate.now().toString("YYYY") : dateYear);
        
        LocalDate selectedStartDate;
        LocalDate selectedEndDate;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMM YYYY");
        if(dateMonth != null && dateYear != null) //Период задан пользователем
        {
            selectedStartDate = formatter.parseLocalDate("01 " + dateMonth + " " + dateYear);
            selectedEndDate = selectedStartDate.dayOfMonth().withMaximumValue();
        }
        else //Период - текущий месяц, по умолчанию
        {
            selectedStartDate = LocalDate.now().withDayOfMonth(1);
            selectedEndDate = selectedStartDate.dayOfMonth().withMaximumValue();
        }
        
        SalesMeta salesMeta = salesService.getByShopAndDate(shop, selectedStartDate, selectedEndDate);
        
        //SalesMeta salesMeta;
        //if(tmpSalesMeta == null)
        //{
        //    salesMeta = new SalesMeta(shop, selectedStartDate, selectedEndDate);
        //    salesMeta.setDescription("Таблица выручек: " 
        //            + shop.getName() 
        //            + " (" 
        //            + selectedStartDate.toString("MMMM YYYY")
        //            + ")"
        //    );
        //}
        //else
        //{
        //    salesMeta = tmpSalesMeta;
        //}
        
        if(shop.isCountersEnabled())
        {
            for(Sales s : salesMeta.getSales())
            {
                Counter counter = counterService.getByShopAndDate(shop, s.getDate().toDateTime(LocalTime.MIDNIGHT));
                s.setPassability(counter == null ? 0 : counter.getIn());
            }
        }
        
        
        model.addAttribute("salesMeta", salesMeta);
        
        return "pages/shops/sales";
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/salesUpd", method = RequestMethod.POST)
    public @ResponseBody String updateSales(
            @RequestParam(required = false) Integer smid, 
            @RequestParam(required = false) Integer shopid,
            @RequestBody List<SalesForm> salesForms, 
            ModelMap model)
    {
        //log.info("Recieved sales: smid: " + smid + ", shopid: " + shopid + ", data: " + salesFormList);
        
        if(smid == null && shopid == null)
            throw new ResourceNotFoundException();
        
        String resultStr;
        
        
        
        if(smid != null) // Обновление существующего периода
        {
            SalesMeta salesMeta = salesService.getById(smid);
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.YYYY");
            
            for(SalesForm sf : salesForms)
            {
                //log.info(formatter.parseLocalDate(sf.getDate()));
                Sales sales = salesMeta.getByDate(formatter.parseLocalDate(sf.getDate()));
                //sales.setPassability(sf.getPassability());
                if(salesMeta.getShop().isCountersEnabled())
                {
                    sales.setPassability(0);
                }
                else
                {
                    sales.setPassability(sf.getPassability());
                }
                sales.setChequeCount(sf.getChequeCount());
                sales.setValue(sf.getValue());
                sales.setCashback(sf.getCashback());
            }
            
            salesService.update(salesMeta);
            
            resultStr = "{\"result\": \"success\"}";
        }
        else // Новый период
        {
            Shop shop = shopService.getById(shopid);
            Set<Sales> newSalesSet = new TreeSet<>();
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.YYYY");
            LocalDate startDate = formatter.parseLocalDate(salesForms.get(0).getDate());
            LocalDate endDate = formatter.parseLocalDate(salesForms.get(salesForms.size() - 1).getDate());
            
            for(SalesForm sf : salesForms)
            {
                Sales sales = new Sales(shop, formatter.parseLocalDate(sf.getDate()));
                //sales.setPassability(sf.getPassability());
                if(shop.isCountersEnabled())
                {
                    sales.setPassability(0);
                }
                else
                {
                    sales.setPassability(sf.getPassability());
                }
                sales.setChequeCount(sf.getChequeCount());
                sales.setValue(sf.getValue());
                sales.setCashback(sf.getCashback());
                
                newSalesSet.add(sales);
            }
            
            SalesMeta newSalesMeta = new SalesMeta(shop);
            newSalesMeta.setSales(newSalesSet);
            newSalesMeta.setStartDate(startDate);
            newSalesMeta.setEndDate(endDate);
            newSalesMeta.setDescription("Таблица выручек: " 
                    + shop.getName() 
                    + " (" 
                    + startDate.toString("MMMM YYYY")
                    + ")"
            );
            
            salesService.create(newSalesMeta);
            
            resultStr = "{\"result\": \"success\"}";
        }
        
        return resultStr;
    }
    
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

