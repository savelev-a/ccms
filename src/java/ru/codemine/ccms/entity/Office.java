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
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
@Table(name = "office", indexes = {@Index(name = "office_name_idx", columnList = "name")})
public class Office implements Serializable
{
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @NotEmpty(message = "Введите название офиса")
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
    @JoinColumn(name = "director_emp_id")
    private Employee director;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "office_employees", 
            joinColumns = @JoinColumn(name = "office_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"))
    private Set<Employee> officeEmployees;
    

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

    public Employee getDirector()
    {
        return director;
    }

    public void setDirector(Employee director)
    {
        this.director = director;
    }

    public Set<Employee> getOfficeEmployees()
    {
        return officeEmployees;
    }

    public void setOfficeEmployees(Set<Employee> officeEmployees)
    {
        this.officeEmployees = officeEmployees;
    }

    @Override
    public String toString()
    {
        return "Office{" + "id=" + id + ", name=" + name + ", address=" + address + '}';
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.name);
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
        final Office other = (Office) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        if (!Objects.equals(this.name, other.name))
        {
            return false;
        }
        return true;
    }
    
    
    
    
}
