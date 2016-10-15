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
import org.hibernate.Query;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;
import ru.codemine.ccms.entity.ActionEvent;
import ru.codemine.ccms.entity.Employee;
import ru.codemine.ccms.entity.Shop;

/**
 *
 * @author Alexander Savelev
 */

@Repository
public class ActionEventDAOImpl extends GenericDAOImpl<ActionEvent, Integer> implements ActionEventDAO
{

    @Override
    public List<ActionEvent> getByCreator(Employee creator)
    {
        Query query = getSession().createQuery("FROM ActionEvent a "
                + "WHERE a.creator.id = :id");
        query.setInteger("id", creator.getId());
        
        return query.list();
    }

    @Override
    public List<ActionEvent> getByShop(Shop shop)
    {
        Query query = getSession().createQuery("FROM ActionEvent a "
                + "WHERE :shop IN ELEMENTS(a.affectedShops)");
        query.setParameter("shop", shop);
        
        return query.list();
    }

    @Override
    public List<ActionEvent> getActiveEvents()
    {
        Query query = getSession().createQuery("FROM ActionEvent a "
                + "WHERE a.startDate <= :now "
                + "AND a.endDate >= :now");
        query.setDate("now", LocalDate.now().toDate());
        
        return query.list();
    }

    @Override
    public List<ActionEvent> getActiveEvents(Shop shop)
    {
        Query query = getSession().createQuery("FROM ActionEvent a "
                + "WHERE a.startDate <= :now "
                + "AND a.endDate >= :now "
                + "AND :shop IN ELEMENTS(a.affectedShops)");
        query.setDate("now", LocalDate.now().toDate());
        query.setParameter("shop", shop);
        
        return query.list();
    }

    @Override
    public List<ActionEvent> getAll()
    {
        Query query = getSession().createQuery("FROM ActionEvent");
        
        return query.list();
    }

    @Override
    public List<ActionEvent> getCurrentFuture()
    {
        Query query = getSession().createQuery("FROM ActionEvent a "
                + "WHERE a.endDate >= :now");
        query.setDate("now", LocalDate.now().toDate());
        
        return query.list();
    }

}
