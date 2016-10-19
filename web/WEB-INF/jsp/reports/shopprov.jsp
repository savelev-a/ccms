<%-- 
    Document   : shopprov
    Created on : 28.06.2016, 12:50:58
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<ccms:page title="Отчет по провайдерам">
    <ccms:layout mainMenuActiveItem="reports" sideMenuSection="none" sideMenuActiveItem="none">
        <ccms:panel cols="10" title="Провайдеры в магазинах">
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
                            <td><ccms:hyperlink target="${shop}" /></td>
                            <td><c:out value="${shop.provider.name}" /></td>
                            <td><c:out value="${shop.provider.contract}" /></td>
                            <td><c:out value="${shop.provider.ip}" /></td>
                            <td><c:out value="${shop.provider.speed}" /></td>
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

        </ccms:panel>
    </ccms:layout>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#shopprovTable").DataTable({
                "order": [[ 0, "desc" ]]
            });
        });
    </script>
</ccms:page>