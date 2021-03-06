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

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.ccms.dao.OrganisationDAO;
import ru.codemine.ccms.entity.Organisation;

/**
 *
 * @author Alexander Savelev
 */

@Service
@Transactional
public class OrganisationService
{
    private static final Logger log = Logger.getLogger("OrganisationService");
    
    @Autowired private OrganisationDAO organisationDAO;
    
    public void create(Organisation org)
    {
        organisationDAO.create(org);
    }
    
    public void delete(Organisation org)
    {
        organisationDAO.delete(org);
    }
    
    public void deleteById(Integer id)
    {
        organisationDAO.deleteById(id);
    }
    
    public void update(Organisation org)
    {
        organisationDAO.update(org);
    }
    
    public Organisation getById(Integer id)
    {
        return organisationDAO.getById(id);
    }
    
    public Organisation getByInn(String inn)
    {
        return organisationDAO.getByInn(inn);
    }
    
    public List<Organisation> getAll()
    {
        return organisationDAO.getAll();
    }
}
