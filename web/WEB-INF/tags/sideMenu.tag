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
                    
<%-- 

                            Администрирование (admin)

--%>
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

                        <tr ${selectedItem == "settings" ? "class='info'" : ""}>
                            <td>
                                <a href="<c:url value="/admin/settings" />">
                                    Настройки
                                </a> 
                            </td>
                        </tr>
                    </c:when>
                        
<%-- 

                                Акции/распродажи (ActionEvents)

--%>
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
                        
<%-- 

                                   Задачи (tasks)

--%>
                    <c:when test="${section == 'tasks'}">
                        <tr ${selectedItem == "newtask" ? "class='info'" : ""}>
                            <td>
                                <a href="<c:url value="/tasks/create" />">
                                    Создать задачу
                                </a> 
                            </td>
                        </tr>

                        <tr><td> </td></tr>

                        <tr ${selectedItem == "performed" ? "class='info'" : ""}>
                            <td>
                                <a href="<c:url value="/tasks/mytasks" />">
                                    Назначенные мне
                                    <c:if test="${currentUserActiveTasksCount != 0}">
                                    <span class="badge badge-right"><c:out value="${currentUserActiveTasksCount}" /></span>
                                </c:if>
                                </a> 
                            </td>
                        </tr>
                        <tr ${selectedItem == "created" ? "class='info'" : ""}>
                            <td>
                                <a href="<c:url value="/tasks/mytasks/created" />">
                                    Созданные мной
                                </a> 
                            </td>
                        </tr>
                        <tr ${selectedItem == "free" ? "class='info'" : ""}>
                            <td>
                                <a href="<c:url value="/tasks/freetasks" />">
                                    Свободные 
                                    <c:if test="${openTasksCount != 0}">
                                        <span class="badge badge-right"><c:out value="${openTasksCount}" /></span>
                                    </c:if>
                                </a> 
                            </td>
                        </tr>
                        <tr ${selectedItem == "closed" ? "class='info'" : ""}>
                            <td>
                                <a href="<c:url value="/tasks/mytasks/closed" />">
                                    Закрытые
                                </a> 
                            </td>
                        </tr>

                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <tr><td> </td></tr>
                            <tr ${selectedItem == "tasksadm" ? "class='info'" : ""}>
                                <td>
                                    <a href="<c:url value="/admin/tasks" />">
                                        Управление задачами
                                    </a> 
                                </td>
                            </tr>
                        </sec:authorize>
                    </c:when>
<%-- 

                                   Магазин (shop)

--%>  
                    <c:when test="${section == 'shop'}">
                        <tr ${selectedItem == "general" ? "class='info'" : ""}>
                            <td>
                                <a href="<c:url value="/shop?id=${shop.id}" />">
                                    Общая информация
                                </a> 
                            </td>
                        </tr>
                        <c:if test="${shop.countersEnabled}">
                            <tr ${selectedItem == "counters" ? "class='info'" : ""}>
                                <td>
                                    <a href="<c:url value="/counters?shopid=${shop.id}" />">
                                        Счетчики посетителей
                                    </a> 
                                </td>
                            </tr>
                        </c:if>
                        <tr ${selectedItem == "sales" ? "class='info'" : ""}>
                            <td>
                                <a href="<c:url value="/sales?shopid=${shop.id}" />">
                                    Таблица проходимости
                                </a> 
                            </td>
                        </tr>
                        <sec:authorize access="hasRole('ROLE_OFFICE')">
                            <tr><td> </td></tr>
                            <tr ${selectedItem == "expences" ? "class='info'" : ""}>
                            <td>
                                <a href="<c:url value="/expences?shopid=${shop.id}" />">
                                    Расходы магазина
                                </a> 
                            </td>
                        </tr>
                        </sec:authorize>
                    </c:when>

<%-- 

                                   Отчет - выручка/проходимость (reports_sp)

--%>
                    <c:when test="${section == 'reports_sp'}">
                        <tr ${selectedItem == "general" ? "class='info'" : ""}>
                            <td>
                                <a href="<c:url value="/reports/sales-pass" />">
                                    Общая таблица
                                </a> 
                            </td>
                        </tr>

                        <tr ${selectedItem == "graph" ? "class='info'" : ""}>
                            <td>
                                <a href="<c:url value="/reports/graph/sales-pass" />">
                                    Графики
                                </a> 
                            </td>
                        </tr>

                        <tr ${selectedItem == "plan" ? "class='info'" : ""}>
                            <td>
                                <a href="<c:url value="/management/setPlan" />">
                                    Установить план
                                </a> 
                            </td>
                        </tr>
                    </c:when>
                            
<%-- 

                                   Расходы, управление (expences)

--%>
                    <c:when test="${section == 'expences'}">
                        <tr ${sideMenuActiveItem == "expences" ? "class='info'" : ""}>
                            <td>
                                <a href="<c:url value="/expences?shopid=${shop.id}" />">
                                    Ввод данных по расходам
                                </a> 
                            </td>
                        </tr>
                        <tr ${sideMenuActiveItem == "expenceTypes" ? "class='info'" : ""}>
                            <td>
                                <a href="<c:url value="/management/expencetypes" />">
                                    Управление типами расходов
                                </a> 
                            </td>
                        </tr>
                    </c:when>
                            
<%-- 

                                     Пустое меню

--%>
                    <c:otherwise>
                        <td align="center">
                            <i><font color="gray">Нет вариантов</font> </i>
                        </td>
                    </c:otherwise>
                </c:choose>
            </table>
        </div> 
    </div> 
</div>