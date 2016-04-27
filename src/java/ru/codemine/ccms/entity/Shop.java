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
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Alexander Savelev
 */

@Entity
@Table(name = "shops", indexes = {@Index(name = "shop_name_idx", columnList = "name")})
public class Shop implements Serializable
{
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @NotEmpty(message = "Введите название магазина")
    @Length(max = 64, message = "Слишком длинное значение")
    @Column(name = "name", length = 64, unique = true, nullable = false)
    private String name;
    
    
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organisation organisation;
    
    @NotEmpty(message = "Введите адрес")
    @Column(name = "address", nullable = false, columnDefinition = "TEXT")
    private String address;
    
    @NotEmpty(message = "Введите Email")
    @Email(message = "Неверный адрес эл. почты")
    @Length(max = 64, message = "Слишком длинное значение")
    @Column(name = "email", length = 64, nullable = false)
    private String email;
    
    @NotEmpty(message = "Введите телефон")
    @Length(max = 32, message = "Слишком длинное значение")
    @Column(name = "phone", length = 32, nullable = false)
    private String phone;
    
    
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "adm_id", nullable = false)
    private Employee shopAdmin;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "shop_employees", 
            joinColumns = @JoinColumn(name = "shop_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"))
    private Set<Employee> shopEmployees;
    
    @Length(max = 16, message = "Слишком длинное значение")
    @Column(name = "icq", length = 16, nullable = false)
    private String icq;
    
    @Column(name = "closed", nullable = false)
    private boolean closed;
    
    @Length(max = 128, message = "Слишком длинное значение")
    @Column(name = "landlord", length = 128, nullable = false)
    private String landlord;
    
    @Length(max = 32, message = "Слишком длинное значение")
    @Column(name = "workingTime", length = 32, nullable = false)
    private String workingTime;
    
    @Column(name = "comment", nullable = false, columnDefinition = "TEXT")
    private String comment;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "shop_hw_id", nullable = false)
    private ShopHardware hardware;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "shop_prov_id", nullable = false)
    private Provider provider;
    
    
    @Column(name = "shopKeys", nullable = false, columnDefinition = "TEXT")
    private String keys;
    
    @Column(name = "passwords", nullable = false, columnDefinition = "TEXT")
    private String passwords;
    
    @Column(name = "techComment", nullable = false, columnDefinition = "TEXT")
    private String techComment;
    
    @Column(name = "countersEnabled", nullable = false)
    private boolean countersEnabled;
    
    
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

    public Organisation getOrganisation()
    {
        return organisation;
    }

    public void setOrganisation(Organisation organisation)
    {
        this.organisation = organisation;
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

    public Employee getShopAdmin()
    {
        return shopAdmin;
    }

    public void setShopAdmin(Employee shopAdmin)
    {
        this.shopAdmin = shopAdmin;
    }

    public Set<Employee> getShopEmployees()
    {
        return shopEmployees;
    }

    public void setShopEmployees(Set<Employee> shopEmployees)
    {
        this.shopEmployees = shopEmployees;
    }

    public String getIcq()
    {
        return icq;
    }

    public void setIcq(String icq)
    {
        this.icq = icq;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public ShopHardware getHardware()
    {
        return hardware;
    }

    public void setHardware(ShopHardware hardware)
    {
        this.hardware = hardware;
    }

    public Provider getProvider()
    {
        return provider;
    }

    public void setProvider(Provider provider)
    {
        this.provider = provider;
    }

    public String getKeys()
    {
        return keys;
    }

    public void setKeys(String keys)
    {
        this.keys = keys;
    }

    public String getPasswords()
    {
        return passwords;
    }

    public void setPasswords(String passwords)
    {
        this.passwords = passwords;
    }

    public String getTechComment()
    {
        return techComment;
    }

    public void setTechComment(String techComment)
    {
        this.techComment = techComment;
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

    public boolean isCountersEnabled()
    {
        return countersEnabled;
    }

    public void setCountersEnabled(boolean countersEnabled)
    {
        this.countersEnabled = countersEnabled;
    }

    
 

    @Override
    public String toString()
    {
        return "Shop{" + "id=" + id + ", name=" + name + ", address=" + address + ", email=" + email + ", phone=" + phone + '}';
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.email);
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
        final Shop other = (Shop) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        if (!Objects.equals(this.name, other.name))
        {
            return false;
        }
        if (!Objects.equals(this.email, other.email))
        {
            return false;
        }
        return true;
    }
    
    
    
    
}
