<%-- 
    Document   : page
    Created on : 15.10.2016, 23:47:25
    Author     : alchemist
--%>

<%@tag description="Page tag" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="title" required="true" rtexprvalue="true" type="java.lang.String"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<c:url value="/res/css/bootstrap.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/bootstrap-theme.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/jquery-ui-timepicker-addon.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/jqgrid/jquery-ui.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/jqgrid/jquery-ui.structure.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/jqgrid/jquery-ui.theme.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/jqgrid/ui.jqgrid.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/jquery.dataTables.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/styles.css" />" >

        <title><c:out value="${title}" /> - ИнфоПортал</title>
    </head>

    <body>
        <jsp:doBody />
    </body>
</html>