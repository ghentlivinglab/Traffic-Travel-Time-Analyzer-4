<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/shared/head.jsp">
        <jsp:param name="title" value="Compare routes"/>
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
            <!-- Row with all options + provider selection -->
            <div class="row">
                <div class="col-md-8">
                    <!-- Row routes -->
                    <div class="row">
                        <div class="col-md-6">
                            <label for="route1">Select first route</label>
                            <select id="route1" class="form-control">
                                <c:forEach var="route" items="${routes}">
                                    <option value="${route.id}">${route.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label for="route2">Select second route</label>
                            <select id="route2" class="form-control">
                                <c:forEach var="route" items="${routes}">
                                    <option value="${route.id}">${route.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <!-- Row dates -->
                    <div class="row" style="margin-top: 10px">
                        <div class="col-md-6">
                            <label for="datetimepicker-begin-input">Start Date</label>
                            <div class="form-group">
                                <div class='input-group date' id='datetimepicker-begin'>
                                    <input type='text' class="form-control" id="datetimepicker-begin-input"/>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label for="datetimepicker-end-input">End Date</label>
                            <div class="form-group">
                                <div class='input-group date' id='datetimepicker-end'>
                                    <input type='text' class="form-control" id="datetimepicker-end-input"/>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <label>Select providers</label><br>
                    <c:forEach var="provider" items="${providers}">
                        <input type="checkbox" name="providers" id="${provider}" value="${provider}" checked>${provider}<br>
                    </c:forEach>
                </div>
            </div>
            <!-- Buttons row -->
            <div class="row">
                <div class="col-md-12">
                    <button id="update-btn" type="button" class="btn btn-primary">
                        <span id="refresh-icon" class="glyphicon glyphicon-refresh"></span>
                        <span id="update-btn-txt">Update</span>
                    </button>
                    <button id="toggle-btn" type="button" class="btn btn-primary" style="margin: 10px auto">
                        <span class="glyphicon glyphicon-stats"></span>
                        <span>Toggle</span>
                    </button>
                </div>
            </div>
            <hr>
            <!-- The graph -->
            <div class="row">
                <div class="col-md-12">
                    <div id="container" style="height: 400px"></div>
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