<%-- 
    Document   : free
    Created on : 08.07.2016, 10:50:03
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
    <ccms:layout mainMenuActiveItem="tasks" sideMenuSection="tasks" sideMenuActiveItem="free">
        <ccms:panel cols="10" title="Свободные задачи">
            <table id="tasksTable" class="table table-hover table-condensed">
                <thead>
                    <tr>
                        <th>Заголовок</th>
                        <th>Инициатор</th>
                        <th>Время создания</th>
                        <th>Срок выполнения</th>
                        <th>Приоритет</th>
                        <th>Действия</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${freetasks}" var="task" varStatus="idx">
                        <tr>
                            <td>
                                ${task.overdued ? "<span class='label label-danger'>Просрочено</span>" : ""}
                                <ccms:hyperlink target="${task}" />
                            </td>
                            <td><c:out value="${task.creator.fullName}" /></td>
                            <fmt:formatDate value="${task.creationTime.toDate()}" type="both" pattern="dd.MM.yyyy HH:mm" var="createfmt" />
                            <td><c:out value="${createfmt}" /></td>
                            <fmt:formatDate value="${task.deadline.toDate()}" type="both" pattern="dd.MM.yyyy HH:mm" var="deadlinefmt" />
                            <td><c:out value="${deadlinefmt}" /><br>  (<c:out value="${task.deadlineString}" />)</td>
                            <td><c:out value="${task.urgencyString}" /></td>
                            <td>
                                <a href="#" class="takeTask" id="take${task.id}"><span class="glyphicon glyphicon-expand"></span> Взять себе</a>
                            </td>
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

            $(".takeTask").click(function () {
                id = this.id;
                $.post("<c:url value="/tasks/taketask" />", {"id": id.toString().substring(4), "${_csrf.parameterName}": "${_csrf.token}"});
                setTimeout(function () {
                    location.reload();
                }, 300);
            });

        });
    </script>

</ccms:page>