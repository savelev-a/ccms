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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.codemine.ccms.entity.Comment;
import ru.codemine.ccms.entity.DataFile;
import ru.codemine.ccms.entity.Employee;
import ru.codemine.ccms.entity.Task;
import ru.codemine.ccms.exceptions.ResourceNotFoundException;
import ru.codemine.ccms.service.DataFileService;
import ru.codemine.ccms.service.EmployeeService;
import ru.codemine.ccms.service.SettingsService;
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
    @Autowired private DataFileService dataFileService;
    @Autowired private SettingsService settingsService;
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
        
        task.setCreator(employeeService.getCurrentUser());
        
        taskService.create(task);
        
        return "redirect:/tasks/mytasks/created";
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks/taskinfo", method = RequestMethod.GET)
    public String getTaskinfo(@RequestParam("id") Integer id, ModelMap model)
    {
        Task task = taskService.getById(id);
        Employee currentUser = employeeService.getCurrentUser();
        
        if(!task.isInTask(currentUser)) throw new ResourceNotFoundException();
        
        model.addAllAttributes(utils.prepareModel("Задача - " + task.getTitle() + " - ИнфоПортал", "tasks", ""));
        model.addAttribute("openTasksCount", taskService.getOpenTaskCount());
        model.addAttribute("task", task);
        model.addAttribute("newcomment", new Comment(employeeService.getCurrentUser()));
        model.addAttribute("allEmps", employeeService.getActive());
        
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
        model.addAttribute("closedTasks", taskService.getByCreatorAndStatus(currentUser, Task.Status.CLOSED));
        
        return "pages/tasks/mytasks_closed";
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks/addfile", method = RequestMethod.POST)
    public String addFileToTask(@RequestParam("file") MultipartFile file, @RequestParam("id") Integer id)
    {
        Task task = taskService.getById(id);
        Employee employee = employeeService.getCurrentUser();
        
        if(file.isEmpty()) return "redirect:/tasks/taskinfo?id=" + task.getId();
        
        if(employee.equals(task.getCreator()) || task.getPerformers().contains(employee))
        {
            String filename = settingsService.getStorageTasksPath() + UUID.randomUUID().toString();
            String viewname = file.getOriginalFilename();
            File targetFile = new File(filename);
            
            DataFile targetDataFile = new DataFile(employee);
            targetDataFile.setFilename(filename);
            targetDataFile.setViewName(viewname);
            targetDataFile.setSize(file.getSize());
            targetDataFile.setTypeByExtension();
            
            try
            {
                file.transferTo(targetFile);
            } 
            catch (IOException | IllegalStateException ex)
            {
                log.error("Ошибка при загрузке файла " + viewname + ", причина: " + ex.getLocalizedMessage());
                return "redirect:/tasks/taskinfo?id=" + task.getId();
            }
            
            dataFileService.create(targetDataFile);
            task.getFiles().add(targetDataFile);
            taskService.update(task);
        }
        
        return "redirect:/tasks/taskinfo?id=" + task.getId();
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks/getfile/{urlfilename}", method = RequestMethod.GET)
    public void getFileFromTask(@RequestParam("taskid") Integer taskid, 
                                  @RequestParam("fileid") Long fileid, 
                                  @PathVariable("urlfilename") String urlFileName,
                                  HttpServletResponse response)
    {
        Task task = taskService.getById(taskid);
        DataFile targetDataFile = dataFileService.getById(fileid);
        Employee currentUser = employeeService.getCurrentUser();
        
        if(     targetDataFile == null 
                || task == null
                || !task.isInTask(currentUser)) 
                return;
        
        File targetFile = new File(targetDataFile.getFilename());
        response.setHeader("Content-Disposition", "attachment;");
        
        try
        {
            BufferedInputStream bs = new BufferedInputStream(new FileInputStream(targetFile));
            IOUtils.copy(bs, response.getOutputStream());
            response.flushBuffer();
        }
        catch (IOException ex)
        {
            log.error("Ошибка при отдаче файла " + targetDataFile.getViewName() + ", причина: " + ex.getLocalizedMessage());
        }

    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks/droptask", method = RequestMethod.POST)
    public String dropTask(@RequestParam("id") Integer id)
    {
        Task task = taskService.getById(id);
        Employee employee = employeeService.getCurrentUser();
        
        taskService.drop(task, employee);
        
        return "redirect:/tasks/mytasks";
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks/closetask", method = RequestMethod.POST)
    public String closeTask(@RequestParam("id") Integer id)
    {
        Task task = taskService.getById(id);
        Employee employee = employeeService.getCurrentUser();
        
        if(employee.equals(task.getCreator()))
        {
            taskService.close(task);
        }
        
        return "redirect:/tasks/mytasks/created";
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks/taketask", method = RequestMethod.POST)
    public String takeTask(@RequestParam("id") Integer id)
    {
        Task task = taskService.getById(id);
        Employee employee = employeeService.getCurrentUser();
        
        taskService.assign(task, employee);
        
        return "redirect:/tasks/freetasks";
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks/addperformers", method = RequestMethod.POST)
    public String addPerformersToTask(@RequestParam("id") Integer id, @ModelAttribute(value = "task") Task tmpTask)
    {
        Task realTask = taskService.getById(id);
        Employee currentUser = employeeService.getCurrentUser();
        Set<Employee> performers = tmpTask.getPerformers();
        
        if(realTask.isInTask(currentUser))
            taskService.assign(realTask, performers);
        
        return "redirect:/tasks/taskinfo?id=" + realTask.getId(); 
    }
}
