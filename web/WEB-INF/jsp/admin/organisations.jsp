<%-- 
    Document   : organisations
    Created on : 30.03.2016, 20:37:09
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

                <div class="g-row">

                    <%@include file="../modules/sideMenu/sideMenu_admin.jspf" %>

                    <br><br>

                    <div class="col-md-10">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Организации</div>
                            <div class="panel-body">
                                <table id="orgsTable" class="table table-hover table-condensed">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Наименование</th>
                                            <th>ИНН</th>
                                            <th>Юр. адрес</th>
                                            <th>Телефон</th>
                                            <th>Директор</th>
                                            <th>Магазины</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${allorgs}" var="organisation" varStatus="idx">
                                            <tr>
                                                <td><c:out value="${organisation.id}" /></td>
                                                <td>
                                                    <a href="<c:url value="/admin/orgprofile?id=${organisation.id}" />" >
                                                        <c:out value="${organisation.name}" />
                                                    </a>
                                                </td>
                                                <td><c:out value="${organisation.inn}" /></td>
                                                <td><c:out value="${organisation.urAddress}" /></td>
                                                <td><c:out value="${organisation.phone}" /></td>
                                                <td><c:out value="${organisation.director.fullName}" /></td>
                                                <td><c:out value="${organisation.shops.size()}" /></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>

                                </table>
                                <br>
                                <span class="glyphicon glyphicon-plus"></span>
                                <a href="<c:url value="/admin/addorganisation" />">Добавить организацию...</a>
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
            $("#orgsTable").DataTable();
        });
    </script>

</body>
</html>