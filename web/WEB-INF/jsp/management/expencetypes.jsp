<%-- 
    Document   : expencetypes
    Created on : 27.07.2016, 14:31:21
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<ccms:page title="Управление типами расходов">
    <ccms:layout mainMenuActiveItem="management"  sideMenuSection="expences" sideMenuActiveItem="expenceTypes">
        <ccms:panel cols="6" title="Типы расходов">
            <table id="expenceTypesTable" class="table table-hover table-condensed">

                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Наименование</th>
                        <th>Действия</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${allExpenceTypes}" var="expenceType" varStatus="idx">
                        <tr>
                            <td><c:out value="${expenceType.id}" /></td>
                            <td><ccms:hyperlink target="${expenceType}" /> </td>
                            <td>
                                <a href="#" class="delete" id="del${expenceType.id}"><span class="glyphicon glyphicon-trash"></span> Удалить</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </ccms:panel>
                    
        <ccms:panel cols="4" title="Новый тип расходов">
            <form:form method="post" commandName="expenceTypeFrm">
                <table class="table table-condensed">
                    <tbody>
                        <tr>
                            <td><b>Наименование</b></td>
                            <td><form:input path="name" class="form-control" /></td>
                            <td><form:errors path="name" cssStyle="color: #ff0000;" /></td>
                        </tr>
                        <tr>
                            <td><b>Описание</b></td>
                            <td><form:textarea path="comment" class="form-control" /></td>
                            <td><form:errors path="comment" cssStyle="color: #ff0000;" /></td>
                        </tr>
                    </tbody>
                </table>
                <input id="create" type="submit" value="Создать" class="btn btn-primary">
            </form:form>
        </ccms:panel>
    </ccms:layout>
            
    <div id="msgDelConfirm" title="Подтверждение удаления">
        <p>
            Удалить данный тип расходов?
        </p>
    </div>

    <script type="text/javascript">
        $(document).ready(function () {
            $("#expenceTypesTable").DataTable({
                "order": [[ 1, "asc" ]]
            });
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
                        $.post("<c:url value="/management/expencetypes/delete" />", {"id": $.data(this, "idtodel").toString().substring(3), "${_csrf.parameterName}": "${_csrf.token}"})
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
</ccms:page>
