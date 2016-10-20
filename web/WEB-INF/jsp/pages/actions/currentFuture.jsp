<%-- 
    Document   : currentFuture
    Created on : 15.10.2016, 14:29:43
    Author     : alchemist
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<c:url value="/res/css/bootstrap.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/bootstrap-theme.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/jqgrid/jquery-ui.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/jqgrid/jquery-ui.theme.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/jquery.dataTables.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/styles.css" />" >

        <title><c:out value="${title}" /></title>
    </head>

    <body>
        <div class="wrapper">
            <div class="container-fluid content">
                <%@include file="../../modules/header.jspf" %>

                <br>

                <div class="row">

                    <%@include file="../../modules/sideMenu/sideMenu_actionEvents.jspf" %>

                    <br><br>

                    <div class="col-md-10">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Управление акциями</div>
                            <div class="panel-body">
                                <table id="actionsTable" class="table table-hover table-condensed">
                                    <thead>
                                        <tr>
                                            <th>Заголовок</th>
                                            <th>Время проведения</th>
                                            <th>Проходит в</th>
                                            <th>Действия</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${currentFutureActions}" var="action" varStatus="idx">
                                            <tr>

                                                <td>
                                                    ${action.active ? "<span class='label label-danger'>Активная</span>" : ""}
                                                    <a href="<c:url value="/actions/actioninfo?id=${action.id}" />" >
                                                        <c:out value="${action.title}" />
                                                    </a>
                                                </td>
                                                <fmt:formatDate value="${action.startDate.toDate()}" type="date" pattern="dd.MM.yyyy" var="startdatefmt" />
                                                <fmt:formatDate value="${action.endDate.toDate()}" type="date" pattern="dd.MM.yyyy" var="enddatefmt" />
                                                <td>С <c:out value="${startdatefmt}" /> по <c:out value="${enddatefmt}" /></td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${action.affectedShops.isEmpty()}">Нет</c:when>
                                                        <c:when test="${action.affectedShops.size() == 1}">
                                                            <c:out value="${action.affectedShops.toArray()[0].name}" />
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a href="#" data-toggle="tooltip" data-placement="right" title="${action.shoplistStr}" >
                                                                <c:out value="${action.affectedShops.size()} магазинах" />
                                                            </a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <a href="#" class="delAction" id="del{action.id}"><span class="glyphicon glyphicon-trash"></span> Удалить</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <%@include file="../../modules/footer.jspf" %>
        </div>
        
        
        <div id="msgDelConfirm" title="Подтвердите действие">
                <p>
                    Удалить данную акцию? 
                    <br>
                    Данная акция будет удалена и более недоступна.
                </p>
        </div>


        <script type="text/javascript">
            $(document).ready(function () {
                $('[data-toggle="tooltip"]').tooltip();
                
                $("#actionsTable").DataTable({
                    "order": [[ 1, "asc" ]]
                });
                
                $(".delAction").click(function () {
                    $("#msgDelConfirm").data("idtodel", this.id);
                    $("#msgDelConfirm").dialog("open");
                });

                $("#msgDelConfirm").dialog({
                    autoOpen: false,
                    modal: true,
                    dialogClass: "no-close",
                    width: 400,
                    buttons: [{
                            text: "Удалить",
                            class: "btn btn-danger",
                            click: function () {
                                //$(this).dialog("close");
                                $.post("<c:url value="/actions/delaction" />", {"id": $.data(this, "idtodel").toString().substring(3), "${_csrf.parameterName}": "${_csrf.token}"});
                                setTimeout(function () {
                                    location.reload()
                                }, 300);

                            }
                        }, {
                            text: "Отмена",
                            class: "btn btn-default",
                            click: function () {
                                $(this).dialog("close");
                            }
                        }],
                    show: {
                        effect: "clip",
                        duration: 200
                    },
                    hide: {
                        effect: "clip",
                        duration: 200
                    }

                });
            });
        </script>

    </body>
</html>
