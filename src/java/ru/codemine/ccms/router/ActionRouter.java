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
import java.util.Set;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.codemine.ccms.entity.SalesMeta;
import ru.codemine.ccms.entity.Shop;
import ru.codemine.ccms.forms.SalesPlanForm;
import ru.codemine.ccms.service.ExpenceTypeService;
import ru.codemine.ccms.service.OrganisationService;
import ru.codemine.ccms.service.SalesService;
import ru.codemine.ccms.service.ShopService;
import ru.codemine.ccms.utils.Utils;

/**
 *
 * @author Alexander Savelev
 */
@Controller
public class ActionRouter
{
//
//    private static final Logger log = Logger.getLogger("ActionRouter");
//
//    @Autowired private ShopService shopService;
//    @Autowired private SalesService salesService;
//    @Autowired private OrganisationService organisationService;
//    @Autowired private ExpenceService expenceService;
//    @Autowired private ExpenceTypeService expenceTypeService;
//    @Autowired private Utils utils;
//

//    
//    
//    //
//    // Расходы
//    //
//    
//    @Secured("ROLE_OFFICE")
//    @RequestMapping(value = "/actions/setexpences", method = RequestMethod.GET)
//    public String getExpencesSetupPage(@RequestParam Integer shopid, ModelMap model)
//    {
//        Shop shop = shopService.getById(shopid);
//        model.addAllAttributes(utils.prepareModel("Установка расходов по магазину - " + shop.getName() + " - ИнфоПортал", "shops", "expences"));
//        model.addAttribute("recurrentExpences", expenceService.getByShop(shop, true));
//        model.addAttribute("oneshotExpences", expenceService.getByShop(shop, false));
//        model.addAttribute("recurrentExpFrm", new Expence(shop));
//        model.addAttribute("oneshotExpFrm", new Expence(shop));
//        model.addAttribute("recurrentExpTypes", expenceTypeService.getAllRecurrent());
//        model.addAttribute("oneshotExpTypes", expenceTypeService.getAllOneshot());
//        model.addAttribute("shop", shop);
//        
//        return "actions/setExpences";
//        
//    }
//    
//    @Secured("ROLE_OFFICE")
//    @RequestMapping(value = "/actions/setoneshotexpences", method = RequestMethod.POST)
//    public String addOneshotExpence(@Valid @ModelAttribute("oneshotExpFrm") Expence expence, 
//            BindingResult result, 
//            ModelMap model)
//    {
//        Shop shop = shopService.getById(expence.getShop().getId());
//        
//        if(result.hasErrors())
//        {
//            model.addAllAttributes(utils.prepareModel("Установка расходов по магазину - " + shop.getName() + " - ИнфоПортал", "shops", "expences"));
//            model.addAttribute("recurrentExpences", expenceService.getByShop(shop, true));
//            model.addAttribute("oneshotExpences", expenceService.getByShop(shop, false));
//            model.addAttribute("recurrentExpFrm", new Expence(shop));
//            model.addAttribute("recurrentExpTypes", expenceTypeService.getAllRecurrent());
//            model.addAttribute("oneshotExpTypes", expenceTypeService.getAllOneshot());
//            model.addAttribute("shop", shop);
//        
//            return "actions/setExpences";
//        }
//        
//        expence.setShop(shop);
//        expence.setRecurrent(false);
//        
//        expenceService.create(expence);
//        
//        return "redirect:/actions/setexpences?shopid=" + shop.getId();
//    }
//    
//    
//    @Secured("ROLE_OFFICE")
//    @RequestMapping(value = "/actions/setrecurrentexpences", method = RequestMethod.POST)
//    public String addRecurrentExpence(@Valid @ModelAttribute("oneshotExpFrm") Expence expence, 
//            BindingResult result, 
//            ModelMap model)
//    {
//        //if(expence == null || expence.getShop() == null) return "redirect:/actions/setexpences";
//        
//        Shop shop = shopService.getById(expence.getShop().getId());
//        
//        if(result.hasErrors())
//        {
//            model.addAllAttributes(utils.prepareModel("Установка расходов по магазину - " + shop.getName() + " - ИнфоПортал", "shops", "expences"));
//            model.addAttribute("recurrentExpences", expenceService.getByShop(shop, true));
//            model.addAttribute("oneshotExpences", expenceService.getByShop(shop, false));
//            model.addAttribute("oneshotExpFrm", new Expence(shop));
//            model.addAttribute("recurrentExpTypes", expenceTypeService.getAllRecurrent());
//            model.addAttribute("oneshotExpTypes", expenceTypeService.getAllOneshot());
//            model.addAttribute("shop", shop);
//        
//            return "actions/setExpences";
//        }
//        
//        expence.setShop(shop);
//        expence.setRecurrent(true);
//        
//        expenceService.create(expence);
//        
//        return "redirect:/actions/setexpences?shopid=" + shop.getId();
//    }
//    
//    @Secured("ROLE_OFFICE")
//    @RequestMapping(value = "/actions/expences/delete", method = RequestMethod.POST)
//    public String deleteExpence(@RequestParam("id") Integer id)
//    {
//        Expence e = expenceService.getById(id);
//        Shop shop = e.getShop();
//        expenceService.delete(e);
//        
//        return "redirect:/actions/setexpences?shopid=" + shop.getId();
//    }
//    
//    @Secured("ROLE_OFFICE")
//    @RequestMapping(value = "/actions/editexpence", method = RequestMethod.GET)
//    public String editExpencePage(@RequestParam("id") Integer id, ModelMap model)
//    {
//        Expence expence = expenceService.getById(id);
//        model.addAllAttributes(utils.prepareModel("Редактирование расходов - ИнфоПортал", "shops", "expences"));
//        model.addAttribute("expence", expence);
//        model.addAttribute("shop", expence.getShop());
//        
//        return "/actions/editExpence";
//    }
//    
//    @Secured("ROLE_OFFICE")
//    @RequestMapping(value = "/actions/editexpence", method = RequestMethod.POST)
//    public String editExpence(@Valid @ModelAttribute Expence expence, BindingResult result, ModelMap model)
//    {
//        Shop shop = expence.getShop();
//        
//        if(result.hasErrors())
//        {
//            model.addAllAttributes(utils.prepareModel("Редактирование расходов - ИнфоПортал", "shops", "expences"));
//            model.addAttribute("shop", shop);
//            return "/actions/editExpence";
//        }
//        
//        expenceService.update(expence);
//        
//        return "redirect:/actions/setexpences?shopid=" + shop.getId();
//    }

}
