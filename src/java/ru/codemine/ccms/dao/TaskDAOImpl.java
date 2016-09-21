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
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import ru.codemine.ccms.entity.Employee;
import ru.codemine.ccms.entity.Task;

/**
 *
 * @author Alexander Savelev
 */

@Repository
public class TaskDAOImpl extends GenericDAOImpl<Task, Integer> implements TaskDAO
{
    private static final Logger log = Logger.getLogger("TaskDAO");

    @Override
    public List<Task> getByCreator(Employee creator)
    {
        List<Task> result = getSession().createQuery("FROM Task t WHERE t.creator.id = " + creator.getId()).list();
        
        return result;
    }

    //TODO: stopped here
    error
    @Override
    public List<Task> getByPerformer(Employee performer)
    {
        List<Task> result = getSession().createQuery("FROM Task t WHERE t.performer.id = " + performer.getId()).list();
        
        return result;
    }

    @Override
    public List<Task> getByStatus(Task.Status status)
    {
        List<Task> result = getSession().createQuery("FROM Task t WHERE t.status = '" + status.ordinal() + "'").list();
        
        return result;
    }

    @Override
    public List<Task> getByCreatorAndStatus(Employee creator, Task.Status status)
    {
        List<Task> result = getSession().createQuery("FROM Task t WHERE t.creator.id = " + creator.getId() 
                + " AND t.status = '" + status.ordinal() + "'").list();
        
        return result;
    }

    @Override
    public List<Task> getByPerformerAndStatus(Employee performer, Task.Status status)
    {
        List<Task> result = getSession().createQuery("FROM Task t WHERE t.performer.id = " + performer.getId() 
                + " AND t.status = '" + status.ordinal() + "'").list();
        
        return result;
    }
    
    @Override
    public List<Task> getByCreatorNotClosed(Employee creator)
    {
        List<Task> result = getSession().createQuery("FROM Task t WHERE t.creator.id = " + creator.getId() 
                + " AND t.status != '" + Task.Status.CLOSED.ordinal() + "'").list();
        
        return result;
    }
    
    @Override
    public List<Task> getByPerformerNotClosed(Employee performer)
    {
        List<Task> result = getSession().createQuery("FROM Task t WHERE t.performer.id = " + performer.getId() 
                + " AND t.status != '" + Task.Status.CLOSED.ordinal() + "'").list();
        
        return result;
    }

    @Override
    public List<Task> getAll()
    {
        List<Task> result = getSession().createQuery("FROM Task").list();
        
        return result;
    }

    @Override
    public List<Task> gedOverdue()
    {
        Query query = getSession().createQuery("FROM Task t WHERE t.deadline < :now AND t.status != '" + Task.Status.CLOSED.ordinal() + "'");
        query.setTimestamp("now", DateTime.now().toDate());
        List<Task> result = query.list();
        
        return result;
    }

    @Override
    public List<Task> getOverdueByCreator(Employee creator)
    {
        Query query = getSession().createQuery("FROM Task t WHERE t.deadline < :now AND t.creator.id = " + creator.getId()
                + " AND t.status != '" + Task.Status.CLOSED.ordinal() + "'");
        query.setTimestamp("now", DateTime.now().toDate());
        List<Task> result = query.list();
        
        return result;
    }

    @Override
    public List<Task> getOverdueByPerformer(Employee performer)
    {
        Query query = getSession().createQuery("FROM Task t WHERE t.deadline < :now AND t.performer.id = " + performer.getId() 
                + " AND t.status != '" + Task.Status.CLOSED.ordinal() + "'");
        query.setTimestamp("now", DateTime.now().toDate());
        List<Task> result = query.list();
        
        return result;
    }
    
    @Override
    public Integer getPerfTaskCount(Employee performer)
    {
        Long count = (Long)getSession().createQuery(
                "SELECT COUNT(*) FROM Task t WHERE t.performer.id = " + performer.getId()
                        + " AND t.status != '" + Task.Status.CLOSED.ordinal() + "'").uniqueResult();
        return count == null ? 0 : count.intValue();
    }
    
    @Override
    public Integer getOpenTaskCount()
    {
        Long count = (Long)getSession().createQuery(
                "SELECT COUNT(*) FROM Task t WHERE t.status = '" + Task.Status.NEW.ordinal() + "'").uniqueResult();
        return count == null ? 0 : count.intValue();
    }

}
