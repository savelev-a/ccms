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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.codemine.ccms.entity.Expence;
import ru.codemine.ccms.entity.ExpenceType;
import ru.codemine.ccms.entity.Shop;

/**
 *
 * @author Alexander Savelev
 */

@Repository
public class ExpenceDAOImpl implements ExpenceDAO
{
    //
    // Множество методов в данном DAO объясняется не вполне четкими требованиями заказчика
    //
    private static final Logger log = Logger.getLogger("ExpenceDAO");
    
    @Autowired
    SessionFactory sessionFactory;
    
    @Override
    public void create(Expence expence)
    {
        if(expence == null || expence.getShop() == null || expence .getType() == null)
        {
            log.warn("Invalid expence to create!");
            return;
        }
        
        log.info("Adding new expence for shop " + expence.getShop().getName() + " of type " + expence.getType().getDescription());
        Session session = sessionFactory.getCurrentSession();
        session.save(expence);
    }

    @Override
    public void delete(Expence expence)
    {
        if(expence == null || expence.getShop() == null || expence .getType() == null)
        {
            log.warn("Invalid expence to delete!");
            return;
        }
        
        log.info("Removing expence for shop " + expence.getShop().getName() + " of type " + expence.getType().getDescription());
        Session session = sessionFactory.getCurrentSession();
        session.delete(expence);
    }

    @Override
    public void deleteById(Integer id)
    {
        log.info("removing expence by id: " + id);
        
        Session session = sessionFactory.getCurrentSession();
        Expence expence = getById(id);
        if(expence != null) session.delete(expence);
    }

    @Override
    public void update(Expence expence)
    {
        if(expence == null || expence.getShop() == null || expence .getType() == null)
        {
            log.warn("Invalid expence to update!");
            return;
        }
        
        log.info("Updating expence for shop " + expence.getShop().getName() + " of type " + expence.getType().getDescription());
        Session session = sessionFactory.getCurrentSession();
        session.update(expence);
    }

    @Override
    public Expence getById(Integer id)
    {
        Session session = sessionFactory.getCurrentSession();
        Expence expence = (Expence)session.createQuery("FROM Expence e WHERE e.id = " + id).uniqueResult();
        
        return expence;
    }
    
    @Override
    public List<Expence> getByShop(Shop shop, boolean recurrent)
    {
        Session session = sessionFactory.getCurrentSession();
        List<Expence> result = session.createQuery(
                "FROM Expence e WHERE e.shop.id = " + shop.getId() + " AND e.recurrent = " + recurrent).list();
        
        return result;
    }

    /**
     * Данная процедура возвращает список объектов периодических расходов по магазину,
     * которые были активены на указанную дату
     * @param shop - Магазин
     * @param date - Дата
     * @return
     */
    @Override
    public List<Expence> getRecurrentExpencesByDate(Shop shop, LocalDate date)
    {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(
                "FROM Expence AS e WHERE e.date in "
                        + "(SELECT MAX(e1.date) FROM Expence AS e1 WHERE e1.type = e.type AND e1.date <= :date) "
                        + "AND e.shop.id = :id AND e.recurrent = true");
        
        query.setDate("date", date.toDate());
        query.setInteger("id", shop.getId());
        
        List<Expence> result = query.list();
        
        return result;
    }
    
    /**
     * Данная процедура возвращает список объектов периодических расходов по магазину,
     * которые были активены на указанную дату и принадлежали к указанному типу
     * @param shop - Магазин
     * @param date - Дата
     * @param type - Тип расхода
     * @return
     */
    @Override
    public List<Expence> getRecurrentExpencesByDate(Shop shop, LocalDate date, ExpenceType type)
    {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(
                "FROM Expence AS e WHERE e.date in "
                        + "(SELECT MAX(e1.date) FROM Expence AS e1 WHERE e1.type = e.type AND e1.date <= :date) "
                        + "AND e.shop.id = :idshop AND e.type.id = :idtype AND e.recurrent = true");
        
        query.setDate("date", date.toDate());
        query.setInteger("idshop", shop.getId());
        query.setInteger("idtype", type.getId());
        
        List<Expence> result = query.list();
        
        return result;
    }

    /**
     * Данная процедура возвращает список разовых расходов по указанному магазину
     * за период
     * @param shop - Магазин
     * @param start - Начало периода
     * @param end - Конец периода
     * @return
     */
    @Override
    public List<Expence> getOneshotExpencesByPeriod(Shop shop, LocalDate start, LocalDate end)
    {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Expence e WHERE e.date >= :start AND e.date <= :end AND e.shop.id = :id AND e.recurrent = false");
        query.setDate("start", start.toDate());
        query.setDate("end", end.toDate());
        query.setInteger("id", shop.getId());
        
        List<Expence> result = query.list();
        
        return result;
    }

