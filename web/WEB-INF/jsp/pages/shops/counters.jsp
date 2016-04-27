<%-- 
    Document   : counters
    Created on : 27.04.2016, 13:24:39
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
                <%@include file="../../modules/header.jspf" %>

                <br>

                <div class="row">

                    <%@include file="../../modules/sideMenu/sideMenu_shop.jspf" %>

                    <br><br>

                    <div class="col-md-4">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Показания счетчиков - <c:out value="${shop.name}" /> (данные)</div>
                            <div class="panel-body">

                                <table class="table table-hover table-condensed" id="countersTable">
                                    <thead>
                                        <tr>
                                            <th>Дата</th>
                                            <th>Входов</th>
                                            <th>Выходов</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${counters}" var="counter">
                                            <tr>
                                                <td><c:out value="${counter.date.toString('dd.MM.yyyy')}" /></td>
                                                <td><c:out value="${counter.in}" /></td>
                                                <td><c:out value="${counter.out}" /></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <form action="<c:url value='/counters' />" method="POST">
                                        <input type="hidden" name="shopid" value="<c:out value="${shop.id}" />" >
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                        <input type="submit" value="Обновить вручную" class="btn btn-primary">
                                    </form>
                                </sec:authorize>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Показания счетчиков - <c:out value="${shop.name}" /> (график)</div>
                            <div class="panel-body">
                                <div id="placeholder" class="graph-placeholder">

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <%@include file="../../modules/footer.jspf" %>
        </div>

        <script type="text/javascript" src="<c:url value="/res/js/jquery.flot.min.js" />"></script>
        <script type="text/javascript" src="<c:url value="/res/js/jquery.flot.categories.min.js" />"></script>
        <script type="text/javascript" src="<c:url value="/res/js/jquery.flot.tickrotor.js" />"></script>
        <script type="text/javascript">
            $(function () {

                var all_data = [
                    {data: ${graphDataIn}, label: "Входы"},
                    {data: ${graphDataOut}, label: "Выходы"}
                ];



                var plot_conf = {
                    
                    series: {
                        bars: {
                            show: true,
                            barWidth: 0.6,
                            align: "center",
                        }
                    },
                    grid : {
                        hoverable: true,
                    },
                    xaxis: {
                        mode: "categories",
                        rotateTicks: 45,
                    }
                };

                $.plot("#placeholder", all_data, plot_conf);
                
                
                $("<div id='tooltip'></div>").css({
			position: "absolute",
			display: "none",
			border: "1px solid #fdd",
			padding: "2px",
			"background-color": "#fee",
			opacity: 0.80
		}).appendTo("body");
                
                $("#placeholder").bind("plothover", function(event, pos, item){
                    if(item) {
                        var v = item.datapoint[1];
                        $("#tooltip").html(item.series.label + ": " + v)
						.css({top: item.pageY+5, left: item.pageX+5})
						.fadeIn(200);
                    } else {
                        $("#tooltip").hide();
                    }
                });

            });
        </script>


    </body>
</html>
