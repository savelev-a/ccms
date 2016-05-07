<%-- 
    Document   : setPlan
    Created on : 04.05.2016, 12:48:28
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
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

                    <div class="col-md-6">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Установить план по отдельным магазинам</div>
                            <div class="panel-body">
                                <div class="form-inline" align="right">
                                    <form name="setByOrgForm" action="<c:url value="/actions/setPlan" />" method="GET">
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
                                        </select>
                                        <input type="submit" value="Загрузить" class="btn btn-primary">
                                    </form>
                                    <br>
                                </div>


                                <!-- Placeholder для основной таблицы -->
                                <div style="width: 100%; height: 100%;">
                                    <table id="planTable">

                                    </table>
                                </div>
                                <br>
                                <input type="button" id="saveTable" class="btn btn-primary" value="Сохранить">
                            </div>
                        </div>
                                        
                                        
                        <!-- Диалоги -->
                        <div id="msgSuccess" title="Сохранение успешно">
                            <p>
                                <span class="glyphicon glyphicon-ok-circle" style="float:left; margin:0 7px 50px 0;"></span> 
                                Сохранение успешно.
                            </p>
                        </div>
                        <div id="msgError" title="">
                            <p>

                            </p>
                        </div>
                    </div>

                    <div class="col-md-4">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Установить план по юр. лицу</div>
                            <div class="panel-body">

                                <form name="setByOrgForm" action="<c:url value="/actions/setPlanByOrg" />" method="POST">
                                    <table class="table table-condensed">
                                        <tbody>
                                            <tr>
                                                <td>Юр. лицо: </td>
                                                <td colspan="2">
                                                    <select name="orgId" class="form-control">
                                                        <c:forEach items="${organisationList}" var="org">
                                                            <option value="${org.id}">
                                                                <c:out value="${org.name}" />
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Период: </td>
                                                <td>
                                                    <select name="dateMonth" class="form-control" >
                                                        <c:forEach items="${monthList}" var="month" >
                                                            <option ${month == selectedMonth ? "selected" : ""} value="${month}">
                                                                <c:out value="${month}" />
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </td><td>
                                                    <select name="dateYear" class="form-control" >
                                                        <c:forEach items="${yearList}" var="year" >
                                                            <option ${year == selectedYear ? "selected" : ""} value="${year}">
                                                                <c:out value="${year}" />
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>План: </td>
                                                <td colspan="2">
                                                    <input type="text" name="value" class="form-control"  >
                                                </td>
                                            </tr>
                                            <tr>
                                                <td colspan="3">
                                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                                    <input type="submit" value="Установить" class="btn btn-primary">
                                                </td>

                                            </tr>
                                        </tbody>
                                    </table>

                                </form>



                            </div>
                        </div>
                        <br>
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Установить план по всем магазинам</div>
                            <div class="panel-body">

                                <form name="setAllForm" action="<c:url value="/actions/setPlanAll" />" method="POST">
                                    <table class="table table-condensed">
                                        <tbody>
                                            <tr>
                                                <td>Период: </td>
                                                <td>
                                                    <select name="dateMonth" class="form-control" >
                                                        <c:forEach items="${monthList}" var="month" >
                                                            <option ${month == selectedMonth ? "selected" : ""} value="${month}">
                                                                <c:out value="${month}" />
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </td><td>
                                                    <select name="dateYear" class="form-control" >
                                                        <c:forEach items="${yearList}" var="year" >
                                                            <option ${year == selectedYear ? "selected" : ""} value="${year}">
                                                                <c:out value="${year}" />
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td>План для всех: </td>
                                                <td colspan="2">
                                                    <input type="text" name="value" class="form-control" >
                                                    <c:if test="${status == 'error-all'}">
                                                        <font color="#ff0000">Неверное значение</font>
                                                    </c:if>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td colspan="3">
                                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                                    <input type="submit" value="Установить" class="btn btn-primary">
                                                </td>

                                            </tr>
                                        </tbody>
                                    </table>


                                </form>

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
                    shopname: "<c:out value='${entry.key.name}' />",
                    passability: <c:out value='${entry.value.passabilityTotals}' default="0" />,
                    cheque: <c:out value='${entry.value.chequeTotals}' default="0" />,
                    periodtotal: <c:out value='${entry.value.periodTotals}' default="0.0" />,
                    plan: <c:out value='${entry.value.plan}' default="0.0" />,
                    plancoverage: <c:out value='${entry.value.plan == 0 ? 0 : entry.value.planCoverage}' default="0.0" />
                }, </c:forEach>];

            $("#planTable").jqGrid({
                datatype: "local",
                height: "100%",
                width: null,
                shrinkToFit: false,
                caption: "",
                sortname: "shopname",
                colNames: ['Магазин', 'Проходимость', 'Покупок', 'Выручка', 'План', 'Выполнение'],
                colModel: [
                    {name: 'shopname', index: 'shopname', width: 100, sorttype: "text", classes: "jqcol-bold"},
                    {name: 'passability', index: 'passability', width: 100, sorttype: "int", align: "right", classes: "jqcol-bold"},
                    {name: 'cheque', index: 'cheque', width: 80, sorttype: "int", align: "right", classes: "jqcol-bold"},
                    {name: 'periodtotal', index: 'periodtotal', width: 100, sorttype: "float", align: "right", formatter: 'number', classes: "jqcol-bold",
                        formatoptions: {
                            decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'
                        }},
                    {name: 'plan', index: 'plan', width: 100, sorttype: "float", align: "right", formatter: 'number', editable: true,
                        formatoptions: {
                            decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'
                        },
                        editrules: {number: true, minValue: 0
                        }},
                    {name: 'plancoverage', index: 'plancoverage', width: 90, sorttype: "float", align: "right", formatter: 'number', classes: "jqcol-bold",
                        formatoptions: {
                            decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00', suffix: '%'
                        }}
                ],
                cellEdit: true,
                cellsubmit: "clientArray",
                afterEditCell: function (id, name, val, iRow, iCol) {
                    $("#" + this.id + " tbody>tr:eq(" + iRow + ")>td:eq(" + iCol + ") input, select, textarea").select();
                },
                afterSaveCell: function (id, name, val, iRow, iCol) {
                    if (name === 'plan') {
                        var v_total = jQuery("#planTable").jqGrid('getCell', id, iCol - 1);
                        if (val !== "0")
                            jQuery("#planTable").jqGrid('setRowData', id, {plancoverage: parseFloat(v_total) / parseFloat(val) * 100});
                        else
                            jQuery("#planTable").jqGrid('setRowData', id, {plancoverage: 0});

                    }
                }
            });

            for (var i = 0; i <= data.length; i++)
                jQuery("#planTable").jqGrid('addRowData', i + 1, data[i]);

            $("#saveTable").click(function () {
                var localGridData = jQuery("#planTable").jqGrid('getGridParam', 'data');
                sendData(localGridData);
            });


            var sendData = function (data) {
                var dataToSend = JSON.stringify(data);
                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                //alert("DEBUG:\n" + dataToSend);
                $.ajax({
                    type: "POST",
                    url: "<c:url value='/actions/setPlanCustom?dateMonth=${selectedMonth}&dateYear=${selectedYear}' />",
                    dataType: "json",
                    data: dataToSend,
                    contentType: "application/json; charset=utf-8",
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function (response, textStatus, jqXHR) {
                        $("#msgSuccess").dialog("open");

                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        $("#msgError").dialog("option", "title", "Ошибка сохранения");
                        $("#msgError").html(
                                "<span class='glyphicon glyphicon-remove-circle' style='float:left; margin:0 7px 150px 0;'></span>"
                                + "Ошибка сохранения данных! <b>Таблица не сохранена.</b> Пожалуйста обратитесь к системному администратору.<br>"
                                + "<br>Тип ошибки: " + textStatus
                                + "<br>Описание ошибки: " + errorThrown
                                );
                        $("#msgError").dialog("open");
                    }

                });
            };

            $("#msgSuccess").dialog({
                autoOpen: false,
                modal: true,
                dialogClass: "no-close",
                buttons: [
                    {
                        text: "OK",
                        class: "btn btn-default",
                        click: function () {
                            location.reload();
                        }
                    }
                ],
                show: {
                    effect: "clip",
                    duration: 200
                },
                hide: {
                    effect: "clip",
                    duration: 200
                },
                close: function () {
                    location.reload();
                }
            });
            $("#msgError").dialog({
                autoOpen: false,
                modal: true,
                dialogClass: "no-close",
                width: 400,
                buttons: [
                    {
                        text: "OK",
                        class: "btn btn-default",
                        click: function () {
                            $(this).dialog("close");
                        }
                    }
                ],
                show: {
                    effect: "clip",
                    duration: 200
                },
                hide: {
                    effect: "clip",
                    duration: 200
                }

            });
        });

    </script>


</body>
</html>