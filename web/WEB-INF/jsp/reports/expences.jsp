<%-- 
    Document   : expences
    Created on : 01.08.2016, 10:35:26
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<c:url value="/res/css/bootstrap.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/bootstrap-theme.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/jqgrid/jquery-ui.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/jqgrid/jquery-ui.theme.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/jqgrid/ui.jqgrid.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/styles.css" />" >
        <title><c:out value="${title}" /></title>
    </head>

    <body>
        <div class="wrapper">
            <div class="container-fluid content">
                <%@include file="../modules/header.jspf" %>

                <br>

                <div class="row">

                    <%@include file="../modules/sideMenu/sideMenu_reports_exp.jspf" %> 

                    <br><br>

                    <div class="col-md-10">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Общая таблица расходов по магазинам: 
                                <u><c:out value="${selectedMonth}" /> <c:out value="${selectedYear}" /></u>
                            </div>
                            <div class="panel-body">

                                <!-- Выбор периода -->
                                <div class="form-inline" align="right">
                                    <form name="dateChooseForm" action="<c:url value="/reports/expences" />" method="GET">
                                        Показать данные за: 
                                        <select name="dateMonth" class="form-control" >
                                            <c:forEach items="${monthList}" var="month" >
                                                <option ${month == selectedMonth ? "selected" : ""} value="${month}">
                                                    <c:out value="${month}" />
                                                </option>
                                            </c:forEach>
                                        </select>
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
                                <br><br>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%@include file="../modules/footer.jspf" %>
        </div>




    <script type="text/javascript" src="<c:url value="/res/js/grid.locale-ru.js" />"></script>
    <script type="text/javascript" src="<c:url value="/res/js/jquery.jqGrid.min.js" />"></script>


    <script type="text/javascript">
        $(function () {

            $("#reportTable").jqGrid({
                url: "<c:url value='/reports/expences/data?dateMonth=${selectedMonth}&dateYear=${selectedYear}' />",
                datatype: "json",
                loadonce: true,
                height: "350px",
                width: null,
                shrinkToFit: false,
                caption: "",
                rowNum: -1,
                sortname: "shopname",
                colNames: ['Магазин', 'Выручка', 'Затраты периодические', 'Затраты разовые', 'Затраты всего', 'Чистая прибыль', 'План', 'Выполнение'],
                colModel: [
                    {name: 'shopname', index: 'shopname', width: 150, sorttype: "text", classes: "jqcol-hyperlink"},
                    {name: 'sales', index: 'sales', width: 100, sorttype: "float", align: "right", formatter: 'number', 
                        formatoptions: {
                            decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'
                        }},
                    {name: 'expencesRec', index: 'expencesRec', width: 100, sorttype: "float", align: "right", formatter: 'number', 
                        formatoptions: {
                            decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'
                        }},
                    {name: 'expencesOne', index: 'expencesOne', width: 100, sorttype: "float", align: "right", formatter: 'number', 
                        formatoptions: {
                            decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'
                        }},
                    {name: 'expencesTotal', index: 'expencesTotal', width: 100, sorttype: "float", align: "right", formatter: 'number', classes: "jqcol-font-bold",
                        formatoptions: {
                            decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'
                        }},
                    {name: 'cleanSales', index: 'cleanSales', width: 100, sorttype: "float", align: "right", formatter: 'number', classes: "jqcol-font-bold",
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
                        url: "<c:url value="/reports/expences/details?dateMonth=${selectedMonth}&dateYear=${selectedYear}&shopname=" />" + shopname,
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
                            if(rd.info == "Чистая прибыль" || rd.info == "Выручка" || rd.info == "Итого расходов") return { "class": "jqrow-yellow" };
                        }
                    });
                }
            });
        });
        
    </script>

</body>
</html>