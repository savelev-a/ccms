<%-- 
    Document   : addoffice
    Created on : 08.04.2016, 13:16:00
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
                <%@include file="../modules/header.jspf" %>

                <br>

                <div class="row">

                    <%@include file="../modules/sideMenu/sideMenu_admin.jspf" %>

                    <br><br>

                    <div class="col-md-10">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Добавление нового офиса</div>
                            <div class="panel-body">
                                <form:form method="post" commandName="addOfficeFrm">
                                    <table class="table table-condensed">
                                        <tbody>
                                            <tr>
                                                <td><b>Название</b></td>
                                                <td><form:input path="name" class="form-control" /></td>
                                                <td><form:errors path="name" cssStyle="color: #ff0000;" /></td>

                                                <td><b>E-mail</b></td>
                                                <td><form:input path="email" class="form-control" /></td>
                                                <td><form:errors path="email" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>Юр. лицо</b></td>
                                                <td>
                                                    <form:select path="organisation" class="form-control">
                                                        <form:options items="${orgs}" itemLabel="name" itemValue="id" />
                                                    </form:select>
                                                </td>
                                                <td><form:errors path="organisation" cssStyle="color: #ff0000;" /></td>

                                                <td><b>Телефон</b></td>
                                                <td><form:input path="phone" class="form-control" /></td>
                                                <td><form:errors path="phone" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                            <tr>
                                                <td><b>Адрес<b></td>
                                                <td><form:textarea path="address" class="form-control" /></td>
                                                <td><form:errors path="address" cssStyle="color: #ff0000;" /></td>
                                            </tr>

                                            <tr>
                                                <th colspan="6" class="th-header-center">Персонал</th>
                                            </tr>
                                            <tr>
                                                <td><b>Администратор / сотрудники<b></td>
                                                <td colspan="4">
                                                    <div id="empTabs">
                                                        <ul>
                                                            <li><a href="#tadmin">Администратор</a></li>
                                                            <li><a href="#temps">Сотрудники</a></li>

                                                            <br>
                                                            <div id="tadmin">
                                                                <table id="admChooseTab">
                                                                    <thead>
                                                                        <tr>
                                                                            <th>#</th>
                                                                            <th>ФИО</th>
                                                                            <th>Должность</th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                        <c:forEach items="${emps}" var="emp" varStatus="idx">
                                                                            <tr>
                                                                                <td>
                                                                                    <c:choose>
                                                                                        <c:when test="${idx.index == 0}">
                                                                                            <form:radiobutton path="director" value="${emp.id}" checked="true" />
                                                                                        </c:when>
                                                                                        <c:otherwise>
                                                                                            <form:radiobutton path="director" value="${emp.id}" />
                                                                                        </c:otherwise>
                                                                                    </c:choose>
                                                                                </td>
                                                                                <td><c:out value="${emp.fullName}" /></td>
                                                                                <td><c:out value="${emp.position}" /></td>
                                                                            </tr>
                                                                        </c:forEach>
                                                                    </tbody>
                                                                </table>
                                                            </div>

                                                            <div id="temps">
                                                                <table id="empsChooseTab">
                                                                    <thead>
                                                                        <tr>
                                                                            <th>#</th>
                                                                            <th>ФИО</th>
                                                                            <th>Должность</th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                        <c:forEach items="${emps}" var="emp" varStatus="idx">
                                                                            <tr>
                                                                                <td>
                                                                                    <form:checkbox path="officeEmployees" value="${emp.id}" />
                                                                                </td>
                                                                                <td><c:out value="${emp.fullName}" /></td>
                                                                                <td><c:out value="${emp.position}" /></td>
                                                                            </tr>
                                                                        </c:forEach>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </ul>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>

                                                <td><form:errors path="director" cssStyle="color: #ff0000;" /></td>

                                                <td><form:errors path="officeEmployees" cssStyle="color: #ff0000;" /></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <input id="save" type="submit" name="save" value="Сохранить" class="btn btn-primary">
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <%@include file="../modules/footer.jspf" %>
        </div>


        <!-- MessageBox & tables --> 

    <link rel="stylesheet" href="<c:url value="/res/css/jquery.dataTables.css" />" >
    <link rel="stylesheet" href="<c:url value="/res/css/messagebox.css" />" >
    <link rel="stylesheet" href="<c:url value="/res/css/jquery-ui.css" />" >
    <link rel="stylesheet" href="<c:url value="/res/css/jquery-ui.structure.css" />" >
    <link rel="stylesheet" href="<c:url value="/res/css/jquery-ui.theme.css" />" >
    <script type="text/javascript">
        $(document).ready(function () {
            $("#admChooseTab").DataTable({
                "scrollY": "200px",
                "scrollCollapse": true,
                "paging": false
            });
            $("#empsChooseTab").DataTable({
                "scrollY": "200px",
                "scrollCollapse": true,
                "paging": false
            });
            $("#empTabs").tabs();
        });
    </script>

    <!-- End MessageBox & tables-->

</body>
</html>