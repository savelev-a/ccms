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
package ru.codemine.ccms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.codemine.ccms.content.Menu;
import ru.codemine.ccms.content.Page;

/**
 *
 * @author Alexander Savelev
 */
@Service
public class ContentService
{
    
    @Autowired EmployeeService employeeService;

    public enum MainMenuIdx
    {

        SHOPS(0),
        OFFICES(1),
        EMPLOYEES(2),
        ORGS(3),
        ADMIN(4),
        NOACTIVE(255);

        private final int value;

        private MainMenuIdx(int value)
        {
            this.value = value;
        }

        public int getValue()
        {
            return value;
        }

    }
    

    public Page createIndexPage(String title)
    {
        Page page = createCustomPage(
                title, 
                createMainMenu(MainMenuIdx.NOACTIVE), 
                null);

        return page;
    }

    public Page createShopsPage(String title, int sideMenuIndex)
    {
        Menu sideMenu = new Menu();
        sideMenu.addMenuItem("Кратко", "/shops");
        sideMenu.addMenuItem("Подробно", "/shops/extended");
        sideMenu.setActiveMenuItem(sideMenuIndex);
        
        Page page = createCustomPage(
                title, 
                createMainMenu(MainMenuIdx.SHOPS), 
                sideMenu);

        return page;
    }

    public Page createEmployeesPage(String title, int sideMenuIndex)
    {
        Menu sideMenu = new Menu();
        sideMenu.addMenuItem("Все", "/employees");
        sideMenu.addMenuItem("По магазинам", "/employees/byShops");
        sideMenu.setActiveMenuItem(sideMenuIndex);
        
        Page page = createCustomPage(
                title, 
                createMainMenu(MainMenuIdx.EMPLOYEES), 
                sideMenu);

        return page;
    }

    public Page createOfficesPage(String title, int sideMenuIndex)
    {
        Menu sideMenu = new Menu();
        sideMenu.addMenuItem("Все", "/offices");
        sideMenu.setActiveMenuItem(sideMenuIndex);
        
        Page page = createCustomPage(
                title, 
                createMainMenu(MainMenuIdx.OFFICES), 
                sideMenu);

        return page;

    }
    
    public Page createOrganisationsPage(String title, int sideMenuIndex)
    {
        Menu sideMenu = new Menu();
        sideMenu.addMenuItem("Все", "/organisations");
        sideMenu.setActiveMenuItem(sideMenuIndex);
        
        Page page = createCustomPage(
                title, 
                createMainMenu(MainMenuIdx.ORGS), 
                sideMenu);

        return page;
    }
    
    public Page createAdminPage(String title, int sideMenuIndex)
    {
        Page page = createCustomPage(
                title, 
                createMainMenu(MainMenuIdx.ADMIN), 
                createAdminSideMenu(sideMenuIndex));

        return page;
    }
    
    public Page createCustomPage(String title, Menu mainMenu, Menu sideMenu)
    {
        Page page = new Page();
        
        page.setMainMenu(mainMenu);
        page.setSideMenu(sideMenu);
        page.setTitle(title);
        
        return page;
    }

    private Menu createMainMenu(MainMenuIdx index)
    {
        Menu mainMenu = new Menu();
        mainMenu.addMenuItem("Магазины", "/shops");
        mainMenu.addMenuItem("Офисы", "/offices");
        mainMenu.addMenuItem("Сотрудники", "/employees");
        mainMenu.addMenuItem("Юр.лица", "/organisations");
        
        if(employeeService.isCurrentUserHasRole("ROLE_ADMIN"))
        {
            mainMenu.addMenuItem("Администрирование", "/admin");
        }
        
        mainMenu.setActiveMenuItem(index.getValue());

        return mainMenu;
    }

    private Menu createAdminSideMenu(int index)
    {
        Menu sideMenu = new Menu();
        sideMenu.addMenuItem("Сотрудники", "/admin/employees");
        sideMenu.addMenuItem("Магазины", "/admin/shops");
        sideMenu.addMenuItem("Офисы", "/admin/offices");
        sideMenu.addMenuItem("Юр. лица", "/admin/organisations");
        sideMenu.addHorizontalDelimiter();
        sideMenu.addMenuItem("Настройки", "/admin/settings");

        sideMenu.setActiveMenuItem(index);

        return sideMenu;
    }

}
