<%-- 
    Document   : taskinfo
    Created on : 05.07.2016, 12:01:45
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
                            <div class="panel-heading panel-heading-dark" align="center">Задача - <c:out value="${task.title}" /> </div>
                            <div class="panel-body">
                                <table class="table table-hover table-condensed">

                                    <tbody>
                                        <tr>
                                            <th>Заголовок задачи</th>
                                            <td colspan="4"><c:out value="${task.title}" /></td>
                                        </tr>
                                        <tr>
                                            <th>Описание</th>
                                            <td colspan="4" style="white-space: pre"><c:out value="${task.text}" /></td>
                                        </tr>
                                        <tr>
                                            <th>Инициатор</th>
                                            <td>
                                                <a href="<c:url value="/employee?id=${task.creator.id}" />" >
                                                    <c:out value="${task.creator.fullName}" />
                                                </a>
                                            </td>
                                            <th>Назначена</th>
                                            <td>
                                                <c:if test="${task.performer == null}">Никому <a href="#">(Взять себе)</a></c:if>
                                                <a href="<c:url value="/employee?id=${task.performer.id}" />" >
                                                    <c:out value="${task.performer.fullName}" />
                                                </a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>Создана</th>
                                                <fmt:formatDate value="${task.creationTime.toDate()}" type="both" pattern="dd.MM.yyyy HH:mm" var="createfmt" />
                                            <td><c:out value="${createfmt}" /></td>
                                            <th>Срок выполнения</th>
                                                <fmt:formatDate value="${task.deadline.toDate()}" type="both" pattern="dd.MM.yyyy HH:mm" var="deadlinefmt" />
                                            <td><c:out value="${deadlinefmt}" /></td>
                                        </tr>
                                        <tr>
                                            <th>Статус</th>
                                            <td><c:out value="${task.statusString}" /></td>
                                            <c:choose>
                                                <c:when test="task.closeTime != null">
                                                    <th>Время закрытия</th>
                                                    <td><c:out value="${task.closeTime}" /></td>
                                                </c:when>
                                                <c:otherwise>
                                                    <th>Осталось времени</th>
                                                    <td ${task.overdued ? "class='td-redfont'" : ""}>
                                                        <c:out value="${task.deadlineString}" />
                                                    </td>
                                                </c:otherwise>
                                            </c:choose>
                                            </tr>
                                            <tr>
                                                <th>Важность</th>
                                                <td><c:out value="${task.urgencyString}" /></td>
                                        </tr>

                                        <tr><td> </td></tr>

                                        <tr>
                                            <th>Прогресс</th>
                                            <td colspan="4">
                                                <c:forEach items="${task.comments}" var="comment" >
                                                    <div class="panel panel-info panel-primary-dark">
                                                        <div class="panel-heading panel-heading-dark" align="center">
                                                            <c:choose>
                                                                <c:when test="${comment.creator != null}">
                                                                    <a href="<c:url value="${comment.creator.id}" />">
                                                                        <c:out value="${comment.creator.fullName}" />
                                                                    </a> написал(а)
                                                                </c:when>
                                                                <c:otherwise>
                                                                    Системное сообщение
                                                                </c:otherwise>
                                                            </c:choose>
                                                            
                                                        </div>
                                                        <div class="panel-body">
                                                            <h4><c:out value="${comment.title}" /></h4>
                                                            <hr>
                                                            <div style="white-space: pre"><c:out value="${comment.text}" /></div>
                                                            <br><br>
                                                            <fmt:formatDate value="${comment.creationTime.toDate()}" type="both" pattern="dd.MM.yyyy HH:mm" var="commenttimefmt" />
                                                            <div align="right"><small><c:out value="${commenttimefmt}" /></small></div>
                                                        </div>
                                                    </div>
                                                </c:forEach>


                                                <!--<div class="panel panel-info panel-primary-dark">
                                                    <div class="panel-heading panel-heading-dark" align="center">
                                                        <a href="#">Савельев Александр</a> написал(а)
                                                    </div>
                                                    <div class="panel-body">
                                                        <h4>Заголовок комментария</h4>
                                                        <hr>
                                                        Текст комментария
                                                        <br><br>
                                                        <div align="right"><small>25.05.2015 11:31</small></div>
                                                    </div>
                                                </div>-->

                                                <div class="panel panel-info panel-primary-dark">
                                                    <div class="panel-heading panel-heading-dark" align="center">
                                                        Новый комментарий
                                                    </div>
                                                    <div class="panel-body">
                                                        <c:url var="comment_post_url"  value="/tasks/addcomment?taskid=${task.id}" />
                                                        <form:form method="post" commandName="newcomment" action="${comment_post_url}">
                                                            <table class="table table-condensed table-hover">
                                                                <tbody>
                                                                    <tr>
                                                                        <td>Заголовок</td>
                                                                        <td><form:input path="title" class="form-control" /></td>
                                                                        <td><form:errors path="title" cssStyle="color: #ff0000;" /></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td>Описание</td>
                                                                        <td><form:textarea path="text" class="form-control" rows="5" /></td>
                                                                        <td><form:errors path="text" cssStyle="color: #ff0000;" /></td>
                                                                    </tr>
                                                                </tbody>
                                                            </table>

                                                            <fmt:formatDate value="${newcomment.creationTime.toDate()}" type="both" pattern="dd.MM.yyyy HH:mm" var="commenttimefmt" />
                                                            <form:hidden path="creator.id" />
                                                            <form:hidden path="creationTime" value="${commenttimefmt}" />
                                                            <form:errors path="creator" cssStyle="color: #ff0000;" />
                                                            <form:errors path="creationTime" cssStyle="color: #ff0000;" />
                                                            <input id="save" type="submit" name="save" value="Сохранить" class="btn btn-primary">
                                                        </form:form>

                                                    </div>
                                                </div>

                                            </td>
                                        </tr>

                                    </tbody>
                                </table>

                                <c:choose>
                                    <c:when test="${task.statusString == 'Новая'}" >
                                        <button class="btn btn-primary" ><span class="glyphicon glyphicon-expand"></span> Взять себе</button>
                                    </c:when>

                                    <c:when test="${task.statusString == 'Назначена'}" >
                                        <button class="btn btn-primary" ><span class="glyphicon glyphicon-play-circle"></span> Принять в работу</button>
                                        <button class="btn btn-primary" ><span class="glyphicon glyphicon-ok-circle"></span> Выполнено</button>
                                    </c:when>

                                    <c:when test="${task.statusString == 'В работе'}" >
                                        <button class="btn btn-primary" ><span class="glyphicon glyphicon-time"></span> Приостановить</button>
                                        <button class="btn btn-primary" ><span class="glyphicon glyphicon-ok-circle"></span> Выполнено</button>
                                    </c:when>
                                    <c:when test="${task.statusString == 'Приостановлена'}" >
                                        <button class="btn btn-primary" ><span class="glyphicon glyphicon-play-circle"></span> Принять в работу</button>
                                        <button class="btn btn-primary" ><span class="glyphicon glyphicon-ok-circle"></span> Выполнено</button>
                                    </c:when>
                                </c:choose>

                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <%@include file="../../modules/footer.jspf" %>
        </div>

    </body>
</html>