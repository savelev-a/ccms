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

package ru.codemine.ccms.router.api.form;

/**
 *
 * @author Alexander Savelev
 */
public class SalesJson 
{
    private String startDate;
    private String endDate;
    private Integer chequeCount;
    private Double kkmValue;
    private Double cashback;
    private Double totalValue;

    public String getStartDate()
    {
        return startDate;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    public Integer getChequeCount()
    {
        return chequeCount;
    }

    public void setChequeCount(Integer chequeCount)
    {
        this.chequeCount = chequeCount;
    }

    public Double getKkmValue()
    {
        return kkmValue;
    }

    public void setKkmValue(Double kkmValue)
    {
        this.kkmValue = kkmValue;
    }

    public Double getCashback()
    {
        return cashback;
    }

    public void setCashback(Double cashback)
    {
        this.cashback = cashback;
    }

    public Double getTotalValue()
    {
        return totalValue;
    }

    public void setTotalValue(Double totalValue)
    {
        this.totalValue = totalValue;
    }
    
    
    
}
