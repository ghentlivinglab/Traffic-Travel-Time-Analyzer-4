<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/shared/head.jsp">
        <jsp:param name="title" value="Traject ${detail.name}"/>
    </jsp:include>
    <jsp:include page="/WEB-INF/shared/maprequirements.jsp"/>
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
                    <div id="map" style="height:500px">
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
                <div class="col-md-6">
                    <label>Begindatum</label>
                    <div class="form-group">
                        <div class='input-group date' id='datetimepicker1'>
                            <input type='text' class="form-control"/>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <label>Einddatum</label>
                    <div class="form-group">
                        <div class='input-group date' id='datetimepicker2'>
                            <input type='text' class="form-control"/>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                        </div>
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
                    <script src="<c:url value="/static/scripts/route/detail/chart.js" />"></script>
                    <div id="container" style="min-width: 100px; height: 400px; margin: 0 auto"></div>
                </div>
                <table class="table table-striped table-condensed">
                    <thead>
                    <tr>
                        <th>Datum</th>
                        <th>Tijd</th>
                        <th>Provider</th>
                        <th>Reistijd (seconden)</th>
                        <th>Vertraging (seconden)</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
<script src="<c:url value="/static/scripts/route/map.js" />"></script>
<script>
    MapManagement.intializeRouteMap("map", "<c:url value="/route/mapdata?id=${detail.id}" />");
</script>
<script src="<c:url value="/static/scripts/highcharts.js" />"></script>
<script src="<c:url value="/static/scripts/route/detail/detail.js" />"></script>
</body>
</html>
