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

package ru.codemine.ccms.router.api.form;

import java.util.ArrayList;
import java.util.List;
import ru.codemine.ccms.entity.Employee;
import ru.codemine.ccms.entity.Shop;

/**
 *
 * @author Alexander Savelev
 */
public class ShopJson 
{
    private Integer id;
    private String name;
    private String orgName;
    private String address;
    private String email;
    private String phone;
    private String adminName;
    private List<String> employees;
    private String icq;
    private boolean closed;
    private String landlord;
    private String workingTime;
    private String comment;
    
    private String providerName;
    private String providerContract;
    private String ip;
    private String subnet;
    private String providerSpeed;
    private String providerTechPhone;
    private String providerAbonPhone;
    private String providerTechData;
    
    private boolean countersEnabled;
    private String dominoName;
    
    public ShopJson(Shop shop)
    {
        this.address = shop.getAddress();
        this.adminName = shop.getShopAdmin().getFullName();
        this.closed = shop.isClosed();
        this.comment = shop.getComment();
        this.countersEnabled = shop.isCountersEnabled();
        this.dominoName = shop.getDominoName();
        this.email = shop.getEmail();
        this.employees = new ArrayList<>();
        this.icq = shop.getIcq();
        this.id = shop.getId();
        this.ip = shop.getProvider().getIp();
        this.landlord = shop.getLandlord();
        this.name = shop.getName();
        this.orgName = shop.getOrganisation().getName();
        this.phone = shop.getPhone();
        this.providerAbonPhone = shop.getProvider().getAbonPhone();
        this.providerContract = shop.getProvider().getContract();
        this.providerName = shop.getProvider().getName();
        this.providerSpeed = shop.getProvider().getSpeed();
        this.providerTechData = shop.getProvider().getTechData();
        this.providerTechPhone = shop.getProvider().getTechPhone();
        this.subnet = shop.getProvider().getSubnet();
        this.workingTime = shop.getWorkingTime();
        
        
        for(Employee emp : shop.getShopEmployees())
        {
            this.employees.add(emp.getFullName());
        }
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getOrgName()
    {
        return orgName;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getAdminName()
    {
        return adminName;
    }

    public void setAdminName(String adminName)
    {
        this.adminName = adminName;
    }

    public List<String> getEmployees()
    {
        return employees;
    }

    public void setEmployees(List<String> employees)
    {
        this.employees = employees;
    }

    public String getIcq()
    {
        return icq;
    }

    public void setIcq(String icq)
    {
        this.icq = icq;
    }

    public boolean isClosed()
    {
        return closed;
    }

    public void setClosed(boolean closed)
    {
        this.closed = closed;
    }

    public String getLandlord()
    {
        return landlord;
    }

    public void setLandlord(String landlord)
    {
        this.landlord = landlord;
    }

    public String getWorkingTime()
    {
        return workingTime;
    }

    public void setWorkingTime(String workingTime)
    {
        this.workingTime = workingTime;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getProviderName()
    {
        return providerName;
    }

    public void setProviderName(String providerName)
    {
        this.providerName = providerName;
    }

    public String getProviderContract()
    {
        return providerContract;
    }

    public void setProviderContract(String providerContract)
    {
        this.providerContract = providerContract;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getSubnet()
    {
        return subnet;
    }

    public void setSubnet(String subnet)
    {
        this.subnet = subnet;
    }

    public String getProviderSpeed()
    {
        return providerSpeed;
    }

    public void setProviderSpeed(String providerSpeed)
    {
        this.providerSpeed = providerSpeed;
    }

    public String getProviderTechPhone()
    {
        return providerTechPhone;
    }

    public void setProviderTechPhone(String providerTechPhone)
    {
        this.providerTechPhone = providerTechPhone;
    }

    public String getProviderAbonPhone()
    {
        return providerAbonPhone;
    }

    public void setProviderAbonPhone(String providerAbonPhone)
    {
        this.providerAbonPhone = providerAbonPhone;
    }

    public String getProviderTechData()
    {
        return providerTechData;
    }

    public void setProviderTechData(String providerTechData)
    {
        this.providerTechData = providerTechData;
    }

    public boolean isCountersEnabled()
    {
        return countersEnabled;
    }

    public void setCountersEnabled(boolean countersEnabled)
    {
        this.countersEnabled = countersEnabled;
    }

    public String getDominoName()
    {
        return dominoName;
    }

    public void setDominoName(String dominoName)
    {
        this.dominoName = dominoName;
    }
    
    
}
