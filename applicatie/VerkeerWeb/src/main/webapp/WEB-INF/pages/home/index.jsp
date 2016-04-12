<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/shared/head.jsp">
            <jsp:param name="title" value="Home - Dashboard" />
        </jsp:include>
        <jsp:include page="/WEB-INF/shared/maprequirements.jsp"/>


        <script src="<c:url value="/static/scripts/route/map.js" />"></script>


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
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Category</th>
                                        <th># Info</th>
                                        <th># Warning</th>
                                        <th># Error</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="logHomeEntry.catgory" items="${logHomeOverview.logEntries}">
                                        <tr>
                                                <td>${logHomeEntry.catgory}</td>
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
            <!-- second row -->
            <div class="row">
                <div class="col-sm-6">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">POI Overview</h3>
                        </div>
                        <div class="panel-body">
                            <table class="table table-striped table-bordered table-hover" width="744">
                                <thead>
                                    <tr>
                                        <th></th>
                                        <th>Name</th>
                                        <th>Traffic Jam</th>
                                        <th>Delay</th>
                                        <th>Remarks</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr class="danger">
                                        <td><a href="#">Detail</a></td>
                                        <td><a href="#">Gasmeterlaan (R40) eastbound</a></td>
                                        <td>15 km</td>
                                        <td>5' 12"</td>
                                        <td>Accident</td>
                                    </tr>
                                    <tr class="info">
                                        <td><a href="#">Detail</a></td>
                                        <td><a href="#">Gasmeterlaan (R40) eastbound</a></td>
                                        <td>15 km</td>
                                        <td>5' 12"</td>
                                        <td></td>
                                    </tr>
                                    <tr class="danger">
                                        <td><a href="#">Detail</a></td>
                                        <td><a href="#">Gasmeterlaan (R40) eastbound</a></td>
                                        <td>15 km</td>
                                        <td>5' 12"</td>
                                        <td>Accident</td>
                                    </tr>
                                    <tr class="info">
                                        <td><a href="#">Detail</a></td>
                                        <td><a href="#">Gasmeterlaan (R40) eastbound</a></td>
                                        <td>15 km</td>
                                        <td>5' 12"</td>
                                        <td>Trajectcontrole</td>
                                    </tr>
                                    <tr class="warning">
                                        <td><a href="#">Detail</a></td>
                                        <td><a href="#">Gasmeterlaan (R40) eastbound</a></td>
                                        <td>15 km</td>
                                        <td>5' 12"</td>
                                        <td>Wegenwerken</td>
                                    </tr>
                                    <tr class="warning">
                                        <td><a href="#">Detail</a></td>
                                        <td><a href="#">Gasmeterlaan (R40) eastbound</a></td>
                                        <td>15 km</td>
                                        <td>5' 12"</td>
                                        <td>Autovrij weekend</td>
                                    </tr>
                                    <tr class="info">
                                        <td><a href="#">Detail</a></td>
                                        <td><a href="#">Gasmeterlaan (R40) eastbound</a></td>
                                        <td>15 km</td>
                                        <td>5' 12"</td>
                                        <td>Omloop Nieuwsblad</td>
                                    </tr>
                                </tbody>
                            </table>

                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">Minimap</h3>
                        </div>
                        <div class="panel-body">
                            <div id="map" style="height:500px; width: 100%"></div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- third row -->
            <div class="row">
                <div class="col-sm-6">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">Wheater</h3>
                        </div>
                        <div class="panel-body">
                            <!--<span style="display: block !important; width: 180px; text-align: center; font-family: sans-serif; font-size: 12px;"><a href="http://dutch.wunderground.com/cgi-bin/findweather/getForecast?query=zmw:00000.7.06434&bannertypeclick=wu_blueglass" title="Gent, Belgium Weather Forecast" target="_blank"><img src="http://weathersticker.wunderground.com/weathersticker/cgi-bin/banner/ban/wxBanner?bannertype=wu_blueglass_metric&airportcode=EBCV&ForcedCity=Gent&ForcedState=Belgium&wmo=06434&language=NL" alt="Find more about Weather in Gent, BX" width="160" /></a><br></span>
                            -->

                            <span style="display: block !important; width: 320px; text-align: center; font-family: sans-serif; font-size: 12px;"><a href="http://dutch.wunderground.com/cgi-bin/findweather/getForecast?query=zmw:94101.1.99999&bannertypeclick=wu_clean2day" title="San Francisco, California Weather Forecast" target="_blank"><img src="http://weathersticker.wunderground.com/weathersticker/cgi-bin/banner/ban/wxBanner?bannertype=wu_clean2day_metric_cond&airportcode=KSFO&ForcedCity=San Francisco&ForcedState=CA&zip=94101&language=NL" alt="Find more about Weather in San Francisco, CA" width="300" /></a><br><a href="http://dutch.wunderground.com/cgi-bin/findweather/getForecast?query=zmw:94101.1.99999&bannertypeclick=wu_clean2day" title="Get latest Weather Forecast updates" style="font-family: sans-serif; font-size: 12px" target="_blank">Click for weather forecast</a></span>


                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">Twitter Wall</h3>
                        </div>
                        <div class="panel-body">
                            <a class="twitter-timeline" href="https://twitter.com/VerkeerGentB" data-widget-id="709061375188930561">Tweets by @VerkeerGentB</a>
                            <script>!function (d, s, id) {
                                    var js, fjs = d.getElementsByTagName(s)[0], p = /^http:/.test(d.location) ? 'http' : 'https';
                                    if (!d.getElementById(id)) {
                                        js = d.createElement(s);
                                        js.id = id;
                                        js.src = p + "://platform.twitter.com/widgets.js";
                                        fjs.parentNode.insertBefore(js, fjs);
                                    }
                                }(document, "script", "twitter-wjs");</script>
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

