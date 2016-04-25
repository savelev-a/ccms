<%-- 
    Document   : employee
    Created on : 15.04.2016, 14:51:38
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
                <%@include file="../../modules/header.jspf" %>

                <br>

                <div class="row">

                    <%@include file="../../modules/sideMenu/sideMenu_dummy.jspf" %>

                    <br><br>

                    <div class="col-md-6">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Профиль сотрудника <c:out value="${employee.fullName}" /></div>
                            <div class="panel-body">
                                <table class="table table-hover table-condensed">

                                    <tbody>
                                        <tr>
                                            <th>Фамилия</th>
                                            <td><c:out value="${employee.lastName}" /></td>
                                        </tr>
                                        <tr>
                                            <th>Имя</th>
                                            <td><c:out value="${employee.firstName}" /></td>
                                        </tr>
                                        <tr>
                                            <th>Отчество</th>
                                            <td><c:out value="${employee.middleName}" /></td>
                                        </tr>
                                        <tr>
                                            <th>E-mail</th>
                                            <td>
                                                <a herf="mailto:<c:out value="${employee.email}" />">
                                                    <c:out value="${employee.email}" />
                                                </a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>Телефон</th>
                                            <td>
                                                <span class="glyphicon glyphicon-phone-alt"></span>
                                                <c:out value="${employee.phone}" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>Должность</th>
                                            <td><c:out value="${employee.position}" /></td>
                                        </tr>
                                    </tbody>
                                </table>
                                <br>

                                <span class="glyphicon glyphicon-cog"></span>
                                <a href="<c:url value="/admin/profile?id=${employee.id}" />" > Редактировать сотрудника </a>
                                &nbsp;
                                <span class="glyphicon glyphicon-print"></span>
                                <a href="#">Распечатать</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <%@include file="../../modules/footer.jspf" %>
        </div>
    </body>
</html>

