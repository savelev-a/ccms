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
        <link rel="stylesheet" href="<c:url value="/res/css/framework.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/styles.css" />" >
        <title>${title} - ИнфоПортал</title>
    </head>

    <body>
        <div class="wrapper">
            <div class="g content">
                <%@include file="../modules/header.jspf" %>

                <br>

                <div class="g-row">

                    <%@include file="../modules/sideMenu/sideMenu_admin.jspf" %>

                    <br>

                    <div class="g-10">
                        <table id="employeesTable">
                            <caption>Сотрудники</caption>
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
                                        <td>${employee.id}</td>
                                        <td>
                                            <a href="<c:url value="/admin/profile?id=${employee.id}" />" >
                                                ${employee.lastName}
                                            </a>
                                        </td>
                                        <td>${employee.firstName}</td>
                                        <td>${employee.username}</td>
                                        <td>${employee.email}</td>
                                        <td>${employee.phone}</td>
                                        <td>${employee.position}</td>
                                        <td>${employee.active ? "Да" : "Нет"}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                            <tfoot>
                            <th colspan="8">
                                <a href="<c:url value="/admin/addemployee" />">Добавить сотрудника...</a>
                            </th>
                            </tfoot>
                        </table>
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