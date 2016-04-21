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
package ru.codemine.ccms.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Alexander Savelev
 */

@Entity
@Table(name = "shopHardware")
public class ShopHardware implements Serializable
{
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "pc", nullable = false, columnDefinition = "TEXT")
    private String pc;
    
    @Length(max = 64, message = "Слишком длинное значение")
    @Column(name = "os", length = 64, nullable = false)
    private String os;
    
    @Length(max = 64, message = "Слишком длинное значение")
    @Column(name = "kkm", length = 64, nullable = false)
    private String kkm;
    
    @Length(max = 64, message = "Слишком длинное значение")
    @Column(name = "scanner", length = 64, nullable = false)
    private String scanner;
    
    @Length(max = 64, message = "Слишком длинное значение")
    @Column(name = "labelPrinter", length = 64, nullable = false)
    private String labelPrinter;
    
    @Length(max = 64, message = "Слишком длинное значение")
    @Column(name = "router", length = 64, nullable = false)
    private String router;
    
    @Length(max = 64, message = "Слишком длинное значение")
    @Column(name = "detektor", length = 64, nullable = false)
    private String detektor;
    
    @Length(max = 64, message = "Слишком длинное значение")
    @Column(name = "printer", length = 64, nullable = false)
    private String printer;
    
    @Length(max = 64, message = "Слишком длинное значение")
    @Column(name = "bankTerm", length = 64, nullable = false)
    private String bankTerm;
    
    @Length(max = 64, message = "Слишком длинное значение")
    @Column(name = "music", length = 64, nullable = false)
    private String music;
    
    @Length(max = 64, message = "Слишком длинное значение")
    @Column(name = "ups", length = 64, nullable = false)
    private String ups;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getPc()
    {
        return pc;
    }

    public void setPc(String pc)
    {
        this.pc = pc;
    }

    public String getOs()
    {
        return os;
    }

    public void setOs(String os)
    {
        this.os = os;
    }

    public String getKkm()
    {
        return kkm;
    }

    public void setKkm(String kkm)
    {
        this.kkm = kkm;
    }

    public String getScanner()
    {
        return scanner;
    }

    public void setScanner(String scanner)
    {
        this.scanner = scanner;
    }

    public String getLabelPrinter()
    {
        return labelPrinter;
    }

    public void setLabelPrinter(String labelPrinter)
    {
        this.labelPrinter = labelPrinter;
    }

    public String getRouter()
    {
        return router;
    }

    public void setRouter(String router)
    {
        this.router = router;
    }

    public String getDetektor()
    {
        return detektor;
    }

    public void setDetektor(String detektor)
    {
        this.detektor = detektor;
    }

    public String getPrinter()
    {
        return printer;
    }

    public void setPrinter(String printer)
    {
        this.printer = printer;
    }

    public String getBankTerm()
    {
        return bankTerm;
    }

    public void setBankTerm(String bankTerm)
    {
        this.bankTerm = bankTerm;
    }

    public String getMusic()
    {
        return music;
    }

    public void setMusic(String music)
    {
        this.music = music;
    }

    public String getUps()
    {
        return ups;
    }

    public void setUps(String ups)
    {
        this.ups = ups;
    }

    @Override
    public String toString()
    {
        return "ShopHardware{" + "id=" + id + ", pc=" + pc + '}';
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final ShopHardware other = (ShopHardware) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }
    
    
    
}
