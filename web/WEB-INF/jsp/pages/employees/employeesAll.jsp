<%-- 
    Document   : employees
    Created on : 08.04.2016, 13:43:48
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<ccms:page title="Сотрудники">
    <ccms:layout mainMenuActiveItem="directories" sideMenuSection="none" sideMenuActiveItem="none">
        <ccms:panel cols="10" title="Сотрудники">
            <table class="table table-hover table-condensed dataTablesPreparedBig">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>ФИО</th>
                        <th>E-mail</th>
                        <th>Телефон</th>
                        <th>Должность</th>

                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${allemps}" var="employee" varStatus="idx">
                        <tr>
                            <td>${idx.count}</td>
                            <td><ccms:hyperlink target="${employee}" /></td>
                            <td>
                                <span class="glyphicon glyphicon-envelope"></span>
                                <a href="mailto:<c:out value="${employee.email}" />">
                                    <c:out value="${employee.email}" />
                                </a>
                            </td>
                            <td><span class="glyphicon glyphicon-phone-alt"></span> <c:out value="${employee.phone}" /></td>
                            <td><c:out value="${employee.position}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <br>

            <span class="glyphicon glyphicon-cog"></span>
            <a href="<c:url value="/admin/employees" />" > Управление сотрудниками </a>
            &nbsp;
            <span class="glyphicon glyphicon-print"></span>
            <a href="<c:url value='/employees?mode=print' /> " target="_blank">Распечатать</a>

        </ccms:panel>
    </ccms:layout>
</ccms:page>