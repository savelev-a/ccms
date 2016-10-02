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
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import ru.codemine.ccms.entity.Employee;

/**
 *
 * @author Alexander Savelev
 */

@Repository
public class EmployeeDAOImpl extends GenericDAOImpl<Employee, Integer> implements EmployeeDAO
{
    private static final Logger log = Logger.getLogger("EmployeeDAO");
    
    @Override
    public Employee getByUsername(String username)
    {
        Employee employee = (Employee)getSession().createQuery("FROM Employee E WHERE E.username = '" + username + "'").uniqueResult();
        
        return employee;
    }
    
    @Override
    public List<Employee> getActive()
    {
        List<Employee> result = getSession().createQuery("FROM Employee E WHERE E.active = true ORDER BY E.lastName ASC").list();
        
        return result;
    }
    
    @Override
    public List<Employee> getAll()
    {
        List<Employee> result = getSession().createQuery("FROM Employee E ORDER BY E.lastName ASC").list();
        
        return result;
    }

    @Override
    public List<Employee> getByFullName(String fullName)
    {
        String[] parts = fullName.split(" ");
        if(parts.length != 2) return new ArrayList<>();
        
        Query query = getSession().createQuery("FROM Employee e WHERE e. firstName = :firstName AND e.lastName = :lastName");
        query.setString("firstName", parts[1]);
        query.setString("lastName", parts[0]);
        
        return query.list();
    }
    
}
