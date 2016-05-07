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
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.ccms.dao.SalesDAO;
import ru.codemine.ccms.entity.SalesMeta;
import ru.codemine.ccms.entity.Shop;

/**
 *
 * @author Alexander Savelev
 */

@Service
public class SalesService
{
    private static final Logger log = Logger.getLogger("SalesService");
    
    @Autowired
    private SalesDAO salesDAO;
    
    @Transactional
    public void create(SalesMeta sm)
    {
        salesDAO.create(sm);
    }
    
    @Transactional
    public void delete(SalesMeta sm)
    {
        salesDAO.delete(sm);
    }
    
    @Transactional
    public void deleteById(Integer id)
    {
        salesDAO.deleteById(id);
    }
    
    @Transactional
    public void update(SalesMeta sm)
    {
        salesDAO.update(sm);
    }
    
    @Transactional
    public boolean updatePlanAll(Double plan, LocalDate startDate, LocalDate endDate)
    {
        return salesDAO.updatePlanAll(plan, startDate, endDate);
    }
    
    
    @Transactional
    public SalesMeta getById(Integer id)
    {
        return salesDAO.getById(id);
    }
    
    @Transactional
    public List<SalesMeta> getByShop(Shop shop)
    {
        return salesDAO.getByShop(shop);
    }
    
    
    @Transactional
    public SalesMeta getByShopAndDate(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        List<SalesMeta> smlist = salesDAO.getByShop(shop);

        for(SalesMeta sm : smlist)
        {
            if(sm.getStartDate().equals(startDate) && sm.getEndDate().equals(endDate))
                return sm;
        }
        SalesMeta newSalesMeta = new SalesMeta(shop, startDate, endDate);
        newSalesMeta.setDescription("Таблица выручек: " 
                    + shop.getName() 
                    + " (" 
                    + startDate.toString("MMMM YYYY")
                    + ")"
            );
        
        return newSalesMeta;
    }
    
}
