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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.ccms.dao.EmployeeDAO;
import ru.codemine.ccms.dao.TaskDAO;
import ru.codemine.ccms.entity.Employee;
import ru.codemine.ccms.entity.Task;

/**
 *
 * @author Alexander Savelev
 */

@Service
public class TaskService 
{
    private static final Logger log = Logger.getLogger("TaskService");
    
    @Autowired
    private TaskDAO taskDAO;
    
    @Autowired
    private EmployeeDAO employeeDAO;
    
    @Transactional
    public void create(Task task)
    {
        if(task.hasPerformer())
            task.assign(task.getPerformer());
        
        taskDAO.create(task);
    }
    
    @Transactional
    public void delete(Task task)
    {
        taskDAO.delete(task);
    }
    
    @Transactional
    public void delete(Integer id)
    {
        taskDAO.delete(id);
    }
    
    @Transactional
    public void update(Task task)
    {
        taskDAO.update(task);
    }
    
    @Transactional
    public Task getById(Integer id)
    {
        return taskDAO.getById(id);
    }
    
    @Transactional
    public List<Task> getByCreator(Employee creator)
    {
        return taskDAO.getByCreator(creator);
    }
    
    @Transactional
    public List<Task> getByPerformer(Employee performer)
    {
        return taskDAO.getByPerformer(performer);
    }
    
    @Transactional
    public List<Task> getByStatus(Task.Status status)
    {
        return taskDAO.getByStatus(status);
    }
    
    @Transactional
    public List<Task> gedOverdue()
    {
        return taskDAO.gedOverdue();
    }
    
    @Transactional
    public List<Task> getByCreatorAndStatus(Employee creator, Task.Status status)
    {
        return taskDAO.getByCreatorAndStatus(creator, status);
    }
    
    @Transactional
    public List<Task> getByPerformerAndStatus(Employee performer, Task.Status status)
    {
        return taskDAO.getByPerformerAndStatus(performer, status);
    }
    
    @Transactional
    public List<Task> getOverdueByCreator(Employee creator)
    {
        return taskDAO.getOverdueByCreator(creator);
    }
    
    @Transactional
    public List<Task> getOverdueByPerformer(Employee performer)
    {
        return taskDAO.getOverdueByPerformer(performer);
    }
    
    @Transactional
    public List<Task> getByCreatorNotClosed(Employee creator)
    {
        return taskDAO.getByCreatorNotClosed(creator);
    }
    
    @Transactional
    public List<Task> getByPerformerNotClosed(Employee performer)
    {
        return taskDAO.getByPerformerNotClosed(performer);
    }
    
    @Transactional
    public List<Task> getAll()
    {
        return taskDAO.getAll();
    }
    
    @Transactional
    public Integer getUserActiveTaskCount(Employee performer)
    {
        if(performer != null)
        {
            Integer count = taskDAO.getPerfTaskCount(performer);
            return count;
        }
        
        return 0;
    }
    
    @Transactional
    public Integer getOpenTaskCount()
    {
        return taskDAO.getOpenTaskCount();
    }

}
