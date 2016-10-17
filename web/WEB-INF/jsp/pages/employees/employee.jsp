<%-- 
    Document   : employee
    Created on : 15.04.2016, 14:51:38
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<ccms:page title="Профиль - ${employee.fullName}">
    <ccms:layout mainMenuActiveItem="directories" sideMenuSection="none" sideMenuActiveItem="none">
        <ccms:panel cols="6" title="Сотрудник - ${employee.fullName}">
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
            <a href="<c:url value="/admin/profile?id=${employee.id}&userpage=true" />" > Редактировать сотрудника </a>
            &nbsp;
            <span class="glyphicon glyphicon-print"></span>
            <a href="<c:url value='/employee?id=${employee.id}&mode=print' /> " target="_blank">Распечатать</a>
        </ccms:panel>
    </ccms:layout>
</ccms:page>
