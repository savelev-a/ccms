<%-- 
    Document   : employees
    Created on : 29.03.2016, 22:26:23
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <%@include file="../modules/header.jspf" %>

                <br>

                <div class="row">

                    <%@include file="../modules/sideMenu/sideMenu_admin.jspf" %>

                    <br><br>

                    <div class="col-md-10">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Сотрудники</div>
                            <div class="panel-body">
                                <table id="employeesTable" class="table table-hover table-condensed">

                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Фамилия</th>
                                            <th>Имя</th>
                                            <th>Логин</th>
                                            <th>E-mail</th>
                                            <th>Телефон</th>
                                            <th>Должность</th>
                                            <th>Вход разрешен</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${allemps}" var="employee" varStatus="idx">
                                            <tr>
                                                <td><c:out value="${employee.id}" /></td>
                                                <td>
                                                    <a href="<c:url value="/admin/profile?id=${employee.id}" />" >
                                                        <c:out value="${employee.lastName}" />
                                                    </a>
                                                </td>
                                                <td><c:out value="${employee.firstName}" /></td>
                                                <td><c:out value="${employee.username}" /></td>
                                                <td><c:out value="${employee.email}" /></td>
                                                <td><c:out value="${employee.phone}" /></td>
                                                <td><c:out value="${employee.position}" /></td>
                                                <td>${employee.active ? "Да" : "Нет"}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <br>
                                <span class="glyphicon glyphicon-plus"></span>
                                <a href="<c:url value="/admin/addemployee" />">Добавить сотрудника...</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%@include file="../modules/footer.jspf" %>

        </div>

    <link rel="stylesheet" href="<c:url value="/res/css/jquery.dataTables.css" />" >
    <script type="text/javascript">
        $(document).ready(function () {
            $("#employeesTable").DataTable();
        });
    </script>

</body>
</html>