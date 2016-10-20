<%-- 
    Document   : offices
    Created on : 08.04.2016, 12:45:28
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<ccms:page title="Администрирование - Офисы">
    <ccms:layout mainMenuActiveItem="admin" sideMenuSection="admin" sideMenuActiveItem="offices">
        <ccms:panel cols="10" title="Офисы">
            <table class="table table-hover table-condensed dataTablesPreparedBig">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Название</th>
                        <th>Юр. лицо</th>
                        <th>E-mail</th>
                        <th>Телефон</th>
                        <th>Адрес</th>
                        <th>Администратор</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${alloffices}" var="office" varStatus="idx">
                        <tr>
                            <td><c:out value="${office.id}" /></td>
                            <td><ccms:hyperlink target="${office}" type="admin" /></td>
                            <td><c:out value="${office.organisation.name}" /></td>
                            <td><c:out value="${office.email}" /></td>
                            <td><c:out value="${office.phone}" /></td>
                            <td><c:out value="${office.address}" /></td>
                            <td><c:out value="${office.director.fullName}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <br>
            <span class="glyphicon glyphicon-plus"></span>
            <a href="<c:url value="/admin/addoffice" />">Добавить офис...</a>
        </ccms:panel>
    </ccms:layout>
</ccms:page>