<%-- 
    Document   : login
    Created on : 11.04.2016, 14:31:00
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
        <div class="container-fluid">
            <%@include file="modules/header.jspf" %>

            <br>

            <c:if test="${param.error}">
                <div id="login-box" class="alert alert-danger" align="center">Неверный пароль!</div>
            </c:if>

            <form name="loginForm" class="form-horizontal" action="<c:url value="/login" />" method="POST">

                <div id="login-box" class="panel panel-primary">
                    <div class="panel-heading" align="center"><b>Авторизация</b></div>
                    <table class="table-login">
                        <thead>
                            <tr>
                                <td colspan="2">

                                </td>
                            </tr>
                        </thead>
                        <tr>
                            <td>Имя пользователя: </td>
                            <td>
                                <select name="username" class="form-control" >
                                    <c:forEach items="${activeUsers}" var="user" >
                                        <option value="${user.username}">
                                            <c:out value="${user.fullName}" />
                                        </option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>

                        <tr class="tr-no-hover">
                            <td>Пароль: </td>
                            <td><input type="password" name="password" class="form-control" /></td>
                        </tr>

                        <tr>
                            <td> </td>
                            <td><input type="checkbox" name="remember-me">&nbsp; Запомнить меня</td>
                        </tr>
                        <tr>
                            <td colspan="2" class="th-header-center">
                                <input name="submit" type="submit" value="Войти" class="btn btn-default" />
                            </td>
                        </tr>
                    </table>

                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                </div>
            </form>

            <%@include file="modules/footer.jspf" %>

    </body>
</html>