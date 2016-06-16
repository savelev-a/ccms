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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.codemine.ccms.entity.Employee;
import ru.codemine.ccms.entity.Office;
import ru.codemine.ccms.entity.Organisation;
import ru.codemine.ccms.entity.Shop;
import ru.codemine.ccms.service.EmployeeService;
import ru.codemine.ccms.service.OfficeService;
import ru.codemine.ccms.service.OrganisationService;
import ru.codemine.ccms.service.ShopService;
import ru.codemine.ccms.utils.Utils;

/**
 *
 * @author Alexander Savelev
 */
@Controller
public class DefaultRouter
{

    private static final Logger log = Logger.getLogger("DefaultRouter");

    @Autowired private ShopService shopService;
    @Autowired private EmployeeService employeeService;
    @Autowired private OrganisationService organisationService;
    @Autowired private OfficeService officeService;
    @Autowired private Utils utils;

    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndex(ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel("Главная - ИнфоПортал", "", ""));
        
        return "index";
    }

    //
    // Основные пункты меню
    //
    @RequestMapping(value = "/shops", method = RequestMethod.GET)
    public String getShops(ModelMap model, @RequestParam(required = false) String mode)
    {
        model.addAllAttributes(utils.prepareModel("Магазины - ИнфоПортал", "shops", "short"));
        model.addAttribute("allshops", shopService.getAll());
        model.addAttribute("allorgs", organisationService.getAll());
        
        if("print".equals(mode))
        {
            model.addAttribute("openshops", shopService.getAllOpen());
            return "/printforms/shops";
        }

        return "pages/shops/shopsAll";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/offices", method = RequestMethod.GET)
    public String getOffices(ModelMap model, @RequestParam(required = false) String mode)
    {
        model.addAllAttributes(utils.prepareModel("Офисы - ИнфоПортал", "offices", "all"));
        model.addAttribute("alloffices", officeService.getAll());

        return "print".equals(mode) ? "printforms/offices" : "pages/offices/officesAll";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public String getEmployees(ModelMap model, @RequestParam(required = false) String mode)
    {
        model.addAllAttributes(utils.prepareModel("Сотрудники - ИнфоПортал", "employees", "all"));
        model.addAttribute("allemps", employeeService.getAll());

        return "print".equals(mode) ? "printforms/employees" : "pages/employees/employeesAll";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/organisations", method = RequestMethod.GET)
    public String getOrganisations(ModelMap model, @RequestParam(required = false) String mode)
    {
        model.addAllAttributes(utils.prepareModel("Юр. лица - ИнфоПортал", "organisations", "all"));
        model.addAttribute("allorgs", organisationService.getAll());

        return "print".equals(mode) ? "/printforms/organisations" : "pages/organisations/organisationsAll";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin(ModelMap model, @RequestParam(required = false) boolean error)
    {
        model.addAllAttributes(utils.prepareModel("Авторизация - ИнфоПортал", "", ""));

        List<Employee> activeEmpsList = employeeService.getActive();
        Map<String, String> upperMap = new LinkedHashMap();
        Map<String, String> lowerMap = new LinkedHashMap();
        Map<String, String> loginMap = new LinkedHashMap();
        for(Employee employee : activeEmpsList)
        {
            List<Shop> empShopsList = shopService.getShopsRuledBy(employee);
            if(empShopsList != null && !empShopsList.isEmpty())
            {
                String shopsString = "";
                for(Shop s : empShopsList)
                {
                    shopsString = shopsString + ", " + s.getName();
                }
                shopsString = shopsString.substring(2); // удаление первой запятой
                    
                lowerMap.put(employee.getUsername(), employee.getFullName() + " (" + shopsString + ")");
            }
            else
            {
                upperMap.put(employee.getUsername(), employee.getFullName());
            }
        }
        
        loginMap.put("#nologin1", "   -- Выберите пользователя --");
        loginMap.putAll(upperMap);
        loginMap.put("#nologin2", "          -- Магазины --");
        loginMap.putAll(lowerMap);
        
        model.addAttribute("loginMap", loginMap);

        return "login";
    }
    
    //
    // Подменю - магазины
    //
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/shop", method = RequestMethod.GET)
    public String getShop(ModelMap model, @RequestParam Integer id, @RequestParam(required = false) String mode)
    {
        Shop shop = shopService.getById(id);
        model.addAllAttributes(utils.prepareModel("Магазин - " + shop.getName() + " - ИнфоПортал", "shops", "general"));
        model.addAttribute("shop", shop);
        
        return "print".equals(mode) ? "/printforms/shopfrm" : "pages/shops/shop";
    }
    
    //
    // Подменю - офисы
    //

    @Secured("ROLE_USER")
    @RequestMapping(value = "/office", method = RequestMethod.GET)
    public String getOffice(ModelMap model, @RequestParam Integer id, @RequestParam(required = false) String mode)
    {
        Office office = officeService.getById(id);
        model.addAllAttributes(utils.prepareModel("Офис - " + office.getName() + " - ИнфоПортал", "offices", ""));
        model.addAttribute("office", office);

        return "print".equals(mode) ? "/printforms/officefrm" : "pages/offices/office";

    }

    //
    // Подменю - организации
    //
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/organisation", method = RequestMethod.GET)
    public String getOrganisation(ModelMap model, @RequestParam Integer id, @RequestParam(required = false) String mode)
    {
        Organisation org = organisationService.getById(id);
        model.addAllAttributes(utils.prepareModel("Реквизиты - " + org.getName() + " - ИнфоПортал", "organisations", ""));
        model.addAttribute("organisation", org);

        return "print".equals(mode) ? "/printforms/organisationfrm" : "pages/organisations/organisation";
    }
    
    //
    // Подменю - сотрудники
    //
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public String getEmployee(ModelMap model, @RequestParam Integer id, @RequestParam(required = false) String mode)
    {
        Employee employee = employeeService.getById(id);
        model.addAllAttributes(utils.prepareModel("Профиль - " + employee.getFullName() + " - ИнфоПортал", "employees", ""));
        model.addAttribute("employee", employee);

        return "print".equals(mode) ? "/printforms/employeefrm" : "pages/employees/employee";
    }
    

    //
    // Ошибки
    //

    @RequestMapping(value = "/403")
    public String errorDenied(ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel("Доступ запрещен - ИнфоПортал", "", ""));
        
        return "403";
    }
    
    @RequestMapping(value = "/404")
    public String errorNotFound(ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel("Страница не найдена - ИнфоПортал", "", ""));
        
        return "404";
    }
}
