<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/shared/head.jsp">
        <jsp:param name="title" value="Route wijzigen"/>
    </jsp:include>
    <jsp:include page="/WEB-INF/shared/maprequirements.jsp"/>
    <script src="<c:url value="/static/scripts/route/edit.js" />"></script>
</head>
<body>
<jsp:include page="/WEB-INF/shared/navigation.jsp"/>
<div class="container">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">Traject wijzigen</h3>
        </div>
        <div class="panel-body">
            <div class="row">
                <div class="panel panel-default">
                    <form:form method="POST" modelAttribute="obj" commandName="routeEdit">
                        <div class="panel-body">
                            <div class="form-group">
                                <label for="name">Naam</label>
                                <form:input class="form-control" path="name"/>
                                <span>Opgelet: De naam moet overeenstemmen met het traject in Coyote om correct de gegevens te kunnen uitlezen</span>
                                <form:errors path="name" cssClass="error"/>
                            </div>

                            <div class="form-group">
                                <div class="row">
                                    <div class="col-md-4">
                                        <label for="fromAddress">Van</label>
                                        <form:input class="form-control" path="fromAddress" id="fromAddress"/>
                                        <form:input class="form-control" path="fromLatLng" id="fromLatLng"/>

                                        <form:errors path="fromAddress" cssClass="error"/>
                                        <form:errors path="fromLatLng" cssClass="error"/>
                                    </div>
                                    <div class="col-md-8">
                                        <span>Klik rechts op de map om de positie te verplaatsen</span>
                                        <div id="fromMap" style="height:300px"></div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="row">
                                    <div class="col-md-4">
                                        <label for="toAddress">Naar</label>
                                        <form:input class="form-control" path="toAddress" id="toAddress"/>
                                        <form:input class="form-control" path="toLatLng" id="toLatLng"/>

                                        <form:errors path="toAddress" cssClass="error"/>
                                        <form:errors path="toLatLng" cssClass="error"/>
                                    </div>
                                    <div class="col-md-8">
                                        <span>Klik rechts op de map om de positie te verplaatsen</span>
                                        <div id="toMap" style="height:300px"></div>
                                    </div>
                                </div>
                            </div>

                        </div>

                        <div class="panel-footer">
                            <button type="submit" class="btn btn-primary">Opslaan</button>
                            <a href="${pageContext.servletContext.contextPath}/route/detail?id=${routeEdit.id}">Traject details</a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    RouteEdit.bindLatLngBox("fromLatLng", "fromMap", "fromAddress");
    RouteEdit.bindLatLngBox("toLatLng", "toMap", "toAddress");
</script>
</body>
</html>
