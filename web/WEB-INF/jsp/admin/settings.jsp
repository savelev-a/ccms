<%-- 
    Document   : settings
    Created on : 26.04.2016, 13:55:27
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
        <link rel="stylesheet" href="<c:url value="/res/css/styles.css" />" >
        <title><c:out value="${title}" /></title>
    </head>

    <body>
        <div class="wrapper">
            <div class="container-fluid content">
                <%@include file="../modules/header.jspf" %>

                <br>

                <div class="row">

                    <%@include file="../modules/sideMenu/sideMenu_admin.jspf" %>

                    <br><br>

                    <div class="col-md-6">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Настройки</div>
                            <div class="panel-body">
                                <form:form method="post" commandName="settingsForm">
                                    <table class="table table-condensed">
                                        <tbody>
                                            <tr>
                                                <th colspan="3" class="th-header-center">Компания</th>
                                            </tr>

                                            <tr>
                                                <td><b>Наименование компании</b></td>
                                                <td><form:input path="companyName" class="form-control" /> </td>
                                                <td><form:errors path="companyName" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr><td colspan="3"> </td></tr>
                                            <tr>
                                                <th colspan="3" class="th-header-center">Счетчики посетителей Кондор-7</th>
                                            </tr>

                                            <tr>
                                                <td><b>FTP - логин</b></td>
                                                <td><form:input path="countersKondorFtpLogin" class="form-control" /> </td>
                                                <td><form:errors path="countersKondorFtpLogin" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>FTP - пароль</b></td>
                                                <td><form:input path="countersKondorFtpPassword" class="form-control" /> </td>
                                                <td><form:errors path="countersKondorFtpPassword" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <th colspan="3" class="th-header-center">Автоматическая загрузка выручки (Email)</th>
                                            </tr>

                                            <tr>
                                                <td><b>Почтовый сервер</b></td>
                                                <td><form:input path="salesLoaderUrl" class="form-control" /> </td>
                                                <td><form:errors path="salesLoaderUrl" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>Имя пользователя</b></td>
                                                <td><form:input path="salesLoaderEmail" class="form-control" /> </td>
                                                <td><form:errors path="salesLoaderEmail" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>Пароль</b></td>
                                                <td><form:input path="salesLoaderEmailPass" class="form-control" /> </td>
                                                <td><form:errors path="salesLoaderEmailPass" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <input id="save" type="submit" value="Сохранить" class="btn btn-primary">
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%@include file="../modules/footer.jspf" %>
        </div>
    </body>
</html>