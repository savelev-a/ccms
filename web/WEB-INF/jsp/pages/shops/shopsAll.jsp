<%-- 
    Document   : shopsAll
    Created on : 15.04.2016, 12:05:29
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<ccms:page title="Магазины">
    <ccms:layout mainMenuActiveItem="shops" sideMenuSection="none" sideMenuActiveItem="none">
        <ccms:panel cols="10" title="Магазины">
            <table id="shopsTable" class="table table-hover table-condensed">
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
                            <td><ccms:hyperlink target="${shop}" /></td>
                            <td><c:out value="${shop.organisation.name}" /></td>
                            <td>
                                <span class="glyphicon glyphicon-phone-alt"></span>
                                <c:out value="${shop.phone}" />
                            </td>
                            <td><c:out value="${shop.workingTime}" /></td>
                            <td>
                                <span class="glyphicon glyphicon-envelope"></span>
                                <a href="mailto:<c:out value="${shop.email}" />">
                                    <c:out value="${shop.email}" />
                                </a>
                            </td>
                            <td><c:out value="${shop.address}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <br>

            <span class="glyphicon glyphicon-cog"></span>
            <a href="<c:url value="/admin/shops" />" > Управление магазинами </a>
            &nbsp;
            <span class="glyphicon glyphicon-print"></span>
            <a href="<c:url value='/shops?mode=print' /> " target="_blank">Распечатать</a>
        </ccms:panel>
    </ccms:layout>
                                
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

</ccms:page>
