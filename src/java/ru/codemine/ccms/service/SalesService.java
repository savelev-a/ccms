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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.ccms.dao.SalesDAO;
import ru.codemine.ccms.entity.ExpenceType;
import ru.codemine.ccms.entity.Sales;
import ru.codemine.ccms.entity.SalesMeta;
import ru.codemine.ccms.entity.Shop;

/**
 *
 * @author Alexander Savelev
 */

@Service
@Transactional
public class SalesService
{
    private static final Logger log = Logger.getLogger("SalesService");
    
    @Autowired private SalesDAO salesDAO;
    
    @Autowired private CounterService counterService;
    
    public void create(SalesMeta sm)
    {
        sm.recalculateTotals();
        salesDAO.create(sm);
    }
    
    public void delete(SalesMeta sm)
    {
        salesDAO.delete(sm);
    }
    
    public void deleteById(Integer id)
    {
        salesDAO.deleteById(id);
    }
    
    public void update(SalesMeta sm)
    {
        sm.recalculateTotals();
        salesDAO.update(sm);
    }
    
    public boolean updatePlanAll(Double plan, LocalDate startDate, LocalDate endDate)
    {
         return salesDAO.updatePlanAll(plan, startDate, endDate);
    }
    
    
    public SalesMeta getById(Integer id)
    {
        return salesDAO.getById(id);
    }
    
    public List<SalesMeta> getByShop(Shop shop)
    {
        return salesDAO.getByShop(shop);
    }
    
    
    public SalesMeta getByShopAndDate(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        SalesMeta sm = salesDAO.getByShopAndDate(shop, startDate, endDate);
        if(sm == null)
        {
            sm = new SalesMeta(shop, startDate, endDate);
            sm.setDescription("Таблица выручек: " 
                    + shop.getName() 
                    + " (" 
                    + startDate.toString("MMMM YYYY")
                    + ")");
        }
        
        return sm;
    }
    
    public List<SalesMeta> getByPeriod(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        return salesDAO.getByPeriod(shop, startDate, endDate);
    }

    public Integer getPassabilityValueByPeriod(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        if(shop.isCountersEnabled())
        {
            return counterService.getPassabilityValueByPeriod(shop, startDate, endDate);
        }
        
        return salesDAO.getPassabilityValueByPeriod(shop, startDate, endDate);
    }

    public Integer getCqcountValueByPeriod(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        return salesDAO.getCqcountValueByPeriod(shop, startDate, endDate);
    }

    public Double getValueByPeriod(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        return salesDAO.getValueByPeriod(shop, startDate, endDate);
    }

    public Double getCashbackValueByPeriod(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        return salesDAO.getCashbackValueByPeriod(shop, startDate, endDate);
    }

    public Double getSalesValueByPeriod(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        return salesDAO.getSalesValueByPeriod(shop, startDate, endDate);
    }

    public Double getMidPriceValueByPeriod(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        return salesDAO.getMidPriceValueByPeriod(shop, startDate, endDate);
    }

    public Double getPlan(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        return salesDAO.getPlan(shop, startDate, endDate);
    }

    public Double getPlanCoverage(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        return salesDAO.getPlanCoverage(shop, startDate, endDate);
    }

    public List<Sales> getAllSalesFromMetaList(List<SalesMeta> smList)
    {
        List<Sales> result = new ArrayList<>();
        for(SalesMeta sm : smList)
            result.addAll(sm.getSales());
        
        return result;
    }
    
    public List<Sales> getAllSalesFromMetaList(List<SalesMeta> smList, LocalDate startDate, LocalDate endDate)
    {
        List<Sales> result = new ArrayList<>();
        for(SalesMeta sm : smList)
        {
            for(Sales s : sm.getSales())
            {
                if(s.getDate().isAfter(startDate.minusDays(1)) && s.getDate().isBefore(endDate.plusDays(1)))
                    result.add(s);
            }
        }
        
        return result;
    }
    
    public Set<ExpenceType> getExpenceTypesByPeriod(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        return salesDAO.getExpenceTypesByPeriod(shop, startDate, endDate);
    }

    public Double getTotalExpenceValueForPeriod(Shop shop, LocalDate startDate, LocalDate endDate, ExpenceType type)
    {
        return salesDAO.getTotalExpenceValueForPeriod(shop, startDate, endDate, type);
    }

    public Double getTotalExpenceValueForPeriod(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        return salesDAO.getTotalExpenceValueForPeriod(shop, startDate, endDate);
    }

    public Double getMidExpences(Shop shop, int months)
    {
        if(months <= 0) return -1.0;
        
        LocalDate endDate = LocalDate.now().minusMonths(1).dayOfMonth().withMaximumValue();
        LocalDate startDate = endDate.withDayOfMonth(1).minusMonths(months - 1);
        
        Double expTotal = getTotalExpenceValueForPeriod(shop, startDate, endDate);
        
        Double result = Math.round(expTotal / months * 100.0) / 100.0;
        
        return result;
    }

    public Double getMidCleanSales(Shop shop, int months)
    {
        if(months <= 0) return -1.0;
        
        LocalDate endDate = LocalDate.now().minusMonths(1).dayOfMonth().withMaximumValue();
        LocalDate startDate = endDate.withDayOfMonth(1).minusMonths(months - 1);
        
        Double cleanSales = getSalesValueByPeriod(shop, startDate, endDate) - getTotalExpenceValueForPeriod(shop, startDate, endDate);
        
        Double result = Math.round(cleanSales / months * 100.0) / 100.0;
        
        return result;
    }
//
//    public List<Sales> getAllSalesByPeriod(Shop shop, LocalDate startDate, LocalDate endDate)
//    {
//        return salesDAO.getAllSalesByPeriod(shop, startDate, endDate);
//    }




    
}
