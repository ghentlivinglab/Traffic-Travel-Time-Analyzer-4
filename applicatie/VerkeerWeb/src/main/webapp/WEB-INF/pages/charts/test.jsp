<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/shared/head.jsp">
            <jsp:param name="title" value="Test" />
        </jsp:include>
        <script src="https://code.highcharts.com/highcharts.js"></script>
        <script src="https://code.highcharts.com/modules/exporting.js"></script>


    </head>
    <body>
        <jsp:include page="/WEB-INF/shared/navigation.jsp" />
        <div class="container">
            <h1>Test Chart</h1>
            <script>
            $(function () {
    $('#container').highcharts({
        title: {
            text: 'Gemiddelde Vertragingen Per Provider',
            x: -20 //center
        },
        
        xAxis: {
            categories: ['Id11', 'Id12', 'Id13', 'Id14', 'Id15', 'Id16',
                'Id17', 'Id18', 'Id19', 'Id20', 'Id21', 'Id22']
        },
        yAxis: {
            title: {
                text: 'Time (seconds)'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: 'sec'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: 'TomTom',
            data: [100, 190, 65, 145, 182, 215, 252, 265, 233, 183, 139, 96]
        }, {
            name: 'Waze',
            data: [100, 108, 57, 113, 170, 220, 248, 241, 201, 141, 86, 25]
        }, {
            name: 'Google Maps',
            data: [110, 106, 35, 84, 135, 170, 186, 179, 143, 90, 39, 10]
        }, {
            name: 'Coyote',
            data: [120, 106, 35, 84, 135, 170, 186, 179, 143, 90, 39, 100]
        }, {
            name: 'Here',
            data: [103, 142, 57, 85, 119, 152, 170, 166, 142, 103, 66, 48]
        }]
    });
});
            </script>
            <div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

            
            
            
        </div>
    </body>
</html>
