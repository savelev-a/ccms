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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.ccms.dao.SettingsDAO;
import ru.codemine.ccms.entity.Settings;

/**
 *
 * @author Alexander Savelev
 */

@Service
public class SettingsService 
{
    @Autowired
    private SettingsDAO settingsDAO;
    
    @Value("${storage.rootpath}")
    private String storageRootPath;

    public String getStorageRootPath()
    {
        return storageRootPath;
    }
    
    
    
    @Transactional
    public String getCompanyName()
    {
        Settings settings = settingsDAO.getByKey("CK_CompanyName");
        String name = (settings == null ? "" : settings.getValue());
        
        return name;
    }
    
    @Transactional
    public void setCompanyName(String name)
    {
        Settings settings = new Settings("CK_CompanyName", name);
        settingsDAO.update(settings);
    }
    
    @Transactional
    public String getCountersKondorFtpLogin()
    {
        Settings settings = settingsDAO.getByKey("CK_KondorFtpLogin");
        String login = (settings == null ? "" : settings.getValue());
        
        return login;
    }
    
    @Transactional
    public void setCountersKondorFtpLogin(String login)
    {
        Settings settings = new Settings("CK_KondorFtpLogin", login);
        settingsDAO.update(settings);
    }
    
    @Transactional
    public String getCountersKondorFtpPassword()
    {
        Settings settings = settingsDAO.getByKey("CK_KondorFtpPassword");
        String password = (settings == null ? "" : settings.getValue());
        
        return password;
    }
    
    @Transactional
    public void setCountersKondorFtpPassword(String password)
    {
        Settings settings = new Settings("CK_KondorFtpPassword", password);
        settingsDAO.update(settings);
    }

}
