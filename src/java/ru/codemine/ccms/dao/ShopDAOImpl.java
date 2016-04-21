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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.codemine.ccms.entity.Shop;

/**
 *
 * @author Alexander Savelev
 */

@Repository
public class ShopDAOImpl implements ShopDAO
{
    private static final Logger log = Logger.getLogger("ShopDAO");
    
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void create(Shop shop)
    {
        log.info("Creating new shop: " + shop.getName());
        Session session = sessionFactory.getCurrentSession();
        session.save(shop);
    }

    @Override
    public void delete(Shop shop)
    {
        log.info("Removing shop: " + shop.getName());
        Session session = sessionFactory.getCurrentSession();
        session.delete(shop);
    }

    @Override
    public void deleteById(Integer id)
    {
        log.info("Removing shop by id: " + id.toString());
        Session session = sessionFactory.getCurrentSession();
        Shop shop = getById(id);
        
        if(shop != null) session.delete(shop);
    }

    @Override
    public void update(Shop shop)
    {
        log.info("Updating shop: " + shop.getName());
        Session session = sessionFactory.getCurrentSession();
        session.update(shop);
    }

    @Override
    public Shop getById(Integer id)
    {
        Session session = sessionFactory.getCurrentSession();
        Shop shop = (Shop)session.get(Shop.class, id);
        
        return shop;
    }

    @Override
    public Shop getByName(String name)
    {
        Session session = sessionFactory.getCurrentSession();
        Shop shop = (Shop)session.createQuery("FROM Shop S WHERE S.name = " + name);
        
        return shop;
    }

    @Override
    public List<Shop> getAll()
    {
        Session session = sessionFactory.getCurrentSession();
        List<Shop> result = session.createQuery("FROM Shop S ORDER BY S.name ASC").list();
        
        return result;
    }

}
