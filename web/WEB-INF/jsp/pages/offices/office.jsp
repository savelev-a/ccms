<%-- 
    Document   : office
    Created on : 15.04.2016, 15:11:52
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
        <title>${title} - ИнфоПортал</title>
    </head>

    <body>
        <div class="wrapper">
            <div class="g content">
                <%@include file="../../modules/header.jspf" %>

                <br>

                <div class="g-row">

                    <%@include file="../../modules/sideMenu/sideMenu_dummy.jspf" %>

                    <br>

                    <div class="g-10">
                        <table>
                            <caption>Офис - ${office.name}</caption>
                            <tbody>
                                <tr>
                                    <th class="th-header-center" colspan="4">Общие данные</th>
                                </tr>
                                <tr>
                                    <th>Наименование</th>
                                    <td colspan="4">${office.name}</td>
                                </tr>
                                <tr>
                                    <th>Юр. лицо</th>
                                    <td>
                                        <a href="<c:url value="/organisation?id=${office.organisation.id}" />">
                                            ${office.organisation.name}
                                        </a>
                                    </td>

                                    <th>E-mail</th>
                                    <td>
                                        <span class="glyphicon glyphicon-envelope"></span>
                                        <a href="mailto:${office.email}">
                                            ${office.email}
                                        </a>
                                    </td>
                                </tr>

                                <tr>
                                    <th>Адрес</th>
                                    <td>${office.address}</td>

                                    <th>Телефон</th>
                                    <td>
                                        <span class="glyphicon glyphicon-phone-alt"></span>
                                        ${office.phone}
                                    </td>
                                </tr>
                                <tr>
                                    <th class="th-header-center" colspan="4">Персонал</th>
                                </tr>
                                <tr>
                                    <th>Администратор</th>
                                    <td>
                                        <a href="<c:url value="/employee?id=${office.director.id}" />">
                                            ${office.director.fullName}
                                        </a>
                                    </td>

                                    <th>Сотрудники</th>
                                    <td>
                                        <c:forEach items="${office.officeEmployees}" var="employee">
                                            <a href="<c:url value="/employee?id=${employee.id}" />">
                                                ${employee.fullName}
                                            </a>
                                            &nbsp;
                                            <c:if test="${employee.position != ''}"> (${employee.position}) </c:if>
                                                <br>
                                        </c:forEach>
                                    </td>
                                </tr>


                            </tbody>
                        </table>
                        <br>

                        <span class="glyphicon glyphicon-cog"></span>
                        <a href="<c:url value="/admin/officeprofile?id=${employee.id}" />" > Редактировать офис </a>
                        &nbsp;
                        <span class="glyphicon glyphicon-print"></span>
                        <a href="#">Распечатать</a>
                    </div>
                </div>
            </div>
            <br>
            <%@include file="../../modules/footer.jspf" %>
        </div>
    </body>
</html>

