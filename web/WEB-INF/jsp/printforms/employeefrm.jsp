<%-- 
    Document   : employeefrm
    Created on : 16.06.2016, 10:07:31
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
        <style type="text/css" media="print">
            @page { size: portrait; margin: 0cm }
        </style>
        <title>Профиль сотрудника</title>
    </head>

    <body><small>
        <div class="wrapper">
            <div class="container-fluid content">
                
                <br><br>

                <div class="row">

                    <div class="col-md-12">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Профиль</div>
                            <div class="panel-body">
                                <table id="employeesTable" class="table table-hover table-condensed">

                                    <tbody>
                                        <tr>
                                            <th>Фамилия</th>
                                            <td><c:out value="${employee.lastName}" /></td>
                                        </tr>
                                        <tr>
                                            <th>Имя</th>
                                            <td><c:out value="${employee.firstName}" /></td>
                                        </tr>
                                        <tr>
                                            <th>Отчество</th>
                                            <td><c:out value="${employee.middleName}" /></td>
                                        </tr>
                                        <tr>
                                            <th>E-mail</th>
                                            <td>
                                                <a herf="mailto:<c:out value="${employee.email}" />">
                                                    <c:out value="${employee.email}" />
                                                </a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>Телефон</th>
                                            <td>
                                                <span class="glyphicon glyphicon-phone-alt"></span>
                                                <c:out value="${employee.phone}" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>Должность</th>
                                            <td><c:out value="${employee.position}" /></td>
                                        </tr>
                                    </tbody>
                                </table>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="footer">
                Актуально на <c:out value="${currentDate}" />
            </div>

        </div>

            <script type="text/javascript">
                window.onload = function() { window.print(); };
            </script>

            </small></body>
</html>