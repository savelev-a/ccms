<%-- 
    Document   : organisationsAll
    Created on : 12.04.2016, 14:29:05
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

                    <%@include file="../../modules/sideMenu/sideMenu_orgs.jspf" %>

                    <br><br>

                    <div class="col-md-10">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Юридические лица</div>
                            <div class="panel-body">
                                <table id="orgsTable" class="table table-hover">

                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Название</th>
                                            <th>ИНН</th>
                                            <th>Юр. адрес</th>
                                            <th>Телефон</th>
                                            <!--    <th>Директор</th> -->

                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${allorgs}" var="org" varStatus="idx">
                                            <tr>
                                                <td>${idx.count}</td>
                                                <td>
                                                    <a href="<c:url value="/organisation?id=${org.id}" />">
                                                        <c:out value="${org.name}" />
                                                    </a>
                                                </td>
                                                <td><c:out value="${org.inn}" /></td>
                                                <td><c:out value="${org.urAddress}" /></td>
                                                <td><span class="glyphicon glyphicon-phone-alt"></span> 
                                                    <c:out value="${org.phone}" />
                                                </td>
                                            <!--    <td>${org.director.fullName}</td> -->
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <br>

                                <span class="glyphicon glyphicon-cog"></span>
                                <a href="<c:url value="/admin/organisations" />" > Управление организациями </a>
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
            $("#orgsTable").DataTable();
        });
    </script>

</body>
</html>
