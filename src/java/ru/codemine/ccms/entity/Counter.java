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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 *
 * @author Alexander Savelev
 */

@Entity
@Table(name = "counters")
public class Counter implements Serializable 
{
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "c_shop_id")
    private Shop shop;
    
    @Column(name = "c_date", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime date;
    
    @Column(name = "c_in", nullable = false)
    private Integer in;
    
    @Column(name = "c_out", nullable = false)
    private Integer out;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Shop getShop()
    {
        return shop;
    }

    public void setShop(Shop shop)
    {
        this.shop = shop;
    }

    public DateTime getDate()
    {
        return date;
    }

    public void setDate(DateTime date)
    {
        this.date = date;
    }

    public Integer getIn()
    {
        return in;
    }

    public void setIn(Integer in)
    {
        this.in = in;
    }

    public Integer getOut()
    {
        return out;
    }

    public void setOut(Integer out)
    {
        this.out = out;
    }
    
    public String getGraphDataIn()
    {
        return "[\"" + date.toString("dd.MM.yyyy") + "\", " + in + "]";
    }
    
    public String getGraphDataOut()
    {
        return "[\"" + date.toString("dd.MM.yyyy") + "\", " + out + "]";
    }

    @Override
    public String toString()
    {
        return "{id=" + id + ", shop=" + shop.getName() + ", date=" + date + ", in=" + in + ", out=" + out + "}";
    }
    
    
    

}
