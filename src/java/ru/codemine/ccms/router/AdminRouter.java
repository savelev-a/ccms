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

import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.codemine.ccms.entity.Employee;
import ru.codemine.ccms.entity.ExpenceType;
import ru.codemine.ccms.entity.Office;
import ru.codemine.ccms.entity.Organisation;
import ru.codemine.ccms.entity.Shop;
import ru.codemine.ccms.forms.SettingsForm;
import ru.codemine.ccms.service.EmployeeService;
import ru.codemine.ccms.service.ExpenceTypeService;
import ru.codemine.ccms.service.OfficeService;
import ru.codemine.ccms.service.OrganisationService;
import ru.codemine.ccms.service.SettingsService;
import ru.codemine.ccms.service.ShopService;
import ru.codemine.ccms.service.TaskService;
import ru.codemine.ccms.utils.Utils;

/**
 *
 * @author Alexander Savelev
 */

@Controller
public class AdminRouter
{
    private static final Logger log = Logger.getLogger("AdminRouter");
    
    @Autowired private EmployeeService employeeService;
    @Autowired private OrganisationService organisationService;
    @Autowired private ShopService shopService;
    @Autowired private OfficeService officeService;
    @Autowired private SettingsService settingsService;
    @Autowired private TaskService taskService;
    @Autowired private ExpenceTypeService expenceTypeService;
    @Autowired private Utils utils;
    
    
    //
    // Главная
    //
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String getUsers(ModelMap model)
    {
        return "redirect:/admin/employees";
    }
    
    
    //
    // Сотрудники
    //
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/employees", method = RequestMethod.GET)
    public String getEmplyees(ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel());
        model.addAttribute("allemps", employeeService.getAll());
        
        return "admin/employees";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/addemployee", method = RequestMethod.GET)
    public String addEmployeeFrm(ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel());
        model.addAttribute("addEmployeeFrm", new Employee());
        model.addAttribute("rolesList", employeeService.getAllRoles());
        
