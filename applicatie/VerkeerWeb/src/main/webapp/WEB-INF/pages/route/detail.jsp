<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/shared/head.jsp">
        <jsp:param name="title" value="Traject ${detail.name}"/>
    </jsp:include>
    <jsp:include page="/WEB-INF/shared/maprequirements.jsp"/>
    <script src="<c:url value="/static/scripts/route/map.js" />"></script>
    <script src="https://code.highcharts.com/highcharts.js"></script>
    <script src="https://code.highcharts.com/modules/exporting.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/shared/navigation.jsp"/>
<div class="container">
    <div class="row">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">Traject ${detail.name}</h3>
            </div>
            <div class="panel-body">
                <div class="col-md-4">
                    <dl>
                        <div class="panel panel-default">
                            <div class="panel-heading">Huidige reistijd</div>
                            <table class="table table-hover">
                                <tr>
                                    <th>Provider</th>
                                    <th>Reistijd (s)</th>
                                    <th>Vertraging (s)</th>
                                </tr>
                                <c:forEach var="data" items="${detail.summaries}">
                                    <tr>
                                        <td>${data.provider}</td>
                                        <td>${data.travelTime}</td>
                                        <td><span class="label label-warning">${data.delay}</span></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <dt>Normale reistijd</dt>
                        <dd>${detail.defaultTravelTime} seconden</dd>
                        <dt>Afstand</dt>
                        <dd>${detail.distance} meter</dd>
                        <dt>Vanaf</dt>
                        <dd>${detail.fromAddress}</dd>
                        <dt>Tot</dt>
                        <dd>${detail.toAddress}</dd>
                    </dl>
                </div>
                <div class="col-md-8">
                    <div id="map" style="height:500px;">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Filteropties</h3>
            </div>
            <div class="panel-body">
                <div class="col-md-4">
                    <label>Van</label>
                    <div class="input-group">
                        <input class="datePicker form-control" type="text"/>
                                <span class="input-group-btn">
                                    <button class="btn btn-default" type="button">Update</button>
                                </span>
                    </div>
                </div>
                <div class="col-md-4">
                    <label>Tot</label>
                    <div class="input-group">
                        <input class="datePicker form-control" type="text"/>
                                <span class="input-group-btn">
                                    <button class="btn btn-default" type="button">Update</button>
                                </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Historiek</h3>
            </div>
            <div class="panel-body">
                <div class="container">
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
                    <div id="container" style="min-width: 100px; height: 400px; margin: 0 auto"></div>
                </div>
                <table class="table">
                    <thead>
                    <tr>
                        <th>Datum</th>
                        <th>Reistijd (seconden)</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="data" items="${detail.data}">
                        <tr>
                            <td>
                                    ${data.timestamp}
                            </td>
                            <td>
                                    ${data.travelTime}
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <script>
        $(document).ready(function () {
            $(".datePicker").datetimepicker();
            MapManagement.intializeRouteMap("map", "<c:url value="/route/mapdata?id=${detail.id}" />");
        });
    </script>
</body>
</html>
