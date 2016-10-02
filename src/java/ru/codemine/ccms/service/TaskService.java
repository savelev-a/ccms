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
import java.util.Set;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.ccms.dao.TaskDAO;
import ru.codemine.ccms.entity.Comment;
import ru.codemine.ccms.entity.Employee;
import ru.codemine.ccms.entity.Task;
import ru.codemine.ccms.mail.EmailService;

/**
 *
 * @author Alexander Savelev
 */

@Service
@Transactional
public class TaskService 
{
    private static final Logger log = Logger.getLogger("TaskService");
    
    @Autowired private TaskDAO taskDAO;
    @Autowired private EmailService emailService;
    @Autowired private SettingsService settingsService;
    
    public void create(Task task)
    {
        taskDAO.create(task);

        if(task.hasPerformer())
            assign(task, task.getPerformers());
    }

    public void delete(Task task)
    {
        taskDAO.delete(task);
    }
    
    public void delete(Integer id)
    {
        taskDAO.deleteById(id);
    }
    
    public void update(Task task)
    {
        taskDAO.update(task);
    }
    
    public Task getById(Integer id)
    {
        return taskDAO.getById(id);
    }
    
    public List<Task> getByCreator(Employee creator)
    {
        return taskDAO.getByCreator(creator);
    }
    
    public List<Task> getByPerformer(Employee performer)
    {
        return taskDAO.getByPerformer(performer);
    }
    
    public List<Task> getByStatus(Task.Status status)
    {
        return taskDAO.getByStatus(status);
    }
    
    public List<Task> gedOverdue()
    {
        return taskDAO.gedOverdue();
    }
    
    public List<Task> getByCreatorAndStatus(Employee creator, Task.Status status)
    {
        return taskDAO.getByCreatorAndStatus(creator, status);
    }
    
    public List<Task> getByPerformerAndStatus(Employee performer, Task.Status status)
    {
        return taskDAO.getByPerformerAndStatus(performer, status);
    }
    
    public List<Task> getOverdueByCreator(Employee creator)
    {
        return taskDAO.getOverdueByCreator(creator);
    }
    
    public List<Task> getOverdueByPerformer(Employee performer)
    {
        return taskDAO.getOverdueByPerformer(performer);
    }
    
    public List<Task> getByCreatorNotClosed(Employee creator)
    {
        return taskDAO.getByCreatorNotClosed(creator);
    }
    
    public List<Task> getByPerformerNotClosed(Employee performer)
    {
        return taskDAO.getByPerformerNotClosed(performer);
    }
    
    public List<Task> getByPerformerAndCloseTimeInPeriod(Employee performer, LocalDate startDate, LocalDate endDate)
    {
        return taskDAO.getByPerformerAndCloseTimeInPeriod(performer, startDate, endDate);
    }
    
    public List<Task> getAll()
    {
        return taskDAO.getAll();
    }
    
    public Integer getUserActiveTaskCount(Employee performer)
    {
        if(performer != null)
        {
            Integer count = taskDAO.getPerfTaskCount(performer);
            return count;
        }
        
        return 0;
    }
    
    public Integer getClosedTasksByPerformerCount(Employee performer, LocalDate startDate, LocalDate endDate)
    {
        return taskDAO.getClosedTasksByPerformerCount(performer, startDate, endDate);
    }
    
    public Integer getOverdueTasksByPerformerCount(Employee performer, LocalDate startDate, LocalDate endDate)
    {
        return taskDAO.getOverdueTasksByPerformerCount(performer, startDate, endDate);
    }
    
    public Period getMidTimeByPerformer(Employee performer, LocalDate startDate, LocalDate endDate)
    {
        return taskDAO.getMidTimeByPerformer(performer, startDate, endDate);
    }
    
    public String getMidTimeByPerformerStr(Employee performer, LocalDate startDate, LocalDate endDate)
    {
        Period p = getMidTimeByPerformer(performer, startDate, endDate);
        
        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .appendDays()
                .appendSuffix(" день", " дней")
                .appendSeparator(" ")
                .appendHours()
                .appendSuffix(" час", " часов")
                .appendSeparator(" ")
                .appendMinutes()
                .appendSuffix(" минута", " минут")
                .toFormatter();
        
        return formatter.print(p);
    }
    
    public Integer getOpenTaskCount()
    {
        return taskDAO.getOpenTaskCount();
    }
    
    public void assign(Task task, Employee performer)
    {
        if(performer == null || task == null) return;
        
        task.getPerformers().add(performer);
        if(task.getStatus() == Task.Status.NEW || task.getStatus() == Task.Status.CLOSED) task.setStatus(Task.Status.ASSIGNED);

        Comment comment = new Comment();
        comment.setTitle("Сотрудник добавлен в исполнители");
        
        String commText = "Исполнитель: " + performer.getFullName();
        comment.setText(commText);
        
        task.addComment(comment);
        
        taskDAO.update(task);
        
        sendMsgOnAssign(task, performer);
    }
    
