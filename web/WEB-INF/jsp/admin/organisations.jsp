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
                        <table id="orgsTable">
                            <caption>Организации</caption>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Наименование</th>
                                    <th>ИНН</th>
                                    <th>Юр. адрес</th>
                                    <th>Телефон</th>
                                    <th>Директор</th>
                                    <th>Кол-во магазинов</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${allorgs}" var="organisation" varStatus="idx">
                                    <tr>
                                        <td>${organisation.id}</td>
                                        <td>
                                            <a href="<c:url value="/admin/orgprofile?id=${organisation.id}" />" >
                                                ${organisation.name}
                                            </a>
                                        </td>
                                        <td>${organisation.inn}</td>
                                        <td>${organisation.urAddress}</td>
                                        <td>${organisation.phone}</td>
                                        <td>${organisation.director.fullName}</td>
                                        <td>${organisation.shops.size()}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                            <tfoot>
                            <th colspan="7">
                                <a href="<c:url value="/admin/addorganisation" />">Добавить организацию...</a>
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
            $("#orgsTable").DataTable();
        });
    </script>

</body>
</html>