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
        <link rel="stylesheet" href="<c:url value="/res/css/framework.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/styles.css" />" >
        <title>${title} - ИнфоПортал</title>
    </head>

    <body>
        <div class="wrapper">
            <div class="g content">
                <%@include file="../../modules/header.jspf" %>

                <br>

                <div class="g-row">

                    <%@include file="../../modules/sideMenu/sideMenu_employees.jspf" %>

                    <br>

                    <div class="g-10">
                        <table id="employeesTable">
                            <caption>Сотрудники</caption>
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
                                                ${employee.fullName}
                                            </a>
                                        </td>
                                        <td>
                                            <span class="glyphicon glyphicon-envelope"></span>
                                            <a href="mailto:${employee.email}">${employee.email}</a>
                                        </td>
                                        <td><span class="glyphicon glyphicon-phone-alt"></span> ${employee.phone}</td>
                                        <td>${employee.position}</td>
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
