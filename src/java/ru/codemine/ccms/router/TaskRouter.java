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

package ru.codemine.ccms.router;

import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.codemine.ccms.entity.Comment;
import ru.codemine.ccms.entity.Employee;
import ru.codemine.ccms.entity.Task;
import ru.codemine.ccms.service.EmployeeService;
import ru.codemine.ccms.service.TaskService;
import ru.codemine.ccms.utils.Utils;

/**
 *
 * @author Alexander Savelev
 */

@Controller
public class TaskRouter 
{
    private static final Logger log = Logger.getLogger("TaskRouter");
    
    @Autowired private TaskService taskService;
    @Autowired private EmployeeService employeeService;
    @Autowired private Utils utils;
    
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks/create", method = RequestMethod.GET)
    public String newTaskFrm(ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel("Новая задача - ИнфоПортал", "tasks", "newtask"));
        model.addAttribute("openTasksCount", taskService.getOpenTaskCount());
        model.addAttribute("allEmps", employeeService.getActive());
        model.addAttribute("newTaskFrm", new Task(employeeService.getCurrentUser()));
        
        return "pages/tasks/newtask";
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks/create", method = RequestMethod.POST)
    public String newTask(@Valid @ModelAttribute(value = "newTaskFrm") Task task, BindingResult result, ModelMap model)
    {
        if(result.hasErrors())
        {
            model.addAllAttributes(utils.prepareModel("Новая задача - ИнфоПортал", "tasks", ""));
            model.addAttribute("openTasksCount", taskService.getOpenTaskCount());
            model.addAttribute("allEmps", employeeService.getActive());
            
            return "pages/tasks/newtask";
        }
        
        taskService.create(task);
        
        return "redirect:/tasks/mytasks/created";
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks/taskinfo", method = RequestMethod.GET)
    public String getTaskinfo(@RequestParam("id") Integer id, ModelMap model)
    {
        Task task = taskService.getById(id);
        
        model.addAllAttributes(utils.prepareModel("Задача - " + task.getTitle() + " - ИнфоПортал", "tasks", ""));
        model.addAttribute("openTasksCount", taskService.getOpenTaskCount());
        model.addAttribute("task", task);
        model.addAttribute("newcomment", new Comment(employeeService.getCurrentUser()));
        
        return "pages/tasks/taskinfo";
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks/addcomment", method = RequestMethod.POST)
    public String addTaskComment(
            @RequestParam("taskid") Integer taskid, 
            @Valid @ModelAttribute(value = "newcomment") Comment comment, 
            BindingResult result,
            ModelMap model)
    {
        Task task = taskService.getById(taskid);
        if(result.hasErrors())
        {
            model.addAllAttributes(utils.prepareModel("Задача - " + task.getTitle() + " - ИнфоПортал", "tasks", ""));
            model.addAttribute("openTasksCount", taskService.getOpenTaskCount());
            model.addAttribute("task", task);
            model.addAttribute("newcomment", comment);
            
            return "pages/tasks/taskinfo";
        }
        
        task.getComments().add(comment);
        taskService.update(task);
        
        return "redirect:/tasks/taskinfo?id=" + task.getId();
    }
    
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks/mytasks", method = RequestMethod.GET)
    public String getMyTasks(ModelMap model)
    {
        Employee currentUser = employeeService.getCurrentUser();
        
        model.addAllAttributes(utils.prepareModel("Мои задачи - назначенные - ИнфоПортал", "tasks", "performed"));
        model.addAttribute("openTasksCount", taskService.getOpenTaskCount());
        model.addAttribute("tasksPerformedByMe", taskService.getByPerformerNotClosed(currentUser));
        
        return "pages/tasks/mytasks_perf";
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks/mytasks/created", method = RequestMethod.GET)
    public String getMyTasksCreated(ModelMap model)
    {
        Employee currentUser = employeeService.getCurrentUser();
        
        model.addAllAttributes(utils.prepareModel("Мои задачи - созданные - ИнфоПортал", "tasks", "created"));
        model.addAttribute("openTasksCount", taskService.getOpenTaskCount());
        model.addAttribute("tasksCreatedByMe", taskService.getByCreatorNotClosed(currentUser));
        
        return "pages/tasks/mytasks_created";
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks/freetasks", method = RequestMethod.GET)
    public String getFreeTasks(ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel("Свободные задачи - ИнфоПортал", "tasks", "free"));
        model.addAttribute("openTasksCount", taskService.getOpenTaskCount());
        model.addAttribute("freetasks", taskService.getByStatus(Task.Status.NEW));
        
        return "pages/tasks/free";
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks/mytasks/closed", method = RequestMethod.GET)
    public String getClosedTasks(ModelMap model)
    {
        Employee currentUser = employeeService.getCurrentUser();
        
        model.addAllAttributes(utils.prepareModel("Свободные задачи - ИнфоПортал", "tasks", "free"));
        model.addAttribute("openTasksCount", taskService.getOpenTaskCount());
        model.addAttribute("closedtasks", taskService.getByCreatorAndStatus(currentUser, Task.Status.CLOSED));
        
        return "pages/tasks/closed";
    }

}
