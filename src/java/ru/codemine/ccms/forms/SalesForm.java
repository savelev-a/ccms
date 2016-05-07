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
package ru.codemine.ccms.forms;


/**
 *
 * @author Alexander Savelev
 */

public class SalesForm
{
    private String date;
    private Integer passability;
    private Integer chequeCount;
    private Double value;
    private Double cashback;
    private Double daytotal;
    private Double midPrice;
    private String id;

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public Integer getPassability()
    {
        return passability;
    }

    public void setPassability(Integer passability)
    {
        this.passability = passability;
    }

    public Integer getChequeCount()
    {
        return chequeCount;
    }

    public void setChequeCount(Integer chequeCount)
    {
        this.chequeCount = chequeCount;
    }

    public Double getValue()
    {
        return value;
    }

    public void setValue(Double value)
    {
        this.value = value;
    }

    public Double getCashback()
    {
        return cashback;
    }

    public void setCashback(Double cashback)
    {
        this.cashback = cashback;
    }

    public Double getDaytotal()
    {
        return daytotal;
    }

    public void setDaytotal(Double daytotal)
    {
        this.daytotal = daytotal;
    }

    public Double getMidPrice()
    {
        return midPrice;
    }

    public void setMidPrice(Double midPrice)
    {
        this.midPrice = midPrice;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "SalesForm{" + "date=" + date + ", passability=" + passability + ", chequeCount=" + chequeCount + ", value=" + value + ", cashback=" + cashback + ", daytotal=" + daytotal + ", midPrice=" + midPrice + ", id=" + id + '}';
    }
    
    
    
    
}
