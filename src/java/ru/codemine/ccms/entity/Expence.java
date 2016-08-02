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
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

/**
 *
 * @author Alexander Savelev
 */

@Entity
@Table(name = "expences")
public class Expence implements Serializable, Comparable<Expence>
{
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "ex_date", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate date;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id", nullable = false)
    private ExpenceType type;
    
    @Column(name = "ex_value", nullable = false)
    private Double value;
    
    @Column(name = "recurrent", nullable = false)
    private boolean recurrent;

    public Expence(){}
    
    public Expence(Shop shop)
    {
        this.shop = shop;
    }
    
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public Shop getShop()
    {
        return shop;
    }

    public void setShop(Shop shop)
    {
        this.shop = shop;
    }

    public ExpenceType getType()
    {
        return type;
    }

    public void setType(ExpenceType type)
    {
        this.type = type;
    }

    public Double getValue()
    {
        return value;
    }

    public void setValue(Double value)
    {
        this.value = value;
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
    public int hashCode()
    {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Expence other = (Expence) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Expence e)
    {
        if(date.equals(e.getDate())) return 0;
        if(date.isBefore(e.getDate())) return -1;
        
        return 1;
    }

    @Override
    public String toString()
    {
        return "Expence{" + "id=" + id + ", date=" + date + ", shop=" + shop + ", type=" + type + ", value=" + value + ", recurrent=" + recurrent + '}';
    }
    
    
    
}
