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

package ru.codemine.ccms.service;

import java.util.List;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.ccms.dao.CounterDAO;
import ru.codemine.ccms.entity.Counter;
import ru.codemine.ccms.entity.SalesMeta;
import ru.codemine.ccms.entity.Shop;

/**
 *
 * @author Alexander Savelev
 */

@Service
@Transactional
public class CounterService 
{
    
    private static final Logger log = Logger.getLogger("CounterService");
    
    @Autowired private CounterDAO counterDAO;
    
    public void saveCounter(Counter counter)
    {
        counterDAO.update(counter);
    }
    
    public List<Counter> getByShop(Shop shop)
    {
        return counterDAO.getByShop(shop);
    }
    
    public Counter getByShopAndDate(Shop shop, DateTime date)
    {
        return counterDAO.getByShopAndDate(shop, date);
    }
    
    public Integer getSumIn(DateTime date)
    {
        return counterDAO.getSumIn(date);
    }
    
    public Integer getSumOut(DateTime date)
    {
        return counterDAO.getSumOut(date);
    }
    
    
    public Integer getPassabilityValueByPeriod(Shop shop, LocalDate dateStart, LocalDate dateEnd)
    {
        return counterDAO.getPassabilityValueByPeriod(shop, dateStart, dateEnd);
    }

    public Integer getInValueByPeriod(Shop shop, LocalDate dateStart, LocalDate dateEnd)
    {
        return counterDAO.getInValueByPeriod(shop, dateStart, dateEnd);
    }

    public Integer getOutValueByPeriod(Shop shop, LocalDate dateStart, LocalDate dateEnd)
    {
        return counterDAO.getOutValueByPeriod(shop, dateStart, dateEnd);
    }

}
