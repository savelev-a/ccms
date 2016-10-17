<%-- 
    Document   : office
    Created on : 15.04.2016, 15:11:52
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<ccms:page title="Офис - ${office.name}">
    <ccms:layout mainMenuActiveItem="directories" sideMenuActiveItem="none" sideMenuSection="none">
        <ccms:panel cols="10" title="Офис - ${office.name}">
            <table class="table table-hover table-condensed">
                <tbody>
                    <tr>
                        <th class="th-header-center" colspan="4">Общие данные</th>
                    </tr>
                    <tr>
                        <th>Наименование</th>
                        <td colspan="4"><c:out value="${office.name}" /></td>
                    </tr>
                    <tr>
                        <th>Юр. лицо</th>
                        <td><ccms:hyperlink target="${office.organisation}" /></td>

                        <th>E-mail</th>
                        <td>
                            <span class="glyphicon glyphicon-envelope"></span>
                            <a href="mailto:<c:out value="${office.email}" />">
                                <c:out value="${office.email}" />
                            </a>
                        </td>
                    </tr>

                    <tr>
                        <th>Адрес</th>
                        <td><c:out value="${office.address}" /></td>

                        <th>Телефон</th>
                        <td>
                            <span class="glyphicon glyphicon-phone-alt"></span>
                            <c:out value="${office.phone}" />
                        </td>
                    </tr>
                    <tr>
                        <th class="th-header-center" colspan="4">Персонал</th>
                    </tr>
                    <tr>
                        <th>Администратор</th>
                        <td>
                            <ccms:hyperlink target="${office.director}" />
                        </td>

                        <th>Сотрудники</th>
                        <td>
                            <c:forEach items="${office.officeEmployees}" var="employee">
                                <ccms:hyperlink target="${employee}" />
                                &nbsp;
                                <c:if test="${employee.position != ''}"> (<c:out value="${employee.position}" />) </c:if>
                                    <br>
                            </c:forEach>
                        </td>
                    </tr>
                </tbody>
            </table>
            <br>

            <span class="glyphicon glyphicon-cog"></span>
            <a href="<c:url value="/admin/officeprofile?id=${office.id}&userpage=true" />" > Редактировать офис </a>
            &nbsp;
            <span class="glyphicon glyphicon-print"></span>
            <a href="<c:url value='/office?id=${office.id}&mode=print' /> " target="_blank">Распечатать</a>
        </ccms:panel>
    </ccms:layout>
</ccms:page>