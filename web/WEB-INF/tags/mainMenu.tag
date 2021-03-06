<%-- 
    Document   : mainMenu
    Created on : 15.10.2016, 20:20:42
    Author     : alchemist
--%>

<%@tag description="Main Menu tag" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@attribute name="mainMenuActiveItem" required="true" rtexprvalue="true" type="java.lang.String"%>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">

        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" 
                    data-toggle="collapse" data-target="#mainMenuNavbarCollapse" aria-expanded="false">
                <span class="sr-only">Навигация</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="<c:url value="/" />">ИнфоПортал</a>
        </div>

        <div class="collapse navbar-collapse" id="mainMenuNavbarCollapse">
            <ul class="nav navbar-nav">

                <li ${mainMenuActiveItem == "shops" ? "class=active" : ""}>
                    <a href="<c:url value="/shops" />">
                        Магазины
                    </a> 
                </li>
                
                <li class="dropdown ${mainMenuActiveItem == "directories" ? "active" : ""}">
                        <a href="#" class="dropdown-toggle" role="button" data-toggle="dropdown">
                            Справочники
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">

                            <li>
                                <a href="<c:url value="/offices" />">
                                    Офисы
                                </a> 
                            </li>

                            <li>
                                <a href="<c:url value="/employees" />">
                                    Сотрудники
                                </a> 
                            </li>

                            <li>
                                <a href="<c:url value="/organisations" />">
                                    Юр. лица
                                </a> 
                            </li>
                            
                        </ul>
                </li>

                <sec:authorize access="hasRole('ROLE_SHOP')">
                    <li class="dropdown ${mainMenuActiveItem == "myshop" ? "active" : ""}">
                        <a href="#" class="dropdown-toggle" role="button" data-toggle="dropdown">
                            Мои магазины
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:forEach items="${currentShops}" var="currentShop">
                                <li>

                                    <div class="dropdown-menu-item-center">-<b><c:out value="${currentShop.name}" />-</b></div>
                                    <a href="<c:url value="/shop?id=${currentShop.id}" />">Информация</a>
                                    <c:if test="${currentShop.countersEnabled}">
                                        <a href="<c:url value="/counters?shopid=${currentShop.id}" />">Счетчики посетителей</a>
                                    </c:if>
                                    <a href="<c:url value="/sales?shopid=${currentShop.id}" />">Таблица проходимости</a>
                                    <br>
                                </li>
                            </c:forEach>
                        </ul>
                    </li>
                </sec:authorize>

                <sec:authorize access="hasRole('ROLE_OFFICE')">
                    <li class="dropdown ${mainMenuActiveItem == "management" ? "active" : ""}">
                        <a href="#" class="dropdown-toggle" role="button" data-toggle="dropdown">
                            Управление
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <div class="dropdown-menu-item-center">-<b>Расходы магазинов</b>-</div>
                            <li>
                                <a href="<c:url value="/expences" />" >
                                    <span class="glyphicon glyphicon-list-alt"></span> Установить расходы
                                </a>
                            </li>
                            <li>
                                <a href="<c:url value="/management/expencetypes" />" >
                                    <span class="glyphicon glyphicon-list-alt"></span> Управление видами расходов
                                </a>
                            </li>
                            <br>
                            <div class="dropdown-menu-item-center">-<b>План продаж</b>-</div>
                            <li>
                                <a href="<c:url value="/management/setPlan" />" >
                                    <span class="glyphicon glyphicon-list-alt"></span> Установить план
                                </a>
                            </li>
                            <br>
                            <div class="dropdown-menu-item-center">-<b>Акции и распродажи</b>-</div>
                            <li>
                                <!--<a href="<c:url value="/actions/create" />" >
                                    <span class="glyphicon glyphicon-plus"></span> Новая акция
                                </a>
                            </li>
                            <li>
                                <a href="<c:url value="/actions/currentFuture" />" >
                                    <span class="glyphicon glyphicon-briefcase"></span> Управление акциями
                                </a>-->
                                <a href="<c:url value="#" />" >
                                    <span class="glyphicon glyphicon-question-sign"></span> Данный раздел в разработке
                                </a>
                            </li>
                        </ul>
                    </li>
                    
                    <li class="dropdown ${mainMenuActiveItem == "reports" ? "active" : ""}">
                        <a href="#" class="dropdown-toggle" role="button" data-toggle="dropdown">
                            Отчеты
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="<c:url value="/reports/sales-pass" />" >
                                    <span class="glyphicon glyphicon-list-alt"></span>  Отчет по проходимости и выручке
                                </a>
                            </li>
                            <li>
                                <a href="<c:url value="/reports/expences" />" >
                                    <span class="glyphicon glyphicon-list-alt"></span>  Отчет по расходам
                                </a>
                            </li>
                            <li>
                                <a href="<c:url value="/reports/shopproviders" />" >
                                    <span class="glyphicon glyphicon-list-alt"></span>  Отчет по провайдерам в магазинах
                                </a>
                            </li>
                            <li>
                                <a href="<c:url value="/reports/tasks" />" >
                                    <span class="glyphicon glyphicon-list-alt"></span>  Отчеты по задачам
                                </a>
                            </li>
                        </ul>
                    </li>
                </sec:authorize>

                <li class="dropdown ${mainMenuActiveItem == "tasks" ? "active" : ""}">
                    <a href="#" class="dropdown-toggle" role="button" data-toggle="dropdown">
                        Задачи 
                        <c:if test="${currentUserActiveTasksCount != 0}">
                            <span class="badge"><c:out value="${currentUserActiveTasksCount}" /></span>
                        </c:if>
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="<c:url value="/tasks/create" />" >
                                <span class="glyphicon glyphicon-plus"></span>  Создать задачу
                            </a>
                        </li>
                        <li>
                            <a href="<c:url value="/tasks/mytasks" />" >
                                <span class="glyphicon glyphicon-briefcase"></span>  Мои задачи
                            </a>
                        </li>
                        <li>
                            <a href="<c:url value="/tasks/freetasks" />" >
                                <span class="glyphicon glyphicon-dashboard"></span>  Свободные задачи
                            </a>
                        </li>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <li>
                                <a href="<c:url value="/admin/tasks" />" >
                                    <span class="glyphicon glyphicon-cog"></span>  Управление задачами
                                </a>
                            </li>
                        </sec:authorize>

                    </ul>
                </li>

                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li ${mainMenuActiveItem == "admin" ? "class=active" : ""}>
                        <a href="<c:url value="/admin" />">
                            Администрирование
                        </a> 
                    </li>
                </sec:authorize>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <sec:authorize access="isAuthenticated()" var="loggedIn" />
                <c:choose>
                    <c:when test="${loggedIn}">

                        <li class="dropdown">

                            <a href="#" class="dropdown-toggle" role="button" data-toggle="dropdown">
                                <span class="glyphicon glyphicon-user"></span>
                                <c:out value="${currentUser.lastName}" />
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="<c:url value="/admin/profile?id=${currentUser.id}" />" >
                                        <span class="glyphicon glyphicon-file"></span>  Мой профиль
                                    </a>
                                </li>
                                <br>
                                <li><a href="#" id="logout"><span class="glyphicon glyphicon-log-out"></span>  Выход</a></li>

                            </ul>
                        </li>


                    </c:when>
                    <c:otherwise>
                        <li><a href="<c:url value="/login" />">Войти</a></li>
                        </c:otherwise>
                    </c:choose>

            </ul>
        </div>
    </div>
</nav>