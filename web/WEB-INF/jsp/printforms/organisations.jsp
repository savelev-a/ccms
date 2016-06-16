<%-- 
    Document   : organisations
    Created on : 09.06.2016, 10:30:51
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
        <title>Юр. лица</title>
    </head>

    <body><small>
        <div class="wrapper">
            <div class="container-fluid content">
                
                <br><br>

                <div class="row">

                    <div class="col-md-12">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Юр. лица</div>
                            <div class="panel-body">
                                <table id="employeesTable" class="table table-hover table-condensed">

                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Название</th>
                                            <th>ИНН</th>
                                            <th>КПП</th>
                                            <th>Юр. адрес</th>
                                            <th>Телефон</th>
                                            <th>Директор</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${allorgs}" var="org" varStatus="idx">
                                            <tr>
                                                <td>${idx.count}</td>
                                                <td>
                                                    <b>
                                                        <c:out value="${org.name}" />
                                                    </b>
                                                </td>
                                                <td><c:out value="${org.inn}" /></td>
                                                <td><c:out value="${org.kpp}" /></td>
                                                <td><c:out value="${org.urAddress}" /></td>
                                                <td><span class="glyphicon glyphicon-phone-alt"></span> 
                                                    <c:out value="${org.phone}" />
                                                </td>
                                                <td><c:out value="${org.director.fullName}" /></td>
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
