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
import org.springframework.stereotype.Repository;
import ru.codemine.ccms.entity.Employee;
import ru.codemine.ccms.entity.Shop;

/**
 *
 * @author Alexander Savelev
 */

@Repository
public class ShopDAOImpl extends GenericDAOImpl<Shop, Integer> implements ShopDAO
{
    private static final Logger log = Logger.getLogger("ShopDAO");

    @Override
    public Shop getByName(String name)
    {
        Shop shop = (Shop)getSession().createQuery("FROM Shop S WHERE S.name = '" + name + "'").uniqueResult();
        
        return shop;
    }
    
    @Override
    public List<Shop> getByAdmin(Employee admin)
    {
        List<Shop> result = getSession().createQuery("FROM Shop S WHERE S.shopAdmin.id = " + admin.getId()).list();
        
        return result;
    }
    
    @Override
    public List<Shop> getWithCounters()
    {
        List<Shop> result = getSession().createQuery("FROM Shop S WHERE S.countersEnabled = true ORDER BY S.name ASC").list();
        
        return result;
    }
    
    @Override
    public List<Shop> getAllOpen()
    {
        List<Shop> result = getSession().createQuery("FROM Shop S WHERE S.closed = false ORDER BY S.name ASC").list();
        
        return result;
    }

    @Override
    public List<Shop> getAll()
    {
        List<Shop> result = getSession().createQuery("FROM Shop S ORDER BY S.name ASC").list();
        
        return result;
    }

}
