<%-- 
    Document   : offices
    Created on : 08.04.2016, 12:45:28
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
                <%@include file="../modules/header.jspf" %>

                <br>

                <div class="g-row">

                    <%@include file="../modules/sideMenu.jspf" %>

                    <br>

                    <div class="g-10">
                        <table id="officesTable">
                            <caption>Офисы</caption>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Название</th>
                                    <th>Юр. лицо</th>
                                    <th>E-mail</th>
                                    <th>Телефон</th>
                                    <th>Адрес</th>
                                    <th>Администратор</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${alloffices}" var="office" varStatus="idx">
                                    <tr>
                                        <td>${office.id}</td>
                                        <td>
                                            <a href="<c:url value="/admin/officeprofile?id=${office.id}" />" >
                                                ${office.name}
                                            </a>
                                        </td>
                                        <td>${office.organisation.name}</td>
                                        <td>${office.email}</td>
                                        <td>${office.phone}</td>
                                        <td>${office.address}</td>
                                        <td>${office.director.lastName} ${office.director.firstName}</td>

                                    </tr>
                                </c:forEach>
                            </tbody>
                            <tfoot>
                            <th colspan="7">
                                <a href="<c:url value="/admin/addoffice" />">Добавить офис...</a>
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
            $("#officesTable").DataTable();
        });
    </script>

</body>
</html>
