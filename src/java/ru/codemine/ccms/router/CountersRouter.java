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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.codemine.ccms.counters.kondor.KondorClient;
import ru.codemine.ccms.entity.Counter;
import ru.codemine.ccms.entity.Shop;
import ru.codemine.ccms.service.CounterService;
import ru.codemine.ccms.service.ShopService;
import ru.codemine.ccms.utils.Utils;

/**
 *
 * @author Alexander Savelev
 */
@Controller
public class CountersRouter
{

    private static final Logger log = Logger.getLogger("CountersRouter");

    @Autowired private ShopService shopService;
    @Autowired private CounterService counterService;
    @Autowired private KondorClient kondorClient;
    @Autowired private Utils utils;

    @Secured("ROLE_USER")
    @RequestMapping(value = "/counters", method = RequestMethod.GET)
    public String getCountersPage(@RequestParam Integer shopid, ModelMap model)
    {
        Shop shop = shopService.getById(shopid);

        model.addAllAttributes(utils.prepareModel("Показания счетчиков - " + shop.getName() + " - ИнфоПортал", "shops", "counters"));
        model.addAttribute("shop", shop);

        List<String> graphDataInList = new ArrayList<>();
        List<String> graphDataOutList = new ArrayList<>();

        List<Counter> countersAll = counterService.getByShop(shop);
        List<Counter> counters = new ArrayList<>();
        for (Counter counter : countersAll)
        {
            if (!counter.getDate().plusMonths(1).isBeforeNow())
            {
                graphDataInList.add(counter.getGraphDataIn());
                graphDataOutList.add(counter.getGraphDataOut());
                counters.add(counter);
            } 
        }

        model.addAttribute("counters", counters);
        model.addAttribute("graphDataIn", graphDataInList);
        model.addAttribute("graphDataOut", graphDataOutList);

        return "pages/shops/counters";
    }

    @RequestMapping(value = "/counters", method = RequestMethod.POST)
    public String shopCountersRefresh(@RequestParam Integer shopid, ModelMap model)
    {
        updateDataFromShop(shopService.getById(shopid));
        
        return "redirect:/counters?shopid=" + shopid;
    }

    private boolean updateDataFromShop(Shop shop)
    {
        List<Counter> counters = kondorClient.getCounters(shop);
        if (counters != null)
        {
            for (Counter counter : counters)
            {
                if (counter.getDate().plusMonths(1).isAfterNow())
                {
                    counterService.saveCounter(counter);
                } 
                else
                {
                    log.debug("discarding old counter value: " + counter.getDate());
                }
            }
            
            return true;
        }
        
        return false;

    }

}