    /**
     * Данная процедура возвращает средние расходы за день, исходя из 
     * периода 1 месяц, которому этот день принадлежит.
     * 
     * @param shop - Магазин
     * @param date - Месяц, на который считать расходы (любой его день)
     * @return значение расходов
     */
    @Override
    public Double getExpencesMidValueForMonth(Shop shop, LocalDate date)
    {
        Integer daysCount = date.dayOfMonth().withMaximumValue().getDayOfMonth();

        Double result = (getOneshotExpencesValueForMonth(shop, date) 
                + getRecurrentExpencesValueForMonth(shop, date)) 
                / daysCount;
        
        return result;
    }
    
    @Override
    public Double getExpencesMidValueForMonth(Shop shop, LocalDate date, ExpenceType type)
    {
        Integer daysCount = date.dayOfMonth().withMaximumValue().getDayOfMonth();
        
        Double result = (getOneshotExpencesValueForMonth(shop, date, type)
                + getRecurrentExpencesValueForMonth(shop, date, type)) 
                / daysCount;
        
        return result;
    }

    @Override
    public Double getRecurrentExpencesValueForMonth(Shop shop, LocalDate date)
    {
        LocalDate start = date.withDayOfMonth(1);
        LocalDate end = date.dayOfMonth().withMaximumValue();
        Integer daysCount = end.getDayOfMonth();
        
        Session session = sessionFactory.getCurrentSession();
        Double recurrentValue = 0.0;
                
        // Загрузка периодических расходов, попадающих в данный период //
        Query recurrentQuery = session.createQuery(
                "FROM Expence e WHERE e.date >= :start AND e.date <= :end AND e.shop.id = :id AND e.recurrent = true");
        
        recurrentQuery.setDate("start", start.toDate());
        recurrentQuery.setDate("end", end.toDate());
        recurrentQuery.setInteger("id", shop.getId());
        
        List<Expence> recurrentList = recurrentQuery.list();
        
        // Добавление периодических расходов, дата начала которых не входит в данный период, //
        // однако действующих на момент начала периода                                       //
        List<Expence> startExpences = getRecurrentExpencesByDate(shop, start);
        for(Expence e : startExpences)
        {
            if(!recurrentList.contains(e))
                recurrentList.add(e);
        }

        // Разделение расходов по типам и вычисление суммы расходов для каждого типа //
        while(!recurrentList.isEmpty())
        {

            List<Expence> typeList = new ArrayList<>();
            Expence currentExpence = recurrentList.get(0);
            for(Iterator<Expence> iterator = recurrentList.iterator(); iterator.hasNext(); )
            {
                Expence e = iterator.next();
                if(currentExpence.getType() == e.getType())
                {
                    typeList.add(e);
                    iterator.remove();
                }
                    
            }
            
            Collections.sort(typeList);

            // Мы получили сортированный по датам список расходов одного типа
            // Теперь для каждой записи этого типа получим фактические расходы за данный месяц
            // Разобьем данный месяц на периоды по точкам начала расходов, для каждого периода вычислим сумму
            for(int i = 0; i < typeList.size(); i++)
            {
                Expence e = typeList.get(i);
                LocalDate periodStart = e.getDate().isBefore(start) ? start : e.getDate();
                LocalDate periodEnd = (i+1 >= typeList.size()) ? end : typeList.get(i+1).getDate();
                Integer periodDays = Days.daysBetween(periodStart, periodEnd).getDays() + 1;

                Double valForPeriod = e.getValue() * (periodDays.doubleValue() / daysCount.doubleValue());
                recurrentValue += valForPeriod;
            }
            
        }
        
        return recurrentValue;
    }
    
    @Override
    public Double getRecurrentExpencesValueForMonth(Shop shop, LocalDate date, ExpenceType type)
    {
        LocalDate start = date.withDayOfMonth(1);
        LocalDate end = date.dayOfMonth().withMaximumValue();
        Integer daysCount = end.getDayOfMonth();
        
        Session session = sessionFactory.getCurrentSession();
        Double recurrentValue = 0.0;
                
        // Загрузка периодических расходов данного типа, попадающих в данный период //
        Query recurrentQuery = session.createQuery(
                "FROM Expence e WHERE e.date >= :start AND e.date <= :end AND e.shop.id = :idshop AND e.type.id = :idtype AND e.recurrent = true");
        
        recurrentQuery.setDate("start", start.toDate());
        recurrentQuery.setDate("end", end.toDate());
        recurrentQuery.setInteger("idshop", shop.getId());
        recurrentQuery.setInteger("idtype", type.getId());
        
        List<Expence> recurrentList = recurrentQuery.list();
        
        // Добавление периодических расходов, дата начала которых не входит в данный период, //
        // однако действующих на момент начала периода                                       //
        List<Expence> startExpences = getRecurrentExpencesByDate(shop, start, type);
        for(Expence e : startExpences)
        {
            if(!recurrentList.contains(e))
                recurrentList.add(e);
        }
        
        Collections.sort(recurrentList);
        
        for(int i = 0; i < recurrentList.size(); i++)
        {
            Expence e = recurrentList.get(i);
            LocalDate periodStart = e.getDate().isBefore(start) ? start : e.getDate();
            LocalDate periodEnd = (i+1 >= recurrentList.size()) ? end : recurrentList.get(i+1).getDate();
            Integer periodDays = Days.daysBetween(periodStart, periodEnd).getDays() + 1;
            
            Double valForPeriod = e.getValue() * (periodDays.doubleValue() / daysCount.doubleValue());
            recurrentValue += valForPeriod;
        }

        
        
        return recurrentValue;
    }
    
