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
    <div class="row">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">Compare routes</h3>
            </div>
            <div class="panel-body">
                <label for="provider1">First provider</label>
                <form id="provider1">
                    <select name="todo">
                        <option value="todo">todo</option>
                    </select>
                </form>
                <label for="provider2">Second provider</label>
                <form id="provider2">
                    <select name="todo">
                        <option value="todo">todo</option>
                    </select>
                </form>
                <div id="container" style="height: 400px"></div>
                <button id="toggle-btn" type="button" class="btn btn-primary" style="margin: 10px auto">Toggle</button>
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