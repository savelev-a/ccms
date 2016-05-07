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
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Range;
import org.joda.time.LocalDate;

/**
 *
 * @author Alexander Savelev
 */

@Entity
@Table(name = "sales")
public class Sales implements Serializable, Comparable<Sales>
{
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "s_date", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate date;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "s_shop_id")
    private Shop shop;
    
    //@Digits(integer = 5, fraction = 0)
    @Range(min = 0)
    @NotNull
    @Column(name = "passability", nullable = false)
    private Integer passability;
    
    //@Digits(integer = 5, fraction = 0)
    @Range(min = 0)
    @NotNull
    @Column(name = "cheque_count", nullable = false)
    private Integer chequeCount;
    
    //@Digits(integer = 5, fraction = 2)
    @Range(min = 0)
    @NotNull
    @Column(name = "s_value", nullable = false)
    private Double value;
    
    //@Digits(integer = 5, fraction = 2)
    @Range(min = 0)
    @NotNull
    @Column(name = "s_cashback", nullable = false)
    private Double cashback;

    public Sales(){}
    
    public Sales(Shop shop, LocalDate date)
    {
        this.cashback = 0.0;
        this.chequeCount = 0;
        this.date = date;
        this.shop = shop;
        this.passability = 0;
        this.value = 0.0;
    }
    
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
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

    public Integer getPassability()
    {
        return passability;
    }

    public void setPassability(Integer passability)
    {
        this.passability = passability;
    }
    
    
    
    //
    // Вычисляемые поля
    //
    
    /**
     * Возвращает общий итог продаж за день учетом возвратов
     * @return
     */
    public Double getDayTotal()
    {
        return value - cashback;
    }
    
    /**
     * Возвращает средний чек за день
     * @return
     */
    public Double getMidPrice()
    {
        return getDayTotal() / chequeCount;
    }
    
    /**
     * Используетсф для формирования данных для графиков jquery flot
     * @return
     */
    public String getGraphDataDayTotal()
    {
        return "[\"" + date.toString("dd.MM.yyyy") + "\", " + getDayTotal() + "]";
    }
    
    /**
     * Используетсф для формирования данных для графиков jquery flot
     * @return
     */
    public String getGraphDataPassability()
    {
        return "[\"" + date.toString("dd.MM.yyyy") + "\", " + getPassability() + "]";
    }

    @Override
    public int compareTo(Sales s)
    {
        if(this.date.equals(s.date)) return 0;
        if(this.date.isBefore(s.date)) return -1;
        
        return 1;
    }


    

}
