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

package ru.codemine.ccms.service;

import java.util.List;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.ccms.dao.ExpenceDAO;
import ru.codemine.ccms.entity.Expence;
import ru.codemine.ccms.entity.ExpenceType;
import ru.codemine.ccms.entity.Shop;

/**
 *
 * @author Alexander Savelev
 */

@Service
@Transactional
public class ExpenceService 
{
    @Autowired private ExpenceDAO expenceDAO;
    
    public void create(Expence expence)
    {
        expenceDAO.create(expence);
    }
    
    public void delete(Expence expence)
    {
        expenceDAO.delete(expence);
    }
    
    public void deleteById(Integer id)
    {
        expenceDAO.deleteById(id);
    }
    
    public void update(Expence expence)
    {
        expenceDAO.update(expence);
    }
    
    public Expence getById(Integer id)
    {
        return expenceDAO.getById(id);
    }
    
    public List<Expence> getByShop(Shop shop, boolean recurrent)
    {
        return expenceDAO.getByShop(shop, recurrent);
    }
    
    /**
     * Данная процедура возвращает список объектов периодических расходов по магазину,
     * которые были активены на указанную дату
     * @param shop - Магазин
     * @param date - Дата
     * @return
     */
    public List<Expence> getRecurrentExpencesByDate(Shop shop, LocalDate date)
    {
        return expenceDAO.getRecurrentExpencesByDate(shop, date);
    }
    
    /**
     * Данная процедура возвращает список объектов периодических расходов по магазину,
     * которые были активены на указанную дату и принадлежали к указанному типу
     * @param shop - Магазин
     * @param date - Дата
     * @param type - Тип расхода
     * @return
     */
    public List<Expence> getRecurrentExpencesByDate(Shop shop, LocalDate date, ExpenceType type)
    {
        return expenceDAO.getRecurrentExpencesByDate(shop, date, type);
    }
    
    /**
     * Данная процедура возвращает список разовых расходов по указанному магазину
     * за период
     * @param shop - Магазин
     * @param start - Начало периода
     * @param end - Конец периода
     * @return
     */
    public List<Expence> getOneshotExpencesByPeriod(Shop shop, LocalDate start, LocalDate end)
    {
        return expenceDAO.getOneshotExpencesByPeriod(shop, start, end);
    }
    
    /**
     * Данная процедура возвращает средние расходы за день, исходя из 
     * периода 1 месяц, которому этот день принадлежит.
     * 
     * @param shop - Магазин
     * @param date - Месяц, на который считать расходы (любой его день)
     * @return значение расходов
     */
    public Double getExpencesMidValueForMonth(Shop shop, LocalDate date)
    {
        return expenceDAO.getExpencesMidValueForMonth(shop, date);
    }
    
    public Double getExpencesMidValueForMonth(Shop shop, LocalDate date, ExpenceType type)
    {
        return expenceDAO.getExpencesMidValueForMonth(shop, date, type);
    }
    
    public Double getRecurrentExpencesValueForMonth(Shop shop, LocalDate date)
    {
        return expenceDAO.getRecurrentExpencesValueForMonth(shop, date);
    }
    
    public Double getRecurrentExpencesValueForMonth(Shop shop, LocalDate date, ExpenceType type)
    {
        return expenceDAO.getRecurrentExpencesValueForMonth(shop, date, type);
    }
    
    public Double getRecurrentExpencesMidValueForMonth(Shop shop, LocalDate date, ExpenceType type)
    {
        return expenceDAO.getRecurrentExpencesMidValueForMonth(shop, date, type);
    }
    
    public Double getOneshotExpencesValueForMonth(Shop shop, LocalDate date)
    {
        return expenceDAO.getOneshotExpencesValueForMonth(shop, date);
    }
    
    public Double getOneshotExpencesValueForMonth(Shop shop, LocalDate date, ExpenceType type)
    {
        return expenceDAO.getOneshotExpencesValueForMonth(shop, date, type);
    }
    
    public Double getExpencesValueForMonth(Shop shop, LocalDate date)
    {
        return expenceDAO.getExpencesValueForMonth(shop, date);
    }
    
    public Double getExpencesValueForMonth(Shop shop, LocalDate date, ExpenceType type)
    {
        return expenceDAO.getExpencesValueForMonth(shop, date, type);
    }
    
/**
     * Данная процедура возвращает значение расхода указанного типа точно на указанную дату.
     * Если такого расхода нет - возвращается ноль.
     * @param shop - Магазин
     * @param date - Точная дата
     * @param type - Тип расхода
     * @return
     */
    public Double getOneshotValueForDate(Shop shop, LocalDate date, ExpenceType type)
    {
        return expenceDAO.getOneshotValueForDate(shop, date, type);
    }
    
    public boolean isShopHasExpenceForMonth(Shop shop, LocalDate date, ExpenceType type)
    {
        return expenceDAO.isShopHasExpenceForMonth(shop, date, type);
    }
}
