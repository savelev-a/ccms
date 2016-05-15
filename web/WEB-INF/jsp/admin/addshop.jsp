<%-- 
    Document   : addshop
    Created on : 31.03.2016, 20:25:08
    Author     : Alexander Savelev
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

                    <div class="col-md-10">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Добавление нового магазина</div>
                            <div class="panel-body">
                                <form:form method="post" commandName="addShopFrm">
                                    <table class="table table-condensed">
                                        <tbody>
                                            <tr>
                                                <th colspan="6" class="th-header-center">Основные данные</th>
                                            </tr>
                                            <tr>
                                                <td><b>Наименование</b></td>
                                                <td><form:input path="name" class="form-control" /></td>
                                                <td><form:errors path="name" cssStyle="color: #ff0000;" /></td>

                                                <td><b>E-mail</b></td>
                                                <td><form:input path="email" class="form-control" /></td>
                                                <td><form:errors path="email" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>Юр. лицо</b></td>
                                                <td>
                                                    <form:select path="organisation" class="form-control">
                                                        <form:options items="${orgs}" itemLabel="name" itemValue="id" />
                                                    </form:select>
                                                </td>
                                                <td><form:errors path="organisation" cssStyle="color: #ff0000;" /></td>

                                                <td><b>Время работы</b></td>
                                                <td><form:input path="workingTime" class="form-control" /></td>
                                                <td><form:errors path="workingTime" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>Телефон</b></td>
                                                <td><form:input path="phone" class="form-control" /></td>
                                                <td><form:errors path="phone" cssStyle="color: #ff0000;" /></td>

                                                <td><b>ICQ</b></td>
                                                <td><form:input path="icq" class="form-control" /></td>
                                                <td><form:errors path="icq" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>Адрес</b></td>
                                                <td><form:textarea path="address" class="form-control" /></td>
                                                <td><form:errors path="address" cssStyle="color: #ff0000;" /></td>

                                                <td><b>Комментарий</b></td>
                                                <td><form:textarea path="comment" class="form-control" /></td>
                                                <td><form:errors path="comment" cssStyle="color: #ff0000;" /></td>
                                            </tr>

                                            <tr>
                                                <td><b>Арендодатель</b></td>
                                                <td><form:textarea path="landlord" class="form-control" /></td>
                                                <td><form:errors path="landlord" cssStyle="color: #ff0000;" /></td>

                                                <td><b>Магазин закрыт</b></td>
                                                <td><form:checkbox path="closed" /><i> (Выберите, если требуется создать закрытый магазин)</i></td>
                                                <td></td>
                                            </tr>


                                            <tr>
                                                <th colspan="6" class="th-header-center">Персонал</th>
                                            </tr>
                                            <tr>
                                                <td><b>Администратор / сотрудники</b></td>
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
                                                                                        <c:when test="${idx.index == 0}">
                                                                                            <form:radiobutton path="shopAdmin" value="${emp.id}" checked="true" />
                                                                                        </c:when>
                                                                                        <c:otherwise>
                                                                                            <form:radiobutton path="shopAdmin" value="${emp.id}" />
                                                                                        </c:otherwise>
                                                                                    </c:choose>
                                                                                </td>
                                                                                <td><c:out value="${emp.fullName}" /></td>
                                                                                <td><c:out value="${emp.position}" /></td>
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
                                                                                    <form:checkbox path="shopEmployees" value="${emp.id}" />
                                                                                </td>
                                                                                <td><c:out value="${emp.fullName}" /></td>
                                                                                <td><c:out value="${emp.position}" /></td>
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
                                                <td><form:errors path="shopEmployees" cssStyle="color: #ff0000;" /></td>
                                                <td><form:errors path="shopAdmin" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <th colspan="6" class="th-header-center">Оборудование и софт</th>
                                            </tr>
                                            <tr>
                                                <td><b>Системный блок</b></td>
                                                <td><form:textarea path="hardware.pc" class="form-control" /></td>
                                                <td><form:errors path="hardware.pc" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>Операционная система</b></td>
                                                <td><form:input path="hardware.os" class="form-control" /></td>
                                                <td><form:errors path="hardware.os" cssStyle="color: #ff0000;" /></td>

                                                <td><b>Детектор валют</b></td>
                                                <td><form:input path="hardware.detektor" class="form-control" /></td>
                                                <td><form:errors path="hardware.detektor" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>ККМ</b></td>
                                                <td><form:input path="hardware.kkm" class="form-control" /></td>
                                                <td><form:errors path="hardware.kkm" cssStyle="color: #ff0000;" /></td>

                                                <td><b>Банковский терминал</b></td>
                                                <td><form:input path="hardware.bankTerm" class="form-control" /></td>
                                                <td><form:errors path="hardware.bankTerm" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>Сканер ШК</b></td>
                                                <td><form:input path="hardware.scanner" class="form-control" /></td>
                                                <td><form:errors path="hardware.scanner" cssStyle="color: #ff0000;" /></td>

                                                <td><b>Роутер</b></td>
                                                <td><form:input path="hardware.router" class="form-control" /></td>
                                                <td><form:errors path="hardware.router" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>Принтер этикеток</b></td>
                                                <td><form:input path="hardware.labelPrinter" class="form-control" /></td>
                                                <td><form:errors path="hardware.labelPrinter" cssStyle="color: #ff0000;" /></td>

                                                <td><b>Звук / колонки</b></td>
                                                <td><form:input path="hardware.music" class="form-control" /></td>
                                                <td><form:errors path="hardware.music" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>Принтер</b></td>
                                                <td><form:input path="hardware.printer" class="form-control" /></td>
                                                <td><form:errors path="hardware.printer" cssStyle="color: #ff0000;" /></td>

                                                <td><b>ИБП</b></td>
                                                <td><form:input path="hardware.ups" class="form-control" /></td>
                                                <td><form:errors path="hardware.ups" cssStyle="color: #ff0000;" /></td>
                                            </tr>

                                            <tr>
                                                <td><b>Включить счетчик посетителей</b></td>
                                                <td><form:checkbox path="countersEnabled" class="form-control" /></td>
                                                <td><form:errors path="countersEnabled" cssStyle="color: #ff0000;" /></td>
                                                
                                                <td><b>Имя в Домино</b></td>
                                                <td><form:input path="dominoName" class="form-control" /></td>
                                                <td><form:errors path="dominoName" cssStyle="color: #ff0000;" /></td>
                                            </tr>



                                            <tr>
                                                <th colspan="6" class="th-header-center">Данные интернет-провайдера</th>
                                            </tr>
                                            <tr>
                                                <td><b>Провайдер</b></td>
                                                <td><form:input path="provider.name" class="form-control" /></td>
                                                <td><form:errors path="provider.name" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>№ договора</b></td>
                                                <td><form:input path="provider.contract" class="form-control" /></td>
                                                <td><form:errors path="provider.contract" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>IP адрес (внешний)</b></td>
                                                <td><form:input path="provider.ip" class="form-control" /></td>
                                                <td><form:errors path="provider.ip" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>IP подсеть (внутренняя)</b></td>
                                                <td><form:input path="provider.subnet" class="form-control" /></td>
                                                <td><form:errors path="provider.subnet" cssStyle="color: #ff0000;" /></td>

                                                <td><b>Скорость подключения</b></td>
                                                <td><form:input path="provider.speed" class="form-control" /></td>
                                                <td><form:errors path="provider.speed" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>Телефон тех. поддержки</b></td>
                                                <td><form:input path="provider.techPhone" class="form-control" /></td>
                                                <td><form:errors path="provider.techPhone" cssStyle="color: #ff0000;" /></td>

                                                <td><b>Телефон абон. службы</b></td>
                                                <td><form:input path="provider.abonPhone" class="form-control" /></td>
                                                <td><form:errors path="provider.abonPhone" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>Комментарии (сеть)</b></td>
                                                <td colspan="4"><form:textarea path="provider.techData" class="form-control" cols="78" rows="8" /></td>
                                                <td><form:errors path="provider.techData" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <th colspan="6" class="th-header-center">Пароли / доступ</th>
                                            </tr>
                                            <tr>
                                                <td><b>Ключи / лицензии</b></td>
                                                <td colspan="4"><form:textarea path="keys" class="form-control" cols="78" rows="8" /></td>
                                                <td><form:errors path="keys" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>Пароли</b></td>
                                                <td colspan="4"><form:textarea path="passwords" class="form-control" cols="78" rows="8" /></td>
                                                <td><form:errors path="passwords" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>Тех. комментарии</b></td>
                                                <td colspan="4"><form:textarea path="techComment" class="form-control" cols="78" rows="8" /></td>
                                                <td><form:errors path="techComment" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <input type="submit" value="Добавить" class="btn btn-primary">
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%@include file="../modules/footer.jspf" %>

        </div>

    <link rel="stylesheet" href="<c:url value="/res/css/jquery.dataTables.css" />" >
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
            $("#empTabs").tabs();
        });
    </script>
</body>
</html>