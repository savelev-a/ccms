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
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
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
@Table(name = "employees", indexes = {@Index(name = "emp_name_idx", columnList = "lastName")})
public class Employee implements Serializable 
{
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @NotEmpty(message = "Введите имя")
    @Length(max = 64, message = "Слишком длинное значение")
    @Column(name = "firstName", length = 64, nullable = false)
    private String firstName;
    
    @NotEmpty(message = "Введите фамилию")
    @Length(max = 64, message = "Слишком длинное значение")
    @Column(name = "lastName", length = 64, nullable = false)
    private String lastName;
    
    @Length(max = 64, message = "Слишком длинное значение")
    @Column(name = "middleName", length = 64, nullable = false)
    private String middleName;
    
    @NotEmpty(message = "Введите Email")
    @Email(message = "Неправильный адрес эл. почты")
    @Length(max = 64, message = "Слишком длинное значение")
    @Column(name = "email", length = 64, nullable = false)
    private String email;
    
    @Length(max = 32, message = "Слишком длинное значение")
    @Column(name = "phone", length = 32, nullable = false)
    private String phone;
    
    @Length(max = 64, message = "Слишком длинное значение")
    @Column(name = "position", length = 64, nullable = false)
    private String position;

    // Данные для входа web
    
    @NotEmpty(message = "Введите имя пользователя")
    @Length(min = 4, max = 64, message = "Имя пользователя должно содержать 4-64 символа")
    @Column(name = "username", length = 64, nullable = false, unique = true)
    private String username;
    
    //@NotEmpty(message = "Введите пароль")
    //@Length(min = 4, message = "Пароль должен быть длиннее 4 символов")
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "active", nullable = false)
    private boolean active;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="roles", joinColumns = {
        @JoinColumn(name="emp_id", referencedColumnName = "id"),
        @JoinColumn(name = "username", referencedColumnName = "username") })
    private List<String> roles;
    
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
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

    public String getPosition()
    {
        return position;
    }

    public void setPosition(String position)
    {
        this.position = position;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public List<String> getRoles()
    {
        return roles;
    }

    public void setRoles(List<String> roles)
    {
        this.roles = roles;
    }
    
    
    
    
    // Вычисляемые поля
    
    public String getFullName()
    {
        String fullName = lastName + " " + firstName;
        
        return fullName;
    }

    @Override
    public String toString()
    {
        return "Employee{" + "id=" + id + ", firstName=" + firstName 
                + ", lastName=" + lastName + ", middleName=" + middleName 
                + ", email=" + email + ", phone=" + phone + ", position=" 
                + position + '}';
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.firstName);
        hash = 17 * hash + Objects.hashCode(this.lastName);
        hash = 17 * hash + Objects.hashCode(this.email);
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
        final Employee other = (Employee) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName))
        {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName))
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
