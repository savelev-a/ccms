<%-- 
    Document   : shop
    Created on : 15.04.2016, 13:18:32
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ccms" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<ccms:page title="Магазин - ${shop.name}">
    <ccms:layout mainMenuActiveItem="shops" sideMenuSection="shop" sideMenuActiveItem="general">
        <ccms:panel cols="10" title="Информация по магазину ${shop.name}">

            <table class="table table-hover table-condensed">
                <tbody>
                    <tr>
                        <th class="th-header-center" colspan="4"><u>Общие данные</u></th>
                    </tr>
                    <tr>
                        <th>Наименование</th>
                        <td><c:out value="${shop.name}" /></td>

                        <th>E-mail</th>
                        <td>
                            <span class="glyphicon glyphicon-envelope"></span>
                            <a href="mailto:<c:out value="${shop.email}" />">
                                <c:out value="${shop.email}" />
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <th>Юр. лицо</th>
                        <td><ccms:hyperlink target="${shop.organisation}" /></td>

                        <th>Время работы</th>
                        <td><c:out value="${shop.workingTime}" /></td>
                    </tr>
                    <tr>
                        <th>Адрес</th>
                        <td><c:out value="${shop.address}" /></td>

                        <th>ICQ</th>
                        <td><c:out value="${shop.icq}" /></td>
                    </tr>

                    <tr>
                        <th>Телефон</th>
                        <td>
                            <span class="glyphicon glyphicon-phone-alt"></span>
                            <c:out value="${shop.phone}" />
                        </td>

                        <th>Арендодатель</th>
                        <td><c:out value="${shop.landlord}" /></td>
                    </tr>

                    <tr>
                        <th class="th-header-center" colspan="4"><u>Персонал</u></th>
                    </tr>
                    <tr>
                        <th>Администратор</th>
                        <td><ccms:hyperlink target="${shop.shopAdmin}" /></td>

                        <th>Сотрудники</th>
                        <td>
                            <c:forEach items="${shop.shopEmployees}" var="employee">
                                <ccms:hyperlink target="${employee}" />
                                <br>
                            </c:forEach>
                        </td>
                    </tr>


                    <tr>
                        <th>Комментарии</th>
                        <td colspan="3" style="white-space: pre"><c:out value="${shop.comment}" /></td>
                    </tr>

                    <tr>
                        <th class="th-header-center" colspan="4"><u>Оборудование</u></th>
                    </tr>
                    <tr>
                        <th>Компьютер</th>
                        <td colspan="3" style="white-space: pre"><c:out value="${shop.hardware.pc}" /></td>
                    </tr>
                    <tr>
                        <th>Операционная система</th>
                        <td><c:out value="${shop.hardware.os}" /></td>

                        <th>Банковский терминал</th>
                        <td><c:out value="${shop.hardware.bankTerm}" /></td>
                    </tr>
                    <tr>
                        <th>ККМ</th>
                        <td><c:out value="${shop.hardware.kkm}" /></td>

                        <th>Детектор валют</th>
                        <td><c:out value="${shop.hardware.detektor}" /></td>
                    </tr>
                    <tr>
                        <th>Сканер ШК</th>
                        <td><c:out value="${shop.hardware.scanner}" /></td>

                        <th>Роутер</th>
                        <td><c:out value="${shop.hardware.router}" /></td>
                    </tr>
                    <tr>
                        <th>Принтер этикеток</th>
                        <td><c:out value="${shop.hardware.labelPrinter}" /></td>

                        <th>Звук / колонки</th>
                        <td><c:out value="${shop.hardware.music}" /></td>
                    </tr>

                    <tr>
                        <th>Принтер</th>
                        <td><c:out value="${shop.hardware.printer}" /></td>

                        <th>ИБП</th>
                        <td><c:out value="${shop.hardware.ups}" /></td>
                    </tr>

                    <tr>
                        <th>Счетчик посетителей</th>
                        <td>
                            <c:choose>
                                <c:when test="${shop.countersEnabled}">
                                    <font color="green">Подключен</font>, Кондор-7
                                    </c:when>
                                    <c:otherwise>
                                    Не подключен
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <th>Имя Домино</th>
                        <td><c:out value="${shop.dominoName}" /></td>
                    </tr>

                    <tr><th> </th><td colspan="4">&nbsp;</td></tr>

                    <tr>
                        <th class="th-header-center" colspan="4"><u>Данные интернет-провайдера</u></th>
                    </tr>
                    <tr>
                        <th>Провайдер</th>
                        <td><c:out value="${shop.provider.name}" /></td>
                        <td> </td>
                        <td> </td>
                    </tr>
                    <tr>
                        <th>№ договора</th>
                        <td><c:out value="${shop.provider.contract}" /></td>
                        <td> </td>
                        <td> </td>
                    </tr>
                    <tr>
                        <th>IP адрес (внешний)</th>
                        <td><c:out value="${shop.provider.ip}" /></td>
                        <td> </td>
                        <td> </td>
                    </tr>
                    <tr>
                        <th>IP подсеть (внутренняя)</th>
                        <td><c:out value="${shop.provider.subnet}" /></td>

                        <th>Скорость подключения</th>
                        <td><c:out value="${shop.provider.speed}" /></td>
                    </tr>
                    <tr>
                        <th>Телефон тех. поддержки</th>
                        <td><c:out value="${shop.provider.techPhone}" /></td>

                        <th>Телефон абон. службы</th>
                        <td><c:out value="${shop.provider.abonPhone}" /></td>
                    </tr>
                    <tr>
                        <th>Комментарии (сеть)</th>
                        <td style="white-space: pre"><c:out value="${shop.provider.techData}" /></td>
                    </tr>
                    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_OFFICE')">
                        <tr>
                            <th class="th-header-center" colspan="4"><u>Пароли / доступ</u></th>
                        </tr>
                        <tr>
                            <th>Ключи / лицензии</th>
                            <td colspan="3" style="white-space: pre"><c:out value="${shop.keys}" /></td>
                        </tr>
                        <tr>
                            <th>Пароли</th>
                            <td colspan="3" style="white-space: pre"><c:out value="${shop.passwords}" /></td>
                        </tr>
                        <tr>
                            <th>Тех. комментарии</th>
                            <td colspan="3" style="white-space: pre"><c:out value="${shop.techComment}" /></td>
                        </tr>
                    </sec:authorize>
                </tbody>
            </table>
            <br>

            <span class="glyphicon glyphicon-cog"></span>
            <a href="<c:url value="/admin/shopprofile?id=${shop.id}&userpage=true" />" > Редактировать магазин </a>
            &nbsp;
            <span class="glyphicon glyphicon-print"></span>
            <a href="<c:url value='/shop?id=${shop.id}&mode=print' /> " target="_blank">Распечатать информацию</a>

        </ccms:panel>
    </ccms:layout>
</ccms:page>