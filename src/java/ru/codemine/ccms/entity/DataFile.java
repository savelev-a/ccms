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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.commons.io.IOUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author Alexander Savelev
 */

@Entity
@Table(name = "datafiles")
public class DataFile implements Serializable
{
    
    public enum FileType
    {
        TEXT,
        IMAGE,
        XLS,
        DOC,
        PDF,
        DATA
    }
    
    @Value("${storage.rootpath}")
    private String rootPath;
    
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @NotEmpty
    @Length(max = 255, message = "Слишком длинное значение")
    @Column(name = "filename", length = 255, nullable = false)
    private String filename;
    
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "created", nullable = false, columnDefinition = "DATETIME")
    private DateTime creationDate;
    
    
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "owner_id", nullable = false)
    private Employee owner;
    
    @Column(name = "ftype", nullable = false)
    private FileType type;
    
    private byte[] bytes;

    //
    
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public DateTime getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate)
    {
        this.creationDate = creationDate;
    }

    public Employee getOwner()
    {
        return owner;
    }

    public void setOwner(Employee owner)
    {
        this.owner = owner;
    }

    public FileType getType()
    {
        return type;
    }

    public void setType(FileType type)
    {
        this.type = type;
    }
    
    public void setBytes(byte[] bytes)
    {
        this.bytes = bytes;
    }
        
    public byte[] getBytes()
    {
        return bytes;
    }
    //
    // Вычисляемые поля
    //
    
    public File getFile()
    {
        return new File(rootPath + filename);
    }
    

    
}
