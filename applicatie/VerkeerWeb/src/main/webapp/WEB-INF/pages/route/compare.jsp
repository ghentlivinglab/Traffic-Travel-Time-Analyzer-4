<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/shared/head.jsp">
        <jsp:param name="title" value="Trajecten veranderen"/>
    </jsp:include>
</head>
<body>
<jsp:include page="/WEB-INF/shared/navigation.jsp"/>
<div class="container">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">Compare routes</h3>
        </div>
        <div class="panel-body">
            <div class="row">
                <div class="col-sm-4">
                    <label for="route1">Select first route</label>
                    <select id="route1" class="form-control">
                        <c:forEach var="route" items="${routes}">
                            <option value="${route.id}">${route.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-4">
                    <label for="route2">Select second route</label>
                    <select id="route2" class="form-control">
                        <c:forEach var="route" items="${routes}">
                            <option value="${route.id}">${route.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-4">
                    <label for="providers">Select providers</label>
                    <select multiple id="providers" class="form-control">
                        <c:forEach var="provider" items="${providers}">
                            <option value="${provider}">${provider}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div id="container" style="height: 400px"></div>
                    <button id="toggle-btn" type="button" class="btn btn-primary" style="margin: 10px auto">Toggle
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="<c:url value="/static/scripts/libs/highcharts.js" />"></script>
<script src="<c:url value="/static/scripts/libs/exporting.js" />"></script>
<script src="<c:url value="/static/scripts/libs/export-csv.js" />"></script>
<script src="<c:url value="/static/scripts/route/route-chart.js" />"></script>
<script src="<c:url value="/static/scripts/route/compare/compare.js" />"></script>
</html>