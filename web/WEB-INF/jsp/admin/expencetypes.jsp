<%-- 
    Document   : expencetypes
    Created on : 27.07.2016, 14:31:21
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <link rel="stylesheet" href="<c:url value="/res/css/styles.css" />" >
        <title><c:out value="${title}" /></title>
    </head>

    <body>
        <div class="wrapper">
            <div class="container-fluid content">
                <%@include file="../modules/header.jspf" %>

                <br>

                <div class="row">

                    <%@include file="../modules/sideMenu/sideMenu_admin.jspf" %>

                    <br><br>

                    <div class="col-md-6">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Типы расходов</div>
                            <div class="panel-body">
                                <table id="expenceTypesTable" class="table table-hover table-condensed">

                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Описание</th>
                                            <th>Тип</th>
                                            <th>Действия</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${allExpenceTypes}" var="expenceType" varStatus="idx">
                                            <tr>
                                                <td><c:out value="${expenceType.id}" /></td>
                                                <td>
                                                    <a href="<c:url value="/admin/expencetypes/edit?id=${expenceType.id}" />" >
                                                        <c:out value="${expenceType.description}" />
                                                    </a>
                                                </td>
                                                <td>${expenceType.recurrent ? "Ежемесячный" : "Разовый"}</td>
                                                <td>
                                                    
                                                    <a href="#" class="delete" id="del${expenceType.id}"><span class="glyphicon glyphicon-trash"></span> Удалить</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <br>
                                
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-4">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Новый тип расходов</div>
                            <div class="panel-body">
                                <form:form method="post" commandName="expenceTypeFrm">
                                    <table class="table table-condensed">
                                        <tbody>
                                            <tr>
                                                <td><b>Описание</b></td>
                                                <td><form:input path="description" class="form-control" /></td>
                                                <td><form:errors path="description" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>Отметить как ежемесячный</b></td>
                                                <td><form:checkbox path="recurrent" /></td>
                                                <td><form:errors path="recurrent" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <input id="create" type="submit" value="Создать" class="btn btn-primary">
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%@include file="../modules/footer.jspf" %>

        </div>
            
            <div id="msgDelConfirm" title="Подтверждение удаления">
                <p>
                    Удалить данный тип расходов?
                </p>
            </div>

    <link rel="stylesheet" href="<c:url value="/res/css/jquery.dataTables.css" />" >
    <script type="text/javascript">
        $(document).ready(function () {
            $("#expenceTypesTable").DataTable();
        });
        
        $(".delete").click(function () {
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
                        $.post("<c:url value="/admin/expencetypes/delete" />", {"id": $.data(this, "idtodel").toString().substring(3), "${_csrf.parameterName}": "${_csrf.token}"})
                        location.reload();
                        
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
    </script>

</body>
</html>
