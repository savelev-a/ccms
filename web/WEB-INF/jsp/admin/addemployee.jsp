<%-- 
    Document   : addemployee
    Created on : 29.03.2016, 22:47:50
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<ccms:page title="Добавление пользователя">
    <ccms:layout mainMenuActiveItem="admin" sideMenuActiveItem="employees" sideMenuSection="admin">
        <ccms:panel cols="10" title="Добавление пользователя">
            <form:form method="post" commandName="addEmployeeFrm">
                <table class="table table-condensed">
                    <tbody>
                        <tr>
                            <td><b>Фамилия</b></td>
                            <td><form:input path="lastName" class="form-control" /></td>
                            <td><form:errors path="lastName" cssStyle="color: #ff0000;" /></td>

                            <td><b>Логин</b></td>
                            <td><form:input path="username" class="form-control" /></td>
                            <td><form:errors path="username" cssStyle="color: #ff0000;" /></td>
                        </tr>
                        <tr>
                            <td><b>Имя</b></td>
                            <td><form:input path="firstName" class="form-control" /></td>
                            <td><form:errors path="firstName" cssStyle="color: #ff0000;" /></td>

                            <td><b>Пароль</b></td>
                            <td><form:password path="password" showPassword="true" class="form-control" /></td>
                            <td><form:errors path="password" cssStyle="color: #ff0000;" /></td>
                        </tr>
                        <tr>
                            <td><b>Отчество</b></td>
                            <td><form:input path="middleName" class="form-control" /></td>
                            <td><form:errors path="middleName" cssStyle="color: #ff0000;" /></td>

                            <td rowspan="3"><b>Роли</b></td>
                            <td rowspan="3"><form:checkboxes items="${rolesList}" path="roles" delimiter="<br>" /></td>
                            <td rowspan="3"><form:errors path="roles" cssStyle="color: #ff0000;" /></td>
                        </tr>
                        <tr>
                            <td><b>E-mail</b></td>
                            <td><form:input path="email" class="form-control" /></td>
                            <td><form:errors path="email" cssStyle="color: #ff0000;" /></td>
                        </tr>
                        <tr>
                            <td><b>Телефон</b></td>
                            <td><form:input path="phone" class="form-control" /></td>
                            <td><form:errors path="phone" cssStyle="color: #ff0000;" /></td>
                        </tr>
                        <tr>
                            <td><b>Должность</b></td>
                            <td><form:input path="position" class="form-control" /></td>
                            <td><form:errors path="position" cssStyle="color: #ff0000;" /></td>

                            <td><b>Вход разрешен</b></td>
                            <td><form:checkbox path="active" checked="true" /></td>
                            <td></td>
                        </tr>

                    </tbody>
                </table>
                <input type="submit" value="Добавить" class="btn btn-primary">

                <br><br><hr>
                <b>Примечание:</b> для добавления пользователя необходимо указать Фамилию, Имя, Email, логин и пароль. <br>
                По умолчанию любой сотрудник имеет доступ к веб-интерфейсу, чтобы запретить это снимите флажок "Вход разрешен". <br>
                Указанный E-mail будет использоваться для рассылки уведомлений.
            </form:form>
        </ccms:panel>
    </ccms:layout>
</ccms:page>