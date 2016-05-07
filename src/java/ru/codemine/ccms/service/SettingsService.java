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
import ru.codemine.ccms.forms.SettingsForm;

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
    
    private static final String CK_COMPANY_NAME = "CK_CompanyName";
    private static final String CK_KONDOR_FTP_LOGIN = "CK_KondorFtpLogin";
    private static final String CK_KONDOR_FTP_PASSWORD = "CK_KondorFtpPassword";

    /**
     * Возвращает путь для сохранения файлов
     * @return
     */
    public String getStorageRootPath()
    {
        return storageRootPath;
    }
    
    /**
     * Возвращает название компании из настроек
     * @return
     */
    @Transactional
    public String getCompanyName()
    {
        Settings settings = settingsDAO.getByKey(CK_COMPANY_NAME);
        String name = (settings == null ? "" : settings.getValue());
        
        return name;
    }
    
    /**
     * Устанавливает название компании
     * @param name
     * название компании
     */
    @Transactional
    public void setCompanyName(String name)
    {
        Settings settings = new Settings(CK_COMPANY_NAME, name);
        settingsDAO.update(settings);
    }
    
    /**
     * Возвращает логин для подключения к счетчикам посетителей "Кондор-7"
     * @return
     */
    @Transactional
    public String getCountersKondorFtpLogin()
    {
        Settings settings = settingsDAO.getByKey(CK_KONDOR_FTP_LOGIN);
        String login = (settings == null ? "" : settings.getValue());
        
        return login;
    }
    
    /**
     * Устанавливает логин для подключения к счетчикам посетителей "Кондор-7"
     * @param login
     * логин
     */
    @Transactional
    public void setCountersKondorFtpLogin(String login)
    {
        Settings settings = new Settings(CK_KONDOR_FTP_LOGIN, login);
        settingsDAO.update(settings);
    }
    
    /**
     * Возвращает пароль для подключения к счетчикам посетителей "Кондор-7"
     * @return
     */
    @Transactional
    public String getCountersKondorFtpPassword()
    {
        Settings settings = settingsDAO.getByKey(CK_KONDOR_FTP_PASSWORD);
        String password = (settings == null ? "" : settings.getValue());
        
        return password;
    }
    
    /**
     * устанавливает пароль для подключения к счетчикам посетителей "Кондор-7"
     * @param password
     * пароль
     */
    @Transactional
    public void setCountersKondorFtpPassword(String password)
    {
        Settings settings = new Settings(CK_KONDOR_FTP_PASSWORD, password);
        settingsDAO.update(settings);
    }
    
    /**
     * Создает форму настроек и загружает ее данными из БД
     * @return
     */
    @Transactional
    public SettingsForm createForm()
    {
        SettingsForm settingsForm = new SettingsForm();
        
        Settings companyName = settingsDAO.getByKey(CK_COMPANY_NAME);
        Settings kondorFtpLogin = settingsDAO.getByKey(CK_KONDOR_FTP_LOGIN);
        Settings kondorFtpPassword = settingsDAO.getByKey(CK_KONDOR_FTP_PASSWORD);
        
        settingsForm.setCompanyName(companyName == null ? "" : companyName.getValue());
        settingsForm.setCountersKondorFtpLogin(kondorFtpLogin == null ? "" : kondorFtpLogin.getValue());
        settingsForm.setCountersKondorFtpPassword(kondorFtpPassword == null ? "" : kondorFtpPassword.getValue());
        
        return settingsForm;
    }
    
    /**
     * Сохраняет данные формы настроек в БД. 
     * В случае если форма или какой либо ее параметр не определены - сохранения не произойдет.
     * 
     * @param form
     * Форма с данными
     */
    @Transactional
    public void saveForm(SettingsForm form)
    {
        if(form == null 
                || form.getCompanyName() == null 
                || form.getCountersKondorFtpLogin() == null 
                || form.getCountersKondorFtpPassword() == null)
            return;
        
        settingsDAO.update(new Settings(CK_COMPANY_NAME, form.getCompanyName()));
        settingsDAO.update(new Settings(CK_KONDOR_FTP_LOGIN, form.getCountersKondorFtpLogin()));
        settingsDAO.update(new Settings(CK_KONDOR_FTP_PASSWORD, form.getCountersKondorFtpPassword()));
    }

}
