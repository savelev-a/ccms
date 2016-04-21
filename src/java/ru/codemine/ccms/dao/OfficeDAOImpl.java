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
import ru.codemine.ccms.entity.Office;

/**
 *
 * @author Alexander Savelev
 */

@Repository
public class OfficeDAOImpl implements OfficeDAO
{
    private static final Logger log = Logger.getLogger("OfficeDAO");
    
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void create(Office office)
    {
        log.info("Creating new office: " + office.getName());
        Session session = sessionFactory.getCurrentSession(); 
        session.save(office);
    }

    @Override
    public void delete(Office office)
    {
        log.info("Removing office: " + office.getName());
        Session session = sessionFactory.getCurrentSession();
        session.delete(office);
    }

    @Override
    public void deleteById(Integer id)
    {
        log.info("Removing office by id: " + id.toString());
        Session session = sessionFactory.getCurrentSession();
        Office office = getById(id);
        
        if(office != null) session.delete(office);
    }

    @Override
    public void update(Office office)
    {
        log.info("Updating office: " + office.getName());
        Session session = sessionFactory.getCurrentSession();
        session.update(office);
    }

    @Override
    public Office getById(Integer id)
    {
        Session session = sessionFactory.getCurrentSession();
        Office office = (Office)session.get(Office.class, id);
        
        return office;    
    }

    @Override
    public Office getByName(String name)
    {
        Session session = sessionFactory.getCurrentSession();
        Office org = (Office)session.createQuery("FROM Office O WHERE O.name = " + name).uniqueResult();
        
        return org;
    }

    @Override
    public List<Office> getAll()
    {
        Session session = sessionFactory.getCurrentSession();
        List<Office> result = session.createQuery("FROM Office O ORDER BY O.name ASC").list();
        
        return result;
    }
    
}
