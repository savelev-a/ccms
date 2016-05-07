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

package ru.codemine.ccms.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.codemine.ccms.entity.Counter;
import ru.codemine.ccms.entity.Shop;

/**
 *
 * @author Alexander Savelev
 */

@Repository
public class CounterDAOImpl implements CounterDAO
{
    private static final Logger log = Logger.getLogger("CounterDAO");
    
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void update(Counter counter)
    {
        log.debug("New counter values: " + counter.toString());
        
        Session session = sessionFactory.getCurrentSession();
        
        Counter c = getByShopAndDate(counter.getShop(), counter.getDate());
        if(c == null)
        {
            session.save(counter);
        }
        else
        {
            log.debug("...and this is current day update.");
            c.setIn(counter.getIn());
            c.setOut(counter.getOut());
            session.update(c);
        }
        
        
    }

    @Override
    public List<Counter> getByShop(Shop shop)
    {
        Session session = sessionFactory.getCurrentSession();
        List<Counter> result = session.createQuery("FROM Counter C WHERE C.shop.id = " + shop.getId() + " ORDER BY C.date ASC").list();
        
        return result;
        
    }
    
    @Override
    public Counter getByShopAndDate(Shop shop, DateTime date)
    {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery(
                "FROM Counter C WHERE C.shop.id = " 
                + shop.getId() 
                + " AND C.date = :date");
        
        q.setDate("date", date.toDate());
        
        Counter counter = (Counter)q.uniqueResult();
        
        return counter;
    }
    
    @Override
    public Integer getSumIn(DateTime date)
    {
        Session session = sessionFactory.getCurrentSession();
        Integer result = 0;
        Query q = session.createQuery(
                "FROM Counter C WHERE C.date = :date");
        
        q.setDate("date", date.toDate());
        
        List<Counter> counterList = q.list();
        for(Counter c : counterList)
        {
            result += c.getIn();
        }
        
        return result;
    }
    
    @Override
    public Integer getSumOut(DateTime date)
    {
        Session session = sessionFactory.getCurrentSession();
        Integer result = 0;
        Query q = session.createQuery(
                "FROM Counter C WHERE C.date = :date");
        
        q.setDate("date", date.toDate());
        
        List<Counter> counterList = q.list();
        for(Counter c : counterList)
        {
            result += c.getOut();
        }
        
        return result;
    }

}
