<%-- 
    Document   : editExpence
    Created on : 02.08.2016, 11:26:08
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
                            <div class="panel-heading panel-heading-dark" align="center">Редактирование расхода</div>
                            <div class="panel-body">
                                <form:form method="post" commandName="expence">
                                    <table id="recurrentTable" class="table table-condensed">
                                        <tbody>
                                            <tr>
                                                <td><b>Тип расхода</b></td>
                                                <td>
                                                    <c:out value="${expence.type.description}" />
                                                </td>
                                                <td><form:errors path="type" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>Периодический расход</b></td>
                                                <td>
                                                    ${expence.isRecurrent() ? "Да" : "Нет"}
                                                </td>
                                                <td><form:errors path="recurrent" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>Начальная дата</b></td>
                                                <fmt:formatDate value="${expence.date.toDate()}" type="date" pattern="dd.MM.yyyy" var="expDate" />
                                                <td><form:input path="date" class="form-control" id="recurrentDate" value="${expDate}" /></td>
                                                <td><form:errors path="date" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>Ежемесячная сумма</b></td>
                                                <td><form:input path="value" class="form-control" /></td>
                                                <td><form:errors path="value" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                        </tbody>

                                    </table>
                                    <form:hidden path="recurrent" />
                                    <form:hidden path="shop" value="${expence.shop.id}"/>
                                    <form:hidden path="type" value="${expence.type.id}"/>
                                    <input id="saveRecurrent" type="submit" value="Сохранить" class="btn btn-primary">
                                </form:form>
                            </div>
                        </div>

                    </div>

                </div>
            </div>
            <%@include file="../modules/footer.jspf" %>

        </div>


        <script type="text/javascript" src="<c:url value="/res/js/datepicker-ru.js" />"></script>
        <script type="text/javascript">
            $("#recurrentDate").datepicker();
            $("#oneshotDate").datepicker();

        </script>

    </body>
</html>
