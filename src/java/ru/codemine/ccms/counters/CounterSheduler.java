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
package ru.codemine.ccms.counters;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.codemine.ccms.counters.kondor.KondorClient;
import ru.codemine.ccms.entity.Counter;
import ru.codemine.ccms.entity.Shop;
import ru.codemine.ccms.service.CounterService;
import ru.codemine.ccms.service.ShopService;

/**
 *
 * @author Alexander Savelev
 */
@Component
public class CounterSheduler
{

    private static final Logger log = Logger.getLogger("CounterSheduler");

    @Autowired
    private ShopService shopService;

    @Autowired
    private CounterService counterService;

    @Autowired
    private KondorClient kondorClient;

    @Scheduled(fixedDelay = 3600000, initialDelay = 300000)
    public void updateAllCounters()
    {
        log.info("Sheduled counters update process started.");
        List<Shop> shoplist = shopService.getWithCounters();

        for (Shop shop : shoplist)
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
            }
        }
    }
    
    
}
