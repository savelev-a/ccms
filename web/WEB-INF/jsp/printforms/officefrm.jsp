<%-- 
    Document   : officefrm
    Created on : 16.06.2016, 10:43:57
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
        <title>Профиль офиса</title>
    </head>

    <body><small>
        <div class="wrapper">
            <div class="container-fluid content">
                
                <br><br>

                <div class="row">

                    <div class="col-md-12">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center"><c:out value="${office.name}" /></div>
                            <div class="panel-body">
                                <table id="employeesTable" class="table table-hover table-condensed">

                                    <tbody>
                                        <tr>
                                            <th class="th-header-center" colspan="4">Общие данные</th>
                                        </tr>
                                        <tr>
                                            <th>Наименование</th>
                                            <td colspan="4"><c:out value="${office.name}" /></td>
                                        </tr>
                                        <tr>
                                            <th>Юр. лицо</th>
                                            <td>
                                                    <c:out value="${office.organisation.name}" />
                                            </td>

                                            <th>E-mail</th>
                                            <td>
                                                <span class="glyphicon glyphicon-envelope"></span>
                                                <u>
                                                    <c:out value="${office.email}" />
                                                </u>
                                            </td>
                                        </tr>

                                        <tr>
                                            <th>Адрес</th>
                                            <td><c:out value="${office.address}" /></td>

                                            <th>Телефон</th>
                                            <td>
                                                <span class="glyphicon glyphicon-phone-alt"></span>
                                                <c:out value="${office.phone}" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <th class="th-header-center" colspan="4">Персонал</th>
                                        </tr>
                                        <tr>
                                            <th>Администратор</th>
                                            <td>
                                                    <c:out value="${office.director.fullName}" />
                                            </td>

                                            <th>Сотрудники</th>
                                            <td>
                                                <c:forEach items="${office.officeEmployees}" var="employee">
                                                        <c:out value="${employee.fullName}" />
                                                    &nbsp;
                                                    <c:if test="${employee.position != ''}"> (<c:out value="${employee.position}" />) </c:if>
                                                        <br>
                                                </c:forEach>
                                            </td>
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