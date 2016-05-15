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

import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.ccms.service.SettingsService;

/**
 *
 * @author Alexander Savelev
 */
@Component
public class EmailAttachmentExtractor
{

    private static final Logger log = Logger.getLogger("EmailExtractor");

    @Autowired
    SettingsService settingsService;

    public void saveAllAttachment()
    {
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");

        try
        {
            Session session = Session.getInstance(props);
            Store store = session.getStore();
            store.connect(settingsService.getSalesLoaderUrl(),
                    settingsService.getSalesLoaderEmail(),
                    settingsService.getSalesLoaderEmailPass());
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            for (Message msg : inbox.getMessages())
            {
                if (!msg.isSet(Flags.Flag.SEEN))
                {
                    String fileNamePrefix = settingsService.getStorageEmailPath() + "msg-" + msg.getMessageNumber();
                    Multipart multipart = (Multipart) msg.getContent();
                    for (int i = 0; i < multipart.getCount(); i++)
                    {
                        BodyPart bodyPart = multipart.getBodyPart(i);
                        if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())
                                && StringUtils.isNotEmpty(bodyPart.getFileName()))
                        {
                            MimeBodyPart mimimi = (MimeBodyPart) bodyPart;   // =(^.^)= 
                            mimimi.saveFile(fileNamePrefix + MimeUtility.decodeText(bodyPart.getFileName()));
                            msg.setFlag(Flags.Flag.SEEN, true);
                        }
                    }
                }
            }
        } catch (Exception e)
        {
            log.error("Cannot save all attachments, error is: " + e.getMessage());
        }

    }
}
