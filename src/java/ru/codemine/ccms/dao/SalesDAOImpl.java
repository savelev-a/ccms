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
import java.util.Set;
import java.util.TreeSet;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;
import ru.codemine.ccms.entity.ExpenceType;
import ru.codemine.ccms.entity.Sales;
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
    public boolean updatePlanAll(Double plan, LocalDate startDate, LocalDate endDate)
    {
        if(plan == null || startDate == null || endDate == null || startDate.isAfter(endDate)) return false;
        
        Query updateQuery = getSession().createQuery("UPDATE SalesMeta Sm "
                + "SET Sm.plan = :plan "
                + "WHERE Sm.startDate = :startdate "
                + "AND Sm.endDate = :enddate");
        updateQuery.setDate("startdate", startDate.toDate());
        updateQuery.setDate("enddate", endDate.toDate());
        updateQuery.setDouble("plan", plan);
        
        return updateQuery.executeUpdate() > 0;
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
        Query query = getSession().createQuery("FROM SalesMeta Sm "
                + "WHERE Sm.shop.id = :shopid "
                + "AND Sm.startDate = :startdate "
                + "AND Sm.endDate = :enddate");
        query.setInteger("shopid", shop.getId());
        query.setDate("startdate", startDate.toDate());
        query.setDate("enddate", endDate.toDate());
        
        return (SalesMeta)query.uniqueResult();
    }
    
    @Override
    public List<SalesMeta> getByPeriod(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        Query query = getSession().createQuery("FROM SalesMeta Sm "
                + "WHERE Sm.shop.id = :shopid "
                + "AND Sm.startDate >= :startdate "
                + "AND Sm.endDate <= :enddate "
                + "ORDER BY Sm.startDate ASC");
        query.setInteger("shopid", shop.getId());
        query.setDate("startdate", startDate.toDate());
        query.setDate("enddate", endDate.toDate());
        
        return query.list();
    }

    @Override
    public Integer getPassabilityValueByPeriod(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        Query query = getSession().createQuery("SELECT SUM(Sm.passabilityTotal) "
                + "FROM SalesMeta Sm "
                + "WHERE Sm.shop.id = :shopid "
                + "AND Sm.startDate >= :startdate "
                + "AND Sm.endDate <= :enddate");
        query.setInteger("shopid", shop.getId());
        query.setDate("startdate", startDate.toDate());
        query.setDate("enddate", endDate.toDate());
        
        Long result = (Long)query.uniqueResult();
        
        return result == null ? 0 : result.intValue();
    }

    @Override
    public Integer getCqcountValueByPeriod(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        Query query = getSession().createQuery("SELECT SUM(Sm.chequeCountTotal) "
                + "FROM SalesMeta Sm "
                + "WHERE Sm.shop.id = :shopid "
                + "AND Sm.startDate >= :startdate "
                + "AND Sm.endDate <= :enddate");
        query.setInteger("shopid", shop.getId());
        query.setDate("startdate", startDate.toDate());
        query.setDate("enddate", endDate.toDate());
        
        Long result = (Long)query.uniqueResult();
        
        return result == null ? 0 : result.intValue();
    }

    @Override
    public Double getValueByPeriod(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        Query query = getSession().createQuery("SELECT SUM(Sm.valueTotal) "
                + "FROM SalesMeta Sm "
                + "WHERE Sm.shop.id = :shopid "
                + "AND Sm.startDate >= :startdate "
                + "AND Sm.endDate <= :enddate");
        query.setInteger("shopid", shop.getId());
        query.setDate("startdate", startDate.toDate());
        query.setDate("enddate", endDate.toDate());
        
        Double result = (Double)query.uniqueResult();
        
        return result == null ? 0.0 : result;
    }

    @Override
    public Double getCashbackValueByPeriod(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        Query query = getSession().createQuery("SELECT SUM(Sm.cashbackTotal) "
                + "FROM SalesMeta Sm "
                + "WHERE Sm.shop.id = :shopid "
                + "AND Sm.startDate >= :startdate "
                + "AND Sm.endDate <= :enddate");
        query.setInteger("shopid", shop.getId());
        query.setDate("startdate", startDate.toDate());
        query.setDate("enddate", endDate.toDate());
        
        Double result = (Double)query.uniqueResult();
        
        return result == null ? 0.0 : result;
    }

    @Override
    public Double getSalesValueByPeriod(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        Query query = getSession().createQuery("SELECT SUM(Sm.salesTotal) "
                + "FROM SalesMeta Sm "
                + "WHERE Sm.shop.id = :shopid "
                + "AND Sm.startDate >= :startdate "
                + "AND Sm.endDate <= :enddate");
        query.setInteger("shopid", shop.getId());
        query.setDate("startdate", startDate.toDate());
        query.setDate("enddate", endDate.toDate());
        
        Double result = (Double)query.uniqueResult();
        
        return result == null ? 0.0 : result;
    }

    @Override
    public Double getMidPriceValueByPeriod(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        Double v = getValueByPeriod(shop, startDate, endDate);
        Integer cq = getCqcountValueByPeriod(shop, startDate, endDate);
        
        return cq.equals(0) ? 0.0 : v / cq;
    }

    @Override
    public Double getPlan(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        Query query = getSession().createQuery("SELECT SUM(Sm.plan) "
                + "FROM SalesMeta Sm "
                + "WHERE Sm.shop.id = :shopid "
                + "AND Sm.startDate >= :startdate "
                + "AND Sm.endDate <= :enddate");
        query.setInteger("shopid", shop.getId());
        query.setDate("startdate", startDate.toDate());
        query.setDate("enddate", endDate.toDate());
        
        Double result = (Double)query.uniqueResult();
        
        return result == null ? 0.0 : result;
    }

    @Override
    public Double getPlanCoverage(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        Double plan = getPlan(shop, startDate, endDate);
        Double sale = getSalesValueByPeriod(shop, startDate, endDate);
        
        return plan.equals(0.0) ? 0.0 : sale / plan * 100;
    }

    @Override
    public Set<ExpenceType> getExpenceTypesByPeriod(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        Query query = getSession().createQuery("SELECT INDEX(Sm.expences) "
                + "FROM SalesMeta Sm "
                + "WHERE Sm.shop.id = :shopid "
                + "AND Sm.startDate >= :startdate "
                + "AND Sm.endDate <= :enddate");
        query.setInteger("shopid", shop.getId());
        query.setDate("startdate", startDate.toDate());
        query.setDate("enddate", endDate.toDate());
        
        Set<ExpenceType> etSet = new TreeSet<>(query.list());

        return etSet;
    }

    @Override
    public Double getTotalExpenceValueForPeriod(Shop shop, LocalDate startDate, LocalDate endDate, ExpenceType type)
    {
        Query query = getSession().createQuery("FROM SalesMeta Sm "
                + "WHERE Sm.shop.id = :shopid "
                + "AND Sm.startDate >= :startdate "
                + "AND Sm.endDate <= :enddate "
                + "AND :expenceType IN INDICES(Sm.expences)");
        query.setInteger("shopid", shop.getId());
        query.setDate("startdate", startDate.toDate());
        query.setDate("enddate", endDate.toDate());
        query.setParameter("expenceType", type);
        
        List<SalesMeta> smList = query.list();
        Double result = 0.0;
        
        for(SalesMeta sm : smList)
        {
            result += sm.getExpences().get(type);
        }
        
        return result;
    }

    @Override
    public Double getTotalExpenceValueForPeriod(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        Query query = getSession().createQuery("SELECT SUM(Sm.expencesTotal) "
                + "FROM SalesMeta Sm "
                + "WHERE Sm.shop.id = :shopid "
                + "AND Sm.startDate >= :startdate "
                + "AND Sm.endDate <= :enddate ");
        query.setInteger("shopid", shop.getId());
        query.setDate("startdate", startDate.toDate());
        query.setDate("enddate", endDate.toDate());
        
        Double result = (Double)query.uniqueResult();

        return result == null ? 0.0 : result;
    }

//    @Override
//    public List<Sales> getAllSalesByPeriod(Shop shop, LocalDate startDate, LocalDate endDate)
//    {
//        Query query = getSession().createQuery("SELECT Sm.sales AS sales FROM SalesMeta sm "
//                + "WHERE sales.shop.id = :shopid "
//                + "AND sales.date >= :startdate "
//                + "AND sales.date <= :enddate "
//                + "ORDER BY sales.date ASC");
//        query.setInteger("shopid", shop.getId());
//        query.setDate("startdate", startDate.withDayOfMonth(1).toDate());
//        query.setDate("enddate", endDate.dayOfMonth().withMaximumValue().toDate());
//        
//        return query.list();
//    }
}
