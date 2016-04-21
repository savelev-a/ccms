<%-- 
    Document   : shops
    Created on : 31.03.2016, 20:17:23
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
                        <table id="shopsTable">
                            <caption>Магазины</caption>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Наименование</th>
                                    <th>Юр. лицо</th>
                                    <th>E-mail</th>
                                    <th>Телефон</th>
                                    <th>Адрес</th>
                                    <th>ICQ</th>
                                    <th>Администратор</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${allshops}" var="shop" varStatus="idx">
                                    <tr>
                                        <td>${shop.id}</td>
                                        <td>
                                            <a href="<c:url value="/admin/shopprofile?id=${shop.id}" />" >
                                                ${shop.name}
                                            </a>
                                        </td>
                                        <td>${shop.organisation.name}</td>
                                        <td>${shop.email}</td>
                                        <td>${shop.phone}</td>
                                        <td>${shop.address}</td>
                                        <td>${shop.icq}</td>
                                        <td>${shop.shopAdmin.lastName} ${shop.shopAdmin.firstName}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                            <tfoot>
                            <th colspan="8">
                                <a href="<c:url value="/admin/addshop" />">Добавить магазин...</a>
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
            $("#shopsTable").DataTable();
        });
    </script>

</body>
</html>
