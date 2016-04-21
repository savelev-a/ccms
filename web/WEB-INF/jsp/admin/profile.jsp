<%-- 
    Document   : profile
    Created on : 30.03.2016, 15:57:28
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
        <link rel="stylesheet" href="<c:url value="/res/css/framework.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/styles.css" />" >
        <title>${title} - ИнфоПортал</title>
    </head>

    <body>
        <div class="wrapper">
            <div class="g content">
                <%@include file="../modules/header.jspf" %>

                <br>

                <div class="g-row">

                    <%@include file="../modules/sideMenu/sideMenu_admin.jspf" %>

                    <div class="g-10">
                        <form:form method="post" commandName="employee">
                            <table>
                                <caption>Профиль сотрудника ${employee.lastName} ${employee.firstName}</caption>
                                <tbody>
                                    <tr>
                                        <th width="30%">Фамилия</th>
                                        <td><form:input path="lastName" /></td>
                                        <td><form:errors path="lastName" cssStyle="color: #ff0000;" /></td>
                                        
                                        <th>Логин</th>
                                        <td><form:input path="username" readonly="true" /></td>
                                        <td><form:errors path="username" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th>Имя</th>
                                        <td><form:input path="firstName" /></td>
                                        <td><form:errors path="firstName" cssStyle="color: #ff0000;" /></td>
                                        
                                        <th>Пароль</th>
                                        <td><form:password path="password" showPassword="false" /></td>
                                        <td><form:errors path="password" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th>Отчество</th>
                                        <td><form:input path="middleName" /></td>
                                        <td><form:errors path="middleName" cssStyle="color: #ff0000;" /></td>
                                        
                                        <th rowspan="3">Роли</th>
                                        <td rowspan="3"><form:checkboxes items="${rolesList}" path="roles" delimiter="<br>" /></td>
                                        <td rowspan="3"><form:errors path="roles" cssStyle="color: #ff0000;" /></td>
                                        
                                        
                                    </tr>
                                    <tr>
                                        <th>E-mail</th>
                                        <td><form:input path="email" /></td>
                                        <td><form:errors path="email" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th>Телефон</th>
                                        <td><form:input path="phone" /></td>
                                        <td><form:errors path="phone" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th>Должность</th>
                                        <td><form:input path="position" /></td>
                                        <td><form:errors path="position" cssStyle="color: #ff0000;" /></td>
                                        
                                        <th>Вход разрешен</th>
                                        <td><form:checkbox path="active" /><i>  (Установка флажка означает что сотрудник может заходить в веб-интерфейс)</i></td>
                                        <td></td>
                                    </tr>
                                </tbody>
                            </table>
                            <input id="save" type="submit" name="save" value="Сохранить" class="f-bu f-bu-default">
                            <input id="delete" type="button" name="delete" value="Удалить сотрудника" class="f-bu f-bu-warning">
                            <br><br><hr>
                            <b>Примечание:</b> Изменять логин разрешено только путем создания нового пользователя. <br>
                            Оставьте поле "Пароль" пустым для сохранения старого пароля
                        </form:form>
                    </div>
                </div>
            </div>

            <%@include file="../modules/footer.jspf" %>
        </div>


        <!-- MessageBox --> 

    <link rel="stylesheet" href="<c:url value="/res/css/messagebox.css" />" >
    <script type="text/javascript">
        $(document).ready(function () {
            $("#delete").on("click", function () {
                $.MessageBox({
                    buttonDone: "Удалить",
                    buttonFail: "Отмена",
                    message: "Удалть сотрудника ${employee.lastName} ${employee.firstName}?"
                }).done(function () {
                    $.post("<c:url value="/admin/delEmployeeById"/>", {id: "${employee.id}", ${_csrf.parameterName} : "${_csrf.token}"}).done(function () {
                        window.location.replace("<c:url value="/admin/employees"/>");
                    });

                });
            });
            $("#delete").mouseup(function () {
                $(this).blur();
            });
        });
    </script>

    <!-- End MessageBox-->

</body>
</html>