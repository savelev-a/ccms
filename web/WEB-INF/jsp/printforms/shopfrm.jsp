<%-- 
    Document   : shopfrm
    Created on : 16.06.2016, 11:02:48
    Author     : Alexander Savelev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<c:url value="/res/css/bootstrap.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/bootstrap-theme.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/styles.css" />" >
        <style type="text/css" media="print">
            @page { size: portrait; margin: 0cm }
        </style>
        <title>Информация по магазину</title>
    </head>

    <body><small>
        <div class="wrapper">
            <div class="container-fluid content">
                
                <br><br>

                <div class="row">

                    <div class="col-md-12">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Информация по магазину <c:out value="${shop.name}" /></div>
                            <div class="panel-body">
                                <table id="employeesTable" class="table table-hover table-condensed">

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
                                                <u>
                                                    <c:out value="${shop.email}" />
                                                </u>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>Юр. лицо</th>
                                            <td>
                                                    <c:out value="${shop.organisation.name}" />
                                            </td>

                                            <th>Время работы</th>
                                            <td>
                                                <c:out value="${shop.workingTime}" />
                                            </td>
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
                                            <td>
                                                    <c:out value="${shop.shopAdmin.fullName}" />
                                            </td>

                                            <th>Сотрудники</th>
                                            <td>
                                                <c:forEach items="${shop.shopEmployees}" var="employee">
                                                        <c:out value="${employee.fullName}" />
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
                                                <td colspan="3" style="white-space: pre">**********</td>
                                            </tr>
                                            <tr>
                                                <th>Тех. комментарии</th>
                                                <td colspan="3" style="white-space: pre"><c:out value="${shop.techComment}" /></td>
                                            </tr>
                                        </sec:authorize>
                                    </tbody>
                                </table>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="footer">
                Актуально на <c:out value="${currentDate}" />
            </div>

        </div>

            <script type="text/javascript">
                window.onload = function() { window.print(); };
            </script>

            </small></body>
</html>