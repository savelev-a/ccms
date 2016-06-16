<%-- 
    Document   : shops
    Created on : 09.06.2016, 10:31:01
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
        <title>Магазины</title>
    </head>

    <body><small>
        <div class="wrapper">
            <div class="container-fluid content">
                
                <br><br>

                <div class="row">

                    <div class="col-md-12">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Магазины</div>
                            <div class="panel-body">
                                <table class="table table-condensed">

                                    <thead>
                                        <tr>
                                            <td><b>#</b></td>
                                            <td><b>Наименование</b></td>
                                            <td><b>Юр. лицо</b></td>
                                            <td><b>Телефон</b></td>
                                            <td><b>Время работы</b></td>
                                            <td><b>E-mail</b></td>
                                            <td><b>Адрес</b></td>

                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${openshops}" var="shop">
                                            <tr>
                                                <td>*</td>
                                                <td>
                                                    <b>
                                                        <c:out value="${shop.name}" />
                                                    </b>
                                                </td>
                                                <td><c:out value="${shop.organisation.name}" /></td>
                                                <td>
                                                    <span class="glyphicon glyphicon-phone-alt"></span>
                                                    <c:out value="${shop.phone}" />
                                                </td>
                                                <td><c:out value="${shop.workingTime}" /></td>
                                                <td>
                                                    <span class="glyphicon glyphicon-envelope"></span>
                                                    <u>
                                                        <c:out value="${shop.email}" />
                                                    </u>
                                                </td>
                                                <td><c:out value="${shop.address}" /></td>
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
