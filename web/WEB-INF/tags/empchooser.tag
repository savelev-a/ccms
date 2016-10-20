<%-- 
    Document   : empchooser
    Created on : 16.10.2016, 9:19:18
    Author     : alchemist
--%>

<%@tag import="ru.codemine.ccms.entity.Employee"%>
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@attribute name="type" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@attribute name="targetPath" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@attribute name="items" required="true" rtexprvalue="true" type="java.util.List<Employee>"%>


<table class="dataTablesPreparedSmall">
    <thead>
        <tr>
            <th>#</th>
            <th>ФИО</th>
            <th>Должность</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${items}" var="emp" varStatus="idx">
            <tr>
                <td>
                    <c:choose>
                        
                        <c:when test="${type == 'radio'}">
                            <c:choose>
                                <c:when test="${idx.index == 0}">
                                    <form:radiobutton path="${targetPath}" value="${emp.id}" checked="true" />
                                </c:when>
                                <c:otherwise>
                                    <form:radiobutton path="${targetPath}" value="${emp.id}" />
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        
                        <c:when test="${type == 'checkbox'}">
                            <form:checkbox path="${targetPath}" value="${emp.id}" />
                        </c:when>
                    </c:choose>
                    
                </td>
                <td><c:out value="${emp.fullName}" /></td>
                <td><c:out value="${emp.position}" /></td>
            </tr>
        </c:forEach>
    </tbody>
</table>
