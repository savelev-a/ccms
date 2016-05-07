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

public class SalesPlanForm
{
    private String shopname;
    private Integer passability;
    private Integer cheque;
    private Double periodtotal;
    private Double plan;
    private Double plancoverage;
    private String id;

    public String getShopname()
    {
        return shopname;
    }

    public void setShopname(String shopname)
    {
        this.shopname = shopname;
    }

    public Integer getPassability()
    {
        return passability;
    }

    public void setPassability(Integer passability)
    {
        this.passability = passability;
    }

    public Integer getCheque()
    {
        return cheque;
    }

    public void setCheque(Integer cheque)
    {
        this.cheque = cheque;
    }

    public Double getPeriodtotal()
    {
        return periodtotal;
    }

    public void setPeriodtotal(Double periodtotal)
    {
        this.periodtotal = periodtotal;
    }

    public Double getPlan()
    {
        return plan;
    }

    public void setPlan(Double plan)
    {
        this.plan = plan;
    }

    public Double getPlancoverage()
    {
        return plancoverage;
    }

    public void setPlancoverage(Double plancoverage)
    {
        this.plancoverage = plancoverage;
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
        return "SalesPlanForm{" + "shopname=" + shopname + ", passability=" + passability + ", cheque=" + cheque + ", periodtotal=" + periodtotal + ", plan=" + plan + ", plancoverage=" + plancoverage + ", id=" + id + '}';
    }
    
    
    
}
