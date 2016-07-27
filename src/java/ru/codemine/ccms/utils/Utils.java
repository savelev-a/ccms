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
package ru.codemine.ccms.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.ccms.entity.Counter;
import ru.codemine.ccms.entity.Employee;
import ru.codemine.ccms.entity.Sales;
import ru.codemine.ccms.entity.SalesMeta;
import ru.codemine.ccms.entity.Shop;
import ru.codemine.ccms.service.CounterService;
import ru.codemine.ccms.service.EmployeeService;
import ru.codemine.ccms.service.SalesService;
import ru.codemine.ccms.service.SettingsService;
import ru.codemine.ccms.service.ShopService;
import ru.codemine.ccms.service.TaskService;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class Utils
{
    @Autowired private EmployeeService employeeService;
    @Autowired private ShopService shopService;
    @Autowired private SalesService salesService;
    @Autowired private CounterService counterService;
    @Autowired private TaskService taskService;
    @Autowired private SettingsService settingsService;
    
    /**
     * Заполнение основных полей модели
     * @param title
     * Заголовок веб-страницы
     * @param mainMenuActiveItem
     * Активный пункт главного меню
     * @param sideMenuActiveItem
     * Активный пункт бокового меню
     * @return
     */
    public Map<String, Object> prepareModel(String title, String mainMenuActiveItem, String sideMenuActiveItem)
    {
        Map<String, Object> modelMap = new HashMap();
        modelMap.put("title", title);
        modelMap.put("mainMenuActiveItem", mainMenuActiveItem);
        modelMap.put("sideMenuActiveItem", sideMenuActiveItem);
        Employee currentUser = employeeService.getCurrentUser();
        modelMap.put("currentUser", currentUser);
        modelMap.put("currentDate", DateTime.now().toString("dd.MM.YY"));
        modelMap.put("companyName", settingsService.getCompanyName());
        if(currentUser != null && currentUser.getRoles() != null)
        {
            if (currentUser.getRoles().contains("ROLE_SHOP")) {
                modelMap.put("currentShops", shopService.getCurrentUserShops());
            }
            
            modelMap.put("currentUserActiveTasksCount", taskService.getUserActiveTaskCount(currentUser));
        }
        
        return modelMap;
    }
    
    /**
     *  Заполнение основных полей модели
     * @param title
     * Заголовок веб-страницы
     * @param mainMenuActiveItem
     * Активный пункт главного меню
     * @param sideMenuActiveItem
     * Активный пункт бокового меню
     * @param dateMonth
     * Выбранный месяц
     * @param dateYear
     * Выбранный год
     * @return
     */
    public Map<String, Object> prepareModel(String title, 
            String mainMenuActiveItem, 
            String sideMenuActiveItem,
            String dateMonth,
            String dateYear)
    {
        Map<String, Object> modelMap = prepareModel(title, mainMenuActiveItem, sideMenuActiveItem);
        modelMap.put("monthList", getMonthStrings());
        modelMap.put("yearList", getYearStrings());
        modelMap.put("selectedMonth", dateMonth == null ? LocalDate.now().toString("MMMM") : dateMonth);
        modelMap.put("selectedYear", dateYear == null? LocalDate.now().toString("YYYY") : dateYear);
        
        return modelMap;
    }
    
    /**
     * Возвращает карту магазин-продажи за выбранный период
     * @param dateMonth
     * выбранный месяц
     * @param dateYear
     * выбранный год
     * @return
     */
    public Map<Shop, SalesMeta> getShopSalesMap(String dateMonth, String dateYear)
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
    
    /**
     * Возвращает список названий месяцев
     * @return
     */
    public List<String> getMonthStrings()
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
    
    /**
     * Возвращает список годов
     * @return
     */
    public List<String> getYearStrings()
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
