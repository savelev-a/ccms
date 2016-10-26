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

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.codemine.ccms.entity.Shop;
import ru.codemine.ccms.router.api.form.ShopJson;
import ru.codemine.ccms.service.ShopService;

/**
 *
 * @author Alexander Savelev
 */

@Controller
public class ApiRouter 
{
    @Autowired private ShopService shopService;
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/api/shop", method = RequestMethod.GET)
    public @ResponseBody ShopJson getShopJson(@RequestParam String name)
    {
        Shop shop = shopService.getByName(name);
        
        return new ShopJson(shop);
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/api/shops", method = RequestMethod.GET)
    public @ResponseBody List<ShopJson> getShopsJson()
    {
        List<ShopJson> result = new ArrayList<>();
        
        for(Shop shop : shopService.getAll())
        {
            result.add(new ShopJson(shop));
        }
        
        return result;
    }
    
    
    
    
//    @Secured("ROLE_OFFICE")
//    @RequestMapping(value = "/api/sales", method = RequestMethod.GET)
//    public @ResponseBody SalesJson getShopJson(
//            @RequestParam String shopname,
//            @RequestParam("startDate") String startDateStr,
//            @RequestParam("endDate") String endDateStr,
//            @RequestParam(required = false) String detail)
//    {
//        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.YYYY");
//        
//        LocalDate startDate = formatter.parseLocalDate(startDateStr);
//        LocalDate endDate = formatter.parseLocalDate(endDateStr);
//        
//        if(detail == null || detail == "none")
//        {
//            
//        }
//    }
    
}
