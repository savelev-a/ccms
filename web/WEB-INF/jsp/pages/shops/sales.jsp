<%-- 
    Document   : sales
    Created on : 30.04.2016, 17:11:09
    Author     : Alexander Savelev
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
                <%@include file="../../modules/header.jspf" %>

                <br>

                <div class="row">

                    <%@include file="../../modules/sideMenu/sideMenu_shop.jspf" %>

                    <br><br>

                    <div class="col-md-8">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Выручка магазина <c:out value="${shop.name}" /> (ввод данных)</div>
                            <div class="panel-body">

                                <!-- Выбор периода -->
                                <div class="form-inline" align="right">
                                    <form name="dateChooseForm" action="<c:url value="/sales" />" method="GET">
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
                                    <table  id="salesTable">

                                    </table>
                                </div>


                            </div>
                        </div>


                        <!-- Диалоги -->
                        <div id="msgSuccess" title="Сохранение успешно">
                            <p>
                                <span class="glyphicon glyphicon-ok-circle" style="float:left; margin:0 7px 50px 0;"></span> 
                                Сохранение успешно: <c:out value="${salesMeta.description}" />
                            </p>
                        </div>
                        <div id="msgError" title="">
                            <p>

                            </p>
                        </div>
                    </div>

                    <!-- Правая панель -->
                    <div class="col-md-2">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">План</div>
                            <div class="panel-body">
                                <table width="100%">
                                    <fmt:formatNumber value="${salesMeta.plan}" maxFractionDigits="2" var="rp_plan" />
                                    <fmt:formatNumber value="${salesMeta.plan == 0 ? 0 : salesMeta.planCoverage}" maxFractionDigits="2" var="rp_plancover" />
                                    <tr>
                                        <td>План: </td>
                                        <td align="right"><b>&nbsp;${fn:replace(rp_plan, ",", " ")}</b></td>
                                    </tr>
                                    <tr>
                                        <td>Выполнение: </td>
                                        <td align="right"><b>&nbsp;${fn:replace(rp_plancover, ",", " ")}%</b></td>
                                    </tr>
                                </table>
                            </div>
                        </div>

                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Итого:</div>
                            <div class="panel-body">
                                <table width="100%">
                                    <fmt:formatNumber value="${salesMeta.passabilityTotals}" maxFractionDigits="0" var="rp_pass" />
                                    <fmt:formatNumber value="${salesMeta.chequeTotals}" maxFractionDigits="0" var="rp_cheq" />
                                    <fmt:formatNumber value="${salesMeta.valueTotals}" maxFractionDigits="2" var="rp_value" />
                                    <fmt:formatNumber value="${salesMeta.cashbackTotals}" maxFractionDigits="2" var="rp_cashback" />
                                    <fmt:formatNumber value="${salesMeta.periodTotals}" maxFractionDigits="2" var="rp_total" />
                                    <fmt:formatNumber value="${salesMeta.chequeTotals == 0 ? 0 : salesMeta.periodMidPrice}" maxFractionDigits="2" var="rp_mdpr" />
                                    <tr>
                                        <td>Проходимость: </td>
                                        <td align="right"><b>&nbsp;${fn:replace(rp_pass, ",", " ")}</b></td>
                                    </tr>
                                    <tr>
                                        <td>Покупки: </td>
                                        <td align="right"><b>&nbsp;${fn:replace(rp_cheq, ",", " ")}</b></td>
                                    </tr>
                                    <tr>
                                        <td>Выручка: </td>
                                        <td align="right"><b>&nbsp;${fn:replace(rp_value, ",", " ")}</b></td>
                                    </tr>
                                    <tr>
                                        <td>Возвраты: </td>
                                        <td align="right"><b>&nbsp;${fn:replace(rp_cashback, ",", " ")}</b></td>
                                    </tr>
                                    <tr>
                                        <td><b>ИТОГО: </b></td>
                                        <td align="right"><b>&nbsp;${fn:replace(rp_total, ",", " ")}</b></td>
                                    </tr>
                                    <tr>
                                        <td>Средний чек: </td>
                                        <td align="right"><b>&nbsp;${fn:replace(rp_mdpr, ",", " ")}</b></td>
                                    </tr>
                                    <!--<tr>
                                        <td>Продажи/прох.: </td>
                                        <td align="right"><b>&nbsp;<c:out value="${
                                           salesMeta.passabilityTotals == 0 ? 0 : salesMeta.chequeTotals / salesMeta.passabilityTotals * 100
                                           }" /> %</b></td>
                                </tr>-->
                                </table>
                            </div>
                        </div>
                        <input type="button" id="saveTable" class="btn btn-primary btn-primary-long" value="Сохранить">
                    </div>
                </div>
            </div>
            <br>
            <br>
            <%@include file="../../modules/footer.jspf" %>
        </div>

    <link rel="stylesheet" href="<c:url value="/res/css/messagebox.css" />" >
    <link rel="stylesheet" href="<c:url value="/res/css/jqgrid/jquery-ui.css" />" >
    <link rel="stylesheet" href="<c:url value="/res/css/jqgrid/jquery-ui.theme.css" />" >
    <link rel="stylesheet" href="<c:url value="/res/css/jqgrid/ui.jqgrid.css" />" >

    <script type="text/javascript" src="<c:url value="/res/js/grid.locale-ru.js" />"></script>
    <script type="text/javascript" src="<c:url value="/res/js/jquery.jqGrid.min.js" />"></script>


    <script type="text/javascript">
        $(function () {
            var mydata = [
                <c:forEach items="${salesMeta.sales}" var="sales">
                    {
                        date: "<c:out value='${sales.date.toString("dd.MM.YYYY")}' />",
                        passability: <c:out value='${sales.passability}' />,
                        chequeCount: <c:out value='${sales.chequeCount}' />,
                        value: <c:out value='${sales.value}' />,
                        cashback: <c:out value='${sales.cashback}' />,
                        daytotal: <c:out value='${sales.dayTotal}' />,
                        midPrice: <c:out value='${sales.chequeCount == 0 ? 0 : sales.midPrice}' />,
                    },
                </c:forEach>
            ];
                
            $("#salesTable").jqGrid({
                datatype: "local",
                height: "370px",
                width: null,
                shrinkToFit: false,
                colNames: ['Дата', 'Проходимость', 'Кол-во покупок', 'Выручка по ККМ', 'Возвраты', 'Выручка без возвратов', 'Средний чек'],
                colModel: [{
                    name: 'date', index: 'date', width: 90, sorttype: "date", classes: "jqcol-bold"
                }, {
                    name: 'passability', index: 'passability', width: 100, sorttype: "int", ${shop.countersEnabled ? "classes: 'jqcol-bold', " : "editable: true, "} align: "right",
                        editrules: {
                            integer: true, minValue: 0
                        }
                }, {
                    name: 'chequeCount', index: 'chequeCount', width: 110, sorttype: "int", editable: true, align: "right",
                        editrules: {
                            integer: true, minValue: 0
                        }
                }, {
                    name: 'value', index: 'value', width: 110, sorttype: "float", formatter: 'number', editable: true, align: "right",
                        editrules: {
                            number: true, minValue: 0
                        },
                        formatoptions: {
                            decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'
                        }
                }, {
                    name: 'cashback', index: 'cashback', width: 100, sorttype: "float", formatter: 'number', editable: true, align: "right",
                        editrules: {
                            number: true, minValue: 0
                        },
                        formatoptions: {
                            decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'
                        }
                }, {
                    name: 'daytotal', index: 'daytotal', width: 150, sorttype: "float", formatter: 'number', classes: "jqcol-bold", align: "right",
                        formatoptions: {
                            decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'
                        }
                }, {
                    name: 'midPrice', index: 'midPrice', width: 110, sorttype: "float", formatter: 'number', classes: "jqcol-bold", align: "right",
                        formatoptions: {
                            decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'
                        }
                }],
                caption: "",
                cellEdit: true,
                cellsubmit: "clientArray",
                afterEditCell: function (id, name, val, iRow, iCol) {
                    $("#" + this.id + " tbody>tr:eq(" + iRow + ")>td:eq(" + iCol + ") input, select, textarea").select();
                },
                afterSaveCell: function (id, name, val, iRow, iCol) {
                    if (name === 'value') {
                        var v_cashback = jQuery("#salesTable").jqGrid('getCell', id, iCol + 1);
                        var v_chequecount = jQuery("#salesTable").jqGrid('getCell', id, iCol - 1);
                        jQuery("#salesTable").jqGrid('setRowData', id, {daytotal: parseFloat(val) - parseFloat(v_cashback)});
                        if (v_chequecount !== 0) {
                            jQuery("#salesTable").jqGrid('setRowData', id, {midPrice: (parseFloat(val) - parseFloat(v_cashback)) / v_chequecount});
                        }
                    }
                    if (name === 'cashback') {
                        var v_value = jQuery("#salesTable").jqGrid('getCell', id, iCol - 1);
                        var v_chequecount = jQuery("#salesTable").jqGrid('getCell', id, iCol - 2);
                        jQuery("#salesTable").jqGrid('setRowData', id, {daytotal: parseFloat(v_value) - parseFloat(val)});
                        if (v_chequecount !== 0) {
                            jQuery("#salesTable").jqGrid('setRowData', id, {midPrice: (parseFloat(v_value) - parseFloat(val)) / v_chequecount});
                        }
                    }
                    if (name === 'chequeCount') {
                        var v_value = jQuery("#salesTable").jqGrid('getCell', id, iCol + 1);
                        var v_cashback = jQuery("#salesTable").jqGrid('getCell', id, iCol + 2);
                        jQuery("#salesTable").jqGrid('setRowData', id, {daytotal: parseFloat(v_value) - parseFloat(v_cashback)});
                        if (v_chequecount !== 0) {
                            jQuery("#salesTable").jqGrid('setRowData', id, {midPrice: (parseFloat(v_value) - parseFloat(v_cashback)) / val});
                        }
                    }
                }

            });
            
            for (var i = 0; i <= mydata.length; i++)
                jQuery("#salesTable").jqGrid('addRowData', i + 1, mydata[i]);
                
            var sendData = function (data) {
                var dataToSend = JSON.stringify(data);
                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                                //alert("DEBUG:\n" + dataToSend);
                $.ajax({
                    type: "POST",
                    url: "<c:url value='/salesUpd${salesMeta.id == null ? "?shopid=" : "?smid="}${salesMeta.id == null ? salesMeta.shop.id : salesMeta.id}' />",
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
            
            $("#saveTable").click(function () {
                $("#salesTable").jqGrid("editCell", 0, 0, false);
            
                var localGridData = jQuery("#salesTable").jqGrid('getGridParam', 'data');
                    sendData(localGridData);
            });
                
            $("#msgSuccess").dialog({
                autoOpen: false,
                modal: true,
                dialogClass: "no-close",
                buttons: [{
                    text: "OK",
                    class: "btn btn-default",
                    click: function () {
                        location.reload();
                    }
                }],
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
                buttons: [{
                    text: "OK",
                    class: "btn btn-default",
                    click: function () {
                        $(this).dialog("close");
                    }
                }],
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