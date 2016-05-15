<%-- 
    Document   : sales-pass
    Created on : 03.05.2016, 10:54:14
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
        <title><c:out value="${title}" /></title>
    </head>

    <body>
        <div class="wrapper">
            <div class="container-fluid content">
                <%@include file="../modules/header.jspf" %>

                <br>

                <div class="row">

                    <%@include file="../modules/sideMenu/sideMenu_reports_sp.jspf" %> 

                    <br><br>

                    <div class="col-md-10">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Общая таблица проходимости и выручек по магазинам: 
                                <u><c:out value="${selectedMonth}" /> <c:out value="${selectedYear}" /></u>
                            </div>
                            <div class="panel-body">

                                <!-- Выбор периода -->
                                <div class="form-inline" align="right">
                                    <form name="dateChooseForm" action="<c:url value="/reports/sales-pass" />" method="GET">
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

                                        <input type="hidden" name="shopid" value="${shop.id}" />
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
                                <b>Примечание:</b> Чтобы увидеть подробный отчет по конкретному магазину - кликните на его названии 
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <br><br>
                                    <form action="<c:url value='/forceSalesAutoload' />" method="POST">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                        <input type="submit" value="Запустить цикл автообновления вручную" class="btn btn-primary">
                                    </form>
                                </sec:authorize>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%@include file="../modules/footer.jspf" %>
        </div>


    <link rel="stylesheet" href="<c:url value="/res/css/jqgrid/jquery-ui.css" />" >
    <link rel="stylesheet" href="<c:url value="/res/css/jqgrid/jquery-ui.theme.css" />" >
    <link rel="stylesheet" href="<c:url value="/res/css/jqgrid/ui.jqgrid.css" />" >

    <script type="text/javascript" src="<c:url value="/res/js/grid.locale-ru.js" />"></script>
    <script type="text/javascript" src="<c:url value="/res/js/jquery.jqGrid.min.js" />"></script>


    <script type="text/javascript">
        $(function () {
            var data = [<c:forEach items="${salesMap}" var="entry">{
                    shopname: "<a href='<c:url value="/sales/?shopid=${entry.key.id}&dateMonth=${selectedMonth}&dateYear=${selectedYear}" />'> <c:out value='${entry.key.name}' /> </a>",
                    passability: <c:out value='${entry.value.passabilityTotals}' default="0" />,
                    cheque: <c:out value='${entry.value.chequeTotals}' default="0" />,
                    value: <c:out value='${entry.value.valueTotals}' default="0.0" />,
                    cashback: <c:out value='${entry.value.cashbackTotals}' default="0.0" />,
                    periodtotal: <c:out value='${entry.value.periodTotals}' default="0.0" />,
                    midPrice: <c:out value='${entry.value.chequeTotals == 0 ? 0 : entry.value.periodMidPrice}' default="0.0" />,
                    plan: <c:out value='${entry.value.plan}' default="0.0" />,
                    plancoverage: <c:out value='${entry.value.plan == 0 ? 0 : entry.value.planCoverage}' default="0.0" />
                }, </c:forEach>];

            $("#reportTable").jqGrid({
                datatype: "local",
                height: "100%",
                width: null,
                shrinkToFit: false,
                caption: "",
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
                ]
            });

            for (var i = 0; i <= data.length; i++)
                jQuery("#reportTable").jqGrid('addRowData', i + 1, data[i]);
        });
    </script>

</body>
</html>