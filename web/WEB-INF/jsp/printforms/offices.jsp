<%-- 
    Document   : offices
    Created on : 09.06.2016, 10:15:11
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
            @page { size: landscape; margin: 0cm }
        </style>
        <title>Офисы</title>
    </head>

    <body><small>
        <div class="wrapper">
            <div class="container-fluid content">
                
                <br><br>

                <div class="row">

                    <div class="col-md-12">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Офисы</div>
                            <div class="panel-body">
                                <table id="employeesTable" class="table table-hover table-condensed">

                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Название</th>
                                            <th>Юр. лицо</th>
                                            <th>E-mail</th>
                                            <th>Телефон</th>
                                            <th>Адрес</th>
                                            <th>Директор</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${alloffices}" var="office" varStatus="idx">
                                            <tr>
                                                <td>${idx.count}</td>
                                                <td>
                                                    <b>
                                                        <c:out value="${office.name}" />
                                                    </b>
                                                </td>
                                                <td><c:out value="${office.organisation.name}" /></td>
                                                <td>
                                                    <span class="glyphicon glyphicon-envelope"></span> 
                                                    <u>
                                                        <c:out value="${office.email}" />
                                                    </u>
                                                </td>
                                                <td>
                                                    <span class="glyphicon glyphicon-phone-alt"></span> 
                                                    <c:out value="${office.phone}" />
                                                </td>
                                                <td><c:out value="${office.address}" /></td>
                                                <td><c:out value="${office.director.fullName}" /></td>
                                            </tr>
                                        </c:forEach>
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