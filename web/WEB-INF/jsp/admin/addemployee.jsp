<%-- 
    Document   : addemployee
    Created on : 29.03.2016, 22:47:50
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
        <link rel="stylesheet" href="<c:url value="/res/css/framework.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/styles.css" />" >
        <title>${title} - ИнфоПортал</title>
    </head>

    <body>
        <div class="wrapper">
            <div class="g content">
                <%@include file="../modules/header.jspf" %>

                <br>

                <div class="g-row">

                    <%@include file="../modules/sideMenu/sideMenu_admin.jspf" %>

                    <div class="g-10">
                        <form:form method="post" commandName="addEmployeeFrm">
                            <table>
                                <caption>Добавление нового сотрудника</caption>
                                <tbody>
                                    <tr>
                                        <th>Фамилия</th>
                                        <td><form:input path="lastName" /></td>
                                        <td><form:errors path="lastName" cssStyle="color: #ff0000;" /></td>

                                        <th>Логин</th>
                                        <td><form:input path="username" /></td>
                                        <td><form:errors path="username" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th>Имя</th>
                                        <td><form:input path="firstName" /></td>
                                        <td><form:errors path="firstName" cssStyle="color: #ff0000;" /></td>

                                        <th>Пароль</th>
                                        <td><form:password path="password" showPassword="true" /></td>
                                        <td><form:errors path="password" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th>Отчество</th>
                                        <td><form:input path="middleName" /></td>
                                        <td><form:errors path="middleName" cssStyle="color: #ff0000;" /></td>

                                        <th rowspan="3">Роли</th>
                                        <td rowspan="3"><form:checkboxes items="${rolesList}" path="roles" delimiter="<br>" /></td>
                                        <td rowspan="3"><form:errors path="roles" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th>E-mail</th>
                                        <td><form:input path="email" /></td>
                                        <td><form:errors path="email" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th>Телефон</th>
                                        <td><form:input path="phone" /></td>
                                        <td><form:errors path="phone" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th>Должность</th>
                                        <td><form:input path="position" /></td>
                                        <td><form:errors path="position" cssStyle="color: #ff0000;" /></td>
                                        
                                        <th>Вход разрешен</th>
                                        <td><form:checkbox path="active" checked="true" /></td>
                                        <td></td>
                                    </tr>

                                </tbody>
                            </table>
                            <input type="submit" value="Добавить" class="f-bu f-bu-default">

                            <br><br><hr>
                            <b>Примечание:</b> для добавления пользователя необходимо указать Фамилию, Имя, Email, логин и пароль. <br>
                            По умолчанию любой сотрудник имеет доступ к веб-интерфейсу, чтобы запретить это снимите флажок "Вход разрешен". <br>
                            Указанный E-mail будет использоваться для рассылки уведомлений.
                        </form:form>
                    </div>
                </div>
            </div>
            <%@include file="../modules/footer.jspf" %>

        </div>
    </body>
</html>