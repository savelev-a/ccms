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

import com.sun.org.apache.bcel.internal.generic.NEW;
import java.io.Serializable;
import java.util.List;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.joda.time.DateTime;

/**
 *
 * @author Alexander Savelev
 */

@Entity
@Table(name = "tasks")
public class Task implements Serializable
{
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
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id")
    private Employee creator;
    
    @Column(name = "creationTime", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime creationTime;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "performer_id")
    private Employee performer;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private Status status;
    
    @Column(name = "closeTime", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime closeTime;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "urgency", nullable = false)
    private Urgency urgency;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "task_comm", 
            joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "comm_id", referencedColumnName = "id"))
    @OrderBy(value = "creationTime ASC")
    private List<Comment> comments;

    
    
    
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

    public Employee getPerformer()
    {
        return performer;
    }

    public void setPerformer(Employee performer)
    {
        this.performer = performer;
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

    public List<Comment> getComments()
    {
        return comments;
    }

    public void setComments(List<Comment> comments)
    {
        this.comments = comments;
    }
    
    
    
    

}