    @Override
    public Double getRecurrentExpencesMidValueForMonth(Shop shop, LocalDate date, ExpenceType type)
    {
        Integer daysCount = date.dayOfMonth().withMaximumValue().getDayOfMonth();

        Double result = getRecurrentExpencesValueForMonth(shop, date, type) / daysCount;
        
        return result;
    }
    
    @Override
    public Double getOneshotExpencesValueForMonth(Shop shop, LocalDate date)
    {
        LocalDate start = date.withDayOfMonth(1);
        LocalDate end = date.dayOfMonth().withMaximumValue();
     
        Session session = sessionFactory.getCurrentSession();
        
        // Вычисление суммы однократных расходов //
        Query oneshotQuery = session.createQuery(
                "SELECT SUM(e.value) FROM Expence e WHERE e.date >= :start AND e.date <= :end AND e.shop.id = :id AND e.recurrent = false");
        
        oneshotQuery.setDate("start", start.toDate());
        oneshotQuery.setDate("end", end.toDate());
        oneshotQuery.setInteger("id", shop.getId());
        
        Double oneshotValue = (Double)oneshotQuery.uniqueResult();
        
        if(oneshotValue == null) oneshotValue = 0.0;
        
        return oneshotValue;
    }
    
    @Override
    public Double getOneshotExpencesValueForMonth(Shop shop, LocalDate date, ExpenceType type)
    {
        LocalDate start = date.withDayOfMonth(1);
        LocalDate end = date.dayOfMonth().withMaximumValue();
     
        Session session = sessionFactory.getCurrentSession();
        
        // Вычисление суммы однократных расходов //
        Query oneshotQuery = session.createQuery(
                "SELECT SUM(e.value) FROM Expence e WHERE e.date >= :start AND e.date <= :end AND e.shop.id = :idshop AND type.id = :idtype AND e.recurrent = false");
        
        oneshotQuery.setDate("start", start.toDate());
        oneshotQuery.setDate("end", end.toDate());
        oneshotQuery.setInteger("idshop", shop.getId());
        oneshotQuery.setInteger("idtype", type.getId());
        
        Double oneshotValue = (Double)oneshotQuery.uniqueResult();
        
        if(oneshotValue == null) oneshotValue = 0.0;
        
        return oneshotValue;
    }
    
    @Override
    public Double getExpencesValueForMonth(Shop shop, LocalDate date)
    {
        Double oneshot = getOneshotExpencesValueForMonth(shop, date);
        Double recurrent = getRecurrentExpencesValueForMonth(shop, date);

        return oneshot + recurrent;
    }
    
    @Override
    public Double getExpencesValueForMonth(Shop shop, LocalDate date, ExpenceType type)
    {
        Double oneshot = getOneshotExpencesValueForMonth(shop, date, type);
        Double recurrent = getRecurrentExpencesValueForMonth(shop, date, type);

        return oneshot + recurrent;
    }
    
    /**
     * Данная процедура возвращает значение расхода указанного типа точно на указанную дату.
     * Если такого расхода нет - возвращается ноль.
     * @param shop - Магазин
     * @param date - Точная дата
     * @param type - Тип расхода
     * @return
     */
    @Override
    public Double getOneshotValueForDate(Shop shop, LocalDate date, ExpenceType type)
    {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Expence e WHERE "
                + "e.date = :date "
                + "AND e.shop.id = :idshop "
                + "AND e.type.id = :idtype "
                + "AND e.recurrent = false");
        
        query.setDate("date", date.toDate());
        query.setInteger("idshop", shop.getId());
        query.setInteger("idtype", type.getId());
        
        List<Expence> explist = query.list();
        
        if(explist != null && !explist.isEmpty())
        {
            return explist.get(explist.size() - 1).getValue();
        }
        
        return 0.0;
    }
    
    @Override
    public boolean isShopHasExpenceForMonth(Shop shop, LocalDate date, ExpenceType type)
    {
        Double value = getOneshotExpencesValueForMonth(shop, date, type) + getRecurrentExpencesValueForMonth(shop, date, type);
        
        return value.compareTo(0.0) != 0;
    }

}
