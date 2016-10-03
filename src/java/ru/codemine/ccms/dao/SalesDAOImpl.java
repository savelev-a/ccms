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
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;
import ru.codemine.ccms.entity.SalesMeta;
import ru.codemine.ccms.entity.Shop;

/**
 *
 * @author Alexander Savelev
 */

@Repository
public class SalesDAOImpl extends GenericDAOImpl<SalesMeta, Integer> implements SalesDAO
{
    private static final Logger log = Logger.getLogger("SalesDAO");
    
    @Override
    public void updatePlanAll(Double plan, LocalDate startDate, LocalDate endDate)
    {
        if(plan == null || startDate == null || endDate == null || startDate.isAfter(endDate)) return;
        
        Query updateQuery = getSession().createQuery("UPDATE SalesMeta Sm SET Sm.plan = :plan WHERE Sm.startDate = :startdate AND Sm.endDate = :enddate");
        updateQuery.setDate("startdate", startDate.toDate());
        updateQuery.setDate("enddate", endDate.toDate());
        updateQuery.setDouble("plan", plan);
        
        updateQuery.executeUpdate();
    }
    

    @Override
    public List<SalesMeta> getByShop(Shop shop)
    {
        Query query = getSession().createQuery("FROM SalesMeta Sm WHERE Sm.shop.id = :id");
        query.setInteger("id", shop.getId());
        
        return query.list();
    }
    
    @Override
    public SalesMeta getByShopAndDate(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        Query query = getSession().createQuery("FROM SalesMeta Sm WHERE Sm.shop.id = :shopid AND Sm.startDate = :startdate AND Sm.endDate = :enddate");
        query.setInteger("shopid", shop.getId());
        query.setDate("startdate", startDate.toDate());
        query.setDate("enddate", endDate.toDate());
        
        return (SalesMeta)query.uniqueResult();
    }
    
    public List<SalesMeta> getByPeriod(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        Query query = getSession().createQuery("FROM SalesMeta Sm WHERE Sm.shop.id = :shopid AND Sm.startDate >= :startdate AND Sm.endDate <= :enddate");
        query.setInteger("shopid", shop.getId());
        query.setDate("startdate", startDate.toDate());
        query.setDate("enddate", endDate.toDate());
        
        return query.list();
    }
}
