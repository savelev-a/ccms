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
import ru.codemine.ccms.entity.Employee;
import ru.codemine.ccms.entity.Task;

/**
 *
 * @author Alexander Savelev
 */

public interface TaskDAO 
{
    public void create(Task task);
    
    public void delete(Task task);
    public void delete(Integer id);
    
    public void update(Task task);
    
    public Task getById(Integer id);
    public List<Task> getByCreator(Employee creator);
    public List<Task> getByPerformer(Employee performer);
    public List<Task> getByStatus(Task.Status status);
    public List<Task> gedOverdue();
    
    public List<Task> getByCreatorAndStatus(Employee creator, Task.Status status);
    public List<Task> getByPerformerAndStatus(Employee performer, Task.Status status);
    public List<Task> getOverdueByCreator(Employee creator);
    public List<Task> getOverdueByPerformer(Employee performer);
    
    public List<Task> getByCreatorNotClosed(Employee creator);
    public List<Task> getByPerformerNotClosed(Employee performer);
    
    public List<Task> getAll();
    
    public Integer getPerfTaskCount(Employee performer);
    public Integer getOpenTaskCount();

}
