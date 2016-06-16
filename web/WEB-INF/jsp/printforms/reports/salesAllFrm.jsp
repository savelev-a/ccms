<%-- 
    Document   : SalesAllFrm
    Created on : 16.06.2016, 11:14:50
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
        <link rel="stylesheet" href="<c:url value="/res/css/styles.css" />" >
        <style type="text/css" media="print">
            @page { size: portrait; margin: 0cm }
        </style>
        <title>Отчет по выручке и проходимости</title>
    </head>

    <body><small>
        <div class="wrapper">
            <div class="container-fluid content">
                
                <br><br>

                <div class="row">

                    <div class="col-md-12">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Отчет по выручке и проходимости за 
                                <u><c:out value="${selectedMonth}" /> <c:out value="${selectedYear}" /></u></div>
                            <div class="panel-body">
                                <div style="width: 100%; height: 100%;">
                                    <table id="reportTable">

                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="footer">
                Актуально на <c:out value="${currentDate}" />
            </div>

        </div>

            </small>
    
    <link rel="stylesheet" href="<c:url value="/res/css/jqgrid/jquery-ui.css" />" >
    <link rel="stylesheet" href="<c:url value="/res/css/jqgrid/jquery-ui.theme.css" />" >
    <link rel="stylesheet" href="<c:url value="/res/css/jqgrid/ui.jqgrid.css" />" >

    <script src="<c:url value="/res/js/jquery-2.2.2.min.js" /> "></script>
    <script src="<c:url value="/res/js/jquery-ui.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/res/js/grid.locale-ru.js" />"></script>
    <script type="text/javascript" src="<c:url value="/res/js/jquery.jqGrid.min.js" />"></script>


    <script type="text/javascript">
        $(function () {

            $("#reportTable").jqGrid({
                url: "<c:url value="/reports/sales-pass/data?dateMonth=${selectedMonth}&dateYear=${selectedYear}" />",
                datatype: "json",
                loadonce: true,
                height: "100%",
                width: null,
                shrinkToFit: false,
                caption: "",
                rowNum: -1,
                sortname: "shopname",
                colNames: ['Магазин', 'Проходимость', 'Кол-во покупок', 'Выручка по ККМ', 'Возвраты', 'Средний чек', 'Итого', 'План', 'Выполнение'],
                colModel: [
                    {name: 'shopname', index: 'shopname', width: 130, sorttype: "text", classes: "jqcol-hyperlink"},
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
                gridComplete: setTimeout(function(){ window.print(); }, 2000)
                
            });
    
        });
    </script>
    
    </body>
</html>