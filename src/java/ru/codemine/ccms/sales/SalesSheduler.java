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
package ru.codemine.ccms.sales;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.codemine.ccms.service.ShopService;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class SalesSheduler
{
    private static final Logger log = Logger.getLogger("SalesSheduler");

    @Autowired
    private ShopService shopService;
    
    @Autowired
    private SalesLoader salesLoader;
    
    @Scheduled(fixedDelay = 3600000, initialDelay = 600000)
    public void updateAllSales()
    {
        log.info("Sheduled sales update process started.");
        salesLoader.processSales(shopService.getAllOpen());
    }
}
