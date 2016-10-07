<%-- 
    Document   : expences
    Created on : 06.10.2016, 9:07:57
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
        <link rel="stylesheet" href="<c:url value="/res/css/jquery.dataTables.css" />" >
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
                                </div>
                                <br>
                                <div id="collasible">
                                    <h4>Добавить типы расходов</h4>
                                    <div>
                                        <c:url var="performers_post_url"  value="/expences/addtype?shopid=${shop.id}&dateYear=${selectedYear}" />
                                            <form:form method="post" commandName="expenceTypesForm" action="${performers_post_url}">
                                                <table id="typesChooseTab">
                                                    <thead>
                                                        <tr>
                                                            <th bgcolor="#d9edf7">#</th>
                                                            <th bgcolor="#d9edf7">Наименование</th>
                                                            <th bgcolor="#d9edf7">Описание</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${allExpTypes}" var="expType" varStatus="idx">
                                                            <tr>
                                                                <td>
                                                                    <c:choose>
                                                                        <c:when test="${addedExpTypes.contains(expType)}">
                                                                            <form:checkbox path="types" value="${expType.id}" checked="true" disabled="true" />
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <form:checkbox path="types" value="${expType.id}" />
                                                                        </c:otherwise>
                                                                    </c:choose>

                                                                </td>
                                                                <td><c:out value="${expType.name}" /></td>
                                                                <td><c:out value="${expType.comment}" /></td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                                <br>
                                                <input id="save" type="submit" name="save" value="Добавить" class="btn btn-primary">
                                            </form:form>
                                    </div>
                                </div>
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

            $("#expencesTable").jqGrid({
                url: "<c:url value='/expences/data?shopid=${shop.id}&dateYear=${selectedYear}' />",
                datatype: "json",
                loadonce: true,
                height: "280px",
                width: null,
                shrinkToFit: false,
                caption: "",
                rowNum: 1000,
                footerrow: true,
                cellEdit: true,
                cellsubmit: "remote",
                cellurl: "<c:url value='/expences/savecell?shopid=${shop.id}&dateYear=${selectedYear}' />",
                beforeSubmitCell: function(rowid,celname,value,iRow,iCol) {       
                    var val = $('#expencesTable').jqGrid('getCell',rowid,'expencetype');
                    return {"${_csrf.parameterName}":"${_csrf.token}", "extypename":val}
                },
                afterEditCell: function (id, name, val, iRow, iCol) {
                    $("#" + this.id + " tbody>tr:eq(" + iRow + ")>td:eq(" + iCol + ") input, select, textarea").select();
                },
                afterSaveCell: function (id, name, val, iRow, iCol) {
                    var sum = 0;
                    for(var i = 1; i <= 12; i++) {sum += parseFloat($('#expencesTable').jqGrid('getCell',id,'c'+i))};
                    jQuery("#expencesTable").jqGrid('setRowData', id, {totals: sum});
                    
                    var elem = {};
                    elem[name] = parseFloat($('#expencesTable').jqGrid('getCol', name, false, 'sum'));
                    elem['totals'] = parseFloat($('#expencesTable').jqGrid('getCol', 'totals', false, 'sum'));
                    $("#expencesTable").jqGrid('footerData','set', elem);

                },
                loadComplete: function () {
                    var elem = {};
                    for(var i = 1; i <= 12; i++) {
                        var cellname = 'c'+i;
                        elem[cellname] = parseFloat($('#expencesTable').jqGrid('getCol', cellname, false, 'sum'))
                    };
                    elem['expencetype'] = 'Итого: ';
                    elem['totals'] = parseFloat($('#expencesTable').jqGrid('getCol', 'totals', false, 'sum'));

                    $("#expencesTable").jqGrid('footerData','set', elem);

                },
                sortname: "expencetype",
                colNames: ['Описание расхода', 'Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл', 'Авг', 'Сен', 'Окт', 'Ноя', 'Дек', 'Итого'],
                colModel: [
                    {name: 'expencetype', index: 'expencetype', width: 120, sorttype: "text", classes: "jqcol-hyperlink",
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
                ]

            });
            
            $("#typesChooseTab").DataTable({
                    "scrollY": "200px",
                    "scrollCollapse": true,
                    "paging": false
            });
            
            $("#collasible").accordion({
                collapsible: true,
                active: false,
                heightStyle: "content"
            });
            
            
            
        });
        
    </script>

</body>
</html>