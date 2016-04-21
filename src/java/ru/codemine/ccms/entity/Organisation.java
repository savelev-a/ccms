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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Alexander Savelev
 */

@Entity
@Table(name = "organisations", indexes = {@Index(name = "org_name_idx", columnList = "name")})
public class Organisation implements Serializable
{
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @NotEmpty(message = "Введите название организации")
    @Length(max = 128, message = "Слишком длинное значение")
    @Column(name = "name", length = 128, nullable = false)
    private String name;
    
    @NotEmpty(message = "Введите ИНН")
    @Length(min = 10, max = 12, message = "Неверный ИНН")
    @Pattern(regexp = "^[0-9]+$", message = "Неверный ИНН")
    @Column(name = "inn", length = 12, unique = true, nullable = false)
    private String inn;
    
    @Length(max = 12, message = "Слишком длинное значение")
    @Pattern(regexp = "^[0-9]*$", message = "Неверный КПП")
    @Column(name = "kpp", length = 12, nullable = false)
    private String kpp;
    
    @NotEmpty(message = "Введите ОГРН")
    @Length(min = 13, max = 15, message = "Неверный ОГРН")
    @Pattern(regexp = "^[0-9]+$", message = "Неверный ОГРН")
    @Column(name = "ogrn", length = 15, nullable = false)
    private String ogrn;
    
    @NotEmpty(message = "Введите расчетный счет")
    @Length(min = 20, max = 20, message = "Неверный расчетный счет")
    @Pattern(regexp = "^[0-9]+$", message = "Неверный расчетный счет")
    @Column(name = "chAccount", length = 20, nullable = false)
    private String chAccount;
    
    @NotEmpty(message = "Введите корр. счет")
    @Length(min = 20, max = 20, message = "Неверный корр. счет")
    @Pattern(regexp = "^[0-9]+$", message = "Неверный корр. счет")
    @Column(name = "coAccount", length = 20, nullable = false)
    private String coAccount;
    
    @NotEmpty(message = "Введите наименование банка")
    @Length(max = 128, message = "Слишком длинное значение")
    @Column(name = "bank", length = 128, nullable = false)
    private String bank;
    
    @NotEmpty(message = "Введите БИК")
    @Length(min = 9, max = 9, message = "Неверный БИК")
    @Pattern(regexp = "^[0-9]+$", message = "Неверный БИК")
    @Column(name = "bik", length = 9, nullable = false)
    private String bik;
    
    @NotEmpty(message = "Введите юр. адрес")
    @Column(name = "urAddress", nullable = false, columnDefinition = "TEXT")
    private String urAddress;
    
    @NotEmpty(message = "Введите почтовый адрес")
    @Column(name = "mailAddress", nullable = false, columnDefinition = "TEXT")
    private String mailAddress;
    
    @NotEmpty(message = "Введите телефон")
    @Length(max = 32, message = "Слишком длинное значение")
    @Column(name = "phone", length = 32, nullable = false)
    private String phone;
    
    
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "director_emp_id")
    private Employee director;

    @OneToMany(mappedBy = "organisation", fetch = FetchType.EAGER)
    private Set<Shop> shops;
    
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

    public String getInn()
    {
        return inn;
    }

    public void setInn(String inn)
    {
        this.inn = inn;
    }

    public String getKpp()
    {
        return kpp;
    }

    public void setKpp(String kpp)
    {
        this.kpp = kpp;
    }

    public String getOgrn()
    {
        return ogrn;
    }

    public void setOgrn(String ogrn)
    {
        this.ogrn = ogrn;
    }

    public String getChAccount()
    {
        return chAccount;
    }

    public void setChAccount(String chAccount)
    {
        this.chAccount = chAccount;
    }

    public String getCoAccount()
    {
        return coAccount;
    }

    public void setCoAccount(String coAccount)
    {
        this.coAccount = coAccount;
    }

    public String getBank()
    {
        return bank;
    }

    public void setBank(String bank)
    {
        this.bank = bank;
    }

    public String getBik()
    {
        return bik;
    }

    public void setBik(String bik)
    {
        this.bik = bik;
    }

    public String getUrAddress()
    {
        return urAddress;
    }

    public void setUrAddress(String urAddress)
    {
        this.urAddress = urAddress;
    }

    public String getMailAddress()
    {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress)
    {
        this.mailAddress = mailAddress;
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

    public Set<Shop> getShops()
    {
        return shops;
    }

    public void setShops(Set<Shop> shops)
    {
        this.shops = shops;
    }
    
    
    
    

    @Override
    public String toString()
    {
        return "Organisation{" + "id=" + id + ", name=" + name + ", inn=" 
                + inn + ", kpp=" + kpp + '}';
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.name);
        hash = 41 * hash + Objects.hashCode(this.inn);
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
        final Organisation other = (Organisation) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        if (!Objects.equals(this.name, other.name))
        {
            return false;
        }
        if (!Objects.equals(this.inn, other.inn))
        {
            return false;
        }
        return true;
    }
    
    

    
}
