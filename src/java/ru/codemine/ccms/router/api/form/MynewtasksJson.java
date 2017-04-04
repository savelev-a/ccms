/*
 * Copyright (C) 2017 Alexander Savelev
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

package ru.codemine.ccms.router.api.form;

import ru.codemine.ccms.entity.Task;

/**
 *
 * @author Alexander Savelev
 */
public class MynewtasksJson 
{
    private Integer id;
    private String title;
    private String text;
    private String creator;
    private String deadline;
    private String urgency;
    
    public MynewtasksJson(Task task)
    {
        this.id = task.getId();
        this.title = task.getTitle();
        this.text = task.getText();
        this.creator = task.getCreator().getFullName();
        this.deadline = task.getDeadlineString();
        this.urgency = task.getUrgencyString();
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    public String getDeadline()
    {
        return deadline;
    }

    public void setDeadline(String deadline)
    {
        this.deadline = deadline;
    }

    public String getUrgency()
    {
        return urgency;
    }

    public void setUrgency(String urgency)
    {
        this.urgency = urgency;
    }
    
    
    
}
