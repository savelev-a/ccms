<%-- 
    Document   : organisations
    Created on : 30.03.2016, 20:37:09
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<ccms:page title="Администрирование - Организации">
    <ccms:layout mainMenuActiveItem="admin" sideMenuSection="admin" sideMenuActiveItem="orgs">
        <ccms:panel cols="10" title="Организации">
            <table class="table table-hover table-condensed dataTablesPreparedBig">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Наименование</th>
                        <th>ИНН</th>
                        <th>Юр. адрес</th>
                        <th>Телефон</th>
                        <th>Директор</th>
                        <th>Магазины</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${allorgs}" var="organisation" varStatus="idx">
                        <tr>
                            <td><c:out value="${organisation.id}" /></td>
                            <td><ccms:hyperlink target="${organisation}" type="admin" /></td>
                            <td><c:out value="${organisation.inn}" /></td>
                            <td><c:out value="${organisation.urAddress}" /></td>
                            <td><c:out value="${organisation.phone}" /></td>
                            <td><c:out value="${organisation.director.fullName}" /></td>
                            <td><c:out value="${organisation.shops.size()}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>

            </table>
            <br>
            <span class="glyphicon glyphicon-plus"></span>
            <a href="<c:url value="/admin/addorganisation" />">Добавить организацию...</a>
        </ccms:panel>
    </ccms:layout>
</ccms:page>