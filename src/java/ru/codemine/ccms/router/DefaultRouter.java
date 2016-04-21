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
import org.springframework.web.bind.annotation.RequestParam;
import ru.codemine.ccms.entity.Employee;
import ru.codemine.ccms.entity.Office;
import ru.codemine.ccms.entity.Organisation;
import ru.codemine.ccms.entity.Shop;
import ru.codemine.ccms.service.EmployeeService;
import ru.codemine.ccms.service.OfficeService;
import ru.codemine.ccms.service.OrganisationService;
import ru.codemine.ccms.service.ShopService;

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


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndex(ModelMap model)
    {
        model.addAttribute("title", "Главная - ИнфоПортал");
        model.addAttribute("mainMenuActiveItem", "");
        model.addAttribute("sideMenuActiveItem", "");
        model.addAttribute("currentUser", employeeService.getCurrentUser());

        return "index";
    }

    //
    // Основные пункты меню
    //
    @RequestMapping(value = "/shops", method = RequestMethod.GET)
    public String getShops(ModelMap model)
    {
        model.addAttribute("title", "Магазины - ИнфоПортал");
        model.addAttribute("mainMenuActiveItem", "shops");
        model.addAttribute("sideMenuActiveItem", "short");
        model.addAttribute("currentUser", employeeService.getCurrentUser());
        
        model.addAttribute("allshops", shopService.getAll());
        model.addAttribute("allorgs", organisationService.getAll());

        return "pages/shops/shopsAll";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/offices", method = RequestMethod.GET)
    public String getOffices(ModelMap model)
    {
        model.addAttribute("title", "Офисы - ИнфоПортал");
        model.addAttribute("mainMenuActiveItem", "offices");
        model.addAttribute("sideMenuActiveItem", "all");
        model.addAttribute("currentUser", employeeService.getCurrentUser());

        model.addAttribute("alloffices", officeService.getAll());

        return "pages/offices/officesAll";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public String getEmployees(ModelMap model)
    {
        model.addAttribute("title", "Сотрудники - ИнфоПортал");
        model.addAttribute("mainMenuActiveItem", "employees");
        model.addAttribute("sideMenuActiveItem", "all");
        model.addAttribute("currentUser", employeeService.getCurrentUser());
        model.addAttribute("allemps", employeeService.getAll());

        return "pages/employees/employeesAll";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/organisations", method = RequestMethod.GET)
    public String getOrganisations(ModelMap model)
    {
        model.addAttribute("title", "Юр. лица - ИнфоПортал");
        model.addAttribute("mainMenuActiveItem", "organisations");
        model.addAttribute("sideMenuActiveItem", "all");
        model.addAttribute("currentUser", employeeService.getCurrentUser());

        model.addAttribute("allorgs", organisationService.getAll());

        return "pages/organisations/organisationsAll";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin(ModelMap model, @RequestParam(required = false) boolean error)
    {
        model.addAttribute("title", "Авторизация - ИнфоПортал");
        model.addAttribute("mainMenuActiveItem", "");
        model.addAttribute("sideMenuActiveItem", "");
        model.addAttribute("currentUser", employeeService.getCurrentUser());
        model.addAttribute("activeUsers", employeeService.getActive());

        return "login";
    }
    
    //
    // Подменю - магазины
    //
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/shop", method = RequestMethod.GET)
    public String getShop(ModelMap model, @RequestParam Integer id)
    {
        Shop shop = shopService.getById(id);
        
        model.addAttribute("title", "Магазин - " + shop.getName() + " - ИнфоПортал");
        model.addAttribute("mainMenuActiveItem", "shops");
        model.addAttribute("sideMenuActiveItem", "");
        model.addAttribute("currentUser", employeeService.getCurrentUser());
        model.addAttribute("shop", shop);
        
        return "pages/shops/shop";
    }
    
    //
    // Подменю - офисы
    //

    @Secured("ROLE_USER")
    @RequestMapping(value = "/office", method = RequestMethod.GET)
    public String getOffice(ModelMap model, @RequestParam Integer id)
    {
        Office office = officeService.getById(id);

        model.addAttribute("title", "Офис - " + office.getName() + " - ИнфоПортал");
        model.addAttribute("mainMenuActiveItem", "offices");
        model.addAttribute("sideMenuActiveItem", "");
        model.addAttribute("currentUser", employeeService.getCurrentUser());
        model.addAttribute("office", office);

        return "pages/offices/office";

    }

    //
    // Подменю - организации
    //
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/organisation", method = RequestMethod.GET)
    public String getOrganisation(ModelMap model, @RequestParam Integer id)
    {
        Organisation org = organisationService.getById(id);

        model.addAttribute("title", "Реквизиты - " + org.getName() + " - ИнфоПортал");
        model.addAttribute("mainMenuActiveItem", "organisations");
        model.addAttribute("sideMenuActiveItem", "");
        model.addAttribute("currentUser", employeeService.getCurrentUser());
        model.addAttribute("organisation", org);

        return "pages/organisations/organisation";
    }
    
    //
    // Подменю - сотрудники
    //
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public String getEmployee(ModelMap model, @RequestParam Integer id)
    {
        Employee employee = employeeService.getById(id);
        
        model.addAttribute("title", "Профиль - " + employee.getFullName() + " - ИнфоПортал");
        model.addAttribute("mainMenuActiveItem", "employees");
        model.addAttribute("sideMenuActiveItem", "");
        model.addAttribute("currentUser", employeeService.getCurrentUser());
        model.addAttribute("employee", employee);

        return "pages/employees/employee";
    }
    
    
    //
    // Ошибки
    //

    @RequestMapping(value = "/403")
    public String errorDenied(ModelMap model)
    {
        model.addAttribute("title", "Доступ запрещен - ИнфоПортал");
        model.addAttribute("mainMenuActiveItem", "");
        model.addAttribute("sideMenuActiveItem", "");
        model.addAttribute("currentUser", employeeService.getCurrentUser());
        
        return "403";
    }
    
    @RequestMapping(value = "/404")
    public String errorNotFound(ModelMap model)
    {
        model.addAttribute("title", "Страница не найдена - ИнфоПортал");
        model.addAttribute("mainMenuActiveItem", "");
        model.addAttribute("sideMenuActiveItem", "");
        model.addAttribute("currentUser", employeeService.getCurrentUser());
        
        return "404";
    }
}
