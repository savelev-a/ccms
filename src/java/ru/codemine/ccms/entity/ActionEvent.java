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
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 *
 * @author Alexander Savelev
 */

@Entity
@Table(name = "actionEvents")
public class ActionEvent implements Serializable
{
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Length(max = 128)
    @Column(name = "title", length = 128, nullable = false)
    private String title;
    
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "creationTime", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime creationTime;
    
    @Column(name = "startDate", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate startDate;
    
    @Column(name = "endDate", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate endDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Employee creator;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "actions_shops", 
            joinColumns = @JoinColumn(name = "action_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "shop_id", referencedColumnName = "id"))
    private Set<Shop> affectedShops;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "action_files",
            joinColumns = @JoinColumn(name = "action_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "file_id", referencedColumnName = "id"))
    private Set<DataFile> files;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "action_comm", 
            joinColumns = @JoinColumn(name = "action_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "comm_id", referencedColumnName = "id"))
    @OrderBy(value = "id ASC")
    private Set<Comment> comments;
    
    @Column(name = "notify_sent", nullable = false)
    private boolean notificationSent;
    
    
    public ActionEvent()
    {
        this.title = "";
        this.description = "";
        this.creationTime = DateTime.now();
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now();

        this.affectedShops = new LinkedHashSet<>();
        this.files = new LinkedHashSet<>();
        this.comments = new LinkedHashSet<>();
        
        this.notificationSent = false;
    }

    public ActionEvent(Employee creator)
    {
        this.title = "";
        this.description = "";
        this.creator = creator;
        this.creationTime = DateTime.now();
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now();

        this.affectedShops = new LinkedHashSet<>();
        this.files = new LinkedHashSet<>();
        this.comments = new LinkedHashSet<>();
        
        this.notificationSent = false;
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

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public DateTime getCreationTime()
    {
        return creationTime;
    }

    public void setCreationTime(DateTime creationTime)
    {
        this.creationTime = creationTime;
    }

    public LocalDate getStartDate()
    {
        return startDate;
    }

    public void setStartDate(LocalDate startDate)
    {
        this.startDate = startDate;
    }

    public LocalDate getEndDate()
    {
        return endDate;
    }

    public void setEndDate(LocalDate endDate)
    {
        this.endDate = endDate;
    }

    public Employee getCreator()
    {
        return creator;
    }

    public void setCreator(Employee creator)
    {
        this.creator = creator;
    }

    public Set<Shop> getAffectedShops()
    {
        return affectedShops;
    }

    public void setAffectedShops(Set<Shop> affectedShops)
    {
        this.affectedShops = affectedShops;
    }

    public Set<DataFile> getFiles()
    {
        return files;
    }

    public void setFiles(Set<DataFile> files)
    {
        this.files = files;
    }

    public Set<Comment> getComments()
    {
        return comments;
    }

    public void setComments(Set<Comment> comments)
    {
        this.comments = comments;
    }

    public boolean isNotificationSent()
    {
        return notificationSent;
    }

    public void setNotificationSent(boolean notificationSent)
    {
        this.notificationSent = notificationSent;
    }

    @Override
    public String toString()
    {
        return "ActionEvent{" 
                + "id=" + id 
                + ", title=" + title 
                + ", description=" + description 
                + ", creationTime=" + creationTime 
                + ", startDate=" + startDate 
                + ", endDate=" + endDate 
                + ", creator=" + creator 
                + ", notificationSent=" + notificationSent + '}';
    }
    
    
    
    public boolean isActive()
    {
        return (getStartDate().isBefore(LocalDate.now()) && getEndDate().isAfter(LocalDate.now()));
    }
    
    public String getShoplistStr()
    {
        String result = "";
        for(Shop shop : getAffectedShops())
        {
            result += shop.getName();
            result += "&#10;";
        }
        
        return result;
    }

}
