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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Alexander Savelev
 */

@Entity
@Table(name = "settings")
public class Settings implements Serializable 
{
    @Id
    @Column(name = "sKey", length = 255, nullable = false)
    private String Key;
    
    @Column(name = "sValue", length = 255, nullable = false)
    private String Value;

    public Settings(){}

    public Settings(String Key, String Value)
    {
        this.Key = Key;
        this.Value = Value;
    }

    
    public String getKey()
    {
        return Key;
    }

    public void setKey(String sKey)
    {
        this.Key = sKey;
    }

    public String getValue()
    {
        return Value;
    }

    public void setValue(String sValue)
    {
        this.Value = sValue;
    }
    
    

}
