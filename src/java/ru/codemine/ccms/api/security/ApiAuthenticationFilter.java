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

package ru.codemine.ccms.api.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import ru.codemine.ccms.entity.Employee;
import ru.codemine.ccms.service.EmployeeService;

/**
 *
 * @author Alexander Savelev
 */


public class ApiAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
    @Autowired private ApiTokenUtils apiTokenUtils;
    @Autowired private EmployeeService employeeService;

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException
    {
        HttpServletResponse responce = (HttpServletResponse)resp;
        HttpServletRequest request = (HttpServletRequest)req;
        
        String authToken = request.getHeader("X-Auth-Token");
        String username = apiTokenUtils.getUsernameFromToken(authToken);
        
        if(username != null)
        {
            Employee employee = employeeService.getByUsername(username);
            if(apiTokenUtils.validateToken(authToken, employee))
            {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(employee, null, employee.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        
        chain.doFilter(req, resp);
    }
    
}
