<%-- 
    Document   : shop
    Created on : 15.04.2016, 13:18:32
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <%@include file="../../modules/header.jspf" %>

                <br>

                <div class="g-row">

                    <%@include file="../../modules/sideMenu.jspf" %>

                    <br>

                    <div class="g-10">
                        <table>
                            <caption>Информация по магазину "${shop.name}"</caption>
                            <tbody>
                                <tr>
                                    <th class="th-header-center" colspan="4"><u>Общие данные</u></th>
                                </tr>
                                <tr>
                                    <th>Наименование</th>
                                    <td>${shop.name}</td>
                                    
                                    <th>E-mail</th>
                                    <td>
                                        <span class="glyphicon glyphicon-envelope"></span>
                                        <a href="mailto:${shop.email}">
                                            ${shop.email}
                                        </a>
                                    </td>
                                </tr>
                                <tr>
                                    <th>Юр. лицо</th>
                                    <td>
                                        <a href="<c:url value="/organisation?id=${shop.organisation.id}" />">
                                        ${shop.organisation.name}
                                        </a>
                                    </td>
                                
                                    <th>Время работы</th>
                                    <td>
                                            ${shop.workingTime}
                                    </td>
                                </tr>
                                <tr>
                                    <th>Адрес</th>
                                    <td>${shop.address}</td>
                                    
                                    <th>ICQ</th>
                                    <td>${shop.icq}</td>
                                </tr>
                                
                                <tr>
                                    <th>Телефон</th>
                                    <td>
                                        <span class="glyphicon glyphicon-phone-alt"></span>
                                        ${shop.phone}
                                    </td>
                                
                                    <th>Арендодатель</th>
                                    <td>${shop.landlord}</td>
                                </tr>
                                    
                                <tr>
                                    <th class="th-header-center" colspan="4"><u>Персонал</u></th>
                                </tr>
                                <tr>
                                    <th>Администратор</th>
                                    <td>
                                        <a href="<c:url value="/employee?id=${shop.shopAdmin.id}" />">
                                            ${shop.shopAdmin.fullName}
                                        </a>
                                    </td>
                                
                                    <th>Сотрудники</th>
                                    <td>
                                        <c:forEach items="${shop.shopEmployees}" var="employee">
                                            <a href="<c:url value="/employee?id=${employee.id}" />">
                                                ${employee.fullName}
                                            </a>
                                            <br>
                                        </c:forEach>
                                    </td>
                                </tr>
                                
                                
                                <tr>
                                    <th>Комментарии</th>
                                    <td colspan="3">${shop.comment}</td>
                                </tr>
                                
                                <tr>
                                    <th class="th-header-center" colspan="4"><u>Оборудование</u></th>
                                </tr>
                                <tr>
                                    <th>Компьютер</th>
                                    <td colspan="3">${shop.hardware.pc}</td>
                                </tr>
                                <tr>
                                    <th>Операционная система</th>
                                    <td>${shop.hardware.os}</td>
                                
                                    <th>Банковский терминал</th>
                                    <td>${shop.hardware.bankTerm}</td>
                                </tr>
                                <tr>
                                    <th>ККМ</th>
                                    <td>${shop.hardware.kkm}</td>
                                
                                    <th>Детектор валют</th>
                                    <td>${shop.hardware.detektor}</td>
                                </tr>
                                <tr>
                                    <th>Сканер ШК</th>
                                    <td>${shop.hardware.scanner}</td>
                                
                                    <th>Роутер</th>
                                    <td>${shop.hardware.router}</td>
                                </tr>
                                <tr>
                                    <th>Принтер этикеток</th>
                                    <td>${shop.hardware.labelPrinter}</td>
                                
                                    <th>Звук / колонки</th>
                                    <td>${shop.hardware.music}</td>
                                </tr>
                                
                                <tr>
                                    <th>Принтер</th>
                                    <td>${shop.hardware.printer}</td>

                                    <th>ИБП</th>
                                    <td>${shop.hardware.ups}</td>
                                </tr>
                                
                                <tr>
                                    <th class="th-header-center" colspan="4"><u>Данные интернет-провайдера</u></th>
                                </tr>
                                <tr>
                                    <th>Провайдер</th>
                                    <td>${shop.provider.name}</td>
                                </tr>
                                <tr>
                                    <th>№ договора</th>
                                    <td>${shop.provider.contract}</td>
                                </tr>
                                <tr>
                                    <th>IP адрес (внешний)</th>
                                    <td>${shop.provider.ip}</td>
                                </tr>
                                <tr>
                                    <th>IP подсеть (внутренняя)</th>
                                    <td>${shop.provider.subnet}</td>

                                    <th>Скорость подключения</th>
                                    <td>${shop.provider.speed}</td>
                                </tr>
                                <tr>
                                    <th>Телефон тех. поддержки</th>
                                    <td>${shop.provider.techPhone}</td>

                                    <th>Телефон абон. службы</th>
                                    <td>${shop.provider.abonPhone}</td>
                                </tr>
                                <tr>
                                    <th>Комментарии (сеть)</th>
                                    <td>${shop.provider.techData}</td>
                                </tr>
                                
                                <tr>
                                    <th class="th-header-center" colspan="4"><u>Пароли / доступ</u></th>
                                </tr>
                                <tr>
                                    <th>Ключи / лицензии</th>
                                    <td colspan="3">${shop.keys}</td>
                                </tr>
                                <tr>
                                    <th>Пароли</th>
                                    <td colspan="3">${shop.passwords}</td>
                                </tr>
                                <tr>
                                    <th>Тех. комментарии</th>
                                    <td colspan="3">${shop.techComment}</td>
                                </tr>
                                
                            </tbody>
                        </table>
                        <br>
                        

                        <span class="glyphicon glyphicon-cog"></span>
                        <a href="<c:url value="/admin/shopprofile?id=${shop.id}" />" > Редактировать магазин </a>
                        &nbsp;
                        <span class="glyphicon glyphicon-print"></span>
                        <a href="#">Распечатать информацию</a>


                    </div>
                </div>
            </div>
            <br>
            <%@include file="../../modules/footer.jspf" %>
        </div>
    </body>
</html>
