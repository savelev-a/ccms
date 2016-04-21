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
        <link rel="stylesheet" href="<c:url value="/res/css/framework.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/styles.css" />" >
        <title>${currentPage.title} - ИнфоПортал</title>
    </head>

    <body>
        <div class="wrapper">
            <div class="g content">
                <%@include file="../../modules/header.jspf" %>

                <br>

                <div class="g-row">

                    <%@include file="../../modules/sideMenu.jspf" %>

                    <br>

                    <div class="g-10">
                        <table id="orgsTable">
                            <caption>Юридические лица</caption>
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
                                            <a href="<c:url value="/organisation?id=${org.id}" />">${org.name}</a>
                                        </td>
                                        <td>${org.inn}</td>
                                        <td>${org.urAddress}</td>
                                        <td><span class="glyphicon glyphicon-phone-alt"></span> ${org.phone}</td>
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
