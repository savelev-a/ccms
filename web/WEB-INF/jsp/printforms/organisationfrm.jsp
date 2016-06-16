<%-- 
    Document   : organisationfrm
    Created on : 16.06.2016, 10:53:00
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
        <title>Реквизиты организации</title>
    </head>

    <body><small>
        <div class="wrapper">
            <div class="container-fluid content">
                
                <br><br>

                <div class="row">

                    <div class="col-md-12">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Реквизиты организации <c:out value="${organisation.name}" /></div>
                            <div class="panel-body">
                                <table id="employeesTable" class="table table-hover table-condensed">

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
                                                    <c:out value="${organisation.director.lastName}" /> 
                                                    <c:out value="${organisation.director.firstName}" /> 
                                                    <c:out value="${organisation.director.middleName}" /> 
                                            </td>
                                        </tr>
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