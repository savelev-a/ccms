<%-- 
    Document   : login
    Created on : 11.04.2016, 14:31:00
    Author     : Alexander Savelev
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<c:url value="/res/css/framework.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/styles.css" />" >
        <title>${currentPage.title} - ИнфоПортал</title>
    </head>

    <body>
        <div class="g">
            <%@include file="modules/header.jspf" %>

            <br>

            <div id="login-box" class="f-input">
                <form name="loginForm" action="<c:url value="/login" />" method="POST">
                    <table>
                        <thead>
                            <tr class="tr-no-hover">
                                <th colspan="2"><h3  class="th-header-center">Авторизация</h3></th>
                            </tr>
                            <tr class="tr-no-hover">
                                <td colspan="2">
                                    <c:if test="${param.error}">
                                        <div class="f-message f-message-error">Неверный пароль!</div>
                                    </c:if>
                                </td>
                            </tr>
                        </thead>
                        <tr class="tr-no-hover">
                            <td>Имя пользователя: </td>
                            <td>
                                <select name="username" >
                                    <c:forEach items="${activeUsers}" var="user" >
                                        <option value="${user.username}">${user.lastName} ${user.firstName}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>

                        <tr class="tr-no-hover">
                            <td>Пароль: </td>
                            <td><input type="password" name="password" /></td>
                        </tr>

                        <tr class="tr-no-hover">
                            <td> </td>
                            <td><input type="checkbox" name="remember-me">&nbsp; Запомнить меня</td>
                        </tr>
                        <tr class="tr-no-hover">
                            <td colspan="2" class="th-header-center">
                                <input name="submit" type="submit" value="Войти" class="f-bu f-bu-default" />
                            </td>
                        </tr>
                    </table>

                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

                </form>

                <%@include file="modules/footer.jspf" %>
            </div>
    </body>
</html>