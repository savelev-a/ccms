<%-- 
    Document   : expences
    Created on : 20.10.2016, 14:11:03
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
        <link rel="stylesheet" href="<c:url value="/res/css/jqgrid/jquery-ui.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/jqgrid/jquery-ui.theme.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/jqgrid/ui.jqgrid.css" />" >
        <link rel="stylesheet" href="<c:url value="/res/css/styles.css" />" >
        <style type="text/css" media="print">
            @page { size: portrait; margin: 0cm }
        </style>
        <title>Расходы магазина <c:out value="${shop.name}" /></title>
    </head>

    <body><small>
        <div class="wrapper">
            <div class="container-fluid content">
                
                <br><br>

                <div class="row">

                    <div class="col-md-12">
                        <div class="panel panel-primary panel-primary-dark">
                            <div class="panel-heading panel-heading-dark" align="center">Расходы магазина <c:out value="${shop.name}" /> за 
                                <u><c:out value="${selectedYear}" /></u> год</div>
                            <div class="panel-body">
                                <div style="width: 100%; height: 100%;">
                                    <table id="expencesTable">

                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="footer">
                Актуально на <c:out value="${currentDate}" />
            </div>

        </div>

            </small>
    
    <script src="<c:url value="/res/js/jquery-2.2.2.min.js" /> "></script>
    <script src="<c:url value="/res/js/jquery-ui.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/res/js/grid.locale-ru.js" />"></script>
    <script type="text/javascript" src="<c:url value="/res/js/jquery.jqGrid.min.js" />"></script>


    <script type="text/javascript">
        $(function () {

            $("#expencesTable").jqGrid({
                url: "<c:url value='/expences/data?shopid=${shop.id}&dateYear=${selectedYear}' />",
                datatype: "json",
                loadonce: true,
                height: "100%",
                width: null,
                shrinkToFit: false,
                caption: "",
                rowNum: 1000,
                footerrow: true,

                loadComplete: function () {
                    var elem = {};
                    for(var i = 1; i <= 12; i++) {
                        var cellname = 'c'+i;
                        elem[cellname] = parseFloat($('#expencesTable').jqGrid('getCol', cellname, false, 'sum'))
                    };
                    elem['expencetype'] = 'Итого: ';
                    elem['totals'] = parseFloat($('#expencesTable').jqGrid('getCol', 'totals', false, 'sum'));

                    $("#expencesTable").jqGrid('footerData','set', elem);

                },
                sortname: "expencetype",
                colNames: ['Описание расхода', 'Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл', 'Авг', 'Сен', 'Окт', 'Ноя', 'Дек', 'Итого'],
                colModel: [
                    {name: 'expencetype', index: 'expencetype', width: 120, sorttype: "text", classes: "jqcol-hyperlink"},
                    <c:forEach varStatus="idx" begin="1" end="12">
                        {name: 'c${idx.count}', index: 'c${idx.count}', width: 70, sorttype: "float", align: "right", formatter: 'number', 
                            editrules: {
                                number: true, minValue: 0
                            },
                            formatoptions: {
                                decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'
                            }},
                    </c:forEach>
                    {name: 'totals', index: 'totals', width: 90, sorttype: "float", align: "right", formatter: 'number', 
                        formatoptions: {
                            decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'
                        }
                    },
                ],
                gridComplete: setTimeout(function(){ window.print(); }, 2000)

            });
            
        });
        
    </script>
    
    </body>
</html>