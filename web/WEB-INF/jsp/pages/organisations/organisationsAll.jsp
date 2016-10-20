<%-- 
    Document   : organisationsAll
    Created on : 12.04.2016, 14:29:05
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<ccms:page title="Юр. лица">
    <ccms:layout mainMenuActiveItem="directories" sideMenuSection="none" sideMenuActiveItem="none">
        <ccms:panel cols="10" title="Юридические лица">
            <table class="table table-hover dataTablesPreparedBig">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Название</th>
                        <th>ИНН</th>
                        <th>Юр. адрес</th>
                        <th>Телефон</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${allorgs}" var="org" varStatus="idx">
                        <tr>
                            <td>${idx.count}</td>
                            <td><ccms:hyperlink target="${org}" /></td>
                            <td><c:out value="${org.inn}" /></td>
                            <td><c:out value="${org.urAddress}" /></td>
                            <td><span class="glyphicon glyphicon-phone-alt"></span> 
                                <c:out value="${org.phone}" />
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <br>

            <span class="glyphicon glyphicon-cog"></span>
            <a href="<c:url value="/admin/organisations" />" > Управление организациями </a>
            &nbsp;
            <span class="glyphicon glyphicon-print"></span>
            <a href="<c:url value='/organisations?mode=print' /> " target="_blank">Распечатать</a>
        </ccms:panel>
    </ccms:layout>
</ccms:page>