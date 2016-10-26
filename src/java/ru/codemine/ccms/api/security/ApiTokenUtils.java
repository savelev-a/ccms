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

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.HashMap;
import java.util.Map;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.codemine.ccms.entity.Employee;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class ApiTokenUtils 
{
    @Value("${api.key}")
    private String key;
    
    public String getUsernameFromToken(String token)
    {
        Claims claims = getClaimsFromToken(token);
        
        return claims == null ? null : claims.getSubject();
    }
    
    public DateTime getTokenCreationTime(String token)
    {
        Claims claims = getClaimsFromToken(token);
        
        return claims == null ? null : new DateTime(claims.get("created", Long.class));
    }
    
    public DateTime getTokenExpirationTime(String token)
    {
        Claims claims = getClaimsFromToken(token);
        
        return claims == null ? null : new DateTime(claims.getExpiration());
    }
    
    private Claims getClaimsFromToken(String token)
    {
        Claims claims;
        try
        {
            claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        } 
        catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e)
        {
            claims = null;
        }
        
        return claims;
    }
    
    
    public String generateToken(Employee employee)
    {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", employee.getUsername());
        claims.put("created", DateTime.now().toDate());
        
        return generateToken(claims);
    }
    
    private String generateToken(Map<String, Object> claims)
    {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(DateTime.now().plusDays(2).toDate())
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }
    
    public Boolean validateToken(String token, Employee employee)
    {
        String username = getUsernameFromToken(token);
        DateTime created = getTokenCreationTime(token);
        DateTime expiration = getTokenExpirationTime(token);
        
        return (
                username != null
                && created != null
                && expiration != null
                && employee != null
                && username.equals(employee.getUsername())
                && expiration.isAfterNow()
                );
    }
}
