<%-- 
    Document   : mytasks_perf
    Created on : 13.07.2016, 9:26:25
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<ccms:page title="Мои задачи - назначенные">
    <ccms:layout mainMenuActiveItem="tasks" sideMenuSection="tasks" sideMenuActiveItem="performed">
        <ccms:panel cols="10" title="Мои задачи (назначенные)">
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
                    <c:forEach items="${tasksPerformedByMe}" var="task" varStatus="idx">
                        <tr>

                            <td>
                                ${task.overdued ? "<span class='label label-danger'>Просрочено</span>" : ""}
                                <ccms:hyperlink target="${task}" />
                            </td>
                            <td><c:out value="${task.creator.fullName}" /></td>
                            <fmt:formatDate value="${task.creationTime.toDate()}" type="both" pattern="dd.MM.yyyy HH:mm" var="createfmt" />
                            <td><c:out value="${createfmt}" /></td>
                            <fmt:formatDate value="${task.deadline.toDate()}" type="both" pattern="dd.MM.yyyy HH:mm" var="deadlinefmt" />
                            <td><c:out value="${deadlinefmt}" /><br> (<c:out value="${task.deadlineString}" />)</td>
                            <td><c:out value="${task.urgencyString}" /></td>
                            <td>
                                <a href="#" class="drop" id="drop${task.id}"><span class="glyphicon glyphicon-remove-circle"></span> Отказаться</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </ccms:panel>
    </ccms:layout>
        
    <div id="msgDropConfirm" title="Подтвердите действие">
            <p>
                Отказаться от данной задачи? 
                <br>
                Это действие будет видно всем пользователям и инициатору задачи.
            </p>
    </div>


    <script type="text/javascript">
        $(document).ready(function () {
            $("#tasksTable").DataTable({
                "order": [[3, "asc"]]
            });

            $(".drop").click(function () {
                $("#msgDropConfirm").data("idtodrop", this.id);
                $("#msgDropConfirm").dialog("open");
            });

            $("#msgDropConfirm").dialog({
                autoOpen: false,
                modal: true,
                dialogClass: "no-close",
                width: 400,
                buttons: [{
                        text: "Отказаться",
                        class: "btn btn-danger",
                        click: function () {
                            //$(this).dialog("close");
                            $.post("<c:url value="/tasks/droptask" />", {"id": $.data(this, "idtodrop").toString().substring(4), "${_csrf.parameterName}": "${_csrf.token}"});
                            setTimeout(function () {
                                location.reload()
                            }, 300);

                        }
                    }, {
                        text: "Отмена",
                        class: "btn btn-default",
                        click: function () {
                            $(this).dialog("close");
                        }
                    }],
                show: {
                    effect: "clip",
                    duration: 200
                },
                hide: {
                    effect: "clip",
                    duration: 200
                }

            });
        });
    </script>
</ccms:page>