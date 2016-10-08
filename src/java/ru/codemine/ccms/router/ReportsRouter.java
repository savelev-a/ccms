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
package ru.codemine.ccms.router;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.codemine.ccms.service.ShopService;
import ru.codemine.ccms.utils.Utils;

/**
 *
 * @author Alexander Savelev
 */
@Controller
public class ReportsRouter 
{

    private static final Logger log = Logger.getLogger("ReportsRouter");

    @Autowired private ShopService shopService;
    @Autowired private Utils utils;

    
    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "/reports/shopproviders", method = RequestMethod.GET)
    public String getShopProvidersReport(ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel("Отчет по провайдерам - ИнфоПортал", "reports", ""));
        model.addAttribute("allshops", shopService.getAll());
        
        return "reports/shopprov";
    }

}
