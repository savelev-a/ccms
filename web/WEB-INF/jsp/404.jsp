<%-- 
    Document   : 404
    Created on : 18.04.2016, 16:21:07
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <div class="container-fluid">
            <%@include file="modules/header.jspf" %>

            <br>
            <div id="login-box" class="panel panel-info">
                <h3>К сожалению, запрошенной страницы нет на сервере</h3>
                <br>
                <h4>Если вы считаете что этого не может быть, просьба написать администратору сайта, предоставив ему следущую инфу:</h4>
                <h4>404 at ${requestScope['javax.servlet.forward.request_uri']}</h4>

            </div>

            <%@include file="modules/footer.jspf" %>
        </div>
    </body>
</html>
