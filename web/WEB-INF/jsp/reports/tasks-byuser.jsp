<%-- 
    Document   : tasks-byuser
    Created on : 01.10.2016, 10:24:19
    Author     : alchemist
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

                    <%@include file="../modules/sideMenu/sideMenu_dummy.jspf" %> 

                    <br><br>

                    <div class="col-md-10">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Отчет по выполнению задач за период с 
                                <u><c:out value="${dateStartStr}" /> по <c:out value="${dateEndStr}" /></u>
                            </div>
                            <div class="panel-body">

                                <!-- Выбор периода -->
                                <div class="form-inline" align="right">
                                    <form name="dateChooseForm" action="<c:url value="/reports/tasks" />" method="GET">
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
                                <br>
                                
                                
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
    <script type="text/javascript" src="<c:url value="/res/js/datepicker-ru.js" />"></script>


    <script type="text/javascript">
        $(function () {
            
            var grid = $("#reportTable");
            
            grid.jqGrid({
                url: "<c:url value='/reports/tasks/data?dateStartStr=${dateStartStr}&dateEndStr=${dateEndStr}' />",
                datatype: "json",
                loadonce: true,
                height: "350px",
                width: null,
                shrinkToFit: false,
                caption: "",
                rowNum: -1,
                sortname: "username",
                colNames: ['Пользователь', 'Задач выполнено', 'Задач просрочено', 'Процент просрочек', 'Среднее время выполнения'],
                colModel: [
                    {name: 'username', index: 'username', width: 250, sorttype: "text", classes: "jqcol-hyperlink"},
                    {name: 'tasksClosed', index: 'tasksClosed', width: 150, sorttype: "int", align: "right", formatter: 'number', 
                        formatoptions: {
                            decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 0, defaultValue: '0'
                        }},
                    {name: 'tasksOverdue', index: 'tasksOverdue', width: 150, sorttype: "int", align: "right", formatter: 'number', 
                        formatoptions: {
                            decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 0, defaultValue: '0'
                        }},
                    {name: 'overduePercent', index: 'OverduePercent', width: 150, sorttype: "float", align: "right", formatter: 'number', 
                        formatoptions: {
                            decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00', suffix: '%'
                        }},
                    {name: 'midTime', index: 'midTime', width: 180, sorttype: "text", align: "right"}
                ],
                subGrid: true,
                subGridRowExpanded: function(subgrid_id, row_id) {
                    var subgrid_table_id = subgrid_id + "_t";
                    var userfullname = jQuery("#reportTable").jqGrid('getCell', row_id, 'username');
                    var subgrid_width = jQuery("#reportTable").width() - 25;
                    
                    $("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table>");
                    jQuery("#"+subgrid_table_id).jqGrid({
                        url: "<c:url value="/reports/tasks/details?dateStartStr=${dateStartStr}&dateEndStr=${dateEndStr}&userFullName=" />" + userfullname,
                        datatype: "json",
                        height: "100%",
                        shrinkToFit: false,
                        width: subgrid_width,
                        colNames: ['Задача', 'Время закрытия', 'Затраченное время'],
                        colModel: [
                            {name: 'taskName', index: 'taskname', width: 470, sorttype: "text", classes: "jqcol-bold jqcol-font-bold"},
                            {name: 'closeTime', index: 'closeTime', width: 200, sorttype: "text", align: "right"},
                            {name: 'progressTime', index: 'progressTime', width: 200, sorttype: "text", align: "right"}
                        ]
                    })
                },

                footerrow: true,
                loadComplete: function () {
                    var sumClosed = grid.jqGrid('getCol', 'tasksClosed', false, 'sum');
                    var sumOverdue = grid.jqGrid('getCol', 'tasksOverdue', false, 'sum');

                    grid.jqGrid('footerData','set', {'username': 'Итого: ', 'tasksClosed': sumClosed, 'tasksOverdue': sumOverdue, 'overduePercent': sumOverdue/sumClosed * 100});
                }
                        
            });
            
            $("#dateStartField").datepicker();
            $("#dateEndField").datepicker();
        });
        
    </script>

</body>
</html>