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
import ru.codemine.ccms.dao.ActionEventDAO;
import ru.codemine.ccms.entity.ActionEvent;
import ru.codemine.ccms.entity.Employee;
import ru.codemine.ccms.entity.Shop;

/**
 *
 * @author Alexander Savelev
 */

@Service
@Transactional
public class ActionEventService 
{
    @Autowired private ActionEventDAO actionEventDAO;
    
    public void create(ActionEvent action)
    {
        actionEventDAO.create(action);
    }
    
    public void delete(ActionEvent action)
    {
        actionEventDAO.delete(action);
    }
    
    public void delete(Integer id)
    {
        actionEventDAO.deleteById(id);
    }
    
    public void update(ActionEvent action)
    {
        actionEventDAO.update(action);
    }
    
    public ActionEvent getById(Integer id)
    {
        return actionEventDAO.getById(id);
    }
    
    public List<ActionEvent> getByCreator(Employee creator)
    {
        return actionEventDAO.getByCreator(creator);
    }
    
    public List<ActionEvent> getByShop(Shop shop)
    {
        return actionEventDAO.getByShop(shop);
    }
    
    public List<ActionEvent> getActiveEvents()
    {
        return actionEventDAO.getActiveEvents();
    }
    
    public List<ActionEvent> getActiveEvents(Shop shop)
    {
        return actionEventDAO.getActiveEvents(shop);
    }
    
    public List<ActionEvent> getAll()
    {
        return actionEventDAO.getAll();
    }

    public List<ActionEvent> getCurrentFuture()
    {
        return actionEventDAO.getCurrentFuture();
    }

}
