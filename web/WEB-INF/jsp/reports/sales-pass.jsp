<%-- 
    Document   : sales-pass
    Created on : 03.05.2016, 10:54:14
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<ccms:page title="Отчет по проходимости и продажам">
    <ccms:layout mainMenuActiveItem="reports" sideMenuSection="reports_sp" sideMenuActiveItem="general">
        <ccms:panel cols="10" title="Общая таблица проходимости и выручек по магазинам за период с ${dateStartStr} по ${dateEndStr}">

            <!-- Выбор периода -->
            <div class="form-inline" align="right">
                <form name="dateChooseForm" action="<c:url value="/reports/sales-pass" />" method="GET">
                    Показать данные за период с  
                    <input type="text" name="dateStartStr" id="dateStartField" value="${dateStartStr}" class="form-control datepicker-z">
                    по
                    <input type="text" name="dateEndStr" id="dateEndField" value="${dateEndStr}" class="form-control datepicker-z"> 
                    &nbsp;
                    <input type="submit" value="Загрузить" class="btn btn-primary">
                </form>
                <br>
            </div>

            <!-- Placeholder для основной таблицы -->
            <div style="width: 100%; height: 100%;">
                <table id="reportTable">

                </table>
            </div>
            <br><br>
            <span class="glyphicon glyphicon-print"></span>
            <a href="<c:url value='/reports/sales-pass?dateStartStr=${dateStartStr}&dateEndStr=${dateEndStr}&mode=print' /> " target="_blank">Распечатать</a>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <br><br>
                <form action="<c:url value='/forceSalesAutoload' />" method="POST">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <input type="submit" value="Запустить цикл автообновления вручную" class="btn btn-primary">
                </form>
            </sec:authorize>
        </ccms:panel>
    </ccms:layout>
                                    
    <script type="text/javascript" src="<c:url value="/res/js/grid.locale-ru.js" />"></script>
    <script type="text/javascript" src="<c:url value="/res/js/jquery.jqGrid.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/res/js/datepicker-ru.js" />"></script>

    <script type="text/javascript">
        $(function () {

            $("#reportTable").jqGrid({
                url: "<c:url value="/reports/sales-pass/data?dateStartStr=${dateStartStr}&dateEndStr=${dateEndStr}" />",
                datatype: "json",
                loadonce: true,
                height: "350px",
                width: null,
                shrinkToFit: false,
                caption: "",
                rowNum: -1,
                sortname: "shopname",
                colNames: ['Магазин', 'Проходимость', 'Кол-во покупок', 'Выручка по ККМ', 'Возвраты', 'Средний чек', 'Итого', 'План', 'Выполнение'],
                colModel: [
                    {name: 'shopname', index: 'shopname', width: 150, sorttype: "text", classes: "jqcol-hyperlink"},
                    {name: 'passability', index: 'passability', width: 100, sorttype: "int", align: "right"},
                    {name: 'cheque', index: 'cheque', width: 100, sorttype: "int", align: "right"},
                    {name: 'value', index: 'value', width: 100, sorttype: "float", align: "right", formatter: 'number',
                        formatoptions: {
                            decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'
                        }},
                    {name: 'cashback', index: 'cashback', width: 100, sorttype: "float", align: "right", formatter: 'number',
                        formatoptions: {
                            decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'
                        }},
                    {name: 'midPrice', index: 'midPrice', width: 100, sorttype: "float", align: "right", formatter: 'number',
                        formatoptions: {
                            decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'
                        }},
                    {name: 'periodtotal', index: 'periodtotal', width: 100, sorttype: "float", align: "right", formatter: 'number', classes: "jqcol-font-bold",
                        formatoptions: {
                            decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'
                        }},
                    {name: 'plan', index: 'plan', width: 100, sorttype: "float", align: "right", formatter: 'number',
                        formatoptions: {
                            decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'
                        }},
                    {name: 'plancoverage', index: 'plancoverage', width: 100, sorttype: "float", align: "right", formatter: 'number',
                        formatoptions: {
                            decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00', suffix: '%'
                        }}
                ],
                subGrid: true,
                subGridRowExpanded: function(subgrid_id, row_id) {
                    var subgrid_table_id = subgrid_id + "_t";
                    var shopname = jQuery("#reportTable").jqGrid('getCell', row_id, 'shopname');
                    var subgrid_width = jQuery("#reportTable").width() - 25;
                    
                    $("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table>");
                    jQuery("#"+subgrid_table_id).jqGrid({
                        url: "<c:url value="/reports/sales-pass/details?dateStartStr=${dateStartStr}&dateEndStr=${dateEndStr}&shopname=" />" + shopname,
                        datatype: "json",
                        height: "100%",
                        //autowidth: true,
                        shrinkToFit: false,
                        width: subgrid_width,
                        colNames: [<c:forEach items="${subgridColNames}" var="colName" >'<c:out value="${colName}" />', </c:forEach>],
                        colModel: [
                                {name: "info", index: "info", width: 100, classes: 'jqcol-bold'},
                                <c:forEach items="${subgridColNames}" varStatus="idx" begin="2">
                                        {name: "c${idx.count}", index: "c${idx.count}", width: 70, align: "right", formatter: 'number', 
                                            formatoptions: {
                                                decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'
                                            }
                                        },
                                </c:forEach>
                                {name: "totals", index: "totals", width: 70, align: "right", formatter: 'number', 
                                formatoptions: {
                                                decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'
                                            }
                                        }
                        ],
                        rowattr: function(rd) {
                            if(rd.info == "Выручка" || rd.info == "Средний чек") return { "class": "jqrow-yellow" };
                        }
                    });
                }
                
            });
            
            $("#dateStartField").datepicker();
            $("#dateEndField").datepicker();
        });
    </script>
</ccms:page>