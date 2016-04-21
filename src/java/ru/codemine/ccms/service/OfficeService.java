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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.ccms.dao.OfficeDAO;
import ru.codemine.ccms.entity.Office;

/**
 *
 * @author Alexander Savelev
 */
@Service
public class OfficeService
{
    private static final Logger log = Logger.getLogger("OfficeService");
    
    @Autowired
    private OfficeDAO officeDAO;
    
    @Transactional
    public void create(Office office)
    {
        officeDAO.create(office);
    }
    
    @Transactional
    public void delete(Office office)
    {
        officeDAO.delete(office);
    }
    
    @Transactional
    public void deleteById(Integer id)
    {
        officeDAO.deleteById(id);
    }
    
    @Transactional
    public void update(Office office)
    {
        officeDAO.update(office);
    }
    
    @Transactional
    public Office getById(Integer id)
    {
        return officeDAO.getById(id);
    }
    
    @Transactional
    public Office getByName(String name)
    {
        return officeDAO.getByName(name);
    }
    
    @Transactional
    public List<Office> getAll()
    {
        return officeDAO.getAll();
    }
    
}
