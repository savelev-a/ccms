<%-- 
    Document   : panel
    Created on : 15.10.2016, 23:35:38
    Author     : alchemist
--%>

<%@tag description="Default panel tag" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="title" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@attribute name="cols" required="true" rtexprvalue="true" type="java.lang.Integer"%>

<div class="col-md-${cols}">
    <div class="panel panel-primary panel-primary-dark">
        <div class="panel-heading panel-heading-dark" align="center"><c:out value="${title}" /></div>
        <div class="panel-body">
            <jsp:doBody />
        </div>
    </div>
</div>