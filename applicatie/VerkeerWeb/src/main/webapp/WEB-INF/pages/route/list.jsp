<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="be.ugent.verkeer4.verkeerdomain.data.ProviderEnum" %>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/shared/head.jsp">
            <jsp:param name="title" value="Traject overzicht" />
        </jsp:include>
    </head>
    <body>
        <jsp:include page="/WEB-INF/shared/navigation.jsp" />
        <div class="container">
            <h1>Traject Overzicht</h1>
            
            <ul class="nav nav-tabs" role="tablist">
                <li class="nav-item active">
                    <a class="nav-link active" data-toggle="tab" href="#summary" role="tab">Samenvatting</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" data-toggle="tab" href="#provider" role="tab">Per provider</a>
                </li>
            </ul>

            <!-- Tab panes -->
            <div class="tab-content">
                <div class="tab-pane active" id="summary" role="tabpanel">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Naam</th>
                                <th>Afstand</th>
                                <th>Normale Reisduur</th>
                                <th>Huidige Reisduur</th>
                                <th>Vertraging</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="routeSummaryEntry" items="${overview.summaries}">
                                <tr>
                                    <td>
                                        ${routeSummaryEntry.route.id}
                                    </td>
                                    <td>
                                        <a href="detail?id=${routeSummaryEntry.route.id}">
                                            ${routeSummaryEntry.route.name}
                                        </a>
                                    </td>
                                    <td>
                                        ${routeSummaryEntry.route.distance} m
                                    </td>
                                    <td>
                                        ${routeSummaryEntry.route.defaultTravelTime}
                                    </td>
                                    <td>
                                        ${routeSummaryEntry.averageCurrentTravelTime}
                                    </td>
                                    <td>
                                        ${routeSummaryEntry.delay}
                                    </td>
                                    <td>
                                        <a href="<c:url value="/route/edit/${routeSummaryEntry.route.id}"/>"><span class="btn icon-pencil">Wijzig</span></a>
                                    </td>                                    
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                </div>
                <div class="tab-pane" id="provider" role="tabpanel">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Naam</th>
                                <th>Afstand</th>
                                <th>Normale Reistijd</th>
                                <th>Gem. Delay</th>
                                <th colspan="2"><img class="resize" src="../images/tomtom.png"/></th> <!--temp-->
                                <th colspan="2"><img class="resize" src="../images/waze.png"/></th> <!--temp-->
                                <th colspan="2"><img class="resize" src="../images/gmaps.png"/></th> <!--temp-->
                                <th colspan="2"><img class="resize" src="../images/here.png"/></th> <!--temp-->
                                <th colspan="2"><img class="resize" src="../images/coyote.jpg"/></th> <!--temp-->
                                <!--  <th>Normale Reisduur</th>
                                  <th>Huidige Reisduur</th>
                                  <th>Vertraging</th>-->
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>

                                </td>
                                <td>

                                </td>
                                <td>
                                    m
                                </td>
                                <td>
                                    sec
                                <td>
                                    %<!-- Gem. delay -->
                                </td>
                                <td>
                                    HR <!-- tomtom -->
                                </td>
                                <td>
                                    V <!-- tomtom -->
                                </td>
                                <td>
                                    HR <!-- waze -->
                                </td>
                                <td>
                                    V <!-- waze -->
                                </td>
                                <td>
                                    HR <!-- gmaps -->
                                </td>
                                <td>
                                    V <!-- gmaps -->
                                </td>
                                <td>
                                    HR <!-- here -->
                                </td>
                                <td>
                                    V <!-- here -->
                                </td>
                                <td>
                                    HR <!-- coyote -->
                                </td>
                                <td>
                                    V <!-- coyote -->
                                </td>
                                <td>
                            </tr>
                            <c:forEach var="routeSummaryEntry" items="${overview.summaries}">
                                <tr>
                                    <td>
                                        ${routeSummaryEntry.route.id}
                                    </td>
                                    <td>
                                        <a href="detail?id=${routeSummaryEntry.route.id}">
                                            ${routeSummaryEntry.route.name}
                                        </a>
                                    </td>
                                    <td>
                                        ${routeSummaryEntry.route.distance} m
                                    </td>
                                    <td>
                                        ${routeSummaryEntry.averageCurrentTravelTime}
                                    </td>
                                    <td>
                                        ${routeSummaryEntry.delay}
                                    </td>
                                    <td>
                                        ${routeSummaryEntry.getTravelTimeForProvider(0)} 
                                    </td>
                                    <td>
                                        ${routeSummaryEntry.getDelayForProvider(0)} 
                                    </td>
                                    <td>
                                        ${routeSummaryEntry.getTravelTimeForProvider(5)} 
                                    </td>
                                    <td>
                                        ${routeSummaryEntry.getDelayForProvider(5)} 
                                    </td>
                                    <td>
                                        ${routeSummaryEntry.getTravelTimeForProvider(2)} 
                                    </td>
                                    <td>
                                        ${routeSummaryEntry.getDelayForProvider(2)} 
                                    </td>
                                    <td>
                                        ${routeSummaryEntry.getTravelTimeForProvider(1)} 
                                    </td>
                                    <td>
                                        ${routeSummaryEntry.getDelayForProvider(1)} 
                                    </td>
                                    <td>
                                        ${routeSummaryEntry.getTravelTimeForProvider(3)} 
                                    </td>
                                    <td>
                                        ${routeSummaryEntry.getDelayForProvider(3)}
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </body>
</html>
