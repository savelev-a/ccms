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
package ru.codemine.ccms.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Alexander Savelev
 */

@Entity
@Table(name = "providers")
public class Provider implements Serializable
{
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Length(max = 64, message = "Слишком длинное значение")
    @Column(name = "name", length = 64, nullable = false)
    private String name;
    
    @Length(max = 32, message = "Слишком длинное значение")
    @Column(name = "contract", length = 32, nullable = false)
    private String contract;
    
    @Length(max = 15, message = "Слишком длинное значение")
    @Column(name = "ip", length = 15, nullable = false)
    private String ip;
    
    @Length(max = 20, message = "Слишком длинное значение")
    @Column(name = "subnet", length = 20, nullable = false)
    private String subnet;
    
    @Length(max = 16, message = "Слишком длинное значение")
    @Column(name = "speed", length = 16, nullable = false)
    private String speed;
    
    @Length(max = 32, message = "Слишком длинное значение")
    @Column(name = "techPhone", length = 32, nullable = false)
    private String techPhone;
    
    @Length(max = 32, message = "Слишком длинное значение")
    @Column(name = "abonPhone", length = 32, nullable = false)
    private String abonPhone;
    
    @Column(name = "techData", nullable = false, columnDefinition = "TEXT")
    private String techData;

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

    public String getContract()
    {
        return contract;
    }

    public void setContract(String contract)
    {
        this.contract = contract;
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

    public String getSpeed()
    {
        return speed;
    }

    public void setSpeed(String speed)
    {
        this.speed = speed;
    }

    public String getTechPhone()
    {
        return techPhone;
    }

    public void setTechPhone(String techPhone)
    {
        this.techPhone = techPhone;
    }

    public String getAbonPhone()
    {
        return abonPhone;
    }

    public void setAbonPhone(String abonPhone)
    {
        this.abonPhone = abonPhone;
    }

    public String getTechData()
    {
        return techData;
    }

    public void setTechData(String techData)
    {
        this.techData = techData;
    }

    @Override
    public String toString()
    {
        return "Provider{" + "id=" + id + ", name=" + name + ", contract=" + contract + '}';
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.contract);
        hash = 47 * hash + Objects.hashCode(this.ip);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Provider other = (Provider) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        if (!Objects.equals(this.name, other.name))
        {
            return false;
        }
        if (!Objects.equals(this.contract, other.contract))
        {
            return false;
        }
        if (!Objects.equals(this.ip, other.ip))
        {
            return false;
        }
        return true;
    }
    
    
    
}
