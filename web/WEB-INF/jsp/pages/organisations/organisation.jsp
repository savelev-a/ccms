<%-- 
    Document   : organisation
    Created on : 12.04.2016, 14:44:29
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

                    <div class="g-7">
                        <table>
                            <caption>Реквизиты организации "${organisation.name}"</caption>
                            <tbody>
                                <tr>
                                    <td>Наименование: </td>
                                    <td>${organisation.name}</td>

                                </tr>
                                <tr>
                                    <td>ИНН</td><td>${organisation.inn}</td>
                                </tr>
                                <tr>
                                    <td>КПП</td><td>${organisation.kpp}</td>
                                </tr>
                                <tr>
                                    <td>ОГРН</td>
                                    <td>${organisation.ogrn}</td>

                                </tr>
                                <tr>
                                    <td>Расчетный счет</td><td>${organisation.chAccount}</td>
                                </tr>
                                <tr>
                                    <td>Корр. счет</td><td>${organisation.coAccount}</td>
                                </tr>
                                <tr>
                                    <td>Банк</td>
                                    <td>${organisation.bank}</td>

                                </tr>
                                <tr>
                                    <td>БИК</td>
                                    <td>${organisation.bik}</td>

                                </tr>
                                <tr>
                                    <td>Юридический адрес</td><td>${organisation.urAddress}</td>
                                </tr>
                                <tr>
                                    <td>Почтовый адрес</td><td>${organisation.mailAddress}</td>
                                </tr>
                                <tr>
                                    <td>Телефон</td>
                                    <td>${organisation.phone}</td>

                                </tr>
                                <tr>
                                    <td>Директор</td>
                                    <td>
                                        <a href="<c:url value="/employee?id=${organisation.director.id}" />" >
                                            ${organisation.director.lastName} 
                                            ${organisation.director.firstName} 
                                            ${organisation.director.middleName} 
                                        </a>
                                    </td>

                                </tr>
                            </tbody>
                        </table>
                        <br>
                        <table>
                            <caption>Магазины, связанные с этим юр. лицом</caption>
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Наименование</th>
                                    <th>Адрес</th>
                                    <th>Телефон</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${organisation.shops}" var="shop" varStatus="idx">
                                    <tr>
                                        <td>${idx.count}</td>
                                        <td>
                                            <a href="<c:url value="/shop?id=${shop.id}" />">${shop.name}</a>
                                        </td>
                                        <td>${shop.address}</td>
                                        <td><span class="glyphicon glyphicon-phone-alt"></span> ${shop.phone}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <br>

                        <span class="glyphicon glyphicon-cog"></span>
                        <a href="<c:url value="/admin/orgprofile?id=${organisation.id}" />" > Редактировать организацию </a>
                        &nbsp;
                        <span class="glyphicon glyphicon-print"></span>
                        <a href="#">Распечатать реквизиты</a>


                    </div>
                </div>
            </div>
            <br>
            <%@include file="../../modules/footer.jspf" %>
        </div>
    </body>
</html>
