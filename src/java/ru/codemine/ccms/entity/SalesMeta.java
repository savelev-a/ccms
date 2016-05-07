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
import java.util.Set;
import java.util.TreeSet;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import org.apache.log4j.Logger;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.joda.time.LocalDate;

/**
 *
 * @author Alexander Savelev
 */

@Entity
@Table(name = "salesMeta")
public class SalesMeta implements Serializable
{
    private static final Logger log = Logger.getLogger("SalesMeta");
    
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "start_date", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate startDate;
    
    @Column(name = "end_date", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate endDate;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "s_shop_id")
    private Shop shop;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "link_sales", 
            joinColumns = @JoinColumn(name = "meta_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "sales_id", referencedColumnName = "id"))
    @OrderBy(value = "date ASC")
    private Set<Sales> sales;
    
    @Digits(integer = 10, fraction = 2)
    @Range(min = 0)
    @NotNull
    @Column(name = "plan", nullable = false)
    private Double plan;
    
    @Length(max = 255)
    @Column(name = "description", nullable = false, length = 255)
    private String description;

    
    public SalesMeta(){}
    
    public SalesMeta(Shop shop)
    {
        this.shop = shop;
        this.description = "";
        this.startDate = LocalDate.now().withDayOfMonth(1);
        this.endDate = LocalDate.now().dayOfMonth().withMaximumValue();
        this.plan = 0.0;
        
        this.sales = new TreeSet<>();
        for(int i = 1; i <= LocalDate.now().dayOfMonth().getMaximumValue(); i++)
        {
            this.sales.add(new Sales(shop, LocalDate.now().withDayOfMonth(i)));
        }
    }
    
    public SalesMeta(Shop shop, LocalDate startDate, LocalDate endDate)
    {
        this.shop = shop;
        this.description = "";
        this.startDate = startDate;
        this.endDate = endDate;
        this.plan = 0.0;
        
        this.sales = new TreeSet<>();
        for(int i = 1; i <= startDate.dayOfMonth().getMaximumValue(); i++)
        {
            this.sales.add(new Sales(shop, startDate.withDayOfMonth(i)));
        }
    }
    
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
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

    public Shop getShop()
    {
        return shop;
    }

    public void setShop(Shop shop)
    {
        this.shop = shop;
    }

    public Set<Sales> getSales()
    {
        return sales;
    }

    public void setSales(Set<Sales> sales)
    {
        this.sales = sales;
    }

    public Double getPlan()
    {
        return plan;
    }

    public void setPlan(Double plan)
    {
        this.plan = plan;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
    
    
    //
    // Вычисляемые поля
    //
    
    /**
     * Возвращает проходимость за период (заполненную позьзователем)
     * @return
     */
    public Integer getPassabilityTotals()
    {
        Integer totals = 0;
        for(Sales s : sales)
        {
            totals += s.getPassability();
        }
        
        return totals;
    }
    
    /**
     * Возвращает количество чеков за период
     * @return
     */
    public Integer getChequeTotals()
    {
        Integer totals = 0;
        for(Sales s : sales)
        {
            totals += s.getChequeCount();
        }
        
        return totals;
    }

    /**
     * Возвращает общий итог продаж за период без учета возвратов
     * @return
     */
    public Double getValueTotals() //без возвратов
    {
        Double totals = 0.0;
        for(Sales s : sales)
        {
            totals += s.getValue();
        }
        
        return totals;
    }
    
    /**
     * Возвращает сумму возвратов за период
     * @return
     */
    public Double getCashbackTotals()
    {
        Double totals = 0.0;
        for(Sales s : sales)
        {
            totals += s.getCashback();
        }

        return totals;
    }
    
    /**
     * Возвращает общий итог продаж за период с учетом возвратов
     * @return
     */
    public Double getPeriodTotals() //с возвратами
    {
        Double totals = 0.0;
        for(Sales s : sales)
        {
            totals += s.getDayTotal();
        }
        
        return totals;
    }
    
    /**
     * Возвращает средний чек за период 
     * @return
     */
    public Double getPeriodMidPrice()
    {
        return getPeriodTotals() / getChequeTotals();
    }
    
    /**
     * Возвращает процент выполнения плана за период
     * @return
     */
    public Double getPlanCoverage()
    {
        return getValueTotals() / getPlan() * 100;
    }
    
    public Sales getByDate(LocalDate date)
    {
        Sales result = null;
        if(date.isAfter(startDate.minusDays(1)) && date.isBefore(endDate.plusDays(1)))
        {
            //log.info("SalesMeta: date in my period");
            for(Sales s : sales)
            {
                //log.info("i have sale: " + s.getId());
                if(s.getDate().equals(date))
                {
                    //log.info("got it! id=" + s.getId());
                    result = s;
                }
            }
        }
        
        return result;
    }
    
}
