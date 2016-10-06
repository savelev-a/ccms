<%-- 
    Document   : expences
    Created on : 06.10.2016, 9:07:57
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
                <%@include file="../../modules/header.jspf" %>

                <br>

                <div class="row">

                    <%@include file="../../modules/sideMenu/sideMenu_shop.jspf" %> 

                    <br><br>

                    <div class="col-md-10">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Установка расходов по магазину  
                                <c:out value="${shop.name}" /> за <c:out value="${selectedYear}" /> год.
                            </div>
                            <div class="panel-body">

                                <!-- Выбор периода -->
                                <div class="form-inline" align="right">
                                    <form name="dateChooseForm" action="<c:url value="/expences" />" method="GET">
                                        Выберите год: 
                                        <select name="dateYear" class="form-control" >
                                            <c:forEach items="${yearList}" var="year" >
                                                <option ${year == selectedYear ? "selected" : ""} value="${year}">
                                                    <c:out value="${year}" />
                                                </option>
                                            </c:forEach>
                                        </select>&nbsp;
                                        
                                        <input type="hidden" name="shopid" value="${shop.id}">
                                        <input type="submit" value="Загрузить" class="btn btn-primary">
                                    </form>
                                    <br>
                                </div>

                                <!-- Placeholder для основной таблицы -->
                                <div style="width: 100%; height: 100%;">
                                    <table id="expencesTable">

                                    </table>
                                    <br>
                                    <div id="expencesTableNav">
                                        <button id="addRecord" class="btn btn-primary">Добавить</button>
                                    </div>
                                </div>
                                <br><br>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%@include file="../../modules/footer.jspf" %>
        </div>




    <script type="text/javascript" src="<c:url value="/res/js/grid.locale-ru.js" />"></script>
    <script type="text/javascript" src="<c:url value="/res/js/jquery.jqGrid.min.js" />"></script>


    <script type="text/javascript">
        $(function () {
            
            var extypesdata = (function (){
                var list = null;
                
                $.ajax({
                    'async': false,
                    'global': false,
                    'url': '<c:url value="/expences/typedata" />',
                    'dataType': 'json',
                    'success': function (data) {
                        list = data;
                    }
                });
                
                return list;
            });

            $("#expencesTable").jqGrid({
                url: "<c:url value='/expences/data?shopid=${shop.id}&dateYear=${selectedYear}' />",
                datatype: "json",
                loadonce: true,
                height: "350px",
                width: null,
                shrinkToFit: false,
                caption: "",
                rowNum: -1,
                cellEdit: true,
                cellsubmit: "clientArray",
                afterEditCell: function (id, name, val, iRow, iCol) {
                    $("#" + this.id + " tbody>tr:eq(" + iRow + ")>td:eq(" + iCol + ") input, select, textarea").select();
                },
                sortname: "expencetype",
                colNames: ['Описание расхода', 'Янв', 'Фев', 'Мар', 'Апр', 'Майь', 'Июн', 'Июл', 'Авг', 'Сен', 'Окт', 'Ноя', 'Дек', 'Итого'],
                colModel: [
                    {name: 'expencetype', index: 'expencetype', width: 200, sorttype: "text", classes: "jqcol-hyperlink"},
                    <c:forEach varStatus="idx" begin="1" end="12">
                        {name: 'c${idx.count}', index: 'c${idx.count}', width: 60, sorttype: "float", editable: true, align: "right", formatter: 'number', 
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
                ]

            });
            
            
        });
        
    </script>

</body>
</html>