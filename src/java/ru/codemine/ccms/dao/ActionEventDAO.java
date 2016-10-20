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
import ru.codemine.ccms.entity.ActionEvent;
import ru.codemine.ccms.entity.Employee;
import ru.codemine.ccms.entity.Shop;

/**
 *
 * @author Alexander Savelev
 */
public interface ActionEventDAO extends GenericDAO<ActionEvent, Integer>
{
    public List<ActionEvent> getByCreator(Employee creator);
    public List<ActionEvent> getByShop(Shop shop);
    
    public List<ActionEvent> getActiveEvents();
    public List<ActionEvent> getActiveEvents(Shop shop);
    
    public List<ActionEvent> getAll();

    public List<ActionEvent> getCurrentFuture();
}
