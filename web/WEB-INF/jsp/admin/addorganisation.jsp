<%-- 
    Document   : addorganisation
    Created on : 30.03.2016, 20:49:19
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<ccms:page title="Добавление организации">
    <ccms:layout mainMenuActiveItem="admin" sideMenuSection="admin" sideMenuActiveItem="orgs">
        <ccms:panel cols="10" title="Добавление организации">

            <form:form method="post" commandName="addOrganisationFrm">

                <table class="table table-condensed">
                    <tbody>
                        <tr>
                            <td><b>Наименование</b></td>
                            <td><form:input path="name" class="form-control" /></td>
                            <td><form:errors path="name" cssStyle="color: #ff0000;" /></td>
                            <td> </td>
                            <td> </td>
                        </tr>
                        <tr>
                            <td><b>ИНН</b></td>
                            <td><form:input path="inn" maxlength="12" class="form-control" /></td> 

                            <td><b>КПП</b></td>
                            <td><form:input path="kpp" maxlength="12" class="form-control" /></td>

                            <td>
                                <form:errors path="kpp" cssStyle="color: #ff0000;" />
                                <form:errors path="inn" cssStyle="color: #ff0000;" />
                            </td>
                        </tr>
                        <tr>
                            <td><b>ОГРН</b></td>
                            <td><form:input path="ogrn" maxlength="15" class="form-control" /></td>
                            <td><form:errors path="ogrn" cssStyle="color: #ff0000;" /></td>
                            <td> </td>
                            <td> </td>
                        </tr>
                        <tr>
                            <td><b>Расчетный счет</b></td>
                            <td><form:input path="chAccount" maxlength="20" class="form-control" /></td>

                            <td><b>Корр. счет</b></td>
                            <td><form:input path="coAccount" maxlength="20" class="form-control" /></td>
                            <td>
                                <form:errors path="coAccount" cssStyle="color: #ff0000;" />
                                <form:errors path="chAccount" cssStyle="color: #ff0000;" />
                            </td>
                        </tr>
                        <tr>
                            <td><b>Банк</b></td>
                            <td><form:input path="bank" class="form-control" /></td>
                            <td><form:errors path="bank" cssStyle="color: #ff0000;" /></td>
                            <td> </td>
                            <td> </td>
                        </tr>
                        <tr>
                            <td><b>БИК</b></td>
                            <td><form:input path="bik" maxlength="9" class="form-control" /></td>
                            <td><form:errors path="bik" cssStyle="color: #ff0000;" /></td>
                            <td> </td>
                            <td> </td>
                        </tr>
                        <tr>
                            <td><b>Юридический адрес</b></td>
                            <td><form:textarea path="urAddress" class="form-control" /></td>

                            <td><b>Почтовый адрес</b></td>
                            <td><form:textarea path="mailAddress" class="form-control" /></td>
                            <td>
                                <form:errors path="mailAddress" cssStyle="color: #ff0000;" />
                                <form:errors path="urAddress" cssStyle="color: #ff0000;" />
                            </td>
                        </tr>
                        <tr>
                            <td><b>Телефон</b></td>
                            <td><form:input path="phone" class="form-control" /></td>
                            <td><form:errors path="phone" cssStyle="color: #ff0000;" /></td>
                            <td> </td>
                            <td> </td>
                        </tr>
                        <tr>
                            <td><b>Директор</b></td>
                            <td colspan="3">
                                <div>
                                    <ccms:empchooser items="${emps}" targetPath="director" type="radio" />
                                </div>
                            </td>
                            <td>
                                <form:errors path="director" cssStyle="color: #ff0000;" />
                            </td>
                        </tr>
                    </tbody>
                </table>
                <input type="submit" value="Добавить" class="btn btn-primary">
            </form:form>
        </ccms:panel>
    </ccms:layout>
</ccms:page>