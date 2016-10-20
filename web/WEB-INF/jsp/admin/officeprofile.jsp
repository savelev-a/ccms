<%-- 
    Document   : officeprofile
    Created on : 08.04.2016, 13:04:14
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<ccms:page title="Администрирование - офис">
    <ccms:layout mainMenuActiveItem="admin" sideMenuSection="admin" sideMenuActiveItem="offices">
        <ccms:panel cols="10" title="Редактирование офиса - ${office.name}">
            <form:form method="post" commandName="office">
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
                            <td><b>Адрес</b></td>
                            <td><form:textarea path="address" class="form-control" /></td>
                            <td><form:errors path="address" cssStyle="color: #ff0000;" /></td>
                        </tr>

                        <tr>
                            <th colspan="6" class="th-header-center">Персонал</th>
                        </tr>
                        <tr>
                            <td><b>Администратор / сотрудники</b></td>
                            <td colspan="4">
                                <div id="empTabs" class="tabs-nobg">
                                    <ul>
                                        <li><a href="#tadmin">Администратор</a></li>
                                        <li><a href="#temps">Сотрудники</a></li>

                                        <br>
                                        <div id="tadmin">
                                            <table class="dataTablesPreparedSmall">
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
                                                                    <c:when test="${emp.id == office.director.id}">
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
                                            <table class="dataTablesPreparedSmall">
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
                                                                    <c:when test="${office.officeEmployees.contains(emp)}">
                                                                        <form:checkbox path="officeEmployees" value="${emp.id}" checked="true" />
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <form:checkbox path="officeEmployees" value="${emp.id}" />
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
                <input id="delete" type="button" name="delete" value="Удалить офис" class="btn btn-danger">
            </form:form>
        </ccms:panel>
    </ccms:layout>
                
    <div id="msgDelConfirm" title="Подтверждение удаления">
        <p>
            Удалить данный офис?
        </p>
    </div>
                
    <script type="text/javascript">
        $(document).ready(function () {

            $("#delete").on("click", function () {
                $("#msgDelConfirm").dialog("open");
            });
            
            $("#msgDelConfirm").dialog({
                autoOpen: false,
                modal: true,
                dialogClass: "no-close",
                width: 400,
                buttons: [{
                    text: "Удалить",
                    class: "btn btn-danger",
                    click: function () {
                        $.post("<c:url value="/admin/delOfficeById"/>", {"id": "${office.id}", "${_csrf.parameterName}": "${_csrf.token}"}).done(function(){
                            window.location.replace("<c:url value="/admin/offices"/>");
                        });
                        
                        
                    }
                }, {
                    text: "Отмена",
                    class: "btn btn-default",
                    click: function () {
                        $(this).dialog("close");
                    }
                }],
                show: {
                    effect: "clip",
                    duration: 200
                },
                hide: {
                    effect: "clip",
                    duration: 200
                }

            });

            $("#empTabs").tabs();
        });
    </script>
</ccms:page>