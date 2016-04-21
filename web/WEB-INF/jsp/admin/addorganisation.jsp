<%-- 
    Document   : addorganisation
    Created on : 30.03.2016, 20:49:19
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
        <link rel="stylesheet" href="<c:url value="/res/css/framework.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/styles.css" />" >
        <title>${currentPage.title} - ИнфоПортал</title>
    </head>

    <body>
        <div class="wrapper">
            <div class="g content">
                <%@include file="../modules/header.jspf" %>

                <br>

                <div class="g-row">

                    <%@include file="../modules/sideMenu.jspf" %>

                    <div class="g-10">
                        <form:form method="post" commandName="addOrganisationFrm">
                            <table>
                                <caption>Добавление новой организации</caption>
                                <tbody>
                                    <tr>
                                        <th>Наименование</th>
                                        <td><form:input path="name" /></td>
                                        <td><form:errors path="name" cssStyle="color: #ff0000;" /></td>
                                        <td> </td>
                                        <td> </td>
                                    </tr>
                                    <tr>
                                        <th>ИНН</th>
                                        <td><form:input path="inn" maxlength="12" /></td> 

                                        <th>КПП</th>
                                        <td><form:input path="kpp" maxlength="12" /></td>

                                        <td>
                                            <form:errors path="kpp" cssStyle="color: #ff0000;" />
                                            <form:errors path="inn" cssStyle="color: #ff0000;" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>ОГРН</th>
                                        <td><form:input path="ogrn" maxlength="15" /></td>
                                        <td><form:errors path="ogrn" cssStyle="color: #ff0000;" /></td>
                                        <td> </td>
                                        <td> </td>
                                    </tr>
                                    <tr>
                                        <th>Расчетный счет</th>
                                        <td><form:input path="chAccount" maxlength="20" /></td>

                                        <th>Корр. счет</th>
                                        <td><form:input path="coAccount" maxlength="20" /></td>
                                        <td>
                                            <form:errors path="coAccount" cssStyle="color: #ff0000;" />
                                            <form:errors path="chAccount" cssStyle="color: #ff0000;" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Банк</th>
                                        <td><form:input path="bank" /></td>
                                        <td><form:errors path="bank" cssStyle="color: #ff0000;" /></td>
                                        <td> </td>
                                        <td> </td>
                                    </tr>
                                    <tr>
                                        <th>БИК</th>
                                        <td><form:input path="bik" maxlength="9" /></td>
                                        <td><form:errors path="bik" cssStyle="color: #ff0000;" /></td>
                                        <td> </td>
                                        <td> </td>
                                    </tr>
                                    <tr>
                                        <th>Юридический адрес</th>
                                        <td><form:textarea path="urAddress" /></td>

                                        <th>Почтовый адрес</th>
                                        <td><form:textarea path="mailAddress" /></td>
                                        <td>
                                            <form:errors path="mailAddress" cssStyle="color: #ff0000;" />
                                            <form:errors path="urAddress" cssStyle="color: #ff0000;" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Телефон</th>
                                        <td><form:input path="phone" /></td>
                                        <td><form:errors path="phone" cssStyle="color: #ff0000;" /></td>
                                        <td> </td>
                                        <td> </td>
                                    </tr>
                                    <tr>
                                        <th>Директор</th>
                                        <td colspan="3">
                                            <div>
                                                <table id="dirChooseTab">
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
                                                                <td>${emp.lastName} ${emp.firstName}</td>
                                                                <td>${emp.position}</td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </td>
                                        <td><form:errors path="director" cssStyle="color: #ff0000;" /></td>

                                    </tr>

                                </tbody>
                            </table>
                            <input type="submit" value="Добавить" class="f-bu f-bu-default">
                        </form:form>
                    </div>
                </div>
            </div>
            <%@include file="../modules/footer.jspf" %>
        </div>

    <link rel="stylesheet" href="<c:url value="/res/css/jquery.dataTables.css" />" >
    <script type="text/javascript">
        $(document).ready(function () {
            $("#dirChooseTab").DataTable({
                "scrollY": "200px",
                "scrollCollapse": true,
                "paging": false
            });
        });
    </script>

</body>
</html>