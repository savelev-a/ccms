<%-- 
    Document   : officesAll
    Created on : 12.04.2016, 21:03:25
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

                    <%@include file="../../modules/sideMenu/sideMenu_offices.jspf" %> 

                    <br>

                    <div class="g-10">
                        <table id="officesTable">
                            <caption>Офисы</caption>
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Название</th>
                                    <th>Юр. лицо</th>
                                    <th>E-mail</th>
                                    <th>Телефон</th>
                                    <th>Адрес</th>

                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${alloffices}" var="office" varStatus="idx">
                                    <tr>
                                        <td>${idx.count}</td>
                                        <td>
                                            <a href="<c:url value="/office?id=${office.id}" />">${office.name}</a>
                                        </td>
                                        <td>${office.organisation.name}</td>
                                        <td><span class="glyphicon glyphicon-envelope"></span> ${office.email}</td>
                                        <td><span class="glyphicon glyphicon-phone-alt"></span> ${office.phone}</td>
                                        <td>${office.address}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <br>
                        
                        <span class="glyphicon glyphicon-cog"></span>
                        <a href="<c:url value="/admin/offices" />" > Управление офисами </a>
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
            $("#officesTable").DataTable();
        });
    </script>

</body>
</html>