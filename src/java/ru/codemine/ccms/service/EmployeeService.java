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

import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.ccms.dao.EmployeeDAO;
import ru.codemine.ccms.entity.Employee;

/**
 *
 * @author Alexander Savelev
 */

@Service
public class EmployeeService
{
    private static final Logger log = Logger.getLogger("EmployeeService");
    
    @Autowired
    private EmployeeDAO employeeDAO;
    
    
    @Transactional
    public void create(Employee employee)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(11);
        String encodedPass = passwordEncoder.encode(employee.getPassword());
        
        employee.setPassword(encodedPass);
        employeeDAO.create(employee);
    }
    
    @Transactional
    public void delete(Employee employee)
    {
        employeeDAO.delete(employee);
    }
    
    @Transactional
    public void deleteById(Integer id)
    {
        employeeDAO.deleteById(id);
    }
    
    @Transactional
    public void update(Employee employee)
    {

        employeeDAO.update(employee);
    }
    
    @Transactional
    public Employee getById(Integer id)
    {
        return employeeDAO.getById(id);
    }
    
    @Transactional
    public Employee getByUsername(String username)
    {
        return employeeDAO.getByUsername(username);
    }
    
    @Transactional
    public List<Employee> getActive()
    {
        return employeeDAO.getActive();
    }
    
    @Transactional
    public List<Employee> getAll()
    {
        return employeeDAO.getAll();
    }
    
    @Transactional
    public Employee getCurrentUser()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null)
        {
            return employeeDAO.getByUsername(auth.getName());
        }
        
        return new Employee();
    }
    
    public boolean isCurrentUserHasRole(String role)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null)
        {
            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            
            return authorities.contains(new SimpleGrantedAuthority(role));
        }
        
        return false;
    }
}
