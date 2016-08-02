<%-- 
    Document   : setExpences
    Created on : 29.07.2016, 11:22:36
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <link rel="stylesheet" href="<c:url value="/res/css/jquery.dataTables.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/styles.css" />" >
        <title><c:out value="${title}" /></title>
    </head>

    <body>
        <div class="wrapper">
            <div class="container-fluid content">
                <%@include file="../modules/header.jspf" %>

                <br>

                <div class="row">

                    <%@include file="../modules/sideMenu/sideMenu_shop.jspf" %>

                    <br><br>

                    <div class="col-md-5">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Периодические расходы</div>
                            <div class="panel-body">
                                <table id="recurrentTable" class="table table-hover table-condensed">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Тип расхода</th>
                                            <th>Начальная дата</th>
                                            <th>Ежемесячная сумма</th>
                                            
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${recurrentExpences}" var="expence" varStatus="idx">
                                            <tr>
                                                <td><c:out value="${idx.count}" /></td>
                                                <td>
                                                    <a href="<c:url value="/actions/editexpence?id=${expence.id}" />" >
                                                        <c:out value="${expence.type.description}" />
                                                    </a>
                                                </td>
                                                <td>
                                                    <fmt:formatDate value="${expence.date.toDate()}" type="date" pattern="dd.MM.yyyy" var="recurrentDate" />
                                                    <c:out value="${recurrentDate}" />
                                                </td>
                                                <td>
                                                    <c:out value="${expence.value}" /> 
                                                    <a href="#" class="delete" id="del${expence.id}">
                                                        <span class="glyphicon glyphicon-trash badge-right"></span>
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Новый периодический расход</div>
                            <div class="panel-body">
                                <form:form method="post" commandName="recurrentExpFrm" action="setrecurrentexpences">
                                    <table class="table table-condensed">
                                        <tbody>
                                            <tr>
                                                <td><b>Тип расхода</b></td>
                                                <td>
                                                    <form:select path="type" class="form-control">
                                                        <form:options items="${recurrentExpTypes}" itemLabel="description" itemValue="id" />
                                                    </form:select>
                                                </td>
                                                <td><form:errors path="type" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>Начальная дата</b></td>
                                                <td><form:input path="date" class="form-control" id="recurrentDate" /></td>
                                                <td><form:errors path="date" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>Ежемесячная сумма</b></td>
                                                <td><form:input path="value" class="form-control" /></td>
                                                <td><form:errors path="value" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <form:hidden path="recurrent" value="true" />
                                    <form:hidden path="shop" value="${recurrentExpFrm.shop.id}"/>
                                    <input id="createRecurrent" type="submit" value="Создать" class="btn btn-primary">
                                </form:form>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-5">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Разовые расходы</div>
                            <div class="panel-body">
                                <table id="oneshotTable" class="table table-hover table-condensed">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Тип расхода</th>
                                            <th>Дата расхода</th>
                                            <th>Сумма</th>
                                            
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${oneshotExpences}" var="expence" varStatus="idx">
                                            <tr>
                                                <td><c:out value="${idx.count}" /></td>
                                                <td>
                                                    <a href="<c:url value="/actions/editexpence?id=${expence.id}" />" >
                                                        <c:out value="${expence.type.description}" />
                                                    </a>
                                                </td>
                                                <td>
                                                    <fmt:formatDate value="${expence.date.toDate()}" type="date" pattern="dd.MM.yyyy" var="oneshotDate" />
                                                    <c:out value="${oneshotDate}" />
                                                </td>
                                                <td>
                                                    <c:out value="${expence.value}" />
                                                    <a href="#" class="delete" id="del${expence.id}">
                                                        <span class="glyphicon glyphicon-trash badge-right"></span>
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Новый разовый расход</div>
                            <div class="panel-body">
                                <form:form method="post" commandName="oneshotExpFrm" action="setoneshotexpences">
                                    <table class="table table-condensed">
                                        <tbody>
                                            <tr>
                                                <td><b>Тип расхода</b></td>
                                                <td>
                                                    <form:select path="type" class="form-control">
                                                        <form:options items="${oneshotExpTypes}" itemLabel="description" itemValue="id" />
                                                    </form:select>
                                                </td>
                                                <td><form:errors path="type" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>Дата расхода</b></td>
                                                <td><form:input path="date" class="form-control" id="oneshotDate"/></td>
                                                <td><form:errors path="date" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>Сумма</b></td>
                                                <td><form:input path="value" class="form-control" /></td>
                                                <td><form:errors path="value" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <form:hidden path="recurrent" value="false" />
                                    <form:hidden path="shop" value="${oneshotExpFrm.shop.id}"/>
                                    <input id="createOneshot" type="submit" value="Создать" class="btn btn-primary">
                                </form:form>
                            </div>
                        </div>
                        
                    </div>
                    
                </div>
            </div>
            <%@include file="../modules/footer.jspf" %>

        </div>
            
            <div id="msgDelConfirm" title="Подтверждение удаления">
                <p>
                    Удалить данную запись?
                </p>
            </div>
            
        <script type="text/javascript" src="<c:url value="/res/js/datepicker-ru.js" />"></script>
        <script type="text/javascript">
            $("#recurrentDate").datepicker();
            $("#oneshotDate").datepicker();
            
            $(document).ready(function () {
                $("#recurrentTable").DataTable();
                $("#oneshotTable").DataTable();
            });
            
            $(".delete").click(function () {
            $("#msgDelConfirm").data("idtodel", this.id);
            $("#msgDelConfirm").dialog("open"); 
        });
        
        $("#msgDelConfirm").dialog({
                autoOpen: false,
                modal: true,
                dialogClass: "no-close",
                width: 400,
                buttons: [{
                    text: "Удалить",
                    class: "btn btn-danger",
                    click: function () {
                        //$(this).dialog("close");
                        $.post("<c:url value="/actions/expences/delete" />", {"id": $.data(this, "idtodel").toString().substring(3), "${_csrf.parameterName}": "${_csrf.token}"});
                        location.reload();
                        
                    }
                }, {
                    text: "Отмена",
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

        </script>

    </body>
</html>

