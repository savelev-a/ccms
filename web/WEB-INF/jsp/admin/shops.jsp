<%-- 
    Document   : shops
    Created on : 31.03.2016, 20:17:23
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<ccms:page title="Администрирование - магазины">
    <ccms:layout mainMenuActiveItem="admin" sideMenuSection="admin" sideMenuActiveItem="shops">
        <ccms:panel cols="10" title="Магазины">
            <table class="table table-hover table-condensed dataTablesPreparedBig">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Наименование</th>
                        <th>Юр. лицо</th>
                        <th>E-mail</th>
                        <th>Телефон</th>
                        <th>Адрес</th>
                        <th>ICQ</th>
                        <th>Администратор</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${allshops}" var="shop" varStatus="idx">
                        <tr>
                            <td><c:out value="${shop.id}" /></td>
                            <td><ccms:hyperlink target="${shop}" type="admin" /></td>
                            <td><c:out value="${shop.organisation.name}" /></td>
                            <td><c:out value="${shop.email}" /></td>
                            <td><c:out value="${shop.phone}" /></td>
                            <td><c:out value="${shop.address}" /></td>
                            <td><c:out value="${shop.icq}" /></td>
                            <td><c:out value="${shop.shopAdmin.fullName}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>

            </table>
            <br>
            <span class="glyphicon glyphicon-plus"></span>
            <a href="<c:url value="/admin/addshop" />">Добавить магазин...</a>
        </ccms:panel>
    </ccms:layout>
</ccms:page>