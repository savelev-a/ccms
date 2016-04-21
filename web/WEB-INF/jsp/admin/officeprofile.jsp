<%-- 
    Document   : officeprofile
    Created on : 08.04.2016, 13:04:14
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
                        <form:form method="post" commandName="office">
                            <table>
                                <caption>Профиль офиса ${office.name}</caption>
                                <tbody>
                                    <tr>
                                        <th>Название</th>
                                        <td><form:input path="name" /></td>
                                        <td><form:errors path="name" cssStyle="color: #ff0000;" /></td>

                                        <th>E-mail</th>
                                        <td><form:input path="email" /></td>
                                        <td><form:errors path="email" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th>Юр. лицо</th>
                                        <td>
                                            <form:select path="organisation">
                                                <form:options items="${orgs}" itemLabel="name" itemValue="id" />
                                            </form:select>
                                        </td>
                                        <td><form:errors path="organisation" cssStyle="color: #ff0000;" /></td>

                                        <th>Телефон</th>
                                        <td><form:input path="phone" /></td>
                                        <td><form:errors path="phone" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th>Адрес</th>
                                        <td><form:textarea path="address" /></td>
                                        <td><form:errors path="address" cssStyle="color: #ff0000;" /></td>
                                    </tr>

                                    <tr>
                                        <th colspan="6" class="th-header-center">Персонал</th>
                                    </tr>
                                    <tr>
                                        <th>Администратор / сотрудники</th>
                                        <td colspan="4">
                                            <div id="empTabs">
                                                <ul>
                                                    <li><a href="#tadmin">Администратор</a></li>
                                                    <li><a href="#temps">Сотрудники</a></li>

                                                    <br>
                                                    <div id="tadmin">
                                                        <table id="admChooseTab">
                                                            <thead>
                                                                <tr>
                                                                    <th>#</th>
                                                                    <th>ФИО</th>
                                                                    <th>Должность</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <c:forEach items="${emps}" var="emp" varStatus="idx">
                                                                    <tr>
                                                                        <td>
                                                                            <c:choose>
                                                                                <c:when test="${emp.id == office.director.id}">
                                                                                    <form:radiobutton path="director" value="${emp.id}" checked="true" />
                                                                                </c:when>
                                                                                <c:otherwise>
                                                                                    <form:radiobutton path="director" value="${emp.id}" />
                                                                                </c:otherwise>
                                                                            </c:choose>
                                                                        </td>
                                                                        <td>${emp.lastName} ${emp.firstName}</td>
                                                                        <td>${emp.position}</td>
                                                                    </tr>
                                                                </c:forEach>
                                                            </tbody>
                                                        </table>
                                                    </div>

                                                    <div id="temps">
                                                        <table id="empsChooseTab">
                                                            <thead>
                                                                <tr>
                                                                    <th>#</th>
                                                                    <th>ФИО</th>
                                                                    <th>Должность</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <c:forEach items="${emps}" var="emp" varStatus="idx">
                                                                    <tr>
                                                                        <td>
                                                                            <c:choose>
                                                                                <c:when test="${office.officeEmployees.contains(emp)}">
                                                                                    <form:checkbox path="officeEmployees" value="${emp.id}" checked="true" />
                                                                                </c:when>
                                                                                <c:otherwise>
                                                                                    <form:checkbox path="officeEmployees" value="${emp.id}" />
                                                                                </c:otherwise>
                                                                            </c:choose>

                                                                        </td>
                                                                        <td>${emp.lastName} ${emp.firstName}</td>
                                                                        <td>${emp.position}</td>
                                                                    </tr>
                                                                </c:forEach>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </ul>

                                            </div>
                                        </td>

                                    </tr>
                                    <tr>
                                        <td><form:errors path="director" cssStyle="color: #ff0000;" /></td>
                                        <td><form:errors path="officeEmployees" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                </tbody>
                            </table>
                            <input id="save" type="submit" name="save" value="Сохранить" class="f-bu f-bu-default">
                            <input id="delete" type="button" name="delete" value="Удалить офис" class="f-bu f-bu-warning">
                        </form:form>
                    </div>
                </div>
            </div>

            <%@include file="../modules/footer.jspf" %>
        </div>


        <!-- MessageBox & tables --> 

    <link rel="stylesheet" href="<c:url value="/res/css/jquery.dataTables.css" />" >
    <link rel="stylesheet" href="<c:url value="/res/css/messagebox.css" />" >
    <link rel="stylesheet" href="<c:url value="/res/css/jquery-ui.css" />" >
    <link rel="stylesheet" href="<c:url value="/res/css/jquery-ui.structure.css" />" >
    <link rel="stylesheet" href="<c:url value="/res/css/jquery-ui.theme.css" />" >
    <script type="text/javascript">
        $(document).ready(function () {
            $("#admChooseTab").DataTable({
                "scrollY": "200px",
                "scrollCollapse": true,
                "paging": false
            });
            $("#empsChooseTab").DataTable({
                "scrollY": "200px",
                "scrollCollapse": true,
                "paging": false
            });

            $("#delete").on("click", function () {
                $.MessageBox({
                    buttonDone: "Удалить",
                    buttonFail: "Отмена",
                    message: "Удалть офис ${office.name}?"
                }).done(function () {
                    $.post("<c:url value="/admin/delOfficeById"/>", {id: "${office.id}", ${_csrf.parameterName} : "${_csrf.token}"}).done(function () {
                        window.location.replace("<c:url value="/admin/offices"/>");
                    });

                });
            });
            $("#delete").mouseup(function () {
                $(this).blur();
            });

            $("#empTabs").tabs();
        });
    </script>

    <!-- End MessageBox & tables-->

</body>
</html>