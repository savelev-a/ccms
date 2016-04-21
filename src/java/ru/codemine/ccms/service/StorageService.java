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
package ru.codemine.ccms.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.ccms.dao.StorageDAO;
import ru.codemine.ccms.entity.DataFile;

/**
 *
 * @author Alexander Savelev
 */

@Service
public class StorageService
{
    private static final Logger log = Logger.getLogger("StorageService");
    
    @Autowired
    private StorageDAO storageDAO;
    
    @Transactional
    public void create(DataFile dataFile)
    {
        boolean isUploadOk;
        try
        {
            BufferedOutputStream bs = new BufferedOutputStream(
                new FileOutputStream(dataFile.getFile()));
            bs.write(dataFile.getBytes());
            bs.close();
            isUploadOk = true;
        } 
        catch (IOException e)
        {
            isUploadOk = false;
            e.printStackTrace();
        }
        
        if(isUploadOk)
        {
            storageDAO.create(dataFile);
        }
        else
        {
            log.error("Upload failed: " + dataFile.getFilename());
        }
    }
    
    @Transactional
    public void delete(DataFile dataFile)
    {
        boolean isDeleteOk;
        try
        {
            Files.delete(dataFile.getFile().toPath());
            isDeleteOk = true;
        } 
        catch (IOException e)
        {
            isDeleteOk = false;
            e.printStackTrace();
        }
        
        if(isDeleteOk)
        {
            storageDAO.delete(dataFile);
        }
        else
        {
            log.error("Delete failed: " + dataFile.getFilename());
        }
    }
    
    @Transactional
    public void deleteById(Integer id)
    {
        boolean isDeleteOk;
        DataFile dataFile = storageDAO.getById(id);
        try
        {
            Files.delete(dataFile.getFile().toPath());
            isDeleteOk = true;
        } 
        catch (IOException e)
        {
            isDeleteOk = false;
            e.printStackTrace();
        }
        
        if(isDeleteOk)
        {
            storageDAO.delete(dataFile);
        }
        else
        {
            log.error("Delete failed: " + dataFile.getFilename());
        }
    }
    
    @Transactional
    public void update(DataFile dataFile)
    {
        boolean isUpdateOk;
        try
        {
            BufferedOutputStream bs = new BufferedOutputStream(
                new FileOutputStream(dataFile.getFile()));
            bs.write(dataFile.getBytes());
            bs.close();
            isUpdateOk = true;
        } 
        catch (IOException e)
        {
            isUpdateOk = false;
            e.printStackTrace();
        }
        
        if(isUpdateOk)
        {
            storageDAO.update(dataFile);
        }
        else
        {
            log.error("Upload failed: " + dataFile.getFilename());
        }
    }
    
    @Transactional
    public DataFile getById(Integer id)
    {
        DataFile dataFile = storageDAO.getById(id);
        
        try
        {
            BufferedInputStream bs = new BufferedInputStream(new FileInputStream(dataFile.getFile()));
            byte[] bytes = IOUtils.toByteArray(bs);
            dataFile.setBytes(bytes);
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return dataFile;
    }
    
}
