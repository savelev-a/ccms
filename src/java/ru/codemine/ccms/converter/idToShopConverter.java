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

package ru.codemine.ccms.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import ru.codemine.ccms.entity.Shop;
import ru.codemine.ccms.service.ShopService;

/**
 *
 * @author Alexander Savelev
 */
public class idToShopConverter implements Converter<String, Shop>
{
    @Autowired
    private ShopService shopService;

    @Override
    public Shop convert(String s)
    {
        Integer id = Integer.parseInt(s);
        
        return shopService.getById(id);
    }
    

}