        return "admin/addemployee";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/addemployee", method = RequestMethod.POST)
    public String addEmployee(@Valid @ModelAttribute("addEmployeeFrm") Employee employee, BindingResult result, ModelMap model)
    {
        if(result.hasErrors())
        {
            model.addAllAttributes(utils.prepareModel());
            model.addAttribute("rolesList", employeeService.getAllRoles());
            
            return "admin/addemployee";
        }
        
        employeeService.create(employee);
        
        return "redirect:/admin/employees";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/profile",  method = RequestMethod.GET)
    public String employeeProfile(@RequestParam("id") Integer id, ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel());
        model.addAttribute("employee", employeeService.getById(id));
        model.addAttribute("rolesList", employeeService.getAllRoles());
        
        return "admin/profile";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/profile", method = RequestMethod.POST)
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee, 
            @RequestParam(required = false) boolean userpage,
            BindingResult result, ModelMap model)
    {
        if(result.hasErrors())
        {
            model.addAllAttributes(utils.prepareModel());
            model.addAttribute("rolesList", employeeService.getAllRoles());
            
            return "admin/profile";
        }
        
        if(employee.getPassword().isEmpty())
        {
            Employee oldEmp = employeeService.getById(employee.getId());
            employee.setPassword(oldEmp.getPassword());
            employeeService.evict(oldEmp);
        }
        else
        {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(11);
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        }
        
        employeeService.update(employee);
        
        return userpage ? "redirect:/employee?id=" + employee.getId() : "redirect:/admin/employees";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/delEmployeeById", method = RequestMethod.POST)
    public String deleteEmployee(@RequestParam("id") Integer id)
    {
        employeeService.deleteById(id);
        
        return "redirect:/admin/employees";
    }
    
    //
    // Юр. Лица
    //
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/organisations", method = RequestMethod.GET)
    public String getOrganisations(ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel());
        model.addAttribute("allorgs", organisationService.getAll());
        
        return "admin/organisations";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/addorganisation", method = RequestMethod.GET)
    public String addOrganisationFrm(ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel());
        model.addAttribute("addOrganisationFrm", new Organisation());
        model.addAttribute("emps", employeeService.getAll());
        
        return "admin/addorganisation";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/addorganisation", method = RequestMethod.POST)
    public String addOrganisation(@Valid @ModelAttribute("addOrganisationFrm") Organisation organisation, BindingResult result, ModelMap model)
    {
        
        
        if(result.hasErrors())
        {
            model.addAllAttributes(utils.prepareModel());
            model.addAttribute("emps", employeeService.getAll());
            
            return "admin/addorganisation";
        }
        
        organisationService.create(organisation);
        
        return "redirect:/admin/organisations";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/orgprofile",  method = RequestMethod.GET)
    public String organisationProfile(@RequestParam("id") Integer id, ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel());
        model.addAttribute("organisation", organisationService.getById(id));
        model.addAttribute("emps", employeeService.getAll());
        
        return "admin/orgprofile";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/orgprofile", method = RequestMethod.POST)
    public String saveOrganisation(@Valid @ModelAttribute("organisation") Organisation organisation, 
            @RequestParam(required = false) boolean userpage,
            BindingResult result, 
            ModelMap model)
    {
        if(result.hasErrors())
        {
            model.addAllAttributes(utils.prepareModel());
            model.addAttribute("emps", employeeService.getAll());
            
            return "admin/orgprofile";
        }
        
        organisationService.update(organisation);
        
        return userpage ? "redirect:/organisation?id=" + organisation.getId() : "redirect:/admin/organisations" ;
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/delOrganisationById", method = RequestMethod.POST)
    public String deleteOrganisation(@RequestParam("id") Integer id)
    {
        organisationService.deleteById(id);
        
        return "redirect:/admin/organisations";
    }
    
    //
    // Магазины
    //
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/shops", method = RequestMethod.GET)
    public String getShops(ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel());
        model.addAttribute("allshops", shopService.getAll());
        
        return "admin/shops";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/addshop", method = RequestMethod.GET)
    public String addShopFrm(ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel());
        model.addAttribute("addShopFrm", new Shop());
        model.addAttribute("emps", employeeService.getAll());
        model.addAttribute("orgs", organisationService.getAll());
        
        return "admin/addshop";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/addshop", method = RequestMethod.POST)
    public String addShop(@Valid @ModelAttribute("addShopFrm") Shop shop, BindingResult result, ModelMap model)
    {
        
        
        if(result.hasErrors())
        {
            model.addAllAttributes(utils.prepareModel());
            model.addAttribute("emps", employeeService.getAll());
            model.addAttribute("orgs", organisationService.getAll());
            
            return "admin/addshop";
        }
        
        shopService.create(shop);
        
        return "redirect:/admin/shops";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/shopprofile",  method = RequestMethod.GET)
    public String shopProfile(@RequestParam("id") Integer id, ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel());
        model.addAttribute("shop", shopService.getById(id));
        model.addAttribute("emps", employeeService.getAll());
        model.addAttribute("orgs", organisationService.getAll());
        
        return "admin/shopprofile";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/shopprofile", method = RequestMethod.POST)
    public String saveShop(@Valid @ModelAttribute("shop") Shop shop, 
            @RequestParam(required = false) boolean userpage,
            BindingResult result, 
            ModelMap model)
    {
        if(result.hasErrors())
        {
            model.addAllAttributes(utils.prepareModel());
            model.addAttribute("emps", employeeService.getAll());
            model.addAttribute("orgs", organisationService.getAll());
            
            return "admin/shopprofile";
        }
        
        shopService.update(shop);
        
        return userpage ? "redirect:/shop?id=" + shop.getId() : "redirect:/admin/shops";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "admin/delShopById", method = RequestMethod.POST)
    public String deleteShop(@RequestParam("id") Integer id)
    {
        shopService.deleteById(id);
        
        return "redirect:/admin/shops";
    }
    
    //
    // Офисы
    //
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/offices")
    public String getOffices(ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel());
        model.addAttribute("alloffices", officeService.getAll());
        
        return "admin/offices";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/addoffice", method = RequestMethod.GET)
    public String addOfficeFrm(ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel());
        model.addAttribute("addOfficeFrm", new Office());
        model.addAttribute("emps", employeeService.getAll());
        model.addAttribute("orgs", organisationService.getAll());
        
        return "admin/addoffice";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/addoffice", method = RequestMethod.POST)
    public String addOffice(@Valid @ModelAttribute("addOfficeFrm") Office office, BindingResult result, ModelMap model)
    {
        if(result.hasErrors())
        {
            model.addAllAttributes(utils.prepareModel());
            model.addAttribute("emps", employeeService.getAll());
            model.addAttribute("orgs", organisationService.getAll());
            
            return "admin/addoffice";
        }
        
        officeService.create(office);
        
        return "redirect:/admin/offices";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/officeprofile",  method = RequestMethod.GET)
    public String officeProfile(@RequestParam("id") Integer id, ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel());
        model.addAttribute("office", officeService.getById(id));
        model.addAttribute("emps", employeeService.getAll());
        model.addAttribute("orgs", organisationService.getAll());
        
        return "admin/officeprofile";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/officeprofile", method = RequestMethod.POST)
    public String saveOffice(@Valid @ModelAttribute("office") Office office, 
            @RequestParam(required = false) boolean userpage,
            BindingResult result, 
            ModelMap model)
    {
        if(result.hasErrors())
        {
            model.addAllAttributes(utils.prepareModel());
            model.addAttribute("emps", employeeService.getAll());
            model.addAttribute("orgs", organisationService.getAll());
            
            return "admin/officeprofile";
        }
        
        officeService.update(office);
        
        return userpage ? "redirect:/office?id=" + office.getId() : "redirect:/admin/offices";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/delOfficeById", method = RequestMethod.POST)
    public String deleteOffice(@RequestParam("id") Integer id)
    {
        officeService.deleteById(id);
        
        return "redirect:/admin/offices";
    }
    
    //
    // Расходы
    //
    
    
    
    //
    // Настройки
    //
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/settings", method = RequestMethod.GET)
    public String getSettingsPage(ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel());
        model.addAttribute("settingsForm", settingsService.createForm());
        
        return "admin/settings";
        
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/settings", method = RequestMethod.POST)
    public String updateSettings(@Valid @ModelAttribute("settingsForm") SettingsForm settingsForm, BindingResult result, ModelMap model)
    {
        if(result.hasErrors())
        {
            model.addAllAttributes(utils.prepareModel());
            
            return "admin/settings";
        }
        
        settingsService.saveForm(settingsForm);
        
        return "redirect:/admin/settings";
    }
    
    //
    // Задачи
    //
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/tasks")
    public String getTasks(ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel());
        model.addAttribute("openTasksCount", taskService.getOpenTaskCount());
        model.addAttribute("alltasks", taskService.getAll());
        
        return "admin/tasks";
    }
    
}
