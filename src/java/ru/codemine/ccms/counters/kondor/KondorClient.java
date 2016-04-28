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

package ru.codemine.ccms.counters.kondor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jamel.dbf.processor.DbfProcessor;
import org.jamel.dbf.processor.DbfRowMapper;
import org.jamel.dbf.utils.DbfUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.ccms.counters.CounterClient;
import ru.codemine.ccms.entity.Counter;
import ru.codemine.ccms.entity.Shop;
import ru.codemine.ccms.service.SettingsService;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class KondorClient implements CounterClient
{
    private static final Logger log = Logger.getLogger("KondorClient");
    
    @Autowired
    private SettingsService settingsService;
    
    private File ftpDownload(Shop shop)
    {
        String login = settingsService.getCountersKondorFtpLogin();
        String pass = settingsService.getCountersKondorFtpPassword();
        String ip = shop.getProvider().getIp();
        String tmpFileName = settingsService.getStorageRootPath() + DateTime.now().toString("YYYYMMdd-s" + shop.getId());
        try
        {
            log.debug("Starting ftp session...");
            
            Socket manageSocket = new Socket();
            manageSocket.connect(new InetSocketAddress(ip, 21), 3000);
            BufferedReader in = new BufferedReader(new InputStreamReader(manageSocket.getInputStream()));
            PrintWriter out = new PrintWriter(manageSocket.getOutputStream(), true);
            
            out.println("USER " + login);
            log.debug("FTP: " + in.readLine());
            
            out.println("PASS " + pass);
            String afterAuth = in.readLine();
            log.debug("FTP: " + afterAuth);
            
            out.println("PASV");
            String pasvResponce = in.readLine();
            log.debug("FTP: PASV command responce: " + pasvResponce);
            
            if(pasvResponce.startsWith("227 ("))
            {
                pasvResponce = pasvResponce.substring(5);
                List<String> parsedPasv = new ArrayList<>(Arrays.asList(pasvResponce.split(",")));
                String p4 = parsedPasv.get(4);
                String p5 = parsedPasv.get(5).replace(")", "");
                int port = Integer.parseInt(p4) * 256 + Integer.parseInt(p5);
                
                log.debug("FTP: Recieved port: " + port);
                
                Socket dataSocket = new Socket();
                dataSocket.connect(new InetSocketAddress(ip, port), 3000);
                InputStream dataIn = dataSocket.getInputStream();
                FileOutputStream dataOut = new FileOutputStream(tmpFileName);
                
                out.println("RETR total.dbf");
                log.debug("FTP: " + in.readLine());
                
                IOUtils.copy(dataIn, dataOut);
                
                out.println("QUIT");
                log.debug("FTP: " + in.readLine());
                
                dataOut.flush();
                dataOut.close();
                dataSocket.close();
            }
            else
            {
                if(afterAuth.startsWith("530")) 
                {
                    log.warn("Auth failure with kondor, shop name: " + shop.getName());
                }
                else
                {
                    log.warn("Invalid PASV responce from kondor, shop name: " + shop.getName());
                }
                    
            }
            
            manageSocket.close();
        } 
        catch (Exception e)
        {
            log.error("Cannot download file from kondor, shop name: " + shop.getName() + ", error is: " + e.getMessage());
        }
        
        return new File(tmpFileName);
    }

    @Override
    public List<Counter> getCounters(Shop shop)
    {
        File dbfFile = ftpDownload(shop);
        if(!dbfFile.exists())
        {
            log.error(".dbf file not recieved for shop: " + shop.getName());
            return null;
        }
        
        List<Counter> result = DbfProcessor.loadData(dbfFile, new DbfRowMapper<Counter>()
        {

            @Override
            public Counter mapRow(Object[] row)
            {
                Counter c = new Counter();
                
                String dateStr = new String(DbfUtils.trimLeftSpaces(
                        (byte[])row[0])
                );
                DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.YY");
                DateTime dt = formatter.parseDateTime(dateStr);
                c.setDate(dt);
                
                Number inNumber = (Number)row[1];
                c.setIn(inNumber.intValue());
                
                Number outNumber = (Number)row[2];
                c.setOut(outNumber.intValue());
                
                return c;
            }
        });
        
        for(Counter counter : result)
            counter.setShop(shop);
        
        try
        {
            Files.delete(dbfFile.toPath());
        } 
        catch (IOException e)
        {
            log.error("Cannot remove temp .dbf file " + dbfFile.getPath());
        }
        
        
        return result;
    }
    
    

}
