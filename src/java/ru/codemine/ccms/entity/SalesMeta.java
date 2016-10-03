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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "s_shop_id")
    private Shop shop;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
    
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "expences", joinColumns = @JoinColumn(name = "id_salesmeta"))
    @Column(name = "exp_value", nullable = false)
    @MapKeyJoinColumn(name = "id_type", referencedColumnName = "id")
    private Map<ExpenceType, Double> expences;
    
    @Digits(integer = 10, fraction = 2)
    @Range(min = 0)
    @NotNull
    @Column(name = "pass_total", nullable = false)
    private Integer passabilityTotal;
    
    @Digits(integer = 10, fraction = 2)
    @Range(min = 0)
    @NotNull
    @Column(name = "cqcount_total", nullable = false)
    private Integer chequeCountTotal;
    
    @Digits(integer = 10, fraction = 2)
    @Range(min = 0)
    @NotNull
    @Column(name = "value_total", nullable = false)
    private Double valueTotal;
    
    @Digits(integer = 10, fraction = 2)
    @Range(min = 0)
    @NotNull
    @Column(name = "cashback_total", nullable = false)
    private Double cashbackTotal;
    
    @Digits(integer = 10, fraction = 2)
    @Range(min = 0)
    @NotNull
    @Column(name = "sales_total", nullable = false)
    private Double salesTotal;
    
    @Digits(integer = 10, fraction = 2)
    @Range(min = 0)
    @NotNull
    @Column(name = "expences_total", nullable = false)
    private Double expencesTotal;
    
    @Length(max = 255)
    @Column(name = "description", nullable = false, length = 255)
    private String description;

    
    public SalesMeta()
    {
        this.plan = 0.0;
        this.description = "";
        this.startDate = LocalDate.now().withDayOfMonth(1);
        this.endDate = LocalDate.now().dayOfMonth().withMaximumValue();
        this.passabilityTotal = 0;
        this.chequeCountTotal = 0;
        this.valueTotal = 0.0;
        this.cashbackTotal = 0.0;
        this.salesTotal = 0.0;
        this.expencesTotal = 0.0;
        
        this.expences = new LinkedHashMap<>();
        this.sales = new TreeSet<>();
    }
    
    public SalesMeta(Shop shop)
    {
        this.shop = shop;
        this.description = "";
        this.startDate = LocalDate.now().withDayOfMonth(1);
        this.endDate = LocalDate.now().dayOfMonth().withMaximumValue();
        this.plan = 0.0;
        this.passabilityTotal = 0;
        this.chequeCountTotal = 0;
        this.valueTotal = 0.0;
        this.cashbackTotal = 0.0;
        this.salesTotal = 0.0;
        this.expencesTotal = 0.0;
        
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
        this.passabilityTotal = 0;
        this.chequeCountTotal = 0;
        this.valueTotal = 0.0;
        this.cashbackTotal = 0.0;
        this.salesTotal = 0.0;
        this.expencesTotal = 0.0;
        
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
        recalculateTotals();
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

    public Map<ExpenceType, Double> getExpences()
    {
        return expences;
    }

    public void setExpences(Map<ExpenceType, Double> expences)
    {
        this.expences = expences;
        recalculateTotals();
    }

    public Integer getPassabilityTotal()
    {
        return passabilityTotal;
    }

    public void setPassabilityTotal(Integer passabilityTotal)
    {
        this.passabilityTotal = passabilityTotal;
    }

    public Integer getChequeCountTotal()
    {
        return chequeCountTotal;
    }

    public void setChequeCountTotal(Integer chequeCountTotal)
    {
        this.chequeCountTotal = chequeCountTotal;
    }

    public Double getValueTotal()
    {
        return valueTotal;
    }

    public void setValueTotal(Double valueTotal)
    {
        this.valueTotal = valueTotal;
    }

    public Double getCashbackTotal()
    {
        return cashbackTotal;
    }

    public void setCashbackTotal(Double cashbackTotal)
    {
        this.cashbackTotal = cashbackTotal;
    }

    public Double getSalesTotal()
    {
        return salesTotal;
    }

    public void setSalesTotal(Double salesTotal)
    {
        this.salesTotal = salesTotal;
    }

    public Double getExpencesTotal()
    {
        return expencesTotal;
    }

    public void setExpencesTotal(Double expencesTotal)
    {
        this.expencesTotal = expencesTotal;
    }
    
    
    //
    // Вычисляемые поля
    //
    
    public void recalculateTotals()
    {
        passabilityTotal = 0;
        chequeCountTotal = 0;
        valueTotal = 0.0;
        cashbackTotal = 0.0;
        salesTotal = 0.0;
        expencesTotal = 0.0;
        
        for(Sales s : sales)
        {
            passabilityTotal += s.getPassability();
            chequeCountTotal += s.getChequeCount();
            valueTotal += s.getValue();
            cashbackTotal += s.getCashback();
            salesTotal += s.getDayTotal();
        }
        
        for(Map.Entry<ExpenceType, Double> entry : expences.entrySet())
        {
            expencesTotal += entry.getValue();
        }

    }
    
    public Double getPeriodMidPrice()
    {
        return getChequeCountTotal().equals(0.0) ? 0.0 : getSalesTotal() / getChequeCountTotal();
    }
    
    public Double getPlanCoverage()
    {
        return getPlan().equals(0.0) ? 0.0 : getValueTotal() / getPlan() * 100;
    }
    
}
