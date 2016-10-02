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
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.Period;
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
        Query query = getSession().createQuery("FROM Task t WHERE t.creator.id = :id");
        query.setInteger("id", creator.getId());
        
        return query.list();
    }

    @Override
    public List<Task> getByPerformer(Employee performer)
    {
        Query query = getSession().createQuery("FROM Task t WHERE :performer IN ELEMENTS(t.performers)");
        query.setParameter("performer", performer);
        
        return query.list();
    }

    @Override
    public List<Task> getByStatus(Task.Status status)
    {
        Query query = getSession().createQuery("FROM Task t WHERE t.status = :status");
        query.setParameter("status", status);
        
        return query.list();
    }

    @Override
    public List<Task> getByCreatorAndStatus(Employee creator, Task.Status status)
    {
        Query query = getSession().createQuery("FROM Task t WHERE t.creator.id = :id AND t.status = :status");
        query.setInteger("id", creator.getId());
        query.setParameter("status", status);
        
        return query.list();
    }

    @Override
    public List<Task> getByPerformerAndStatus(Employee performer, Task.Status status)
    {
        Query query = getSession().createQuery("FROM Task t WHERE :performer IN ELEMENTS(t.performers) AND t.status = :status");
        query.setParameter("performer", performer);
        query.setParameter("status", status);
        
        return query.list();
    }
    
    @Override
    public List<Task> getByCreatorNotClosed(Employee creator)
    {
        Query query = getSession().createQuery("FROM Task t WHERE t.creator.id = :id AND t.status != :closedStatus");
        query.setInteger("id", creator.getId());
        query.setParameter("closedStatus", Task.Status.CLOSED);
        
        return query.list();
    }
    
    @Override
    public List<Task> getByPerformerNotClosed(Employee performer)
    {
        Query query = getSession().createQuery("FROM Task t WHERE :performer IN ELEMENTS(t.performers) AND t.status != :statusClosed");
        query.setParameter("performer", performer);
        query.setParameter("statusClosed", Task.Status.CLOSED);
        
        return query.list();
    }

    @Override
    public List<Task> getAll()
    {
        Query query = getSession().createQuery("FROM Task");
        
        return query.list();
    }

    @Override
    public List<Task> gedOverdue()
    {
        Query query = getSession().createQuery("FROM Task t WHERE t.deadline < :now AND t.status != :statusClosed");
        query.setTimestamp("now", DateTime.now().toDate());
        query.setParameter("statusClosed", Task.Status.CLOSED);
        
        return query.list();
    }

    @Override
    public List<Task> getOverdueByCreator(Employee creator)
    {
        Query query = getSession().createQuery("FROM Task t WHERE t.deadline < :now AND t.creator.id = :id AND t.status != :statusClosed");
        query.setTimestamp("now", DateTime.now().toDate());
        query.setInteger("id", creator.getId());
        query.setParameter("statusClosed", Task.Status.CLOSED);
        
        return query.list();
    }

    @Override
    public List<Task> getOverdueByPerformer(Employee performer)
    {
        Query query = getSession().createQuery("FROM Task t WHERE t.deadline < :now AND :performer IN ELEMENTS(t.performers) AND t.status != :statusClosed");
        query.setTimestamp("now", DateTime.now().toDate());
        query.setParameter("performer", performer);
        query.setParameter("statusClosed", Task.Status.CLOSED);
        
        return query.list();
    }
    
    @Override
    public Integer getPerfTaskCount(Employee performer)
    {
        Query query = getSession().createQuery("SELECT COUNT(*) FROM Task t WHERE :performer IN ELEMENTS(t.performers) AND t.status != :statusClosed");
        query.setParameter("performer", performer);
        query.setParameter("statusClosed", Task.Status.CLOSED);
        Long count = (Long)query.uniqueResult();
        
        return count == null ? 0 : count.intValue();
    }
    
    @Override
    public Integer getOpenTaskCount()
    {
        Query query = getSession().createQuery("SELECT COUNT(*) FROM Task t WHERE t.status = :statusNew");
        query.setParameter("statusNew", Task.Status.NEW);
        Long count = (Long)query.uniqueResult();
        
        return count == null ? 0 : count.intValue();
    }

    @Override
    public Integer getClosedTasksByPerformerCount(Employee performer, LocalDate startDate, LocalDate endDate)
    {
        Query query = getSession().createQuery("SELECT COUNT(*) FROM Task t WHERE :performer IN ELEMENTS(t.performers) AND t.closeTime >= :startDate AND t.closeTime <= :endDate");
        query.setParameter("performer", performer);
        query.setDate("startDate", startDate.toDate());
        query.setDate("endDate", endDate.toDate());
        
        Long count = (Long)query.uniqueResult();
        
        return count == null ? 0 : count.intValue();
    }

    @Override
    public Integer getOverdueTasksByPerformerCount(Employee performer, LocalDate startDate, LocalDate endDate)
    {
        Query query = getSession().createQuery("SELECT COUNT(*) FROM Task t WHERE :performer IN ELEMENTS(t.performers) AND t.closeTime >= :startDate AND t.closeTime <= :endDate AND t.deadline < t.closeTime");
        query.setParameter("performer", performer);
        query.setDate("startDate", startDate.toDate());
        query.setDate("endDate", endDate.toDate());
        
        Long count = (Long)query.uniqueResult();
        
        return count == null ? 0 : count.intValue();
    }

    @Override
    public Period getMidTimeByPerformer(Employee performer, LocalDate startDate, LocalDate endDate)
    {
        
        List<Task> tasks = getByPerformerAndCloseTimeInPeriod(performer, startDate, endDate);
        
        if(tasks.isEmpty()) return new Period(0);
        
        Duration totalDuration = new Duration(0);
        for(Task task : tasks)
        {
            Duration d = new Duration(task.getCreationTime(), task.getCloseTime());
            totalDuration = totalDuration.plus(d);
        }
        Duration resultDuration = totalDuration.dividedBy(tasks.size());
        
        return resultDuration.toPeriod();
    }

    @Override
    public List<Task> getByPerformerAndCloseTimeInPeriod(Employee performer, LocalDate startDate, LocalDate endDate)
    {
        Query query = getSession().createQuery("FROM Task t WHERE :performer IN ELEMENTS(t.performers) AND t.closeTime >= :startDate AND t.closeTime <= :endDate");
        query.setParameter("performer", performer);
        query.setDate("startDate", startDate.toDate());
        query.setDate("endDate", endDate.toDate());
        
        return query.list();
    }

}
