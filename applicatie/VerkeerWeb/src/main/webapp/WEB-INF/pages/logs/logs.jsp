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
                            <h3 class="panel-title">Status - Errors</h3>
                        </div>
                        <div class="panel-body">
                            <!-- Table -->
                            <table class="table sortable">
                                <thead>
                                    <tr>
                                        <th>Category</th>
                                        <th># Info</th>
                                        <th># Warning</th>
                                        <th># Error</th>
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
        <script>
            MapManagement.intializeRouteMap("map", "<c:url value="/route/mapdata" />");
        </script>

    </body>
</html>

