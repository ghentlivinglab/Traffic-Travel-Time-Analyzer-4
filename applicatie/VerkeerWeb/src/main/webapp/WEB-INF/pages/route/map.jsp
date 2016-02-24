<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/shared/head.jsp">
            <jsp:param name="title" value="Traject overzicht" />
        </jsp:include>
        <jsp:include page="/WEB-INF/shared/maprequirements.jsp" />
        <script src="<c:url value="/static/scripts/route/map.js" />"></script>            
    </head>
    <body>
        <jsp:include page="/WEB-INF/shared/navigation.jsp" />
        <div class="container">
            <h1>Traject map</h1>



            <div class="row">
                <div class="col-md-8">
                    <div class="map" id="map" style="height:800px">

                    </div>        
                </div>   
                <div class="col-md-4">
                    <c:if test="${not empty routes}">

                        <table class="table">
                            <thead>
                                <tr>
                                    <th><input name="routecheckAll" type="checkbox" class="routecheckAll" checked="checked"></input>Naam</th>
                                    <th>Afstand</th>
                                    <th>Vertraging</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="route" items="${routes}">
                                    <tr data-id="${route.id}">

                                        <td>
                                            <input name="check${route.id}" type="checkbox" class="routecheck" checked="checked" data-id="${route.id}"/>

                                            <a class="routename" href="#" data-id="${route.id}">${route.name}</a>
                                        </td>
                                        <td>
                                            ${route.distance} m
                                        </td>
                                        <td>
                                            ${route.distance} m
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                </div>
            </div>
        </div>

        <script>
            $(document).ready(function () {
                var map = MapManagement.intializeRouteMap("map", "<c:url value="/route/mapdata" />");

                $(document).on("change", ".routecheck", function () {
                    var id = parseInt($(this).attr("data-id"));
                    map.setRouteVisibility(id, $(this).prop("checked"));
                });

                $(document).on("click", ".routename", function () {
                    var id = parseInt($(this).attr("data-id"));
                    map.centerMapOnRoute(id);
                });

                $(document).on("change", ".routecheckAll", function () {
                    $(".routecheck").prop("checked", $(this).prop("checked"));
                    $(".routecheck").change();
                });
            });
        </script>
    </body>
</html>