    public void assign(Task task, Set<Employee> performers)
    {
        if(performers == null || performers.isEmpty() || task == null) return;
        
        for(Employee performer : performers)
        {
            task.getPerformers().add(performer);
        }
        if(task.getStatus() == Task.Status.NEW || task.getStatus() == Task.Status.CLOSED) task.setStatus(Task.Status.ASSIGNED);

        Comment comment = new Comment();
        comment.setTitle("Задача назначена сотрудникам");
        
        String commText = "Исполнители: \n";
        for(Employee performer : performers) 
        {
            commText += performer.getFullName();
            commText += "\n";
        }
        comment.setText(commText);
        
        task.addComment(comment);
        
        taskDAO.update(task);
        
        for(Employee performer : performers)
        {
            sendMsgOnAssign(task, performer);
        }
    }
    
    public void drop(Task task)
    {
        Comment comment = new Comment();
        comment.setTitle("Задача возвращена в свободные");
        task.addComment(comment);

        
        task.getPerformers().clear();
        task.setStatus(Task.Status.NEW);
        
        taskDAO.update(task);
    }
    
    public void drop(Task task, Employee performer)
    {
        if(performer == null || task == null || !task.getPerformers().contains(performer)) return;
        
        Comment comment = new Comment();
        comment.setTitle("Сотрудник прекратил выполнение задачи");
        String commText = "Сотрудник: " + performer.getFullName();
        comment.setText(commText);
        
        task.addComment(comment);
        
        task.getPerformers().remove(performer);
        
        if(task.getPerformers().isEmpty()) 
            drop(task);
        
        taskDAO.update(task);
    }
    
    public void drop(Task task, Set<Employee> performers)
    {
        if(performers == null || performers.isEmpty() || task == null) return;
        
        Comment comment = new Comment();
        comment.setTitle("Сотрудники прекратил выполнение задачи");
        String commText = "Сотрудники: \n" ;
        for(Employee preformer : performers)
        {
            commText += preformer.getFullName();
            commText += "\n";
        }
        
        comment.setText(commText);
        
        task.addComment(comment);
        
        for(Employee performer : performers)
            task.getPerformers().remove(performer);
        
        if(task.getPerformers().isEmpty()) 
            drop(task);
        
        taskDAO.update(task);
    }
    
    public void close(Task task)
    {
        task.setCloseTime(DateTime.now());
        task.setStatus(Task.Status.CLOSED);
        
        Comment comment = new Comment();
        comment.setTitle("Задача закрыта");
        task.addComment(comment);
        
        taskDAO.update(task);
    }
    
    public void sendMsgOnAssign(Task task, Employee targetEmployee)
    {
        String title = "Вам назначена новая задача";
        String text  = "Добрый день, " + targetEmployee.getFirstName() + ",<br>" +
                       "Вам назначена новая задача на веб-портале компании.<br><br>" +
                       "Заголовок задачи: <a href=\"" + settingsService.getRootURL() + "/tasks/taskinfo?id=" + task.getId() + "\">" + task.getTitle() + "</a><br>" +
                       "Описание задачи : " + task.getText() + "<br><br>" +
                       "Инициатор задачи " + task.getCreator().getFullName() + " просит вас выполнить ее до " + task.getDeadline().toString("dd.MM.YY HH:mm") + "<br>" +
                       "Чтобы просмотреть задачу пройдите по вышеуказанной ссылке.<br><br>" +
                       "Спасибо за пользование веб-порталом!";
        
        emailService.sendSimpleMessage(targetEmployee.getEmail(), title, text);
        //log.info(text);

    }
    
    public void sendMsgOnAddComment(Task task, Comment comment)
    {
        log.info("in service: " + comment);
        String title = "К созданной вами задаче добавлен новый комментарий";
        String text  = "Добрый день, " + task.getCreator().getFirstName() + ",<br>" +
                       "К вашей задаче на портале добавлен новый комментарий.<br><br>" +
                       "Пользователь <b>" + comment.getCreator().getFullName() + "</b> написал комментарий к задаче <b>" + task.getTitle() + "</b>:<br><br>" +
                       "Заголовок комментария: <a href=\"" + settingsService.getRootURL() + "/tasks/taskinfo?id=" + task.getId() + "\">" + comment.getTitle() + "</a><br>" +
                       "Текст комментария : " + comment.getText() + "<br><br>" +
                       "Чтобы просмотреть комментарий пройдите по вышеуказанной ссылке.<br><br>" +
                       "Спасибо за пользование веб-порталом!";
        
        emailService.sendSimpleMessage(task.getCreator().getEmail(), title, text);
        //log.info(text);
    }

}
