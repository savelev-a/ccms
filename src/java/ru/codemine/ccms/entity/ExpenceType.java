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

import ru.codemine.ccms.entity.interfaces.Hyperlinkable;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Alexander Savelev
 */

@Entity
@Table(name = "expenceTypes")
public class ExpenceType implements Serializable, Comparable<ExpenceType>, Hyperlinkable
{
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "name", length = 128, nullable = false, unique = true)
    @Length(max = 128, message = "Слишком длинное значение")
    private String name;
    
    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    @Override
    public String toString()
    {
        return "ExpenceType{" + "id=" + id + ", name=" + name + ", comment=" + comment + '}';
    }

    @Override
    public int compareTo(ExpenceType o)
    {
        return this.getName().compareTo(o.getName());
    }

    @Override
    public String getLinkTarget()
    {
        return "/management/expencetypes/edit?id=" + getId();
    }

    @Override
    public String getLinkCaption()
    {
        return getName();
    }

    @Override
    public String getLinkAdminTarget()
    {
        return getLinkTarget();
    }
    
    
    
    
}
