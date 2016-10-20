<%-- 
    Document   : expencetypeedit
    Created on : 27.07.2016, 15:58:22
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<ccms:page title="Управление типами расходов" >
    <ccms:layout mainMenuActiveItem="management" sideMenuSection="expences" sideMenuActiveItem="expenceTypes">
        <ccms:panel cols="6" title="Редактировать тип расходов">
            <form:form method="post" commandName="expenceType">
                <table class="table table-condensed">
                    <tbody>
                        <tr>
                            <td><b>Наименование</b></td>
                            <td><form:input path="name" class="form-control" /></td>
                            <td><form:errors path="name" cssStyle="color: #ff0000;" /></td>
                        </tr>
                        <tr>
                            <td><b>Описание</b></td>
                            <td><form:textarea path="comment" class="form-control" /></td>
                            <td><form:errors path="comment" cssStyle="color: #ff0000;" /></td>
                        </tr>
                    </tbody>
                </table>
                <input id="save" type="submit" value="Сохранить" class="btn btn-primary">
            </form:form>
        </ccms:panel>
    </ccms:layout>
</ccms:page>