<%-- 
    Document   : shopprofile
    Created on : 04.04.2016, 22:46:31
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
        <title>${currentPage.title} - ИнфоПортал</title>
    </head>

    <body>
        <div class="wrapper">
            <div class="g content">
                <%@include file="../modules/header.jspf" %>

                <br>

                <div class="g-row">

                    <%@include file="../modules/sideMenu.jspf" %>

                    <div class="g-10">
                        <form:form method="post" commandName="shop">
                            <table>
                                <caption>Редактирование магазина</caption>
                                <tbody>
                                    <tr>
                                        <th colspan="6" class="th-header-center">Основные данные</th>
                                    </tr>
                                    <tr>
                                        <th>Наименование</th>
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

                                        <th>Время работы</th>
                                        <td><form:input path="workingTime" /></td>
                                        <td><form:errors path="workingTime" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th>Телефон</th>
                                        <td><form:input path="phone" /></td>
                                        <td><form:errors path="phone" cssStyle="color: #ff0000;" /></td>

                                        <th>ICQ</th>
                                        <td><form:input path="icq" /></td>
                                        <td><form:errors path="icq" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th>Адрес</th>
                                        <td><form:textarea path="address" /></td>
                                        <td><form:errors path="address" cssStyle="color: #ff0000;" /></td>

                                        <th>Комментарий</th>
                                        <td><form:textarea path="comment" /></td>
                                        <td><form:errors path="comment" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th>Арендодатель</th>
                                        <td><form:textarea path="landlord" /></td>
                                        <td><form:errors path="landlord" cssStyle="color: #ff0000;" /></td>

                                        <th>Магазин закрыт</th>
                                        <td><form:checkbox path="closed" /></td>
                                        <td><form:errors path="closed" cssStyle="color: #ff0000;" /></td>
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
                                                                                <c:when test="${emp.id == shop.shopAdmin.id}">
                                                                                    <form:radiobutton path="shopAdmin" value="${emp.id}" checked="true" />
                                                                                </c:when>
                                                                                <c:otherwise>
                                                                                    <form:radiobutton path="shopAdmin" value="${emp.id}" />
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
                                                                                <c:when test="${shop.shopEmployees.contains(emp)}">
                                                                                    <form:checkbox path="shopEmployees" value="${emp.id}" checked="true" />
                                                                                </c:when>
                                                                                <c:otherwise>
                                                                                    <form:checkbox path="shopEmployees" value="${emp.id}" />
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
                                        <td><form:errors path="shopAdmin" cssStyle="color: #ff0000;" /></td>
                                        <td><form:errors path="shopEmployees" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th colspan="6" class="th-header-center">Оборудование и софт</th>
                                    </tr>
                                    <tr>
                                        <th>Системный блок</th>
                                        <td><form:textarea path="hardware.pc" /></td>
                                        <td><form:errors path="hardware.pc" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th>Операционная система</th>
                                        <td><form:input path="hardware.os" /></td>
                                        <td><form:errors path="hardware.os" cssStyle="color: #ff0000;" /></td>

                                        <th>Детектор валют</th>
                                        <td><form:input path="hardware.detektor" /></td>
                                        <td><form:errors path="hardware.detektor" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th>ККМ</th>
                                        <td><form:input path="hardware.kkm" /></td>
                                        <td><form:errors path="hardware.kkm" cssStyle="color: #ff0000;" /></td>

                                        <th>Банковский терминал</th>
                                        <td><form:input path="hardware.bankTerm" /></td>
                                        <td><form:errors path="hardware.bankTerm" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th>Сканер ШК</th>
                                        <td><form:input path="hardware.scanner" /></td>
                                        <td><form:errors path="hardware.scanner" cssStyle="color: #ff0000;" /></td>

                                        <th>Роутер</th>
                                        <td><form:input path="hardware.router" /></td>
                                        <td><form:errors path="hardware.router" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th>Принтер этикеток</th>
                                        <td><form:input path="hardware.labelPrinter" /></td>
                                        <td><form:errors path="hardware.labelPrinter" cssStyle="color: #ff0000;" /></td>

                                        <th>Звук / колонки</th>
                                        <td><form:input path="hardware.music" /></td>
                                        <td><form:errors path="hardware.music" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th>Принтер</th>
                                        <td><form:input path="hardware.printer" /></td>
                                        <td><form:errors path="hardware.printer" cssStyle="color: #ff0000;" /></td>

                                        <th>ИБП</th>
                                        <td><form:input path="hardware.ups" /></td>
                                        <td><form:errors path="hardware.ups" cssStyle="color: #ff0000;" /></td>
                                    </tr>




                                    <tr>
                                        <th colspan="6" class="th-header-center">Данные интернет-провайдера</th>
                                    </tr>
                                    <tr>
                                        <th>Провайдер</th>
                                        <td><form:input path="provider.name" /></td>
                                        <td><form:errors path="provider.name" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th>№ договора</th>
                                        <td><form:input path="provider.contract" /></td>
                                        <td><form:errors path="provider.contract" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th>IP адрес (внешний)</th>
                                        <td><form:input path="provider.ip" /></td>
                                        <td><form:errors path="provider.ip" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th>IP подсеть (внутренняя)</th>
                                        <td><form:input path="provider.subnet" /></td>
                                        <td><form:errors path="provider.subnet" cssStyle="color: #ff0000;" /></td>

                                        <th>Скорость подключения</th>
                                        <td><form:input path="provider.speed" /></td>
                                        <td><form:errors path="provider.speed" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th>Телефон тех. поддержки</th>
                                        <td><form:input path="provider.techPhone" /></td>
                                        <td><form:errors path="provider.techPhone" cssStyle="color: #ff0000;" /></td>

                                        <th>Телефон абон. службы</th>
                                        <td><form:input path="provider.abonPhone" /></td>
                                        <td><form:errors path="provider.abonPhone" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th>Комментарии (сеть)</th>
                                        <td colspan="4"><form:textarea path="provider.techData" cols="78" rows="8" /></td>
                                        <td><form:errors path="provider.techData" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th colspan="6" class="th-header-center">Пароли / доступ</th>
                                    </tr>
                                    <tr>
                                        <th>Ключи / лицензии</th>
                                        <td colspan="4"><form:textarea path="keys" cols="78" rows="8" /></td>
                                        <td><form:errors path="keys" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th>Пароли</th>
                                        <td colspan="4"><form:textarea path="passwords" cols="78" rows="8" /></td>
                                        <td><form:errors path="passwords" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                    <tr>
                                        <th>Тех. комментарии</th>
                                        <td colspan="4"><form:textarea path="techComment" cols="78" rows="8" /></td>
                                        <td><form:errors path="techComment" cssStyle="color: #ff0000;" /></td>
                                    </tr>
                                </tbody>
                            </table>
                            <form:hidden path="provider.id" />
                            <form:hidden path="hardware.id" />
                            <input id="save" type="submit" value="Сохранить" class="f-bu f-bu-default">
                            <input id="delete" type="button" name="delete" value="Удалить магазин" class="f-bu f-bu-warning">
                        </form:form>
                    </div>
                </div>
            </div>
            <%@include file="../modules/footer.jspf" %>

        </div>

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
                    message: "Удалть магазин ${shop.name}?"
                }).done(function () {
                    $.post("<c:url value="/admin/delShopById"/>", {id: "${shop.id}", ${_csrf.parameterName}: "${_csrf.token}"}).done(function () {
                        window.location.replace("<c:url value="/admin/shops"/>");
                    });

                });
            });
            $("#delete").mouseup(function () {
                $(this).blur();
            });

            $("#empTabs").tabs();

        });
    </script>
</body>
</html>