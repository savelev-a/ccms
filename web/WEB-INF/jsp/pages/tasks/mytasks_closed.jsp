<%-- 
    Document   : mytasks_closed
    Created on : 26.09.2016, 11:46:05
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<ccms:page title="Свободные задачи">
    <ccms:layout mainMenuActiveItem="tasks" sideMenuSection="tasks" sideMenuActiveItem="closed">
        <ccms:panel cols="10" title="Мои задачи (закрытые)">
            <table id="tasksTable" class="table table-hover table-condensed">
                <thead>
                    <tr>
                        <th>Заголовок</th>
                        <th>Назначена</th>
                        <th>Время создания</th>
                        <th>Срок выполнения</th>
                        <th>Приоритет</th>
                        <th>Время закрытия</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${closedTasks}" var="task" varStatus="idx">
                        <tr>

                            <td>
                                ${task.overdued ? "<span class='label label-danger'>Просрочено</span>" : ""}
                                <ccms:hyperlink target="${task}" />
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${task.performers.isEmpty()}">Никому</c:when>
                                    <c:when test="${task.performers.size() == 1}">
                                        <c:out value="${task.performers.toArray()[0].fullName}" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${task.performers.size()} пользователям" />
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <fmt:formatDate value="${task.creationTime.toDate()}" type="both" pattern="dd.MM.yyyy HH:mm" var="createfmt" />
                            <td><c:out value="${createfmt}" /></td>
                            <fmt:formatDate value="${task.deadline.toDate()}" type="both" pattern="dd.MM.yyyy HH:mm" var="deadlinefmt" />
                            <td><c:out value="${deadlinefmt}" />
                            <td><c:out value="${task.urgencyString}" /></td>
                            <fmt:formatDate value="${task.closeTime.toDate()}" type="both" pattern="dd.MM.yyyy HH:mm" var="closefmt" />
                            <td><c:out value="${closefmt}" />
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </ccms:panel>
    </ccms:layout>

    <script type="text/javascript">
        $(document).ready(function () {
            $("#tasksTable").DataTable({
                "order": [[ 3, "asc" ]]
            });
        });
    </script>

</ccms:page>
