<%-- 
    Document   : newtask
    Created on : 29.06.2016, 12:41:48
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
        <link rel="stylesheet" href="<c:url value="/res/css/jquery-ui-timepicker-addon.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/jqgrid/jquery-ui.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/jqgrid/jquery-ui.structure.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/jqgrid/jquery-ui.theme.css" />" >
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
                            <div class="panel-heading panel-heading-dark" align="center">Добавление новой задачи</div>
                            <div class="panel-body">
                                <form:form method="post" commandName="newTaskFrm">
                                    <table class="table table-condensed">
                                        <tbody>
                                            <tr>
                                                <td><b>Краткий заголовок</b></td>
                                                <td><form:input path="title" class="form-control" /></td>
                                                <td><form:errors path="title" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            
                                            <tr>
                                                <td><b>Подробное описание</b></td>
                                                <td><form:textarea path="text" class="form-control" rows="10" /></td>
                                                <td><form:errors path="text" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            
                                            <tr>
                                                <td><b>Инициатор</b></td>
                                                <td><c:out value="${newTaskFrm.creator.fullName}" /></td>
                                                <form:hidden path="creator.id" />
                                                <td><form:errors path="creator" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            
                                            <tr>
                                                <td><b>Назначить сотруднику</b></td>
                                                <td>
                                                    <form:select path="performer" class="form-control">
                                                        <form:option value="0">
                                                            Никому не назначать
                                                        </form:option>
                                                        <c:forEach items="${allEmps}" var="emp">
                                                            <form:option value="${emp.id}">
                                                                <c:out value="${emp.fullName}" />
                                                            </form:option>
                                                        </c:forEach>
                                                    </form:select>
                                                </td>
                                                <td><form:errors path="performer" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            
                                            <fmt:formatDate value="${newTaskFrm.deadline.toDate()}" type="both" pattern="dd.MM.yyyy HH:mm" var="deadlinefmt" />
                                            
                                            <tr>
                                                <td><b>Выпонить до</b></td>
                                                <td><form:input path="deadline" class="form-control" id="deadline" value="${deadlinefmt}" /></td>
                                                <td><form:errors path="deadline" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            
                                            <tr>
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

                                        </tbody>
                                    </table>
                                    <input id="save" type="submit" name="save" value="Сохранить" class="btn btn-primary">
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <%@include file="../../modules/footer.jspf" %>
        </div>
        
        <script src="<c:url value="/res/js/jquery-ui-timepicker-addon.js" />"></script>
        <script src="<c:url value="/res/js/jquery-ui-timepicker-ru.js" />"></script>
        <script type="text/javascript">
            $("#deadline").datetimepicker({
                
            });
        </script>
    </body>
</html>