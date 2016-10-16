<%-- 
    Document   : layout
    Created on : 15.10.2016, 23:26:33
    Author     : alchemist
--%>

<%@tag description="Main layout tag" pageEncoding="UTF-8"%>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>

<%@attribute name="mainMenuActiveItem" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@attribute name="sideMenuSection" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@attribute name="sideMenuActiveItem" required="true" rtexprvalue="true" type="java.lang.String"%>

<div class="wrapper">
    <div class="container-fluid content">
        <ccms:mainMenu mainMenuActiveItem="${mainMenuActiveItem}" />
        <br>
        <div class="row">
            <ccms:sideMenu section="${sideMenuSection}" selectedItem="${sideMenuActiveItem}" />
            <br><br>

            <jsp:doBody />
            
        </div>
    </div>
    <ccms:footer />
</div>