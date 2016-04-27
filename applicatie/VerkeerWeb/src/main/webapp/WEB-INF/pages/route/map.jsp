<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/shared/head.jsp">
            <jsp:param name="title" value="Map view" />
        </jsp:include>
        <jsp:include page="/WEB-INF/shared/maprequirements.jsp" />
        <script src="<c:url value="/static/scripts/route/map.js" />"></script>            
        <link rel="stylesheet" href="<c:url value="/static/styles/trajectmapcss.css" />"/>
    </head>
    <body>
        <jsp:include page="/WEB-INF/shared/navigation.jsp" />
        <div class="container-fluid">
            <div class="row">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">Map View</h3>
                    </div>
                    <div class="panel-body">


                        <div class="col-md-8">
                            <div class="map" id="map" style="height:800px">

                            </div>        
                        </div>   
                        <div class="col-md-4">
                            <!--   <div class="panel panel-default">
                                      <div class="panel-heading">
                                          <h3 class="panel-title">At a certain point in time</h3>
                                      </div>
                                      <div class="panel-body">
                                          <div class="row">
                                              <div class="col-md-12">
                                                  <div class="row">
                                                      <div class="form-group">
                                                          <div class="col-md-9">
                                                              <div class='input-group date' id='dtpBefore'>
                                                                  <input type='text' class="form-control" id="datetimepicker-begin-input"/>
                                                                  <span class="input-group-addon">
                                                                      <span class="glyphicon glyphicon-calendar"></span>
                                                                  </span>
                                                              </div>
                                                          </div>
      
                                                          <div class="col-md-3">
                                                              <button id="btnUpdate" type="button" class="btn btn-primary">
                                                                  <span id="refresh-icon" class="glyphicon glyphicon-refresh"></span>
                                                                  <span id="update-btn-txt">Update</span>
                                                              </button>
                                                          </div>
                                                      </div>
                                                  </div>
                                              </div>
                                          </div>
                                      </div>   
                                  </div>-->
                            <table class="table sortable">
                                <thead>
                                    <tr>
                                        <th><input name="routecheckAll" type="checkbox" class="routecheckAll" checked="checked"></input>Name</th>
                                        <th>Distance</th>
                                        <th>CTT</th>
                                        <th>Delay</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="routeSummaryEntry" items="${overview.summaries}">
                                        <tr id="row-${routeSummaryEntry.route.id}" data-id="${routeSummaryEntry.route.id}">

                                            <td>
                                                <input name="check${routeSummaryEntry.route.id}" type="checkbox" class="routecheck" checked="checked" data-id="${routeSummaryEntry.route.id}"/>

                                                <a class="routename" href="#" data-id="${routeSummaryEntry.route.id}">${routeSummaryEntry.route.name}</a>
                                            </td>
                                            <td>
                                                ${routeSummaryEntry.route.distance} m
                                            </td>
                                            <td class="row-travelTime">
                                                <span class="label label-info time" data-time="${routeSummaryEntry.averageCurrentTravelTime}">
                                                    ${routeSummaryEntry.averageCurrentTravelTime}
                                                </span>  
                                            </td>
                                            <td class="row-delay">
                                                <span class="label time label-delay" data-time="${routeSummaryEntry.delay}">
                                                    ${routeSummaryEntry.delay}
                                                </span>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>

                        </div>
                    </div>
                </div>                
            </div>

            <script>
                $(document).ready(function () {
                    var map = MapManagement.intializeRouteMap("map", "<c:url value="/route/mapdata" />");

                    $("#dtpBefore").datetimepicker({
                        format: "DD/MM/YYYY HH:mm",
                        showTodayButton: true,
                        showClear: true,
                    });
                    $("#btnUpdate").click(function () {
                        map.updateRoutes($("#dtpBefore").data("DateTimePicker").date().format("YYYY-MM-DD HH:mm"), function (data) {
                            for (var i = 0; i < data.routes.length; i++) {
                                var route = data.routes[i];

                                var tr = document.getElementById("row-" + route.id);
                                $(tr).find(".row-travelTime span")
                                        .replaceWith("<span class='label label-info time' data-time='" + route.averageCurrentTravelTime + "'>" +
                                                route.averageCurrentTravelTime +
                                                "</span>");

                                $(tr).find(".row-delay span")
                                        .replaceWith("<span class='label label-info time' data-time='" + route.currentDelay + "'>" +
                                                route.currentDelay +
                                                "</span>");
                            }
                        });
                    });

                    $(document).on("change", ".routecheck", function () {
                        var id = parseInt($(this).attr("data-id"));
                        map.setRouteVisibility(id, $(this).prop("checked"));
                    }
                    );
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
