<%-- 
    Document   : shopsAll
    Created on : 15.04.2016, 12:05:29
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
                        <table id="shopsTable">
                            <caption>Магазины</caption>
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
                                <c:forEach items="${allshops}" var="shop">
                                    <tr ${shop.closed ? "class='tr-closed-shop'" : ""}>
                                        <td>${shop.closed ? "<span class='glyphicon glyphicon-lock'></span>" : "*"}</td>
                                        <td>
                                            <a href="<c:url value="/shop?id=${shop.id}" />">
                                                ${shop.name}
                                            </a>
                                        </td>
                                        <td>${shop.organisation.name}</td>
                                        <td>
                                            <span class="glyphicon glyphicon-phone-alt"></span>
                                            ${shop.phone}
                                        </td>
                                        <td>${shop.workingTime}</td>
                                        <td>
                                            <span class="glyphicon glyphicon-envelope"></span>
                                            ${shop.email}
                                        </td>
                                        <td>${shop.address}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <br>

                        <span class="glyphicon glyphicon-cog"></span>
                        <a href="<c:url value="/admin/shops" />" > Управление магазинами </a>
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
            $("#shopsTable").DataTable({
                "columnDefs": [
                    {"visible": false, "targets": 2}
                ],
                "order": [[2, 'asc']],
                "paging": false,
                "drawCallback": function (settings) {
                    var api = this.api();
                    var rows = api.rows({page: 'current'}).nodes();
                    var last = null;

                    api.column(2, {page: 'current'}).data().each(function (group, i) {
                        if (last !== group) {
                            $(rows).eq(i).before(
                                    '<tr class="tr-group"><td colspan="6">' + group + '</td></tr>'
                                    );
                            last = group;
                        }
                    });
                }
            });
        });
    </script>

</body>
</html>
