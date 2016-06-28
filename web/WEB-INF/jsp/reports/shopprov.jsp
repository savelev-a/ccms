<%-- 
    Document   : shopprov
    Created on : 28.06.2016, 12:50:58
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

                <div class="row">

                    <%@include file="../modules/sideMenu/sideMenu_dummy.jspf" %> 

                    <br><br>

                    <div class="col-md-10">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Провайдеры в магазинах</div>
                            <div class="panel-body">
                                <table id="shopprovTable" class="table table-hover table-condensed">

                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Магазин</th>
                                            <th>Провайдер</th>
                                            <th>№ договора</th>
                                            <th>IP адрес</th>
                                            <th>Скорость</th>
                                            <th>Телефон техподдержки</th>
                                            <th>Телефон абон. отдела</th>

                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${allshops}" var="shop" varStatus="idx">
                                            <tr ${shop.closed ? "class='tr-closed-shop'" : ""}>
                                                <td>${shop.closed ? "<span class='glyphicon glyphicon-lock'></span>" : "*"}</td>
                                                <td>
                                                    <a href="<c:url value="/shop?id=${shop.id}" />">
                                                        <c:out value="${shop.name}" />
                                                    </a>
                                                </td>
                                                <td>
                                                    <c:out value="${shop.provider.name}" />
                                                </td>
                                                <td>
                                                    <c:out value="${shop.provider.contract}" />
                                                </td>
                                                <td>
                                                    <c:out value="${shop.provider.ip}" />
                                                </td>
                                                <td>
                                                    <c:out value="${shop.provider.speed}" />
                                                </td>
                                                <td>
                                                    <span class="glyphicon glyphicon-phone-alt"></span>
                                                    <c:out value="${shop.provider.techPhone}" />
                                                </td>
                                                <td>
                                                    <span class="glyphicon glyphicon-phone-alt"></span>
                                                    <c:out value="${shop.provider.abonPhone}" />
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <br>

                                <span class="glyphicon glyphicon-cog"></span>
                                <a href="<c:url value="/admin/shops" />" > Управление магазинами </a>
                                &nbsp;
                                <span class="glyphicon glyphicon-print"></span>
                                <a href="<c:url value='/reports/shopprviders?mode=print' />" target="_blank">Распечатать</a>

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
            $("#shopprovTable").DataTable({
                "order": [[ 0, "desc" ]]
            });
        });
    </script>

</body>
</html>