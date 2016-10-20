<%-- 
    Document   : addoffice
    Created on : 08.04.2016, 13:16:00
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<ccms:page title="Добавление офиса">
    <ccms:layout mainMenuActiveItem="admin" sideMenuSection="admin" sideMenuActiveItem="offices">
        <ccms:panel cols="10" title="Добавление офиса">
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
                                                <ccms:empchooser items="${emps}" type="radio" targetPath="director"/>
                                            </div>

                                            <div id="temps">
                                                <ccms:empchooser items="${emps}" type="checkbox" targetPath="officeEmployees"/>
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
        </ccms:panel>
    </ccms:layout>

    <script type="text/javascript">
        $(document).ready(function () {
            $("#empTabs").tabs();
        });
    </script>

</ccms:page>