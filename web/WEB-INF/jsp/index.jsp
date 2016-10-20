<%-- 
    Document   : index
    Created on : 28.03.2016, 11:59:13
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<ccms:page title="Главная" >
    <div class="wrapper">
        <div class="container-fluid content">
            <ccms:mainMenu mainMenuActiveItem="" />

            <br>

            <div class="row">

                <br><br>

                <ccms:panel cols="9" title="Добро пожаловать!">
                    <c:choose>
                        <c:when test="${currentUser != null}">
                            Добро пожаловать на интранет-портал компании <c:out value="${companyName}" />.
                            <br>
                            Текущий пользователь: 
                            <ccms:hyperlink target="${currentUser}" />
                            <br>
                            Здесь вы можете получить подробную информацию по каждой 
                            <a href="<c:url value="/shops" />">торговой точке</a>
                            компании, ее 
                            <a href="<c:url value="/organisations" />">юридическим лицам</a>
                            и 
                            <a href="<c:url value="/employees" />">сотрудникам</a>.
                            <br><br>
                            Также вы можете посмотреть 
                            <a href="<c:url value="/reports/sales-pass" />">сводный отчет по проходимости и выручке</a>
                            , построить 
                            <a href="<c:url value="/reports/graph/sales-pass" />">графики</a>
                            и задать <a href="<c:url value="/management/setPlan" />">план выручек</a>.
                            <br><br>
                            <c:if test="${currentUserActiveTasksCount > 0}">
                                <c:out value="${currentUser.firstName} ${currentUser.middleName}" />, у вас <c:out value="${currentUserActiveTasksCount}" /> активных задач. Нажмите 
                                <a href="<c:url value="/tasks/mytasks" />">сюда</a> 
                                чтобы просмотреть их. 
                            </c:if>

                        </c:when>
                        <c:otherwise>
                            Вы зашли на внутренний портал компании <c:out value="${companyName}" />.
                            <br><br>
                            Пожалуйста, <a href="<c:url value="/login" />">авторизируйтесь</a>  чтобы получить доступ к информации
                        </c:otherwise>
                    </c:choose>
                </ccms:panel>

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

        <ccms:footer />
    </div>

    <script type="text/javascript" src="<c:url value="/res/js/analogclock.js" />"></script>
    <script type="text/javascript" src="<c:url value="/res/js/datepicker-ru.js" />"></script>
    <script type="text/javascript">
        $(function () {
            $("#datepicker").datepicker($.datepicker.regional["ru"]);
        });
    </script>
</ccms:page>