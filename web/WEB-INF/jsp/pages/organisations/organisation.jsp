<%-- 
    Document   : organisation
    Created on : 12.04.2016, 14:44:29
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<ccms:page title="Реквизиты - ${organisation.name}">
    <ccms:layout mainMenuActiveItem="directories" sideMenuSection="none" sideMenuActiveItem="none">
        <ccms:panel cols="5" title="Реквизиты организации ${organisation.name}">
            <table class="table table-hover table-condensed">
                <tbody>
                    <tr>
                        <td>Наименование: </td>
                        <td><c:out value="${organisation.name}" /></td>

                    </tr>
                    <tr>
                        <td>ИНН</td><td><c:out value="${organisation.inn}" /></td>
                    </tr>
                    <tr>
                        <td>КПП</td><td><c:out value="${organisation.kpp}" /></td>
                    </tr>
                    <tr>
                        <td>ОГРН</td>
                        <td><c:out value="${organisation.ogrn}" /></td>

                    </tr>
                    <tr>
                        <td>Расчетный счет</td><td><c:out value="${organisation.chAccount}" /></td>
                    </tr>
                    <tr>
                        <td>Корр. счет</td><td><c:out value="${organisation.coAccount}" /></td>
                    </tr>
                    <tr>
                        <td>Банк</td>
                        <td><c:out value="${organisation.bank}" /></td>

                    </tr>
                    <tr>
                        <td>БИК</td>
                        <td><c:out value="${organisation.bik}" /></td>

                    </tr>
                    <tr>
                        <td>Юридический адрес</td><td><c:out value="${organisation.urAddress}" /></td>
                    </tr>
                    <tr>
                        <td>Почтовый адрес</td><td><c:out value="${organisation.mailAddress}" /></td>
                    </tr>
                    <tr>
                        <td>Телефон</td>
                        <td><c:out value="${organisation.phone}" /></td>

                    </tr>
                    <tr>
                        <td>Директор</td>
                        <td>
                            <a href="<c:url value="/employee?id=${organisation.director.id}" />" >
                                <c:out value="${organisation.director.lastName}" /> 
                                <c:out value="${organisation.director.firstName}" /> 
                                <c:out value="${organisation.director.middleName}" /> 
                            </a>
                        </td>
                    </tr>
                </tbody>
            </table>
            <br>

            <span class="glyphicon glyphicon-cog"></span>
            <a href="<c:url value="/admin/orgprofile?id=${organisation.id}&userpage=true" />" > Редактировать организацию </a>
            &nbsp;
            <span class="glyphicon glyphicon-print"></span>
            <a href="<c:url value='/organisation?id=${organisation.id}&mode=print' /> " target="_blank">Распечатать реквизиты</a>
        </ccms:panel>
                                
        <ccms:panel cols="5" title="Магазины, связанные с этим юр. лицом">
            <table class="table table-hover table-condensed">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Наименование</th>
                        <th>Адрес</th>
                        <th>Телефон</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${organisation.shops}" var="shop" varStatus="idx">
                        <tr>
                            <td>${idx.count}</td>
                            <td><ccms:hyperlink target="${shop}" /></td>
                            <td><c:out value="${shop.address}" /></td>
                            <td><span class="glyphicon glyphicon-phone-alt"></span> <c:out value="${shop.phone}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>  
        </ccms:panel>
    </ccms:layout>
</ccms:page>