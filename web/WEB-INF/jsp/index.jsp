<%-- 
    Document   : index
    Created on : 28.03.2016, 11:59:13
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
        <link rel="stylesheet" href="<c:url value="/res/css/jqgrid/jquery-ui.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/jqgrid/jquery-ui.theme.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/styles.css" />" >
        <title><c:out value="${title}" /></title>
    </head>

    <body>
        <div class="wrapper">
            <div class="container-fluid content">
                <%@include file="modules/header.jspf" %>

                <br>

                <div class="row">

                    <br><br>

                    <div class="col-md-9">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Добро пожаловать!</div>
                            <div class="panel-body" align="center">
                                
                            </div>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Часы</div>
                            <div class="panel-body" align="center">
                                <canvas id="canvas" width="200" height="200"></canvas>
                            </div>
                        </div>
                        
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Календарь</div>
                            <div class="panel-body" align="center">
                                <div id="datepicker" width="200" height="200"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <%@include file="modules/footer.jspf" %>
        </div>

        <script type="text/javascript" src="<c:url value="/res/js/analogclock.js" />"></script>
        <script type="text/javascript" src="<c:url value="/res/js/datepicker-ru.js" />"></script>
        <script type="text/javascript">
            $(function () {
                $("#datepicker").datepicker($.datepicker.regional["ru"]);
            });
        </script>
    </body>
</html>