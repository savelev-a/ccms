<%-- 
    Document   : officesAll
    Created on : 12.04.2016, 21:03:25
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<ccms:page title="Офисы">
    <ccms:layout mainMenuActiveItem="directories" sideMenuSection="none" sideMenuActiveItem="none" >
        <ccms:panel cols="10" title="Офисы">
            <table class="table table-hover table-condensed dataTablesPreparedBig">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Название</th>
                        <th>Юр. лицо</th>
                        <th>E-mail</th>
                        <th>Телефон</th>
                        <th>Адрес</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${alloffices}" var="office" varStatus="idx">
                        <tr>
                            <td>${idx.count}</td>
                            <td><ccms:hyperlink target="${office}" /></td>
                            <td><c:out value="${office.organisation.name}" /></td>
                            <td>
                                <span class="glyphicon glyphicon-envelope"></span> 
                                <a href="mailto:<c:out value="${office.email}" />">
                                    <c:out value="${office.email}" />
                                </a>
                            </td>
                            <td>
                                <span class="glyphicon glyphicon-phone-alt"></span> 
                                <c:out value="${office.phone}" />
                            </td>
                            <td><c:out value="${office.address}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <br>

            <span class="glyphicon glyphicon-cog"></span>
            <a href="<c:url value="/admin/offices" />" > Управление офисами </a>
            &nbsp;
            <span class="glyphicon glyphicon-print"></span>
            <a href="<c:url value='/offices?mode=print' />" target="_blank">Распечатать</a>
        </ccms:panel>
    </ccms:layout>
</ccms:page>