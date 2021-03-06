<%-- 
    Document   : newaction
    Created on : 15.10.2016, 10:02:25
    Author     : alchemist
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<ccms:page title="Новая акция">
    <ccms:layout mainMenuActiveItem="management" sideMenuSection="actionEvents" sideMenuActiveItem="newaction">
        <ccms:panel cols="10" title="Создание новой акции">
            
            <form:form method="post" commandName="newActionFrm">
                <table class="table table-condensed">
                    <tbody>
                        <tr>
                            <td><b>Краткий заголовок</b></td>
                            <td colspan="4"><form:input path="title" class="form-control" /></td>
                            <td><form:errors path="title" cssStyle="color: #ff0000;" /></td>
                        </tr>

                        <tr>
                            <td><b>Подробное описание</b></td>
                            <td colspan="4"><form:textarea path="description" class="form-control" rows="10" /></td>
                            <td><form:errors path="description" cssStyle="color: #ff0000;" /></td>
                        </tr>

                        <tr>
                            <td><b>Инициатор</b></td>
                            <td colspan="4"><c:out value="${newActionFrm.creator.fullName}" /></td>
                            <form:hidden path="creator.id" />
                            <td><form:errors path="creator" cssStyle="color: #ff0000;" /></td>
                        </tr>

                        <fmt:formatDate value="${newActionFrm.startDate.toDate()}" type="date" pattern="dd.MM.yyyy" var="startdatefmt" />
                        <fmt:formatDate value="${newActionFrm.endDate.toDate()}" type="date" pattern="dd.MM.yyyy" var="enddatefmt" />

                        <tr>
                            <td><b>Дата начала акции</b></td>
                            <td><form:input path="startDate" class="form-control" id="startDate" value="${startdatefmt}" /></td>
                            <td><form:errors path="startDate" cssStyle="color: #ff0000;" /></td>

                            <td><b>Дата окончания акции</b></td>
                            <td><form:input path="endDate" class="form-control" id="endDate" value="${enddatefmt}" /></td>
                            <td><form:errors path="endDate" cssStyle="color: #ff0000;" /></td>
                        </tr>

                        <tr>
                            <td><b>Выбор магазинов</b></td>
                            <td colspan="4">
                                <table id="shopsChooseTab">
                                    <thead>
                                        <tr>
                                            <th><input type="checkbox" id="selectAll"></th>
                                            <th>Наименование</th>
                                            <th>Адрес</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${allShops}" var="shop" varStatus="idx">
                                            <tr>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${newActionFrm.affectedShops.contains(shop)}">
                                                            <form:checkbox path="affectedShops" value="${shop.id}" checked="true" />
                                                        </c:when>
                                                        <c:otherwise>
                                                            <form:checkbox path="affectedShops" value="${shop.id}" />
                                                        </c:otherwise>
                                                    </c:choose>

                                                </td>
                                                <td><c:out value="${shop.name}" /></td>
                                                <td><c:out value="${shop.address}" /></td>
                                            </tr>
                                       </c:forEach>
                                    </tbody>
                                </table>
                            </td>
                            <td><form:errors path="affectedShops" cssStyle="color: #ff0000;" /></td>
                        </tr>

                    </tbody>
                </table>
                <input id="save" type="submit" name="save" value="Сохранить" class="btn btn-primary">
            </form:form>
        </ccms:panel>
    </ccms:layout>
        
    <script type="text/javascript" src="<c:url value="/res/js/datepicker-ru.js" />"></script>
        
    <script type="text/javascript">
        $("#startDate").datepicker();
        $("#endDate").datepicker();

        $("#shopsChooseTab").DataTable({
            "scrollY": "200px",
            "scrollCollapse": true,
            "paging": false
        });

        $("#selectAll").change(function(){
            var checkboxes = $(this).closest('form').find(':checkbox');
            checkboxes.prop('checked', $(this).prop('checked'));
        });
    </script>
</ccms:page>