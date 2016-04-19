<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/shared/head.jsp">
            <jsp:param name="title" value="Log Messages" />
        </jsp:include>

    </head>
    <body>
        <jsp:include page="/WEB-INF/shared/navigation.jsp" />

        <div class="container">
            <!-- first row -->
            <div class="row">
                <div class="col-sm-12">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">Filter Logs</h3>
                        </div>
                        <div class="panel-body">
                            <!-- Row with all filter options -->
                            <div class="row">
                                <div class="col-md-12">
                                    <!-- Filters -->
                                    <div class="row">
                                        <div class="col-md-4">
                                            <label for="category">Select category</label>
                                            <select id="category" class="form-control">
                                                <c:forEach var="logs" items="${logs}">
                                                    <option value="${logs.category}">${logs.category}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-md-4">
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
                                        <div class="col-md-4">
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
                            </div>
                            <!-- Buttons row -->
                            <div class="row">
                                <div class="col-md-12">
                                    <button id="update-btn" type="button" class="btn btn-primary">
                                        <span id="refresh-icon" class="glyphicon glyphicon-refresh"></span>
                                        <span id="update-btn-txt">Update</span>
                                    </button>
                                </div>
                            </div>
                            <hr>
                        </div>
                        <div class="panel-body">
                            <!-- Table -->
                            <table class="table sortable">
                                <thead>
                                    <tr>
                                        <th>Category</th>
                                        <th>Date</th>
                                        <th>Time</th>
                                        <th>Message</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="logHomeEntry" items="${logHomeOverview.logEntries}">
                                        <tr>
                                                <td><a href="#">${logHomeEntry.category}</a></td>
                                                <td>${logHomeEntry.infoCount}</td>
                                                <td>${logHomeEntry.warningCount}</td>
                                                <td>${logHomeEntry.errorCount}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    
                </div>
            </div>
        </div>

        
<script src="<c:url value="/static/scripts/route/logs/logs.js" />"></script>

    </body>
</html>

