<%-- 
    Document   : newtask
    Created on : 29.06.2016, 12:41:48
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<ccms:page title="Новая задача">
    <ccms:layout mainMenuActiveItem="tasks" sideMenuSection="tasks" sideMenuActiveItem="newtask">
        <ccms:panel cols="10" title="Добавление новой задачи">
            <form:form method="post" commandName="newTaskFrm">
                <table class="table table-condensed">
                    <tbody>
                        <tr>
                            <td><b>Краткий заголовок</b></td>
                            <td colspan="4"><form:input path="title" class="form-control" /></td>
                            <td><form:errors path="title" cssStyle="color: #ff0000;" /></td>
                        </tr>

                        <tr>
                            <td><b>Подробное описание</b></td>
                            <td colspan="4"><form:textarea path="text" class="form-control" rows="10" /></td>
                            <td><form:errors path="text" cssStyle="color: #ff0000;" /></td>
                        </tr>

                        <tr>
                            <td><b>Инициатор</b></td>
                            <td colspan="4"><c:out value="${newTaskFrm.creator.fullName}" /></td>
                            <form:hidden path="creator.id" />
                            <td><form:errors path="creator" cssStyle="color: #ff0000;" /></td>
                        </tr>

                        <fmt:formatDate value="${newTaskFrm.deadline.toDate()}" type="both" pattern="dd.MM.yyyy HH:mm" var="deadlinefmt" />

                        <tr>
                            <td><b>Выпонить до</b></td>
                            <td><form:input path="deadline" class="form-control" id="deadline" value="${deadlinefmt}" /></td>
                            <td><form:errors path="deadline" cssStyle="color: #ff0000;" /></td>

                            <td><b>Приоритет</b></td>
                            <td>
                                <form:select path="urgency" class="form-control">
                                    <form:option value="LOW">Низкий</form:option>
                                    <form:option value="MEDIUM">Средний</form:option>
                                    <form:option value="HIGH">Высокий</form:option>
                                    <form:option value="CRITICAL">Чрезвычайный</form:option>
                                </form:select>
                            </td>
                            <td><form:errors path="urgency" cssStyle="color: #ff0000;" /></td>
                        </tr>

                        <tr>
                            <td><b>Назначить сотрудникам</b></td>
                            <td colspan="4">
                                <table id="empsChooseTab">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>ФИО</th>
                                            <th>Должность</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${allEmps}" var="emp" varStatus="idx">
                                            <tr>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${newTaskFrm.performers.contains(emp)}">
                                                            <form:checkbox path="performers" value="${emp.id}" checked="true" />
                                                        </c:when>
                                                        <c:otherwise>
                                                            <form:checkbox path="performers" value="${emp.id}" />
                                                        </c:otherwise>
                                                    </c:choose>

                                                </td>
                                                <td><c:out value="${emp.fullName}" /></td>
                                                <td><c:out value="${emp.position}" /></td>
                                            </tr>
                                       </c:forEach>
                                    </tbody>
                                </table>
                            </td>
                            <td><form:errors path="performers" cssStyle="color: #ff0000;" /></td>
                        </tr>

                    </tbody>
                </table>
                <input id="save" type="submit" name="save" value="Сохранить" class="btn btn-primary">
            </form:form>
        </ccms:panel>
    </ccms:layout>
        
    <script src="<c:url value="/res/js/jquery-ui-timepicker-addon.js" />"></script>
    <script src="<c:url value="/res/js/jquery-ui-timepicker-ru.js" />"></script>
    <script type="text/javascript">
        $("#deadline").datetimepicker({

        });

        $("#empsChooseTab").DataTable({
            "scrollY": "200px",
            "scrollCollapse": true,
            "paging": false
        });
    </script>
</ccms:page>