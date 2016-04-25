<%-- 
    Document   : employees
    Created on : 08.04.2016, 13:43:48
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
                <%@include file="../../modules/header.jspf" %>

                <br>

                <div class="row">

                    <%@include file="../../modules/sideMenu/sideMenu_employees.jspf" %>

                    <br><br>

                    <div class="col-md-10">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Сотрудники</div>
                            <div class="panel-body">
                                <table id="employeesTable" class="table table-hover table-condensed">

                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>ФИО</th>
                                            <th>E-mail</th>
                                            <th>Телефон</th>
                                            <th>Должность</th>

                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${allemps}" var="employee" varStatus="idx">
                                            <tr>
                                                <td>${idx.count}</td>
                                                <td>
                                                    <a href="<c:url value="/employee?id=${employee.id}" />" >
                                                        <c:out value="${employee.fullName}" />
                                                    </a>
                                                </td>
                                                <td>
                                                    <span class="glyphicon glyphicon-envelope"></span>
                                                    <a href="mailto:<c:out value="${employee.email}" />">
                                                        <c:out value="${employee.email}" />
                                                    </a>
                                                </td>
                                                <td><span class="glyphicon glyphicon-phone-alt"></span> <c:out value="${employee.phone}" /></td>
                                                <td><c:out value="${employee.position}" /></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <br>

                                <span class="glyphicon glyphicon-cog"></span>
                                <a href="<c:url value="/admin/employees" />" > Управление сотрудниками </a>
                                &nbsp;
                                <span class="glyphicon glyphicon-print"></span>
                                <a href="#">Распечатать</a>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%@include file="../../modules/footer.jspf" %>

        </div>

    <link rel="stylesheet" href="<c:url value="/res/css/jquery.dataTables.css" />" >
    <script type="text/javascript">
        $(document).ready(function () {
            $("#employeesTable").DataTable();
        });
    </script>

</body>
</html>
