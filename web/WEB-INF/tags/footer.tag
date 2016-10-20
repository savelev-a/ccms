<%-- 
    Document   : footer
    Created on : 15.10.2016, 23:22:39
    Author     : alchemist
--%>

<%@tag description="Footer tag" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="footer">
    Alexander Savelev, 2016
</div>

<script src="<c:url value="/res/js/jquery-2.2.2.min.js" /> "></script>
<script src="<c:url value="/res/js/messagebox.js" /> "></script>
<script src="<c:url value="/res/js/jquery.dataTables.js" /> "></script>
<script src="<c:url value="/res/js/jquery-ui.min.js" />"></script>
<script src="<c:url value="/res/js/bootstrap.js" />"></script>

<sec:authorize access="isAuthenticated()" var="loggedIn" />
<c:choose>
    <c:when test="${loggedIn}">
        <script type="text/javascript">
            $(document).ready(function () {
                $("#logout").on("click", function () {
                    $.post("<c:url value="/logout"/>", {${_csrf.parameterName}: "${_csrf.token}"}).done(function () {
                        window.location.replace("<c:url value="/"/>");
                    });
                });
                
                $(".dataTablesPreparedSmall").DataTable({
                    "scrollY": "200px",
                    "scrollCollapse": true,
                    "paging": false
                });
                
                $(".dataTablesPreparedBig").DataTable();
            });
        </script>
    </c:when>
</c:choose>