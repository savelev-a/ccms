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
import ru.codemine.ccms.entity.Organisation;

/**
 *
 * @author Alexander Savelev
 */

@Repository
public class OrganisationDAOImpl implements OrganisationDAO
{
    private static final Logger log = Logger.getLogger("OrganisationDAO");
    
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void create(Organisation org)
    {
        log.info("Creating new organisation: " + org.getName());
        Session session = sessionFactory.getCurrentSession(); 
        session.save(org);
    }

    @Override
    public void delete(Organisation org)
    {
        log.info("Removing organisation: " + org.getName());
        Session session = sessionFactory.getCurrentSession();
        session.delete(org);
    }

    @Override
    public void deleteById(Integer id)
    {
        log.info("Removing organisation by id: " + id.toString());
        Session session = sessionFactory.getCurrentSession();
        Organisation org = getById(id);
        
        if(org != null) session.delete(org);
    }

    @Override
    public void update(Organisation org)
    {
        log.info("Updating organisation: " + org.getName());
        Session session = sessionFactory.getCurrentSession();
        session.update(org);
    }

    @Override
    public Organisation getById(Integer id)
    {
        Session session = sessionFactory.getCurrentSession();
        Organisation org = (Organisation)session.get(Organisation.class, id);
        
        return org;
    }

    @Override
    public Organisation getByInn(String inn)
    {
        Session session = sessionFactory.getCurrentSession();
        Organisation org = (Organisation)session.createQuery("FROM Organisation O WHERE O.inn = " + inn).uniqueResult();
        
        return org;
    }

    @Override
    public List<Organisation> getAll()
    {
        Session session = sessionFactory.getCurrentSession();
        List<Organisation> result = session.createQuery("FROM Organisation O ORDER BY O.name ASC").list();
        
        return result;
    }
    
}
