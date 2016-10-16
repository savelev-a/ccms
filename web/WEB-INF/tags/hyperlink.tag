<%-- 
    Document   : hyperlink
    Created on : 15.10.2016, 23:02:47
    Author     : alchemist
--%>

<%@tag description="Hyperlink tag" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="target" required="true" rtexprvalue="true" type="ru.codemine.ccms.entity.interfaces.Hyperlinkable"%>
<%@attribute name="type" required="false" rtexprvalue="true" %>

<c:choose>
    <c:when test="${type == 'admin'}">
        <a href="<c:url value="${target.linkAdminTarget}" />">
            <c:out value="${target.linkCaption}" />
        </a>
    </c:when>
    <c:otherwise>
        <a href="<c:url value="${target.linkTarget}" />">
            <c:out value="${target.linkCaption}" />
        </a>
    </c:otherwise>
</c:choose>

