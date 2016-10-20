<%-- 
    Document   : employees
    Created on : 29.03.2016, 22:26:23
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<ccms:page title="Администрирование - сотрудники">
    <ccms:layout mainMenuActiveItem="admin" sideMenuSection="admin" sideMenuActiveItem="employees">
        <ccms:panel cols="10" title="Сотрудники">
            <table class="table table-hover table-condensed dataTablesPreparedBig">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Фамилия</th>
                        <th>Имя</th>
                        <th>Логин</th>
                        <th>E-mail</th>
                        <th>Телефон</th>
                        <th>Должность</th>
                        <th>Вход разрешен</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${allemps}" var="employee" varStatus="idx">
                        <tr>
                            <td><c:out value="${employee.id}" /></td>
                            <td><ccms:hyperlink target="${employee}" type="admin"/></td>
                            <td><c:out value="${employee.firstName}" /></td>
                            <td><c:out value="${employee.username}" /></td>
                            <td><c:out value="${employee.email}" /></td>
                            <td><c:out value="${employee.phone}" /></td>
                            <td><c:out value="${employee.position}" /></td>
                            <td>${employee.active ? "Да" : "Нет"}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <br>
            <span class="glyphicon glyphicon-plus"></span>
            <a href="<c:url value="/admin/addemployee" />">Добавить сотрудника...</a>
        </ccms:panel>
    </ccms:layout>
</ccms:page>