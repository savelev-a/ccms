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

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.codemine.ccms.entity.ActionEvent;
import ru.codemine.ccms.service.ActionEventService;
import ru.codemine.ccms.service.EmployeeService;
import ru.codemine.ccms.service.ShopService;
import ru.codemine.ccms.utils.Utils;

/**
 *
 * @author Alexander Savelev
 */

@Controller
public class ActionEventsRouter 
{
    @Autowired private ActionEventService actionEventService;
    @Autowired private EmployeeService employeeService;
    @Autowired private ShopService shopService;
    @Autowired private Utils utils;
    
    @RequestMapping(value = "/actions/create", method = RequestMethod.GET)
    public String newActionFrm(ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel());
        model.addAttribute("allShops", shopService.getAllOpen());
        model.addAttribute("newActionFrm", new ActionEvent(employeeService.getCurrentUser()));
        
        return "pages/actions/newaction";
    }
    
    @RequestMapping(value = "/actions/create", method = RequestMethod.POST)
    public String newAction(@Valid @ModelAttribute(value = "newActionFrm") ActionEvent actionEvent, BindingResult result, ModelMap model)
    {
        if(result.hasErrors())
        {
            model.addAllAttributes(utils.prepareModel());
            model.addAttribute("allShops", shopService.getAllOpen());
            
            return "pages/actions/newaction";
        }
        
        actionEvent.setCreator(employeeService.getCurrentUser());
        
        actionEventService.create(actionEvent);
        
        return "redirect://actions/currentFuture";
    }
    
    @RequestMapping(value = "/actions/currentFuture", method = RequestMethod.GET)
    public String getCurrentFuture(ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel());
     
        List<ActionEvent> currentFutureActions = actionEventService.getCurrentFuture();
        model.addAttribute("currentFutureActions", currentFutureActions);
        
        return "pages/actions/currentFuture";
        
    }
    
}
