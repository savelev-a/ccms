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

package ru.codemine.ccms.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import org.apache.log4j.Logger;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 *
 * @author Alexander Savelev
 */

@Entity
@Table(name = "tasks")
public class Task implements Serializable
{
    
    private static final Logger log = Logger.getLogger("Task");

    public enum Status
    {
        NEW,
        ASSIGNED,
        INWORK,
        PAUSED,
        CLOSED
    }
    
    public enum Urgency
    {
        LOW,
        MEDIUM,
        HIGH,
        CRITICAL
    }
    
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Length(max = 128)
    @Column(name = "title", length = 128, nullable = false)
    private String title;
    
    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    private String text;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Employee creator;
    
    @Column(name = "creationTime", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime creationTime;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "task_employees", 
            joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "performer_id", referencedColumnName = "id"))
    private Set<Employee> performers;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private Status status;
    
    @Column(name = "deadline", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime deadline;
    
    @Column(name = "closeTime", nullable = true)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime closeTime;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "urgency", nullable = false)
    private Urgency urgency;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "task_files",
            joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "file_id", referencedColumnName = "id"))
    private Set<DataFile> files;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "task_comm", 
            joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "comm_id", referencedColumnName = "id"))
    @OrderBy(value = "id ASC")
    private Set<Comment> comments;

        
    public Task()
    {
        this.creationTime = DateTime.now();
        this.status = Status.NEW;
        this.urgency = Urgency.MEDIUM;
        this.deadline = DateTime.now().plusDays(3);
        this.comments = new LinkedHashSet<>();
        this.title = "";
        this.text = "";
        this.performers = new LinkedHashSet<>();
        this.files = new LinkedHashSet<>();
    }
    
    public Task(Employee creator)
    {
        this.creationTime = DateTime.now();
        this.status = Status.NEW;
        this.creator = creator;
        this.urgency = Urgency.MEDIUM;
        this.deadline = DateTime.now().plusDays(3);
        this.comments = new LinkedHashSet<>();
        this.title = "";
        this.text = "";
        this.performers = new LinkedHashSet<>();
        this.files = new LinkedHashSet<>();
    }
    
    
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public Employee getCreator()
    {
        return creator;
    }

    public void setCreator(Employee creator)
    {
        this.creator = creator;
    }

    public DateTime getCreationTime()
    {
        return creationTime;
    }

    public void setCreationTime(DateTime creationTime)
    {
        this.creationTime = creationTime;
    }

    public Set<Employee> getPerformers()
    {
        return performers;
    }

    public void setPerformers(Set<Employee> performers)
    {
        this.performers = performers;
    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public DateTime getCloseTime()
    {
        return closeTime;
    }

    public void setCloseTime(DateTime closeTime)
    {
        this.closeTime = closeTime;
    }

    public Urgency getUrgency()
    {
        return urgency;
    }

    public void setUrgency(Urgency urgency)
    {
        this.urgency = urgency;
    }

    public Set<Comment> getComments()
    {
        return comments;
    }

    public void setComments(Set<Comment> comments)
    {
        this.comments = comments;
    }
    
    public void addComment(Comment comment)
    {
        comments.add(comment);
    }

    public DateTime getDeadline()
    {
        return deadline;
    }

    public void setDeadline(DateTime deadline)
    {
        this.deadline = deadline;
    }

    public Set<DataFile> getFiles()
    {
        return files;
    }

    public void setFiles(Set<DataFile> files)
    {
        this.files = files;
    }

    @Override
    public String toString()
    {
        return "Task{" 
                + "id=" + id 
                + ", title=" + title 
                + ", text=" + text 
                + ", creator=" + creator 
                + ", creationTime=" + creationTime 
                + ", status=" + status 
                + ", deadline=" + deadline 
                + ", closeTime=" + closeTime 
                + ", urgency=" + urgency + '}';
    }
    


    public boolean hasPerformer()
    {
        return (this.performers != null && !this.performers.isEmpty());
    }
    
    public boolean hasPerformer(Employee performer)
    {
        return (performer != null && this.performers.contains(performer));
    }
    
    public boolean isInTask(Employee employee)
    {
        return employee != null && (employee.equals(getCreator()) || getPerformers().contains(employee));
    }
    
    
    public boolean isOverdued()
    {
        return DateTime.now().isAfter(deadline) && status != Status.CLOSED;
    }
    
    public boolean isClosed()
    {
        return status == Status.CLOSED;
    }
    
    public String getUrgencyString()
    {
        switch(urgency)
        {
            case CRITICAL:
                return "Чрезвычайный";
            case HIGH:
                return "Высокий";
            case LOW:
                return "Низкий";
        }
        
        return "Средний";
    }
    
    public String getStatusString()
    {
        switch(status)
        {
            case NEW:
                return "Новая";
            case ASSIGNED:
                return "Назначена";
            case INWORK:
                return "В работе";
            case PAUSED:
                return "Приостановлена";
        }
        
        return "Закрыта";
    }
    
    public String getDeadlineString()
    {
        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .appendDays()
                .appendSuffix(" день", " дней")
                .appendSeparator(" ")
                .appendHours()
                .appendSuffix(" час", " часов")
                .appendSeparator(" ")
                .appendMinutes()
                .appendSuffix(" минута", " минут")
                .toFormatter();
        
        if(!isOverdued())
        {
            Period deadlinePeriod = new Period(DateTime.now(), deadline);
            return formatter.print(deadlinePeriod);
        }
        
        Period deadlinePeriod = new Period(deadline, DateTime.now());
        return "Просрочено на " + formatter.print(deadlinePeriod);
        
    }
    
    public String getProgressTimeStr()
    {
        if(!isClosed() || getCloseTime() == null) return "";
        
        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .appendDays()
                .appendSuffix(" день", " дней")
                .appendSeparator(" ")
                .appendHours()
                .appendSuffix(" час", " часов")
                .appendSeparator(" ")
                .appendMinutes()
                .appendSuffix(" минута", " минут")
                .toFormatter();
        
        Period progressPeriod = new Period(getCreationTime(), getCloseTime());
        
        return formatter.print(progressPeriod);
    }
    

}
