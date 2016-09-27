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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "files")
public class DataFile implements Serializable
{
    public enum FileType
    {
        OTHER,
        PICTURE,
        DOC,
        XLS,
        TXT
    }
    
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "filename", nullable = false, unique = true)
    private String filename;
    
    @Length(max = 256)
    @Column(name = "viewName", nullable = false)
    private String viewName;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable = false)
    private FileType type;

    public DataFile()
    {
        type = FileType.OTHER;
    }
    
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
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

    public String getViewName()
    {
        return viewName;
    }

    public void setViewName(String viewName)
    {
        this.viewName = viewName;
    }

    public FileType getType()
    {
        return type;
    }

    public void setType(FileType type)
    {
        this.type = type;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.filename);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof DataFile))
        {
            return false;
        }
        final DataFile other = (DataFile) obj;
        if (!Objects.equals(this.getFilename(), other.getFilename()))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DataFile{" + "id=" + id + ", filename=" + filename + ", viewName=" + viewName + ", type=" + type + '}';
    }
    
    
    public String getIconPath()
    {
        switch(type)
        {
            case DOC : return "document.png";
            case PICTURE : return "picture.png";
            case XLS : return "spreadsheet.png";
            case TXT : return "text.png";
            default : return "file.png";
        }
    }
    
    public static List<String> getImageExtensions()
    {
        List<String> result = new ArrayList<>();
        result.add("jpg");
        result.add("png");
        result.add("gif");
        result.add("bmp");
        result.add("tif");
        result.add("tiff");
        result.add("jpeg");
        
        return result;
    }
    
    public static List<String> getDocumentExtensions()
    {
        List<String> result = new ArrayList<>();
        result.add("doc");
        result.add("docx");
        result.add("odt");
        
        return result;
    }
    
    public static List<String> getSpreadsheetExtensions()
    {
        List<String> result = new ArrayList<>();
        result.add("xls");
        result.add("xlsx");
        result.add("ods");
        
        return result;
    }
    
    public static List<String> getTextExtensions()
    {
        List<String> result = new ArrayList<>();
        result.add("txt");
        result.add("xml");
        
        return result;
    }
    
    public void setTypeByExtension()
    {
        String[] parts = viewName.split("\\.");

        if(parts.length == 1)
        {
            setType(FileType.OTHER);
            return;
        }
        
        String extension = parts[parts.length - 1];
        if     (getDocumentExtensions().contains(extension))    setType(FileType.DOC);
        else if(getImageExtensions().contains(extension))       setType(FileType.PICTURE);
        else if(getSpreadsheetExtensions().contains(extension)) setType(FileType.XLS);
        else if(getTextExtensions().contains(extension))        setType(FileType.TXT);
    }
}
