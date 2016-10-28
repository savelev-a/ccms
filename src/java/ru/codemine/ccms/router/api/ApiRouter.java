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

package ru.codemine.ccms.router.api;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.codemine.ccms.entity.Sales;
import ru.codemine.ccms.entity.SalesMeta;
import ru.codemine.ccms.entity.Shop;
import ru.codemine.ccms.exceptions.InvalidParametersException;
import ru.codemine.ccms.router.api.form.PassabilityJson;
import ru.codemine.ccms.router.api.form.ShopJson;
import ru.codemine.ccms.service.CounterService;
import ru.codemine.ccms.service.SalesService;
import ru.codemine.ccms.service.ShopService;

/**
 *
 * @author Alexander Savelev
 */

@Controller
public class ApiRouter 
{
    @Autowired private ShopService shopService;
    @Autowired private SalesService salesService;
    @Autowired private CounterService counterService;
    
    private static final Logger log = Logger.getLogger("API Router");
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/api/shop", method = RequestMethod.GET)
    public @ResponseBody ShopJson getShopJson(
            @RequestParam(required = false) Integer shopid,
            @RequestParam(required = false) String shopname)
    {
        if(shopid == null && shopname == null) throw new InvalidParametersException();
        if(shopid != null && shopname != null) throw new InvalidParametersException();
            
        try
        {
            Shop shop = shopid == null ? shopService.getByName(shopname) : shopService.getById(shopid);
            
            return new ShopJson(shop);
        }
        catch(Exception e)
        {
            log.warn("Ошибка доступа через API (магазин) - " + e.getLocalizedMessage());
            return null;
        }
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/api/shops", method = RequestMethod.GET)
    public @ResponseBody List<ShopJson> getShopsJson()
    {
        try
        {
            List<ShopJson> result = new ArrayList<>();

            for(Shop shop : shopService.getAll())
            {
                result.add(new ShopJson(shop));
            }

            return result;
        }
        catch(Exception e)
        {
            log.warn("Ошибка доступа через API (магазины) - " + e.getLocalizedMessage());
            return new ArrayList<>();
        }
    }
    
    
    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "/api/passability", method = RequestMethod.GET)
    public @ResponseBody List<PassabilityJson> getPassabilityJson(
            @RequestParam(required = false) Integer shopid,
            @RequestParam(required = false) String shopname,
            @RequestParam("startDate") String startDateStr,
            @RequestParam("endDate") String endDateStr,
            @RequestParam(required = false) String detail)
    {        
        if(shopid == null && shopname == null) throw new InvalidParametersException();
        if(shopid != null && shopname != null) throw new InvalidParametersException();
            
        try
        {
            Shop shop = shopid == null ? shopService.getByName(shopname) : shopService.getById(shopid);

            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.YYYY");
            List<PassabilityJson> resulList = new ArrayList<>();

            LocalDate startDate = formatter.parseLocalDate(startDateStr);
            LocalDate endDate = formatter.parseLocalDate(endDateStr);

            if(detail == null || "none".equals(detail))
            {
                PassabilityJson record = new PassabilityJson();

                record.setStartDate(startDate.toString("dd.MM.YYYY"));
                record.setEndDate(endDate.toString("dd.MM.YYYY"));
                if(shop.isCountersEnabled())
                {
                    record.setIn(counterService.getInValueByPeriod(shop, startDate, endDate));
                    record.setOut(counterService.getOutValueByPeriod(shop, startDate, endDate));
                }
                else
                {
                    record.setIn(salesService.getPassabilityValueByPeriod(shop, startDate, endDate));
                    record.setOut(record.getIn());
                }
                resulList.add(record);
            }
            else if("day".equals(detail))
            {
                //Это быстрое решение, нужно переписать потом
                List<SalesMeta> smList = salesService.getByPeriod(shop, startDate.withDayOfMonth(1), endDate.dayOfMonth().withMaximumValue());
                List<Sales> allSales = salesService.getAllSalesFromMetaList(smList, startDate, endDate);

                for(Sales s : allSales)
                {
                    PassabilityJson record = new PassabilityJson();

                    record.setStartDate(s.getDate().toString("dd.MM.YYYY"));
                    record.setEndDate(s.getDate().toString("dd.MM.YYYY"));
                    record.setIn(shop.isCountersEnabled()
                            ? counterService.getInValueByPeriod(shop, s.getDate(), s.getDate())
                            : s.getPassability());
                    record.setOut(shop.isCountersEnabled()
                            ? counterService.getOutValueByPeriod(shop, s.getDate(), s.getDate())
                            : s.getPassability());

                    resulList.add(record);
                }
            }
            else if("month".equals(detail))
            {
                startDate = startDate.withDayOfMonth(1);
                endDate = endDate.dayOfMonth().withMaximumValue();

                List<SalesMeta> smList = salesService.getByPeriod(shop, startDate, endDate);
                for(SalesMeta sm : smList)
                {
                    PassabilityJson record = new PassabilityJson();

                    record.setStartDate(sm.getStartDate().toString("dd.MM.YYYY"));
                    record.setEndDate(sm.getEndDate().toString("dd.MM.YYYY"));
                    record.setIn(shop.isCountersEnabled() 
                            ? counterService.getInValueByPeriod(shop, sm.getStartDate(), sm.getEndDate()) 
                            : sm.getPassabilityTotal());
                    record.setOut(shop.isCountersEnabled() 
                            ? counterService.getOutValueByPeriod(shop, sm.getStartDate(), sm.getEndDate()) 
                            : sm.getPassabilityTotal());

                    resulList.add(record);
                }
            }

            return resulList;
        }
        catch(Exception e)
        {
            log.warn("Ошибка доступа через API (проходимость) - " + e.getLocalizedMessage());
            return new ArrayList<>();
        }
    }
    
}
