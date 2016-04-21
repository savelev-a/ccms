<%-- 
    Document   : employee
    Created on : 15.04.2016, 14:51:38
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
                            <caption>Профиль сотрудника ${employee.fullName}</caption>
                            <tbody>
                                <tr>
                                    <th>Фамилия</th>
                                    <td>${employee.lastName}</td>
                                </tr>
                                <tr>
                                    <th>Имя</th>
                                    <td>${employee.firstName}</td>
                                </tr>
                                <tr>
                                    <th>Отчество</th>
                                    <td>${employee.middleName}</td>
                                </tr>
                                <tr>
                                    <th>E-mail</th>
                                    <td>
                                        <a herf="mailto:${employee.email}">
                                            ${employee.email}
                                        </a>
                                    </td>
                                </tr>
                                <tr>
                                    <th>Телефон</th>
                                    <td>
                                        <span class="glyphicon glyphicon-phone-alt"></span>
                                        ${employee.phone}
                                    </td>
                                </tr>
                                <tr>
                                    <th>Должность</th>
                                    <td>${employee.position}</td>
                                </tr>
                            </tbody>
                        </table>
                        <br>

                        <span class="glyphicon glyphicon-cog"></span>
                        <a href="<c:url value="/admin/profile?id=${employee.id}" />" > Редактировать сотрудника </a>
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

