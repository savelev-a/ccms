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

package ru.codemine.ccms.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Alexander Savelev
 */
public class SettingsForm 
{
    @Length(max = 255)
    private String companyName;
    
    @Length(max = 255)
    private String rootUrl;
    
    @Length(max = 64)
    private String countersKondorFtpLogin;
    
    @Length(max = 64)
    private String countersKondorFtpPassword;

    public String getCompanyName()
    {
        return companyName;
    }

    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    public String getCountersKondorFtpLogin()
    {
        return countersKondorFtpLogin;
    }

    public void setCountersKondorFtpLogin(String countersKondorFtpLogin)
    {
        this.countersKondorFtpLogin = countersKondorFtpLogin;
    }

    public String getCountersKondorFtpPassword()
    {
        return countersKondorFtpPassword;
    }

    public void setCountersKondorFtpPassword(String countersKondorFtpPassword)
    {
        this.countersKondorFtpPassword = countersKondorFtpPassword;
    }

    public String getRootUrl()
    {
        return rootUrl;
    }

    public void setRootUrl(String rootUrl)
    {
        this.rootUrl = rootUrl;
    }
    
    
    
}
