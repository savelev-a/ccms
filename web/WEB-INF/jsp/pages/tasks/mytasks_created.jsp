<%-- 
    Document   : mytasks_created
    Created on : 13.07.2016, 14:12:56
    Author     : Alexander Savelev
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<c:url value="/res/css/bootstrap.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/bootstrap-theme.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/jquery.dataTables.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/styles.css" />" >

        <title><c:out value="${title}" /></title>
    </head>

    <body>
        <div class="wrapper">
            <div class="container-fluid content">
                <%@include file="../../modules/header.jspf" %>

                <br>

                <div class="row">

                    <%@include file="../../modules/sideMenu/sideMenu_tasks.jspf" %>

                    <br><br>

                    <div class="col-md-10">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Мои задачи (созданные)</div>
                            <div class="panel-body">
                                <table id="tasksTable" class="table table-hover table-condensed">
                                    <thead>
                                        <tr>
                                            <th>Заголовок</th>
                                            <th>Назначена</th>
                                            <th>Время создания</th>
                                            <th>Срок выполнения</th>
                                            <th>Приоритет</th>
                                            <th>Действия</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${tasksCreatedByMe}" var="task" varStatus="idx">
                                            <tr>

                                                <td>
                                                    ${task.overdued ? "<span class='label label-danger'>Просрочено</span>" : ""}
                                                    <a href="<c:url value="/tasks/taskinfo?id=${task.id}" />" >
                                                        <c:out value="${task.title}" />
                                                    </a>
                                                </td>
                                                <td><c:out value="${task.performer.fullName}" /></td>
                                                <fmt:formatDate value="${task.creationTime.toDate()}" type="both" pattern="dd.MM.yyyy HH:mm" var="createfmt" />
                                                <td><c:out value="${createfmt}" /></td>
                                                <fmt:formatDate value="${task.deadline.toDate()}" type="both" pattern="dd.MM.yyyy HH:mm" var="deadlinefmt" />
                                                <td><c:out value="${deadlinefmt}" /><br> (<c:out value="${task.deadlineString}" />)</td>
                                                <td><c:out value="${task.urgencyString}" /></td>
                                                <td>
                                                    <a href="#"><span class="glyphicon glyphicon-ok-circle"></span> Закрыть задачу</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <%@include file="../../modules/footer.jspf" %>
        </div>


        <script type="text/javascript">
            $(document).ready(function () {
                $("#tasksTable").DataTable({
                    "order": [[ 3, "asc" ]]
                });
            });
        </script>

    </body>
</html>
