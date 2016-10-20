<%-- 
    Document   : counters
    Created on : 27.04.2016, 13:24:39
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<ccms:page title="Показания счетчиков - ${shop.name}">
    <ccms:layout mainMenuActiveItem="shops" sideMenuSection="shop" sideMenuActiveItem="counters">
        <ccms:panel cols="4" title="Показания счетчиков - ${shop.name} (данные)">
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
        </ccms:panel>
        
        <ccms:panel cols="6" title="Показания счетчиков - ${shop.name} (график)">
            <div id="placeholder" class="graph-placeholder">

            </div>
        </ccms:panel>
        
    </ccms:layout>

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
                        align: "center"
                    }
                },
                grid : {
                    hoverable: true
                },
                xaxis: {
                    mode: "categories",
                    rotateTicks: 45
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
</ccms:page>
