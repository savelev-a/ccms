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
public class ExpenceType implements Serializable
{
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "description", length = 128, nullable = false)
    @Length(max = 128, message = "Слишком длинное значение")
    private String description;
    
    @Column(name = "recurrent", nullable = false)
    private boolean recurrent;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean isRecurrent()
    {
        return recurrent;
    }

    public void setRecurrent(boolean recurrent)
    {
        this.recurrent = recurrent;
    }

    @Override
    public String toString()
    {
        return "ExpenceType{" + "id=" + id + ", description=" + description + ", recurrent=" + recurrent + '}';
    }
    
    
    
    
}
