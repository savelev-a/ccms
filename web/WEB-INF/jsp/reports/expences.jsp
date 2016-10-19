<%-- 
    Document   : expences
    Created on : 01.08.2016, 10:35:26
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<ccms:page title="Отчет по расходам">
    <ccms:layout mainMenuActiveItem="reports" sideMenuSection="reports_exp" sideMenuActiveItem="all">
        <ccms:panel cols="10" title="Общая таблица расходов по магазинам за ${selectedYear} год">

            <!-- Выбор периода -->
            <div class="form-inline" align="right">
                <form name="dateChooseForm" action="<c:url value="/reports/expences" />" method="GET">
                    Выберите год: 
                    <select name="dateYear" class="form-control" >
                        <c:forEach items="${yearList}" var="year" >
                            <option ${year == selectedYear ? "selected" : ""} value="${year}">
                                <c:out value="${year}" />
                            </option>
                        </c:forEach>
                    </select>&nbsp;

                    <input type="submit" value="Загрузить" class="btn btn-primary">
                </form>
                <br>
            </div>

            <!-- Placeholder для основной таблицы -->
            <div style="width: 100%; height: 100%;">
                <table id="reportTable">

                </table>
            </div>
            <br>
            <b>Примечание: </b>Выручка, затраты и чистая прибыль считаются с начала выбранного года. <br>
            План. затраты в мес. - это средние ежемесячные затраты за последние 6 месяцев. <br>
            План. прибыль в мес. - это средняя чистая прибыль магазина за последние 6 месяцев.<br>
        </ccms:panel>
    </ccms:layout>
                                
    <script type="text/javascript" src="<c:url value="/res/js/grid.locale-ru.js" />"></script>
    <script type="text/javascript" src="<c:url value="/res/js/jquery.jqGrid.min.js" />"></script>

    <script type="text/javascript">
        $(function () {

            $("#reportTable").jqGrid({
                url: "<c:url value='/reports/expences/data?dateYear=${selectedYear}' />",
                datatype: "json",
                loadonce: true,
                height: "280px",
                width: null,
                shrinkToFit: false,
                caption: "",
                rowNum: 1000,
                footerrow: true,
                sortname: "shopname",
                colNames: ['Магазин', 'Выручка', 'Затраты', 'Чистая прибыль', 'План. затраты в мес.', 'План. прибыль в мес.'],
                colModel: [
                    {name: 'shopname', index: 'shopname', width: 150, sorttype: "text", classes: "jqcol-hyperlink"},
                    {name: 'sales', index: 'sales', width: 150, sorttype: "float", align: "right", formatter: 'number', 
                            formatoptions: {
                                decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'
                            }},
                    {name: 'expences', index: 'expences', width: 150, sorttype: "float", align: "right", formatter: 'number', 
                        formatoptions: {
                            decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'
                        }},
                    {name: 'cleanSales', index: 'cleanSales', width: 150, sorttype: "float", align: "right", formatter: 'number', classes: "jqcol-font-bold",
                        formatoptions: {
                            decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'
                        }},
                    {name: 'midExpences', index: 'midExpences', width: 150, sorttype: "float", align: "right", formatter: 'number',
                        formatoptions: {
                            decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'
                        }},
                    {name: 'midCleanSales', index: 'midCleanSales', width: 180, sorttype: "float", align: "right", formatter: 'number',
                        formatoptions: {
                            decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'
                        }}
                ],
                loadComplete: function () {
                    var elem = {};
                    elem['shopname'] = 'Итого: ';
                    elem['sales'] = parseFloat($('#reportTable').jqGrid('getCol', 'sales', false, 'sum'));
                    elem['expences'] = parseFloat($('#reportTable').jqGrid('getCol', 'expences', false, 'sum'));
                    elem['cleanSales'] = parseFloat($('#reportTable').jqGrid('getCol', 'cleanSales', false, 'sum'));
                    elem['midExpences'] = parseFloat($('#reportTable').jqGrid('getCol', 'midExpences', false, 'sum'));
                    elem['midCleanSales'] = parseFloat($('#reportTable').jqGrid('getCol', 'midCleanSales', false, 'sum'));

                    $("#reportTable").jqGrid('footerData','set', elem);

                },
                subGrid: true,
                subGridRowExpanded: function(subgrid_id, row_id) {
                    var subgrid_table_id = subgrid_id + "_t";
                    var shopname = jQuery("#reportTable").jqGrid('getCell', row_id, 'shopname');
                    var subgrid_width = jQuery("#reportTable").width() - 25;
                    
                    $("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table>");
                    jQuery("#"+subgrid_table_id).jqGrid({
                        url: "<c:url value="/reports/expences/details?dateYear=${selectedYear}&shopname=" />" + shopname,
                        datatype: "json",
                        height: "100%",
                        shrinkToFit: false,
                        width: subgrid_width,
                        colNames: ['Описание расхода', 'Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл', 'Авг', 'Сен', 'Окт', 'Ноя', 'Дек', 'Итого'],
                        colModel: [
                            {name: 'expencetype', index: 'expencetype', width: 120, sorttype: "text", classes: "jqcol-bold",
                                cellattr: function (rowId, val, rawObject, cm, rdata) {
                                    return 'title="' + rawObject.expencenote + '"';
                                }
                            },
                            <c:forEach varStatus="idx" begin="1" end="12">
                                {name: 'c${idx.count}', index: 'c${idx.count}', width: 69, sorttype: "float", editable: true, align: "right", formatter: 'number', 
                                    editrules: {
                                        number: true, minValue: 0
                                    },
                                    formatoptions: {
                                        decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'
                                    }},
                            </c:forEach>
                            {name: 'totals', index: 'totals', width: 100, sorttype: "float", align: "right", formatter: 'number', 
                                formatoptions: {
                                    decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'
                                }
                            },
                        ],
                        rowattr: function(rd) {
                            if(rd.info == "ИТОГО" || rd.info == "Выручка" || rd.info == "Чистая прибыль") return { "class": "jqrow-yellow" };
                        }
                    });
                }
            });
        });
            
    </script>
</ccms:page>