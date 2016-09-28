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
import java.util.Set;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.ccms.dao.EmployeeDAO;
import ru.codemine.ccms.dao.TaskDAO;
import ru.codemine.ccms.entity.Comment;
import ru.codemine.ccms.entity.Employee;
import ru.codemine.ccms.entity.Task;

/**
 *
 * @author Alexander Savelev
 */

@Service
@Transactional
public class TaskService 
{
    private static final Logger log = Logger.getLogger("TaskService");
    
    @Autowired private TaskDAO taskDAO;
    @Autowired private EmployeeDAO employeeDAO;
    
    public void create(Task task)
    {
        if(task.hasPerformer())
            assign(task, task.getPerformers());
        
        taskDAO.create(task);
    }

    public void delete(Task task)
    {
        taskDAO.delete(task);
    }
    
    public void delete(Integer id)
    {
        taskDAO.deleteById(id);
    }
    
    public void update(Task task)
    {
        taskDAO.update(task);
    }
    
    public Task getById(Integer id)
    {
        return taskDAO.getById(id);
    }
    
    public List<Task> getByCreator(Employee creator)
    {
        return taskDAO.getByCreator(creator);
    }
    
    public List<Task> getByPerformer(Employee performer)
    {
        return taskDAO.getByPerformer(performer);
    }
    
    public List<Task> getByStatus(Task.Status status)
    {
        return taskDAO.getByStatus(status);
    }
    
    public List<Task> gedOverdue()
    {
        return taskDAO.gedOverdue();
    }
    
    public List<Task> getByCreatorAndStatus(Employee creator, Task.Status status)
    {
        return taskDAO.getByCreatorAndStatus(creator, status);
    }
    
    public List<Task> getByPerformerAndStatus(Employee performer, Task.Status status)
    {
        return taskDAO.getByPerformerAndStatus(performer, status);
    }
    
    public List<Task> getOverdueByCreator(Employee creator)
    {
        return taskDAO.getOverdueByCreator(creator);
    }
    
    public List<Task> getOverdueByPerformer(Employee performer)
    {
        return taskDAO.getOverdueByPerformer(performer);
    }
    
    public List<Task> getByCreatorNotClosed(Employee creator)
    {
        return taskDAO.getByCreatorNotClosed(creator);
    }
    
    public List<Task> getByPerformerNotClosed(Employee performer)
    {
        return taskDAO.getByPerformerNotClosed(performer);
    }
    
    public List<Task> getAll()
    {
        return taskDAO.getAll();
    }
    
    public Integer getUserActiveTaskCount(Employee performer)
    {
        if(performer != null)
        {
            Integer count = taskDAO.getPerfTaskCount(performer);
            return count;
        }
        
        return 0;
    }
    
    public Integer getOpenTaskCount()
    {
        return taskDAO.getOpenTaskCount();
    }
    
    public void assign(Task task, Employee performer)
    {
        if(performer == null || task == null) return;
        
        task.getPerformers().add(performer);
        if(task.getStatus() == Task.Status.NEW || task.getStatus() == Task.Status.CLOSED) task.setStatus(Task.Status.ASSIGNED);

        Comment comment = new Comment();
        comment.setTitle("Сотрудник добавлен в исполнители");
        
        String commText = "Исполнитель: " + performer.getFullName();
        comment.setText(commText);
        
        task.addComment(comment);
        
        taskDAO.update(task);
        
    }
    
    public void assign(Task task, Set<Employee> performers)
    {
        if(performers == null || performers.isEmpty() || task == null) return;
        
        for(Employee performer : performers)
        {
            task.getPerformers().add(performer);
        }
        if(task.getStatus() == Task.Status.NEW || task.getStatus() == Task.Status.CLOSED) task.setStatus(Task.Status.ASSIGNED);

        Comment comment = new Comment();
        comment.setTitle("Задача назначена сотрудникам");
        
        String commText = "Исполнители: \n";
        for(Employee performer : performers) 
        {
            commText += performer.getFullName();
            commText += "\n";
        }
        comment.setText(commText);
        
        task.addComment(comment);
        
        taskDAO.update(task);
    }
    
    public void drop(Task task)
    {
        Comment comment = new Comment();
        comment.setTitle("Задача возвращена в свободные");
        task.addComment(comment);

        
        task.getPerformers().clear();
        task.setStatus(Task.Status.NEW);
        
        taskDAO.update(task);
    }
    
    public void drop(Task task, Employee performer)
    {
        if(performer == null || task == null || !task.getPerformers().contains(performer)) return;
        
        Comment comment = new Comment();
        comment.setTitle("Сотрудник прекратил выполнение задачи");
        String commText = "Сотрудник: " + performer.getFullName();
        comment.setText(commText);
        
        task.addComment(comment);
        
        task.getPerformers().remove(performer);
        
        if(task.getPerformers().isEmpty()) 
            drop(task);
        
        taskDAO.update(task);
    }
    
    public void drop(Task task, Set<Employee> performers)
    {
        if(performers == null || performers.isEmpty() || task == null) return;
        
        Comment comment = new Comment();
        comment.setTitle("Сотрудники прекратил выполнение задачи");
        String commText = "Сотрудники: \n" ;
        for(Employee preformer : performers)
        {
            commText += preformer.getFullName();
            commText += "\n";
        }
        
        comment.setText(commText);
        
        task.addComment(comment);
        
        for(Employee performer : performers)
            task.getPerformers().remove(performer);
        
        if(task.getPerformers().isEmpty()) 
            drop(task);
        
        taskDAO.update(task);
    }
    
    public void close(Task task)
    {
        task.setCloseTime(DateTime.now());
        task.setStatus(Task.Status.CLOSED);
        
        Comment comment = new Comment();
        comment.setTitle("Задача закрыта");
        task.addComment(comment);
        
        taskDAO.update(task);
    }

}
