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

package ru.codemine.ccms.mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.codemine.ccms.service.SettingsService;

/**
 *
 * @author Alexander Savelev
 */

@Service
public class EmailService 
{
    private static final Logger log = Logger.getLogger("EmailSender");
    
    @Value("${smtp.url}")
    private String host;
    
    @Value("${smtp.username}")
    private String username;
    
    @Value("${smtp.pass}")
    private String password;
    
    @Value("${smtp.port}")
    private String port;
    
    @Value("${smtp.ssl}")
    private String ssl;
    
    
    public void sendSimpleMessage(String address, String subject, String content)
    {
        try
        {
            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.auth", "true");
            props.put("mail.mime.charset", "UTF-8");
            props.put("mail.smtp.ssl.enable", ssl);
            
            Session session = Session.getInstance(props, new EmailAuthenticator(username, password));
            
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username, "ИнфоПортал"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(address));
            message.setSubject(subject);
            message.setText(content, "utf-8", "html");
            Transport transport = session.getTransport("smtp");
            transport.connect();
            transport.sendMessage(message, message.getAllRecipients());
        } 
        catch (MessagingException | UnsupportedEncodingException ex)
        {
            log.error("Невозможно отправить сообщение email, возникла ошибка: " + ex.getLocalizedMessage());
            ex.printStackTrace();
        }
        
    }

    
}


class EmailAuthenticator extends Authenticator 
{ 
    private final String user; 
    private final String password; 

    EmailAuthenticator(String user, String password) 
    { 
        this.user = user; 
        this.password = password; 
    } 

    @Override
    public PasswordAuthentication getPasswordAuthentication() 
    { 
        return new PasswordAuthentication(user, password); 
    } 
}