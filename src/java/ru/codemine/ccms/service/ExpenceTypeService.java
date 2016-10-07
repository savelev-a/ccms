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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.ccms.dao.ExpenceTypeDAO;
import ru.codemine.ccms.entity.ExpenceType;

/**
 *
 * @author Alexander Savelev
 */

@Service
@Transactional
public class ExpenceTypeService 
{
    @Autowired private ExpenceTypeDAO expenceTypeDAO;
    
    public void create(ExpenceType type)
    {
        expenceTypeDAO.create(type);
    }
    
    public void delete(ExpenceType type)
    {
        expenceTypeDAO.delete(type);
    }
    
    public void deleteById(Integer id)
    {
        expenceTypeDAO.deleteById(id);
    }
    
    public void update(ExpenceType type)
    {
        expenceTypeDAO.update(type);
    }
    
    public ExpenceType getById(Integer id)
    {
        return expenceTypeDAO.getById(id);
    }
    
    public List<ExpenceType> getAll()
    {
        return expenceTypeDAO.getAll();
    }

    public ExpenceType getByName(String expenceTypeName)
    {
        return expenceTypeDAO.getByName(expenceTypeName);
    }
}
