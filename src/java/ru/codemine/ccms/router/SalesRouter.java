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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.codemine.ccms.entity.Counter;
import ru.codemine.ccms.entity.Sales;
import ru.codemine.ccms.entity.SalesMeta;
import ru.codemine.ccms.entity.Shop;
import ru.codemine.ccms.exceptions.ResourceNotFoundException;
import ru.codemine.ccms.forms.SalesForm;
import ru.codemine.ccms.forms.SalesPlanForm;
import ru.codemine.ccms.sales.SalesLoader;
import ru.codemine.ccms.service.CounterService;
import ru.codemine.ccms.service.OrganisationService;
import ru.codemine.ccms.service.SalesService;
import ru.codemine.ccms.service.ShopService;
import ru.codemine.ccms.utils.Utils;

/**
 *
 * @author Alexander Savelev
 */

@Controller
public class SalesRouter
{
    private static final Logger log = Logger.getLogger("SalesRouter");
    
    @Autowired private SalesService salesService;
    @Autowired private ShopService shopService;
    @Autowired private OrganisationService organisationService;
    @Autowired private CounterService counterService;
    @Autowired private SalesLoader salesLoader;
    @Autowired private Utils utils;
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/sales", method = RequestMethod.GET)
    public String getSalesPage(
            @RequestParam Integer shopid, 
            @RequestParam(required = false) String dateMonth,
            @RequestParam(required = false) String dateYear,
            ModelMap model)
    {
        Shop shop = shopService.getById(shopid);
        
        model.addAllAttributes(utils.prepareModel("Выручка магазина - " + shop.getName() + " - ИнфоПортал", 
                "shops", "sales", 
                dateMonth, dateYear));

        LocalDate selectedStartDate;
        LocalDate selectedEndDate;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMM YYYY");
        if(dateMonth != null && dateYear != null) //Период задан пользователем
        {
            selectedStartDate = formatter.parseLocalDate("01 " + dateMonth + " " + dateYear);
            selectedEndDate = selectedStartDate.dayOfMonth().withMaximumValue();
        }
        else //Период - текущий месяц, по умолчанию
        {
            selectedStartDate = LocalDate.now().withDayOfMonth(1);
            selectedEndDate = selectedStartDate.dayOfMonth().withMaximumValue();
        }
        
        SalesMeta salesMeta = salesService.getByShopAndDate(shop, selectedStartDate, selectedEndDate);
        
        if(shop.isCountersEnabled())
        {
            for(Sales s : salesMeta.getSales())
            {
                Counter counter = counterService.getByShopAndDate(shop, s.getDate().toDateTime(LocalTime.MIDNIGHT));
                s.setPassability(counter == null ? 0 : counter.getIn());
            }
        }
        
        
        model.addAttribute("salesMeta", salesMeta);
        model.addAttribute("shop", shop);
        
        return "pages/shops/sales";
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/salesUpd", method = RequestMethod.POST)
    public @ResponseBody String updateSales(
            @RequestParam(required = false) Integer smid, 
            @RequestParam(required = false) Integer shopid,
            @RequestBody List<SalesForm> salesForms, 
            ModelMap model)
    {
        
        if(smid == null && shopid == null) throw new ResourceNotFoundException();
        
        String resultStr;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.YYYY");

        if(smid != null) // Обновление существующего периода
        {
            SalesMeta salesMeta = salesService.getById(smid);
            
            for(SalesForm sf : salesForms)
            {
                Sales sales = salesMeta.getByDate(formatter.parseLocalDate(sf.getDate()));
                
                sales.setPassability(sf.getPassability());
                sales.setChequeCount(sf.getChequeCount());
                sales.setValue(sf.getValue());
                sales.setCashback(sf.getCashback());
            }
            
            salesService.update(salesMeta);
            
            resultStr = "{\"result\": \"success\"}";
        }
        else // Новый период
        {
            Shop shop = shopService.getById(shopid);
            Set<Sales> newSalesSet = new TreeSet<>();
            
            LocalDate startDate = formatter.parseLocalDate(salesForms.get(0).getDate());
            LocalDate endDate = formatter.parseLocalDate(salesForms.get(salesForms.size() - 1).getDate());
            
            for(SalesForm sf : salesForms)
            {
                Sales sales = new Sales(shop, formatter.parseLocalDate(sf.getDate()));

                sales.setPassability(sf.getPassability());
                sales.setChequeCount(sf.getChequeCount());
                sales.setValue(sf.getValue());
                sales.setCashback(sf.getCashback());
                
                newSalesSet.add(sales);
            }
            
            SalesMeta newSalesMeta = new SalesMeta(shop);
            newSalesMeta.setSales(newSalesSet);
            newSalesMeta.setStartDate(startDate);
            newSalesMeta.setEndDate(endDate);
            newSalesMeta.setDescription("Таблица выручек: " 
                    + shop.getName() 
                    + " (" 
                    + startDate.toString("MMMM YYYY")
                    + ")"
            );
            
            salesService.create(newSalesMeta);
            
            resultStr = "{\"result\": \"success\"}";
        }
        
        return resultStr;
    }
    
    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "/reports/sales-pass", method = RequestMethod.GET)
    public String getSalesPassReport(
            @RequestParam(required = false) String dateStartStr,
            @RequestParam(required = false) String dateEndStr, 
            @RequestParam(required = false) String mode,
            ModelMap model)
    {

        model.addAllAttributes(utils.prepareModel("Отчет по проходимости и продажам - ИнфоПортал", 
                "reports", "general"));
        
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.YYYY");
        LocalDate dateStart = dateStartStr == null 
                ? LocalDate.now().withDayOfMonth(1) 
                : formatter.parseLocalDate(dateStartStr).withDayOfMonth(1);
        LocalDate dateEnd = dateEndStr == null 
                ? LocalDate.now().dayOfMonth().withMaximumValue() 
                : formatter.parseLocalDate(dateEndStr).dayOfMonth().withMaximumValue();
        
        if(dateEnd.isBefore(dateStart)) dateEnd = dateStart.dayOfMonth().withMaximumValue();
        
        model.addAttribute("dateStartStr", dateStart.toString("dd.MM.YYYY"));
        model.addAttribute("dateEndStr", dateEnd.toString("dd.MM.YYYY"));
        
        List<String> subgridColNames = new ArrayList<>();
        subgridColNames.add("Сведения");
        
        if(dateStart.getMonthOfYear() == dateEnd.getMonthOfYear())
        {
            for(int i = 1; i <= dateEnd.getDayOfMonth(); i++)
            {
                subgridColNames.add(String.valueOf(i) + dateStart.toString(".MM.YY"));
            }
        }
        else
        {
            LocalDate printDate = dateStart;
            while(printDate.isBefore(dateEnd))
            {
                subgridColNames.add(printDate.toString("MMM YYYY"));
                printDate = printDate.plusMonths(1);
            }
        }
        
        subgridColNames.add("Итого");
        
        model.addAttribute("subgridColNames", subgridColNames);
        
        return "print".equals(mode) ? "printforms/reports/salesAllFrm" : "reports/sales-pass";
    }
    
    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "/reports/sales-pass/data")
    public @ResponseBody List<Map<String, Object>> getSalesPassReportData(
            @RequestParam(required = false) String dateStartStr,
            @RequestParam(required = false) String dateEndStr,
            ModelMap model)
    {
        List<Map<String, Object>> recordsList = new ArrayList<>();
        
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.YYYY");
        LocalDate dateStart = dateStartStr == null 
                ? LocalDate.now().withDayOfMonth(1) 
                : formatter.parseLocalDate(dateStartStr).withDayOfMonth(1);
        LocalDate dateEnd = dateEndStr == null 
                ? LocalDate.now().dayOfMonth().withMaximumValue() 
                : formatter.parseLocalDate(dateEndStr).dayOfMonth().withMaximumValue();
        
        if(dateEnd.isBefore(dateStart)) dateEnd = dateStart.dayOfMonth().withMaximumValue();

        List<Shop> shopList = shopService.getAllOpen();
        
        for(Shop shop : shopList)
        {
            Map<String, Object> record = new HashMap();
            
            record.put( "shopname",      shop.getName());
            record.put( "passability",   salesService.getPassabilityValueByPeriod(shop, dateStart, dateEnd));
            record.put( "cheque",        salesService.getCqcountValueByPeriod(shop, dateStart, dateEnd));
            record.put( "value",         salesService.getValueByPeriod(shop, dateStart, dateEnd));
            record.put( "cashback",      salesService.getCashbackValueByPeriod(shop, dateStart, dateEnd));
            record.put( "periodtotal",   salesService.getSalesValueByPeriod(shop, dateStart, dateEnd));
            record.put( "midPrice",      salesService.getMidPriceValueByPeriod(shop, dateStart, dateEnd));
            record.put( "plan",          salesService.getPlan(shop, dateStart, dateEnd));
            record.put( "plancoverage",  salesService.getPlanCoverage(shop, dateStart, dateEnd));
            
            recordsList.add(record);
        }
        
        
        return recordsList;
    }
    
    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "/reports/sales-pass/details")
    public @ResponseBody List<Map<String, Object>> getSalesPassReportDetails(
            @RequestParam String dateStartStr,
            @RequestParam String dateEndStr,
            @RequestParam String shopname,
            ModelMap model)
    {
        List<Map<String, Object>> recordsList = new ArrayList<>();
        
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.YYYY");
        LocalDate dateStart = formatter.parseLocalDate(dateStartStr).withDayOfMonth(1);
        LocalDate dateEnd = formatter.parseLocalDate(dateEndStr).dayOfMonth().withMaximumValue();
        
        if(dateEnd.isBefore(dateStart)) dateEnd = dateStart.dayOfMonth().withMaximumValue();
        
        Integer daysCount = dateEnd.getDayOfMonth();
        Integer counterTotals = 0;
        
        List<String> recordNames = new ArrayList<>();
        recordNames.add("Проходимость");
        recordNames.add("Кол-во покупок");
        recordNames.add("Отчет ККМ");
        recordNames.add("Возвраты");
        recordNames.add("Выручка");
        recordNames.add("Средний чек");
        
        Shop shop = shopService.getByName(shopname);
        
        if(dateStart.getMonthOfYear() == dateEnd.getMonthOfYear())
        {
            SalesMeta salesMeta = salesService.getByShopAndDate(shop, dateStart, dateEnd);

            for(int row = 1; row <= recordNames.size(); row++)
            {
                Map<String, Object> record = new HashMap();
                record.put("info", recordNames.get(row - 1));

                for(int cell = 1; cell <= daysCount; cell++)
                {
                    String cellname = "c" + String.valueOf(cell);
                    Sales sale = salesMeta.getByDate(dateStart.withDayOfMonth(cell));
                    switch(row)
                    {
                        case 1:                         //Проходимость
                            if(shop.isCountersEnabled())
                            {
                                Counter counter = counterService.getByShopAndDate(shop, dateStart.withDayOfMonth(cell).toDateTime(LocalTime.MIDNIGHT));
                                Integer counterValue = counter == null ? 0 : counter.getIn();
                                record.put(cellname, counterValue);
                                counterTotals += counterValue;
                                if (cell == daysCount) record.put("totals", counterTotals); 
                            }
                            else
                            {
                                record.put(cellname, sale.getPassability());
                                counterTotals += sale.getPassability();
                                if (cell == daysCount) record.put("totals", counterTotals); 
                            }
                            break;
                        case 2:                         //Кол-во покупок
                            record.put(cellname, sale.getChequeCount());
                            if (cell == daysCount) record.put("totals", salesMeta.getChequeCountTotal());
                            break;
                        case 3:                         //Отчет ККМ
                            record.put(cellname, sale.getValue());
                            if (cell == daysCount) record.put("totals", salesMeta.getValueTotal());
                            break;
                        case 4:                         //Возвраты
                            record.put(cellname, sale.getCashback());
                            if (cell == daysCount) record.put("totals", salesMeta.getCashbackTotal());
                            break;
                        case 5:                         //Выручка
                            record.put(cellname, sale.getDayTotal());
                            if (cell == daysCount) record.put("totals", salesMeta.getSalesTotal());
                            break;
                        case 6:                         //Средний чек
                            Integer chequeCount = sale.getChequeCount();
                            Double midPrice = chequeCount == 0 ? 0 : sale.getMidPrice();
                            record.put(cellname, midPrice);
                            if (cell == daysCount) record.put("totals", salesMeta.getPeriodMidPrice());
                            break;
                    }
                }
                
                recordsList.add(record);
            }
        }
        else  //Выбранный период более одного месяца
        {
            List<SalesMeta> smList = new ArrayList<>();
            
            LocalDate tempStartDate = dateStart;
            while(tempStartDate.isBefore(dateEnd))
            {
                smList.add(salesService.getByShopAndDate(shop, tempStartDate, tempStartDate.dayOfMonth().withMaximumValue()));
                tempStartDate = tempStartDate.plusMonths(1);
            }
            
            for(int row = 1; row <= recordNames.size(); row++)
            {
                Map<String, Object> record = new HashMap();
                record.put("info", recordNames.get(row - 1));

                for(int cell = 1; cell <= smList.size(); cell++)
                {
                    String cellname = "c" + String.valueOf(cell);
                    SalesMeta sm = smList.get(cell - 1);
                    switch(row)
                    {
                        case 1 : //проходимость
                            if(shop.isCountersEnabled())
                            {
                                record.put(cellname, counterService.getPassabilityValueByPeriod(shop, sm.getStartDate(), sm.getEndDate()));
                                if (cell == smList.size()) record.put("totals", counterService.getPassabilityValueByPeriod(shop, dateStart, dateEnd));
                            }
                            else
                            {
                                record.put(cellname, sm.getPassabilityTotal());
                                if (cell == smList.size()) record.put("totals", salesService.getPassabilityValueByPeriod(shop, dateStart, dateEnd));
                            }
                            break;
                        case 2 : //Кол-во покупок
                            record.put(cellname, sm.getChequeCountTotal());
                            if (cell == smList.size()) record.put("totals", salesService.getCqcountValueByPeriod(shop, dateStart, dateEnd));
                            break;
                        case 3 : //Отчет ККМ
                            record.put(cellname, sm.getValueTotal());
                            if (cell == smList.size()) record.put("totals", salesService.getValueByPeriod(shop, dateStart, dateEnd));
                            break;
                        case 4 : //Возвраты
                            record.put(cellname, sm.getCashbackTotal());
                            if (cell == smList.size()) record.put("totals", salesService.getCashbackValueByPeriod(shop, dateStart, dateEnd));
                            break;
                        case 5 : //Выручка
                            record.put(cellname, sm.getSalesTotal());
                            if (cell == smList.size()) record.put("totals", salesService.getSalesValueByPeriod(shop, dateStart, dateEnd));
                            break;
                        case 6 : //Средний чек
                            record.put(cellname, sm.getPeriodMidPrice());
                            if (cell == smList.size()) record.put("totals", salesService.getMidPriceValueByPeriod(shop, dateStart, dateEnd));
                            break;
                    }
                }
                
                recordsList.add(record);
            }
        }
        
        return recordsList;
    }
    
    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "/reports/graph/sales-pass", method = RequestMethod.GET)
    public String getSalesPassGraph(
            @RequestParam(required = false) String dateStartStr,
            @RequestParam(required = false) String dateEndStr,
            @RequestParam(required = false) Integer shopid,
            ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel("Графики проходимости и продаж - ИнфоПортал", 
                "reports", "graph"));
        
        List<Shop> shopList = shopService.getAllOpen();
        List<String> graphDataDayTotal = new ArrayList<>();
        List<String> graphDataPassability = new ArrayList<>();

        Shop shop = shopid == null ? shopList.get(0) : shopService.getById(shopid);

        model.addAttribute("shop", shop);
        model.addAttribute("shopList", shopList);

       DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.YYYY");
        LocalDate dateStart = dateStartStr == null 
                ? LocalDate.now().withDayOfMonth(1) 
                : formatter.parseLocalDate(dateStartStr).withDayOfMonth(1);
        LocalDate dateEnd = dateEndStr == null 
                ? LocalDate.now().dayOfMonth().withMaximumValue() 
                : formatter.parseLocalDate(dateEndStr).dayOfMonth().withMaximumValue();
        
        if(dateEnd.isBefore(dateStart)) dateEnd = dateStart.dayOfMonth().withMaximumValue();
        
        model.addAttribute("dateStartStr", dateStart.toString("dd.MM.YYYY"));
        model.addAttribute("dateEndStr", dateEnd.toString("dd.MM.YYYY"));

        List<SalesMeta> smList = salesService.getByPeriod(shop, dateStart, dateEnd);
        
        for (Sales sales : salesService.getAllSalesFromMetaList(smList))
        {
            if (shop.isCountersEnabled())
            {
                Counter counter = counterService.getByShopAndDate(shop, sales.getDate().toDateTime(LocalTime.MIDNIGHT));
                if(sales.getPassability() == 0) sales.setPassability(counter == null ? 0 : counter.getIn());
            }

            graphDataDayTotal.add(sales.getGraphDataDayTotal());
            graphDataPassability.add(sales.getGraphDataPassability());
        }

        model.addAttribute("graphDataDayTotal", graphDataDayTotal);
        model.addAttribute("graphDataPassability", graphDataPassability);

        return "reports/sales-pass-graph";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/forceSalesAutoload", method = RequestMethod.POST)
    public String forceSalesAutoload(ModelMap model)
    {
        salesLoader.processSales(shopService.getAllOpen());
        return "redirect:/reports/sales-pass";
    }
    
    
    //      //
    // План //
    //      //
    
    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "/actions/setPlan", method = RequestMethod.GET)
    public String getPlanSetupPage(
            @RequestParam(required = false) String dateMonth,
            @RequestParam(required = false) String dateYear,
            ModelMap model)
    {
        model.addAllAttributes(utils.prepareModel("Установить план продаж - ИнфоПортал", 
                "actions", "plan", 
                dateMonth, dateYear));
        
        model.addAttribute("organisationList", organisationService.getAll());
        model.addAttribute("salesMap", utils.getShopSalesMap(dateMonth, dateYear));

        return "actions/setPlan";
    }

    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "/actions/setPlanAll", method = RequestMethod.POST)
    public String setPlanAll(
            @RequestParam String dateMonth,
            @RequestParam String dateYear,
            @RequestParam String value,
            ModelMap model)
    {
        String status;
        Double plan = 0.0;
        try
        {
            plan = Double.valueOf(value.replace(",", "."));

            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMM YYYY");
            LocalDate startDate = formatter.parseLocalDate("01 " + dateMonth + " " + dateYear);
            LocalDate endDate = formatter.parseLocalDate("01 " + dateMonth + " " + dateYear).dayOfMonth().withMaximumValue();
            List<Shop> shopList = shopService.getAllOpen();
            for(Shop shop: shopList)
            {
                SalesMeta sm = salesService.getByShopAndDate(shop, startDate, endDate);
                if(sm.getId() == null) salesService.update(sm);
            }
            
            
            if (salesService.updatePlanAll(plan, startDate, endDate))
            {
                status = "ok";
            }
            else
            {
                status = "error-server";
            }

            
        } catch (NumberFormatException e)
        {
            status = "error-all";
        }

        model.addAttribute("dateMonth", dateMonth);
        model.addAttribute("dateYear", dateYear);
        model.addAttribute("status", status);

        return "redirect:/actions/setPlan";
    }
    
    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "/actions/setPlanByOrg", method = RequestMethod.POST)
    public String setPlanByOrg(
            @RequestParam String dateMonth,
            @RequestParam String dateYear,
            @RequestParam Integer orgId,
            @RequestParam String value,
            ModelMap model)
    {
        String status;
        Double plan = 0.0;
        try
        {
            plan = Double.valueOf(value.replace(",", "."));

            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMM YYYY");
            LocalDate startDate = formatter.parseLocalDate("01 " + dateMonth + " " + dateYear);
            LocalDate endDate = formatter.parseLocalDate("01 " + dateMonth + " " + dateYear).dayOfMonth().withMaximumValue();
            Set<Shop> shopList = organisationService.getById(orgId).getShops();
            for(Shop shop: shopList)
            {
                SalesMeta sm = salesService.getByShopAndDate(shop, startDate, endDate);
                sm.setPlan(plan);
                salesService.update(sm);
                
            }
            status = "ok";
        } 
        catch (NumberFormatException e)
        {
            status = "error-org";
        }

        model.addAttribute("dateMonth", dateMonth);
        model.addAttribute("dateYear", dateYear);
        model.addAttribute("status", status);

        return "redirect:/actions/setPlan";
        
    }
    
    @Secured("ROLE_OFFICE")
    @RequestMapping(value = "/actions/setPlanCustom", method = RequestMethod.POST)
    public @ResponseBody String setPlanCustom(
            @RequestParam String dateMonth,
            @RequestParam String dateYear,
            @RequestBody List<SalesPlanForm> salesPlanForms)
    {
        String resultStr;

        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMM YYYY");
        LocalDate selectedStartDate = formatter.parseLocalDate("01 " + dateMonth + " " + dateYear);
        LocalDate selectedEndDate = selectedStartDate.dayOfMonth().withMaximumValue();
        
        for(SalesPlanForm spf : salesPlanForms)
        {
            Shop shop = shopService.getByName(spf.getShopname());
            SalesMeta salesMeta = salesService.getByShopAndDate(shop, selectedStartDate, selectedEndDate);
            if(!salesMeta.getPlan().equals(spf.getPlan()))
            {
                salesMeta.setPlan(spf.getPlan());
                salesService.update(salesMeta);
            }
        }
        
        resultStr = "{\"result\": \"success\"}";
        
        return resultStr;
        
    }
}
