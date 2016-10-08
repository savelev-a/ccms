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
package ru.codemine.ccms.dao;

import java.util.List;
import java.util.Set;
import org.joda.time.LocalDate;
import ru.codemine.ccms.entity.ExpenceType;
import ru.codemine.ccms.entity.SalesMeta;
import ru.codemine.ccms.entity.Shop;

/**
 *
 * @author Alexander Savelev
 */

public interface SalesDAO extends GenericDAO<SalesMeta, Integer>
{
    public boolean updatePlanAll(Double plan, LocalDate startDate, LocalDate endDate);

    public List<SalesMeta> getByShop(Shop shop);

    public SalesMeta getByShopAndDate(  Shop shop, LocalDate startDate, LocalDate endDate);
    public List<SalesMeta> getByPeriod( Shop shop, LocalDate startDate, LocalDate endDate);

    public Integer getPassabilityValueByPeriod( Shop shop, LocalDate startDate, LocalDate endDate);
    public Integer getCqcountValueByPeriod(     Shop shop, LocalDate startDate, LocalDate endDate);
    public Double getValueByPeriod(             Shop shop, LocalDate startDate, LocalDate endDate);
    public Double getCashbackValueByPeriod(     Shop shop, LocalDate startDate, LocalDate endDate);
    public Double getSalesValueByPeriod(        Shop shop, LocalDate startDate, LocalDate endDate);
    public Double getMidPriceValueByPeriod(     Shop shop, LocalDate startDate, LocalDate endDate);
    public Double getPlan(                      Shop shop, LocalDate startDate, LocalDate endDate);
    public Double getPlanCoverage(              Shop shop, LocalDate startDate, LocalDate endDate);

    public Set<ExpenceType> getExpenceTypesByPeriod(Shop shop, LocalDate startDate, LocalDate endDate);

    public Double getTotalExpenceValueForPeriod(Shop shop, LocalDate startDate, LocalDate endDate, ExpenceType type);
    public Double getTotalExpenceValueForPeriod(Shop shop, LocalDate startDate, LocalDate endDate);

}
