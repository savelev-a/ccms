<%-- 
    Document   : periodSelector
    Created on : 17.10.2016, 12:39:57
    Author     : Alexander Savelev
--%>

<%@tag description="Выбор периода для отчетов / установок." pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="type" required="true" rtexprvalue="true" type="java.lang.String" description="Месяц и год - month-year, год - year, произвольный период - custom(по умолчанию)"%>
<%@attribute name="url" required="true" rtexprvalue="true" type="java.lang.String" description="url для перехода после выбора"%>
<%@attribute name="months" required="false" rtexprvalue="true" type="java.util.List<String>" description="Только для month-year, список месяцев"%>
<%@attribute name="years" required="false" rtexprvalue="true" type="java.util.List<String>" description="Только для month-year или year, список годов"%>

<div class="form-inline" align="right">
    <form name="setByOrgForm" action="<c:url value="${url}" />" method="GET">
        <c:choose>
            <c:when test="${type == 'month-year'}">

                Показать данные за: 
                <select name="dateMonth" class="form-control" >
                    <c:forEach items="${months}" var="month" >
                        <option ${month == selectedMonth ? "selected" : ""} value="${month}">
                            <c:out value="${month}" />
                        </option>
                    </c:forEach>
                </select>

                <select name="dateYear" class="form-control" >
                    <c:forEach items="${years}" var="year" >
                        <option ${year == selectedYear ? "selected" : ""} value="${year}">
                            <c:out value="${year}" />
                        </option>
                    </c:forEach>
                </select>

            </c:when>
        </c:choose>
        <input type="submit" value="Загрузить" class="btn btn-primary">
    </form>
    <br>
</div>