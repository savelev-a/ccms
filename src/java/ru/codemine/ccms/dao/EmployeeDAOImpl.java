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
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.codemine.ccms.entity.Employee;

/**
 *
 * @author Alexander Savelev
 */

@Repository
public class EmployeeDAOImpl implements EmployeeDAO
{
    private static final Logger log = Logger.getLogger("EmployeeDAO");
    
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void create(Employee employee)
    {
        log.info("Creating new employee: " + employee.getFullName());
        Session session = sessionFactory.getCurrentSession();
        session.save(employee);
    }

    @Override
    public void delete(Employee employee)
    {
        log.info("Removing employee: " + employee.getFullName());
        Session session = sessionFactory.getCurrentSession();
        session.delete(employee);
    }

    @Override
    public void deleteById(Integer id)
    {
        log.info("Removing employee by id: " + id.toString());
        Session session = sessionFactory.getCurrentSession();
        
        Employee employee = getById(id);
        if(employee != null) session.delete(employee);
    }

    @Override
    public void update(Employee employee)
    {
        log.info("Updating employee: " + employee.getFullName());
        Session session = sessionFactory.getCurrentSession();
        session.update(employee);
    }

    @Override
    public Employee getById(Integer id)
    {
        Session session = sessionFactory.getCurrentSession();
        Employee employee = (Employee)session.get(Employee.class, id);
        
        return employee;
    }
    
    @Override
    public Employee getByUsername(String username)
    {
        Session session = sessionFactory.getCurrentSession();
        Employee employee = (Employee)session.createQuery("FROM Employee E WHERE E.username = '" + username + "'").uniqueResult();
        
        return employee;
    }
    
    @Override
    public List<Employee> getActive()
    {
        Session session = sessionFactory.getCurrentSession();
        List<Employee> result = session.createQuery("FROM Employee E WHERE E.active = true ORDER BY E.lastName ASC").list();
        
        return result;
    }
    
    @Override
    public List<Employee> getAll()
    {
        Session session = sessionFactory.getCurrentSession();
        List<Employee> result = session.createQuery("FROM Employee E ORDER BY E.lastName ASC").list();
        
        return result;
    }
    
}
