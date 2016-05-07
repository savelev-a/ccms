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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import ru.codemine.ccms.forms.SalesPlanForm;
import ru.codemine.ccms.service.CounterService;
import ru.codemine.ccms.service.EmployeeService;
import ru.codemine.ccms.service.OrganisationService;
import ru.codemine.ccms.service.SalesService;
import ru.codemine.ccms.service.ShopService;

/**
 *
 * @author Alexander Savelev
 */
@Controller
public class ActionRouter
{

    private static final Logger log = Logger.getLogger("ActionRouter");

    @Autowired
    private ShopService shopService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CounterService counterService;
    @Autowired
    private SalesService salesService;
    @Autowired
    private OrganisationService organisationService;

    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "/actions/setPlan")
    public String getPlanSetupPage(
            @RequestParam(required = false) String dateMonth,
            @RequestParam(required = false) String dateYear,
            ModelMap model)
    {
        model.addAttribute("title", "Установить план продаж - ИнфоПортал");
        model.addAttribute("mainMenuActiveItem", "reports");
        model.addAttribute("sideMenuActiveItem", "plan");
        model.addAttribute("currentUser", employeeService.getCurrentUser());

        model.addAttribute("monthList", formMonthValues());
        model.addAttribute("yearList", formYearValues());

        model.addAttribute("selectedMonth", dateMonth == null ? LocalDate.now().toString("MMMM") : dateMonth);
        model.addAttribute("selectedYear", dateYear == null ? LocalDate.now().toString("YYYY") : dateYear);

        model.addAttribute("organisationList", organisationService.getAll());
        model.addAttribute("salesMap", formShopSalesMap(dateMonth, dateYear));

        return "actions/setPlan";
    }

    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "/actions/setPlanAll", method = RequestMethod.POST)
    public String setPlanAll(
            @RequestParam String dateMonth,
            @RequestParam String dateYear,
            @RequestParam String value,
            ModelMap model)
    {
        String status;
        Double plan = 0.0;
        try
        {
            plan = Double.valueOf(value.replace(",", "."));

            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMM YYYY");
            LocalDate startDate = formatter.parseLocalDate("01 " + dateMonth + " " + dateYear);
            LocalDate endDate = formatter.parseLocalDate("01 " + dateMonth + " " + dateYear).dayOfMonth().withMaximumValue();
            List<Shop> shopList = shopService.getAllOpen();
            for(Shop shop: shopList)
            {
                SalesMeta sm = salesService.getByShopAndDate(shop, startDate, endDate);
                if(sm.getId() == null) salesService.update(sm);
            }
            
            
            if (salesService.updatePlanAll(plan, startDate, endDate))
            {
                status = "ok";
            }
            else
            {
                status = "error-server";
            }

            
        } catch (NumberFormatException e)
        {
            status = "error-all";
        }

        model.addAttribute("title", "Установить план продаж - ИнфоПортал");
        model.addAttribute("mainMenuActiveItem", "reports");
        model.addAttribute("sideMenuActiveItem", "plan");
        model.addAttribute("currentUser", employeeService.getCurrentUser());

        model.addAttribute("monthList", formMonthValues());
        model.addAttribute("yearList", formYearValues());

        model.addAttribute("selectedMonth", dateMonth == null ? LocalDate.now().toString("MMMM") : dateMonth);
        model.addAttribute("selectedYear", dateYear == null ? LocalDate.now().toString("YYYY") : dateYear);

        model.addAttribute("organisationList", organisationService.getAll());
        model.addAttribute("salesMap", formShopSalesMap(dateMonth, dateYear));
        model.addAttribute("status", status);

        return "actions/setPlan";
    }
    
    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "/actions/setPlanByOrg", method = RequestMethod.POST)
    public String setPlanByOrg(
            @RequestParam String dateMonth,
            @RequestParam String dateYear,
            @RequestParam Integer orgId,
            @RequestParam String value,
            ModelMap model)
    {
        String status;
        Double plan = 0.0;
        try
        {
            plan = Double.valueOf(value.replace(",", "."));

            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMM YYYY");
            LocalDate startDate = formatter.parseLocalDate("01 " + dateMonth + " " + dateYear);
            LocalDate endDate = formatter.parseLocalDate("01 " + dateMonth + " " + dateYear).dayOfMonth().withMaximumValue();
            Set<Shop> shopList = organisationService.getById(orgId).getShops();
            for(Shop shop: shopList)
            {
                SalesMeta sm = salesService.getByShopAndDate(shop, startDate, endDate);
                sm.setPlan(plan);
                salesService.update(sm);
                
            }
            status = "ok";
        } 
        catch (NumberFormatException e)
        {
            status = "error-org";
        }
        
        model.addAttribute("title", "Установить план продаж - ИнфоПортал");
        model.addAttribute("mainMenuActiveItem", "reports");
        model.addAttribute("sideMenuActiveItem", "plan");
        model.addAttribute("currentUser", employeeService.getCurrentUser());

        model.addAttribute("monthList", formMonthValues());
        model.addAttribute("yearList", formYearValues());

        model.addAttribute("selectedMonth", dateMonth == null ? LocalDate.now().toString("MMMM") : dateMonth);
        model.addAttribute("selectedYear", dateYear == null ? LocalDate.now().toString("YYYY") : dateYear);

        model.addAttribute("organisationList", organisationService.getAll());
        model.addAttribute("salesMap", formShopSalesMap(dateMonth, dateYear));
        model.addAttribute("status", status);

        return "actions/setPlan";
        
    }
    
    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "/actions/setPlanCustom", method = RequestMethod.POST)
    public @ResponseBody String setPlanByOrg(
            @RequestParam String dateMonth,
            @RequestParam String dateYear,
            @RequestBody List<SalesPlanForm> salesPlanForms)
    {
        String resultStr;

        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMM YYYY");
        LocalDate selectedStartDate = formatter.parseLocalDate("01 " + dateMonth + " " + dateYear);
        LocalDate selectedEndDate = selectedStartDate.dayOfMonth().withMaximumValue();
        
        for(SalesPlanForm spf : salesPlanForms)
        {
            Shop shop = shopService.getByName(spf.getShopname());
            SalesMeta salesMeta = salesService.getByShopAndDate(shop, selectedStartDate, selectedEndDate);
            if(!salesMeta.getPlan().equals(spf.getPlan()))
            {
                salesMeta.setPlan(spf.getPlan());
                salesService.update(salesMeta);
            }
        }
        
        resultStr = "{\"result\": \"success\"}";
        
        return resultStr;
        
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
