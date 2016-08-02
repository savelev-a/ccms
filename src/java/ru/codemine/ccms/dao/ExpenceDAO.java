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
import java.util.Map;
import org.joda.time.LocalDate;
import ru.codemine.ccms.entity.Expence;
import ru.codemine.ccms.entity.ExpenceType;
import ru.codemine.ccms.entity.Shop;

/**
 *
 * @author Alexander Savelev
 */
public interface ExpenceDAO 
{
    public void create(Expence expence);
    
    public void delete(Expence expence);
    public void deleteById(Integer id);
    
    public void update(Expence expence);
    
    public Expence getById(Integer id);
    public List<Expence> getByShop(Shop shop, boolean recurrent);
    public List<Expence> getRecurrentExpencesByDate(Shop shop, LocalDate date);
    public List<Expence> getRecurrentExpencesByDate(Shop shop, LocalDate date, ExpenceType type);
    public List<Expence> getOneshotExpencesByPeriod(Shop shop, LocalDate start, LocalDate end);
    
    public Double getExpencesMidValueForMonth(Shop shop, LocalDate date);
    public Double getExpencesMidValueForMonth(Shop shop, LocalDate date, ExpenceType type);
    public Double getRecurrentExpencesValueForMonth(Shop shop, LocalDate date);
    public Double getRecurrentExpencesValueForMonth(Shop shop, LocalDate date, ExpenceType type);
    public Double getRecurrentExpencesMidValueForMonth(Shop shop, LocalDate date, ExpenceType type);
    public Double getOneshotExpencesValueForMonth(Shop shop, LocalDate date);
    public Double getOneshotExpencesValueForMonth(Shop shop, LocalDate date, ExpenceType type);
    public Double getExpencesValueForMonth(Shop shop, LocalDate date);
    public Double getExpencesValueForMonth(Shop shop, LocalDate date, ExpenceType type);

    public Double getOneshotValueForDate(Shop shop, LocalDate date, ExpenceType type);
    
    public boolean isShopHasExpenceForMonth(Shop shop, LocalDate date, ExpenceType type);
}
