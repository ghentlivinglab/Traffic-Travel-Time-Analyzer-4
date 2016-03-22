<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="be.ugent.verkeer4.verkeerdomain.data.ProviderEnum" %>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/shared/head.jsp">
            <jsp:param name="title" value="Traject overzicht" />
        </jsp:include>
        <link rel="stylesheet" href="<c:url value="/static/styles/trajectlistcss.css" />"/>
        <script src="<c:url value="/static/scripts/route/overview.js" />"></script>
    </head>
    <body>
        <jsp:include page="/WEB-INF/shared/navigation.jsp" />
        <div class="container-fluid">
            <h1>Route Overview</h1>

            <span>Most recent data from ${overview.recentRouteDateFrom}</span>
            <ul class="nav nav-tabs" role="tablist">
                <li class="nav-item active">
                    <a class="nav-link active" data-toggle="tab" href="#summary" role="tab">Summary</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" data-toggle="tab" href="#provider" role="tab">For each provider</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" data-toggle="tab" href="#route" role="tab">By route</a>
                </li>
            </ul>

            <!-- Tab panes -->
            <div class="tab-content">
                <div class="tab-pane active" id="summary" role="tabpanel">
                    <table class="table sortable">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Distance</th>
                                <th>Standard Travel Time</th>
                                <th>Current Travel Time</th>
                                <th>Delay</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="routeSummaryEntry" items="${overview.summaries}">
                                <tr>
                                    <td>
                                        <a href="detail/${routeSummaryEntry.route.id}">
                                            ${routeSummaryEntry.route.name}
                                        </a>
                                    </td>
                                    <td>
                                        ${routeSummaryEntry.route.distance} m
                                    </td>
                                    <td>
                                        <span class="time" data-time="${routeSummaryEntry.route.defaultTravelTime}">
                                            ${routeSummaryEntry.route.defaultTravelTime}
                                        </span>
                                    </td>
                                    <td>
                                        <span class="time" data-time="${routeSummaryEntry.averageCurrentTravelTime}">
                                            ${routeSummaryEntry.averageCurrentTravelTime}
                                        </span>                                        
                                    </td>
                                    <td class="delay-column">
                                        <div class="cell-background" style="opacity: ${routeSummaryEntry.trafficDelayPercentage}">
                                        </div>                                                                                
                                        <span class="time" data-time="${routeSummaryEntry.delay}">
                                            ${routeSummaryEntry.delay}
                                        </span>
                                    </td>
                                    <td>
                                        <a href="<c:url value="/route/edit/${routeSummaryEntry.route.id}"/>"><span class="btn icon-pencil">Edit</span></a>
                                    </td>                                    
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                </div>
                <div class="tab-pane" id="provider" role="tabpanel">

                    <table class="table sortable perprovidertable">
                        <thead>
                            <tr>
                                <th>
                                    <div class="dropdown">
                                        <div class="button-group">
                                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                                <span class="glyphicon glyphicon-cog"></span>
                                                <span class="caret"></span>
                                            </button>
                                            <ul class="dropdown-menu filterlist">
                                                <li><a class="small nodefault" tabIndex="-1"><label><input class="chkProviderFilter" checked="checked" data-provider="0" type="checkbox"/>&nbsp;TomTom</label></a></li>
                                                <li><a class="small nodefault" tabIndex="-1"><input class="chkProviderFilter" checked="checked" data-provider="5" type="checkbox"/>&nbsp;Waze</a></li>
                                                <li><a class="small nodefault" tabIndex="-1"><input class="chkProviderFilter" checked="checked" data-provider="2" type="checkbox"/>&nbsp;Google</a></li>
                                                <li><a class="small nodefault" tabIndex="-1"><input class="chkProviderFilter" checked="checked" data-provider="1" type="checkbox"/>&nbsp;Here</a></li>
                                                <li><a class="small nodefault" tabIndex="-1"><input class="chkProviderFilter" checked="checked" data-provider="3" type="checkbox"/>&nbsp;Coyote</a></li>
                                                <li><a class="small nodefault" tabIndex="-1"><input class="chkProviderFilter" checked="checked" data-provider="4" type="checkbox"/>&nbsp;Be-mobile</a></li>
                                                <li><a class="small nodefault" tabIndex="-1"><input class="chkProviderFilter" checked="checked" data-provider="6" type="checkbox"/>&nbsp;Bing</a></li>
                                                <li><a class="small nodefault" tabIndex="-1"><input class="chkProviderFilter" checked="checked" data-provider="7" type="checkbox"/>&nbsp;Via Michelin</a></li>
                                            </ul>
                                        </div>                      
                                    </div>
                                    Name</th>
                                <th>Distance</th>
                                <th>Current Travel Time</th>
                                <th>Avg. Delay</th>
                                <th class="provider-header provider-hr" data-provider="0">
                                    <img class="header" src="<c:url value="/static/images/tomtom.png" />"/>
                                    <br/>
                                    CTT
                                </th>
                                <th class="provider-header provider-v delay-column" data-provider="0">
                                    <br/>
                                    D
                                </th>
                                <th class="provider-header provider-hr" data-provider="5">
                                    <img class="header" src="<c:url value="/static/images/waze.png" />"/>
                                    <br/>
                                    CTT
                                </th>
                                <th class="provider-header provider-v delay-column" data-provider="5">
                                    <br/>
                                    D
                                </th>
                                <th class="provider-header provider-hr" data-provider="2">
                                    <img class="header" src="<c:url value="/static/images/gmaps.png" />"/>
                                    <br>
                                    CTT
                                </th>
                                <th class="provider-header provider-v delay-column" data-provider="2">
                                    <br/>
                                    D
                                </th>
                                <th class="provider-header provider-hr" data-provider="1">
                                    <img class="header" src="<c:url value="/static/images/here.png" />"/>
                                    <br/>
                                    CTT
                                </th> 
                                <th class="provider-header provider-v delay-column" data-provider="1">
                                    <br/>
                                    D
                                </th>
                                <th class="provider-header provider-hr" data-provider="3">
                                    <img class="header" src="<c:url value="/static/images/coyote.jpg" />"/>
                                    <br/>
                                    CTT
                                </th>
                                <th class="provider-header provider-v delay-column" data-provider="3">
                                    <br/>
                                    D
                                </th>
                                <th class="provider-header provider-hr" data-provider="4">
                                    <img class="header" src="<c:url value="/static/images/bemobile.png" />"/>
                                    <br/>
                                    CTT
                                </th>
                                <th class="provider-header provider-v delay-column" data-provider="4">
                                    <br/>
                                    D
                                </th>
                                <th class="provider-header provider-hr" data-provider="6">
                                    <img class="header" src="<c:url value="/static/images/bing.png" />"/>
                                    <br/>
                                    CTT
                                </th>
                                <th class="provider-header provider-v delay-column" data-provider="6">
                                    <br/>
                                    D
                                </th>                                
                                <th class="provider-header provider-hr" data-provider="7">
                                    <img class="header" src="<c:url value="/static/images/viamichelin.png" />"/>
                                    <br/>
                                    CTT
                                </th>
                                <th class="provider-header provider-v delay-column" data-provider="7">
                                    <br/>
                                    D
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="routeSummaryEntry" items="${overview.summaries}">
                                <tr>
                                    <td>
                                        <a href="detail/${routeSummaryEntry.route.id}">
                                            ${routeSummaryEntry.route.name}
                                        </a>
                                    </td>
                                    <td>
                                        ${routeSummaryEntry.route.distance} m
                                    </td>
                                    <td>
                                        <span class="time" data-time="${routeSummaryEntry.averageCurrentTravelTime}">
                                            ${routeSummaryEntry.averageCurrentTravelTime}
                                        </span>                                                                                
                                    </td>
                                    <td class="delay-column">
                                        <span class="time" data-time="${routeSummaryEntry.delay}">
                                            ${routeSummaryEntry.delay}
                                        </span>                                                                                                                        
                                    </td>
                                    <td class="provider-cell provider-hr" data-provider="0">                                        
                                        <span class="time" data-time="${routeSummaryEntry.getTravelTimeForProvider(0)}">
                                            ${routeSummaryEntry.getTravelTimeForProvider(0)} 
                                        </span>                                                                                                                        
                                    </td>
                                    <td class="provider-cell provider-v delay-column" data-provider="0">
                                        <div class="cell-background" style="opacity: ${routeSummaryEntry.getTrafficPercentageForProvider(0)}">
                                        </div>                                        
                                        <span class="time" data-time="${routeSummaryEntry.getDelayForProvider(0)}">
                                            ${routeSummaryEntry.getDelayForProvider(0)} 
                                        </span>                                                                                                                        
                                    </td>
                                    <td class="provider-cell provider-hr" data-provider="5">
                                        <span class="time" data-time="${routeSummaryEntry.getTravelTimeForProvider(5)}">
                                            ${routeSummaryEntry.getTravelTimeForProvider(5)}
                                        </span>                                                                                                                        
                                    </td>
                                    <td class="provider-cell provider-v delay-column" data-provider="5">
                                        <div class="cell-background" style="opacity: ${routeSummaryEntry.getTrafficPercentageForProvider(5)}">
                                        </div>                                        
                                        <span class="time" data-time="${routeSummaryEntry.getDelayForProvider(5)}">
                                            ${routeSummaryEntry.getDelayForProvider(5)}
                                        </span>                                                                                                                                                                                                        
                                    </td>
                                    <td class="provider-cell provider-hr" data-provider="2">
                                        <span class="time" data-time="${routeSummaryEntry.getTravelTimeForProvider(2)}">
                                            ${routeSummaryEntry.getTravelTimeForProvider(2)}
                                        </span>
                                    </td>
                                    <td class="provider-cell provider-v delay-column" data-provider="2">
                                        <div class="cell-background" style="opacity: ${routeSummaryEntry.getTrafficPercentageForProvider(2)}">
                                        </div>                                        
                                        <span class="time" data-time="${routeSummaryEntry.getDelayForProvider(2)}">
                                            ${routeSummaryEntry.getDelayForProvider(2)}
                                        </span>
                                    </td>
                                    <td class="provider-cell provider-hr" data-provider="1">
                                        <span class="time" data-time="${routeSummaryEntry.getTravelTimeForProvider(1)}">
                                            ${routeSummaryEntry.getTravelTimeForProvider(1)}
                                        </span>
                                    </td>
                                    <td class="provider-cell provider-v delay-column" data-provider="1">
                                        <div class="cell-background" style="opacity: ${routeSummaryEntry.getTrafficPercentageForProvider(1)}">
                                        </div>                                        
                                        <span class="time" data-time="${routeSummaryEntry.getDelayForProvider(1)}">
                                            ${routeSummaryEntry.getDelayForProvider(1)}
                                        </span>
                                    </td>
                                    <td class="provider-cell provider-hr" data-provider="3">
                                        <span class="time" data-time="${routeSummaryEntry.getTravelTimeForProvider(3)}">
                                            ${routeSummaryEntry.getTravelTimeForProvider(3)}
                                        </span>
                                    </td>
                                    <td class="provider-cell provider-v delay-column" data-provider="3">
                                        <div class="cell-background" style="opacity: ${routeSummaryEntry.getTrafficPercentageForProvider(3)}">
                                        </div>                                        
                                        <span class="time" data-time="${routeSummaryEntry.getDelayForProvider(3)}">
                                            ${routeSummaryEntry.getDelayForProvider(3)}
                                        </span>
                                    </td>
                                    <td class="provider-cell provider-hr" data-provider="4">
                                        <span class="time" data-time="${routeSummaryEntry.getTravelTimeForProvider(4)}">
                                            ${routeSummaryEntry.getTravelTimeForProvider(4)}
                                        </span>
                                    </td>
                                    <td class="provider-cell provider-v delay-column" data-provider="4">
                                        <div class="cell-background" style="opacity: ${routeSummaryEntry.getTrafficPercentageForProvider(4)}">
                                        </div>
                                        <span class="time" data-time="${routeSummaryEntry.getDelayForProvider(4)}">
                                            ${routeSummaryEntry.getDelayForProvider(4)}
                                        </span>
                                    </td>
                                    <td class="provider-cell provider-hr" data-provider="6">
                                        <span class="time" data-time="${routeSummaryEntry.getTravelTimeForProvider(6)}">
                                            ${routeSummaryEntry.getTravelTimeForProvider(6)}
                                        </span>
                                    </td>
                                    <td class="provider-cell provider-v delay-column" data-provider="6">
                                        <div class="cell-background" style="opacity: ${routeSummaryEntry.getTrafficPercentageForProvider(6)}">
                                        </div>                                        
                                        <span class="time" data-time="${routeSummaryEntry.getDelayForProvider(6)}">
                                            ${routeSummaryEntry.getDelayForProvider(6)}
                                        </span>
                                    </td>
                                    <td class="provider-cell provider-hr" data-provider="7">
                                        <span class="time" data-time="${routeSummaryEntry.getTravelTimeForProvider(7)}">
                                            ${routeSummaryEntry.getTravelTimeForProvider(7)}
                                        </span>
                                    </td>
                                    <td class="provider-cell provider-v delay-column" data-provider="7">
                                        <div class="cell-background" style="opacity: ${routeSummaryEntry.getTrafficPercentageForProvider(7)}">
                                        </div>                                        
                                        <span class="time" data-time="${routeSummaryEntry.getDelayForProvider(7)}">
                                            ${routeSummaryEntry.getDelayForProvider(7)}
                                        </span>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="tab-pane" id="route" role="tabpanel">
                    <div class="row">
                        <c:forEach var="routeSummaryEntry" items="${overview.summaries}">
                            <div class="col-sm-3">
                                <div class="panel panel-primary">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">${routeSummaryEntry.route.name}</h3>
                                    </div>
                                    <div class="panel-body">
                                        <ul>
                                            <li>Distance: ${routeSummaryEntry.route.distance} m </li>
                                            <li>Standard Travel Time: ${routeSummaryEntry.route.defaultTravelTime}</li>
                                            <li>Average Delay: ${routeSummaryEntry.averageCurrentTravelTime} </li>
                                            <li>TomTom CTT: ${routeSummaryEntry.getTravelTimeForProvider(0)}</li>
                                            <li>TomTom Delay: ${routeSummaryEntry.getDelayForProvider(0)}</li>
                                            <li>Waze CTT: ${routeSummaryEntry.getTravelTimeForProvider(5)}</li>
                                            <li>Waze Delay: ${routeSummaryEntry.getDelayForProvider(5)}</li>
                                            <li>GoogleMaps CTT: ${routeSummaryEntry.getTravelTimeForProvider(2)}</li>
                                            <li>GoogleMaps Delay: ${routeSummaryEntry.getDelayForProvider(2)}</li>
                                            <li>HereMaps CTT: ${routeSummaryEntry.getTravelTimeForProvider(1)}</li>
                                            <li>HereMaps Delay: ${routeSummaryEntry.getDelayForProvider(1)}</li>
                                            <li>Coyote CTT: ${routeSummaryEntry.getTravelTimeForProvider(3)}</li>
                                            <li>Coyote Delay: ${routeSummaryEntry.getDelayForProvider(3)}</li>
                                            <li>BeMobile CTT: ${routeSummaryEntry.getTravelTimeForProvider(4)}</li>
                                            <li>BeMobile Delay: ${routeSummaryEntry.getDelayForProvider(4)}</li>
                                            <li>BingMaps CTT: ${routeSummaryEntry.getTravelTimeForProvider(6)}</li>
                                            <li>BingMaps Delay: ${routeSummaryEntry.getDelayForProvider(6)}</li>
                                            <li>ViaMichelin CTT: ${routeSummaryEntry.getTravelTimeForProvider(7)}</li>
                                            <li>ViaMichelin Delay: ${routeSummaryEntry.getDelayForProvider(7)}</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
