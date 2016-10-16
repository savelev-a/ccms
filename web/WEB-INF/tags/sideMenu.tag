<%-- 
    Document   : sideMenu
    Created on : 15.10.2016, 23:11:43
    Author     : alchemist
--%>

<%@tag description="Side menu tag" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@attribute name="section" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@attribute name="selectedItem" required="true" rtexprvalue="true" type="java.lang.String"%>

<div class="col-md-2 right-line">
    <br>
    <br>

    <div class="panel panel-primary panel-primary-dark">
        <div class="panel-heading panel-heading-dark" align="center">Навигация</div>
        <div class="panel-body">
            <table class="table table-condensed">
                <c:choose>
                    
                    <c:when test="${section == 'admin'}">
                        <tr ${selectedItem == "employees" ? "class='info'" : ""}>
                            <td>
                                <a href="<c:url value="/admin/employees" />">
                                    Сотрудники
                                </a> 
                            </td>
                        </tr>
                        <tr ${selectedItem == "shops" ? "class='info'" : ""}>
                            <td>
                                <a href="<c:url value="/admin/shops" />">
                                    Магазины
                                </a> 
                            </td>
                        </tr>
                        <tr ${selectedItem == "offices" ? "class='info'" : ""}>
                            <td>
                                <a href="<c:url value="/admin/offices" />">
                                    Офисы
                                </a> 
                            </td>
                        </tr>
                        <tr ${selectedItem == "orgs" ? "class='info'" : ""}>
                            <td>
                                <a href="<c:url value="/admin/organisations" />">
                                    Юр. лица
                                </a> 
                            </td>
                        </tr>

                        <tr><td> </td></tr>

                        <tr ${selectedItem == "expenceTypes" ? "class='info'" : ""}>
                            <td>
                                <a href="<c:url value="/admin/expencetypes" />">
                                    Типы расходов
                                </a> 
                            </td>
                        </tr>

                        <tr><td> </td></tr>

                        <tr ${selectedItem == "settings" ? "class='info'" : ""}>
                            <td>
                                <a href="<c:url value="/admin/settings" />">
                                    Настройки
                                </a> 
                            </td>
                        </tr>
                    </c:when>

                    <c:when test="${section == 'actionEvents'}">
                        <tr ${selectedItem == "newaction" ? "class='info'" : ""}>
                            <td>
                                <a href="<c:url value="/actions/create" />">
                                    Создать акцию
                                </a> 
                            </td>
                        </tr>

                        <tr><td> </td></tr>

                        <tr ${selectedItem == "currentFuture" ? "class='info'" : ""}>
                            <td>
                                <a href="<c:url value="/actions/currentFuture" />">
                                    Управление акциями
                                </a> 
                            </td>
                        </tr>
                        <tr ${selectedItem == "past" ? "class='info'" : ""}>
                            <td>
                                <a href="<c:url value="/actions/past" />">
                                    Прошедшие акции
                                </a> 
                            </td>
                        </tr>
                        <tr ${selectedItem == "calendar" ? "class='info'" : ""}>
                            <td>
                                <a href="<c:url value="/actions/calendar" />">
                                    Календарь акций
                                </a> 
                            </td>
                        </tr>
                    </c:when>

                    <c:otherwise>
                        <td>
                            <a href="javascript:history.back()">
                                <- назад --
                            </a>
                        </td>
                    </c:otherwise>
                </c:choose>
            </table>
        </div> 
    </div> 
</div>