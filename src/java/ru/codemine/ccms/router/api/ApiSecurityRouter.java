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

package ru.codemine.ccms.router.api;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.codemine.ccms.api.security.ApiTokenUtils;
import ru.codemine.ccms.entity.Employee;
import ru.codemine.ccms.service.EmployeeService;

/**
 *
 * @author Alexander Savelev
 */

@Controller
public class ApiSecurityRouter 
{
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private ApiTokenUtils apiTokenUtils;
    @Autowired private EmployeeService employeeService;
    
    private static final Logger log = Logger.getLogger("ApiSecurity");
    
    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ResponseEntity<?> authRequest(HttpServletRequest req)
    {
        String reqUsername = req.getHeader("username");
        String reqPass = req.getHeader("password");
        
        String token = null;
        Map<String, String> headers = new HashMap();
        
        try
        {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(reqUsername, reqPass));
        
            SecurityContextHolder.getContext().setAuthentication(authentication);
        
            Employee employee = employeeService.getByUsername(reqUsername);
            token = apiTokenUtils.generateToken(employee);
        
            
            headers.put("X-Auth-Token", token);
        } 
        catch (BadCredentialsException e)
        {
            log.warn("Ошибка авторизации при получении токена, имя: " + reqUsername);
        }
        
        
        return token == null 
                ? new ResponseEntity<>(HttpStatus.FORBIDDEN) 
                : new ResponseEntity<>(headers, HttpStatus.OK);
    }

}
