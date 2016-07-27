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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.codemine.ccms.entity.Employee;
import ru.codemine.ccms.entity.Task;

/**
 *
 * @author Alexander Savelev
 */

@Repository
public class TaskDAOImpl implements TaskDAO
{
    private static final Logger log = Logger.getLogger("TaskDAO");
    
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void create(Task task)
    {
        log.info("New task added: " + task.getTitle());
        Session session = sessionFactory.getCurrentSession();
        session.save(task);
    }

    @Override
    public void delete(Task task)
    {
        log.info("Task deleted: " + task.getTitle());
        Session session = sessionFactory.getCurrentSession();
        session.delete(task);
    }

    @Override
    public void delete(Integer id)
    {
        log.info("Task deleted: ID" + id);
        Session session = sessionFactory.getCurrentSession();
        Task task = getById(id);
        
        if(task != null) session.delete(task);
    }

    @Override
    public void update(Task task)
    {
        log.info("Task updated: " + task.getTitle());
        Session session = sessionFactory.getCurrentSession();
        session.update(task);
    }

    @Override
    public Task getById(Integer id)
    {
        Session session = sessionFactory.getCurrentSession();
        Task task = (Task)session.createQuery("FROM Task t WHERE t.id = " + id).uniqueResult();
        
        return task;
    }

    @Override
    public List<Task> getByCreator(Employee creator)
    {
        Session session = sessionFactory.getCurrentSession();
        List<Task> result = session.createQuery("FROM Task t WHERE t.creator.id = " + creator.getId()).list();
        
        return result;
    }

    @Override
    public List<Task> getByPerformer(Employee performer)
    {
        Session session = sessionFactory.getCurrentSession();
        List<Task> result = session.createQuery("FROM Task t WHERE t.performer.id = " + performer.getId()).list();
        
        return result;
    }

    @Override
    public List<Task> getByStatus(Task.Status status)
    {
        Session session = sessionFactory.getCurrentSession();
        List<Task> result = session.createQuery("FROM Task t WHERE t.status = '" + status.ordinal() + "'").list();
        
        return result;
    }

    @Override
    public List<Task> getByCreatorAndStatus(Employee creator, Task.Status status)
    {
        Session session = sessionFactory.getCurrentSession();
        List<Task> result = session.createQuery("FROM Task t WHERE t.creator.id = " + creator.getId() 
                + " AND t.status = '" + status.ordinal() + "'").list();
        
        return result;
    }

    @Override
    public List<Task> getByPerformerAndStatus(Employee performer, Task.Status status)
    {
        Session session = sessionFactory.getCurrentSession();
        List<Task> result = session.createQuery("FROM Task t WHERE t.performer.id = " + performer.getId() 
                + " AND t.status = '" + status.ordinal() + "'").list();
        
        return result;
    }
    
    @Override
    public List<Task> getByCreatorNotClosed(Employee creator)
    {
        Session session = sessionFactory.getCurrentSession();
        List<Task> result = session.createQuery("FROM Task t WHERE t.creator.id = " + creator.getId() 
                + " AND t.status != '" + Task.Status.CLOSED.ordinal() + "'").list();
        
        return result;
    }
    
    @Override
    public List<Task> getByPerformerNotClosed(Employee performer)
    {
        Session session = sessionFactory.getCurrentSession();
        List<Task> result = session.createQuery("FROM Task t WHERE t.performer.id = " + performer.getId() 
                + " AND t.status != '" + Task.Status.CLOSED.ordinal() + "'").list();
        
        return result;
    }

    @Override
    public List<Task> getAll()
    {
        Session session = sessionFactory.getCurrentSession();
        List<Task> result = session.createQuery("FROM Task").list();
        
        return result;
    }

    @Override
    public List<Task> gedOverdue()
    {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Task t WHERE t.deadline < :now AND t.status != '" + Task.Status.CLOSED.ordinal() + "'");
        query.setTimestamp("now", DateTime.now().toDate());
        List<Task> result = query.list();
        
        return result;
    }

    @Override
    public List<Task> getOverdueByCreator(Employee creator)
    {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Task t WHERE t.deadline < :now AND t.creator.id = " + creator.getId()
                + " AND t.status != '" + Task.Status.CLOSED.ordinal() + "'");
        query.setTimestamp("now", DateTime.now().toDate());
        List<Task> result = query.list();
        
        return result;
    }

    @Override
    public List<Task> getOverdueByPerformer(Employee performer)
    {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Task t WHERE t.deadline < :now AND t.performer.id = " + performer.getId() 
                + " AND t.status != '" + Task.Status.CLOSED.ordinal() + "'");
        query.setTimestamp("now", DateTime.now().toDate());
        List<Task> result = query.list();
        
        return result;
    }
    
    @Override
    public Integer getPerfTaskCount(Employee performer)
    {
        Session session = sessionFactory.getCurrentSession();
        Long count = (Long)session.createQuery(
                "SELECT COUNT(*) FROM Task t WHERE t.performer.id = " + performer.getId()
                        + " AND t.status != '" + Task.Status.CLOSED.ordinal() + "'").uniqueResult();
        return count == null ? 0 : count.intValue();
    }
    
    @Override
    public Integer getOpenTaskCount()
    {
        Session session = sessionFactory.getCurrentSession();
        Long count = (Long)session.createQuery(
                "SELECT COUNT(*) FROM Task t WHERE t.status = '" + Task.Status.NEW.ordinal() + "'").uniqueResult();
        return count == null ? 0 : count.intValue();
    }

}
