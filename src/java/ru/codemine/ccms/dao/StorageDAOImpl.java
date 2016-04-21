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

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.codemine.ccms.entity.DataFile;

/**
 *
 * @author Alexander Savelev
 */

@Repository
public class StorageDAOImpl implements StorageDAO
{
    private static final Logger log = Logger.getLogger("StorageDAO");
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public void create(DataFile dataFile)
    {
        log.info("Creating new file record: " + dataFile.getFilename());
        Session session = sessionFactory.getCurrentSession();
        session.save(dataFile);
    }

    @Override
    public void delete(DataFile dataFile)
    {
        log.info("Removing file record: " + dataFile.getFilename());
        Session session = sessionFactory.getCurrentSession();
        session.delete(dataFile);
    }

    @Override
    public void deleteById(Integer id)
    {
        log.info("Removing file record by id: " + id.toString());
        Session session = sessionFactory.getCurrentSession();
        DataFile dataFile = getById(id);
        
        if(dataFile != null) session.delete(dataFile);
    }

    @Override
    public void update(DataFile dataFile)
    {
        log.info("Updating file record: " + dataFile.getFilename());
        Session session = sessionFactory.getCurrentSession();
        session.update(dataFile);
    }

    @Override
    public DataFile getById(Integer id)
    {
        Session session = sessionFactory.getCurrentSession();
        DataFile dataFile = (DataFile)session.get(DataFile.class, id);
        
        return dataFile;
    }
    
}
