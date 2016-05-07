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
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.codemine.ccms.entity.SalesMeta;
import ru.codemine.ccms.entity.Shop;

/**
 *
 * @author Alexander Savelev
 */

@Repository
public class SalesDAOImpl implements SalesDAO
{
    private static final Logger log = Logger.getLogger("SalesDAO");
    
    @Autowired
    SessionFactory sessionFactory;
    
    @Override
    public void create(SalesMeta sm)
    {
        Session session = sessionFactory.getCurrentSession();
        
        session.save(sm);
    }

    @Override
    public void delete(SalesMeta sm)
    {
        Session session = sessionFactory.getCurrentSession();
        
        session.delete(sm);
    }

    @Override
    public void deleteById(Integer id)
    {
        Session session = sessionFactory.getCurrentSession();
        
        SalesMeta sm = getById(id);
        if(sm != null) session.delete(sm);
    }

    @Override
    public void update(SalesMeta sm)
    {
        Session session = sessionFactory.getCurrentSession();
        
        session.saveOrUpdate(sm);
    }
    
    @Override
    public boolean updatePlanAll(Double plan, LocalDate startDate, LocalDate endDate)
    {
        if(plan == null || startDate == null || endDate == null || startDate.isAfter(endDate)) return false;
        
        Session session = sessionFactory.getCurrentSession();
        Query updateQuery = session.createQuery("UPDATE SalesMeta Sm SET Sm.plan = :plan WHERE Sm.startDate = :startdate AND Sm.endDate = :enddate");
        updateQuery.setDate("startdate", startDate.toDate());
        updateQuery.setDate("enddate", endDate.toDate());
        updateQuery.setDouble("plan", plan);
        
        return (updateQuery.executeUpdate() > 0);
    }
    
    @Override
    public SalesMeta getById(Integer id)
    {
        Session session = sessionFactory.getCurrentSession();
        
        //SalesMeta sm = (SalesMeta)session.get(SalesMeta.class, id);
        SalesMeta sm = (SalesMeta)session.createQuery("FROM SalesMeta Sm WHERE Sm.id = " + id).uniqueResult();
        
        return sm;
    }

    @Override
    public List<SalesMeta> getByShop(Shop shop)
    {
        Session session = sessionFactory.getCurrentSession();
        
        List<SalesMeta> result = session.createQuery("FROM SalesMeta Sm WHERE Sm.shop.id = " + shop.getId()).list();
        
        return result;
    }
    
    



    //@Override
    //public SalesMeta getByShopAndDates(Shop shop, DateTime startDate, DateTime endDate)
    //{
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    //}
    
}
