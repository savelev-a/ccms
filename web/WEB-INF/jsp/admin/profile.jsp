<%-- 
    Document   : profile
    Created on : 30.03.2016, 15:57:28
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<ccms:page title="Администрирование - сотрудники" >
    <ccms:layout mainMenuActiveItem="admin" sideMenuSection="admin" sideMenuActiveItem="employees">
        <ccms:panel cols="10" title="Cjnhelybr - ${employee.fullName}">
            <form:form method="post" commandName="employee">
                <table class="table table-condensed">
                    <tbody>
                        <tr>
                            <td><b>Фамилия</b></td>
                            <td><form:input path="lastName" class="form-control" /></td>
                            <td><form:errors path="lastName" cssStyle="color: #ff0000;" /></td>

                            <td><b>Логин</b></td>
                            <td><form:input path="username" readonly="true" class="form-control" /></td>
                            <td><form:errors path="username" cssStyle="color: #ff0000;" /></td>
                        </tr>
                        <tr>
                            <td><b>Имя</b></td>
                            <td><form:input path="firstName" class="form-control" /></td>
                            <td><form:errors path="firstName" cssStyle="color: #ff0000;" /></td>

                            <td><b>Пароль</b></td>
                            <td><form:password path="password" showPassword="false" class="form-control" /></td>
                            <td><form:errors path="password" cssStyle="color: #ff0000;" /></td>
                        </tr>
                        <tr>
                            <td><b>Отчество</b></td>
                            <td><form:input path="middleName" class="form-control" /></td>
                            <td><form:errors path="middleName" cssStyle="color: #ff0000;" /></td>

                            <td rowspan="3"><b>Роли</b></td>
                            <td rowspan="3"><form:checkboxes items="${rolesList}" path="roles" delimiter="<br>" /></td>
                            <td rowspan="3"><form:errors path="roles" cssStyle="color: #ff0000;" /></td>
                        </tr>
                        <tr>
                            <td><b>E-mail</b></td>
                            <td><form:input path="email" class="form-control" /></td>
                            <td><form:errors path="email" cssStyle="color: #ff0000;" /></td>
                        </tr>
                        <tr>
                            <td><b>Телефон</b></td>
                            <td><form:input path="phone" class="form-control" /></td>
                            <td><form:errors path="phone" cssStyle="color: #ff0000;" /></td>
                        </tr>
                        <tr>
                            <td><b>Должность</b></td>
                            <td><form:input path="position" class="form-control" /></td>
                            <td><form:errors path="position" cssStyle="color: #ff0000;" /></td>

                            <td><b>Вход разрешен</b></td>
                            <td><form:checkbox path="active" /><i>  (Установка флажка означает что сотрудник может заходить в веб-интерфейс)</i></td>
                            <td></td>
                        </tr>
                    </tbody>
                </table>
                <input id="save" type="submit" name="save" value="Сохранить" class="btn btn-primary">
                <input id="delete" type="button" name="delete" value="Удалить сотрудника" class="btn btn-danger">
                <br><br><hr>
                <b>Примечание:</b> Изменять логин разрешено только путем создания нового пользователя. <br>
                Оставьте поле "Пароль" пустым для сохранения старого пароля
            </form:form>
        </ccms:panel>
    </ccms:layout>
                                    
    <div id="msgDelConfirm" title="Подтверждение удаления">
        <p>
            Удалить данного сотрудника?
        </p>
    </div>
                                    
    <script type="text/javascript">
        $(document).ready(function () {
            
            $("#delete").on("click", function () {
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
                        $.post("<c:url value="/admin/delEmployeeById"/>", {"id": "${employee.id}", "${_csrf.parameterName}": "${_csrf.token}"}).done(function(){
                            window.location.replace("<c:url value="/admin/employees"/>");
                        });
                        
                        
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
</ccms:page>