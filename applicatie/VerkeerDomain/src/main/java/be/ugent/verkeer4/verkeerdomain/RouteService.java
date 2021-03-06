package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.LogTypeEnum;
import be.ugent.verkeer4.verkeerdomain.data.composite.BoundingBox;
import be.ugent.verkeer4.verkeerdomain.data.POICategoryEnum;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import be.ugent.verkeer4.verkeerdomain.data.RouteTrafficJam;
import be.ugent.verkeer4.verkeerdomain.data.RouteTrafficJamCause;
import be.ugent.verkeer4.verkeerdomain.data.RouteTrafficJamCauseCategoryEnum;
import be.ugent.verkeer4.verkeerdomain.data.RouteWaypoint;
import be.ugent.verkeer4.verkeerdomain.data.WeatherConditionEnum;
import be.ugent.verkeer4.verkeerdomain.data.composite.POIWithDistanceToRoute;
import be.ugent.verkeer4.verkeerdomain.data.composite.WeatherWithDistanceToRoute;
import be.ugent.verkeer4.verkeerdomain.data.composite.GroupedRouteTrafficJamCause;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.CalculateRouteResponse;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.Leg;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.Point;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.TomTomClient;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteService extends BaseService implements IRouteService {

    public RouteService() throws ClassNotFoundException {
        super();
    }

    /**
     * Geeft alle routes terug
     *
     * @return lijst van route objecten
     * @throws ClassNotFoundException
     */
    @Override
    public List<Route> getRoutes() throws ClassNotFoundException {

        return repo.getRouteSet().getItems();
    }

    public List<Route> getRoutesInfo() throws ClassNotFoundException {
        return repo.getRouteSet().getRoutesInfo();
    }

    /**
     * Geeft de route met gegeven id terug
     *
     * @param id
     * @return een route of null als de route niet gevonden is
     * @throws ClassNotFoundException
     */
    @Override
    public Route getRoute(int id) throws ClassNotFoundException {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("Id", id);
        return repo.getRouteSet().getItem("Id = :Id", parameters);
    }

    /**
     * Voegt een route waypoint toe in de database
     *
     * @param wp
     */
    @Override
    public void insertRouteWaypoint(RouteWaypoint wp) {
        repo.getRouteWaypointSet().insert(wp);
    }

    /**
     * Update de route gegevens voor een bepaalde route
     *
     * @param r
     * @param updateWaypoints geeft aan of de waypoints ook opnieuw moeten
     * opgevraagd worden
     */
    @Override
    public void updateRoute(Route r, boolean updateWaypoints) {
        try {
            repo.getRouteSet().update(r);

            if (updateWaypoints) {
                this.updateWayPoints(r);
            }
        } catch (Exception ex) {
            LogService.getInstance().insert(LogTypeEnum.Error, RouteService.class.getName(), ex.getMessage());
        }
    }

    /**
     * Update de waypoints van een route door de bestaande te verwijderen en de
     * nieuwe waypoints op te vragen tussen de from en to van de route
     *
     * @param r
     * @throws IOException
     * @throws Exception
     */
    private void updateWayPoints(Route r) throws IOException, Exception {
        // delete de huidige waypoints van de route
        repo.getRouteWaypointSet().deleteFromRoute(r.getId());

        // bepaal route gegevens van TomTom
        CalculateRouteResponse response = TomTomClient.GetRoute(r.getFromLatitude(), r.getFromLongitude(), r.getToLatitude(), r.getToLongitude(), false, r.getAvoidHighwaysOrUseShortest());
        if (response != null) {
            UpdateRouteFromTomTom(response, r);
        }
    }

    private void UpdateRouteFromTomTom(CalculateRouteResponse response, Route r) throws Exception {
        be.ugent.verkeer4.verkeerdomain.provider.tomtom.Route tomtomRoute = response.getRoutes().get(0);

        // update de afstand en default travel time op
        r.setDistance(tomtomRoute.getSummary().getLengthInMeters());
        r.setDefaultTravelTime(tomtomRoute.getSummary().getTravelTimeInSeconds());

        repo.getRouteSet().update(r);

        // sla alle waypoints op voor de route
        int idx = 0;
        for (Leg leg : tomtomRoute.getLegs()) {
            for (Point pt : leg.getPoints()) {
                RouteWaypoint wp = new RouteWaypoint();
                wp.setIndex(idx);
                wp.setLatitude(pt.getLatitude());
                wp.setLongitude(pt.getLongitude());
                wp.setRouteId(r.getId());

                this.insertRouteWaypoint(wp);
                System.out.println("Inserting new waypoint " + idx);
                idx++;
            }
        }
    }

    /**
     * Geeft alle route waypoints terug
     *
     * @return
     */
    @Override
    public List<RouteWaypoint> getRouteWaypoints() {
        return repo.getRouteWaypointSet().getItems();
    }

    /**
     * Geeft alle waypoints terug voor de route met gegeven id
     *
     * @param routeId
     * @return
     */
    @Override
    public List<RouteWaypoint> getRouteWaypointsForRoute(int routeId) {
        return repo.getRouteWaypointSet().getWaypointsForRoute(routeId);
    }

    /**
     * Geeft enkel recentste route data terug voor alle routes voor elke
     * provider
     *
     * @param before Als before null is wordt de laatste gegevens gereturned,
     * anders wordt tot op het before tijdstip gekeken
     * @return
     */
    @Override
    public List<RouteData> getMostRecentRouteSummaries(Date before) {
        return repo.getRouteDataSet().getMostRecentSummaries(before);
    }

    /**
     * Geeft de recentste route data terug voor een bepaalde route voor elke
     * provider
     *
     * @param id
     * @return lijst van route data voor een specifiek route
     */
    @Override
    public List<RouteData> getMostRecentRouteSummariesForRoute(int id) {
        return repo.getRouteDataSet().getMostRecentSummaryForRoute(id);
    }

    /**
     * Bepaalt aan de hand van de route waypoints een bounding box waarin alle
     * routes liggen
     *
     * @return
     */
    @Override
    public BoundingBox getBoundingBoxOfAllRoutes() {
        return repo.getRouteWaypointSet().getBoundingBox();
    }

    /**
     * Geeft de files terug voor een bepaalde route tussen de gegeven periode
     *
     * @param routeId
     * @param from
     * @param until
     * @return een lijst van files
     */
    @Override
    public List<RouteTrafficJam> getRouteTrafficJamsForRouteBetween(int routeId, Date from, Date until) {
        List<RouteTrafficJam> jams;
        jams = repo.getRouteTrafficJamSet().getRouteTrafficJamsForRouteBetween(routeId, from, until);

        // als vandaag in de periode zit bereken de file tot op het laatste moment
        if (new Date().before(until)) {
            // alle jams van vandaag zijn nog niet in de RouteTrafficJam opgeslagen
            jams.addAll(getTrafficJamsForDay(routeId, new Date()));
        }
        return jams;
    }

    /**
     * Geeft een lijst van alle oorzaken van alle files voor een route voor een
     * bepaalde periode. De oorzaken zullen gegroepeerd worden op categorie,
     * subcategorie en omschrijving zodat er maar 1 object wordt teruggegeven.
     *
     * @param routeId
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<GroupedRouteTrafficJamCause> getRouteTrafficJamCausesForRouteBetween(int routeId, Date startDate, Date endDate) {
        return repo.getRouteTrafficJamCauseSet().getRouteTrafficJamsCausesForRouteBetween(routeId, startDate, endDate);
    }

    /**
     * Berekent de files voor een bepaalde route voor de gegeven dag
     *
     * @param routeId
     * @param day
     * @return een lijst van files
     */
    private List<RouteTrafficJam> getTrafficJamsForDay(int routeId, Date day) {
        // today    
        Calendar date = new GregorianCalendar();
        date.setTime(day);

        // reset hour, minutes, seconds and millis
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        Date from = date.getTime();
        // next day
        date.add(Calendar.DAY_OF_MONTH, 1);
        Date until = date.getTime();

        return repo.getRouteDataSet().calculateTrafficJams(routeId, from, until,
                Settings.getInstance().getMinimumDelayFromTrafficJam(), Settings.getInstance().getTrafficJamMovingAverageOverXMin());
    }

    // als een last traffic time check null is in een route
    private final Date initialTrafficJamStartPoint = new GregorianCalendar(2016, 01, 01).getTime();

    /**
     * Berekent alle files voor alle dagen kleiner dan vandaag voor een bepaalde
     * route (zodat enkel volledige dagen gefinaliseerd worden) en sla de
     * bekomen files en oorzaken op in de database
     *
     * @param route
     * @param today
     */
    @Override
    public void finalizeTrafficJams(Route route, Date today) {
        Date lastTrafficJamCheck = route.getLastTrafficJamCheck();
        if (lastTrafficJamCheck == null) {
            lastTrafficJamCheck = (Date) initialTrafficJamStartPoint.clone();
        }

        try {
            // TODO pas aan zodat poi service in constructor kan meegegevn worden maar zonder cyclic dependency
            IPOIService poiService = new POIService(this);

            double maxDistanceForPOIRouteMatching = Settings.getInstance().getMaxDistanceForPOIRouteMatching();

            // voor elke dag kleiner dan vandaag
            while (lastTrafficJamCheck.getTime() < today.getTime()) {

                Date from = lastTrafficJamCheck;
                Date until = new Date(from.getTime() + 24 * 60 * 60 * 1000);

                // bereken de files
                List<RouteTrafficJam> jams = repo.getRouteDataSet().calculateTrafficJams(route.getId(), from, until, Settings.getInstance().getMinimumDelayFromTrafficJam(), Settings.getInstance().getTrafficJamMovingAverageOverXMin());
                for (RouteTrafficJam jam : jams) {

                    // voeg traffic jam toe
                    int jamId = repo.getRouteTrafficJamSet().insert(jam);
                    jam.setId(jamId);

                    analyzeNearByPOIsForJamCauses(poiService, jam, maxDistanceForPOIRouteMatching);
                    analyzeNearByWeatherForJamCauses(jam);

                }

                // next day
                lastTrafficJamCheck = until;
            }

            route.setLastTrafficJamCheck(lastTrafficJamCheck);

            repo.getRouteSet().update(route);
        } catch (Exception ex) {
            LogService.getInstance().insert(LogTypeEnum.Error, RouteService.class.getName(), ex.getMessage());
        }
    }

    /**
     * Analyseert alle weersomstandigheden van het dichtst bijzijnd weerstation
     * om te kijken of het weer een invloed kon gehad hebben op de file op die
     * route.
     *
     * @param jam
     */
    private void analyzeNearByWeatherForJamCauses(RouteTrafficJam jam) {

        Map<String, Object> map = new HashMap<>();
        List<WeatherWithDistanceToRoute> lst;
        //Initieel op 0
        double score = 0;
        map.put("Id", jam.getRouteId());

        try {
            //Route ophalen met doorgegeven route id.
            Route route = repo.getRouteSet().getItem("Id = :Id", map);
            if (route != null) {
                //Dichtst bijzijnde weerstation opzoeken en data terughalen van die datum
                lst = repo.getWeatherSet().getWeatherForRoute(route, jam.getFrom());
                if (lst != null && lst.size() > 0) {
                    //Geeft maar 1 rij terug
                    WeatherWithDistanceToRoute weather = lst.get(0);

                    switch(WeatherConditionEnum.fromInt(weather.getWeatherCondition()))
                    {
                        case LightSnowGrains:
                            score += 0.20;
                            break;
                        case LightIceCrystals:
                            score += 0.20;
                            break;
                        case LightIcePellets:
                            score += 0.20;
                            break;
                        case LightHail:
                            score += 0.20;
                            break;
                        case LightMist:
                            score += 0.25;
                            break;
                        case LightFog:
                            score += 0.25;
                            break;
                        case LightFogPatches:
                            score += 0.25;
                            break;
                        case LightBlowingSnow:
                            score += 0.30;
                            break;
                        case LightRainMist:
                            score += 0.30;
                            break;
                        case LightThunderstorm:
                            score += 0.40;
                            break;
                        case LightThunderstormsandRain:
                            score += 0.40;
                            break;
                        case LightThunderstormsandSnow:
                            score += 0.40;
                            break;
                        case HeavyRain:
                            score += 0.35;
                            break;
                        case HeavySnow:
                            score += 0.75;
                            break;
                        case HeavySnowGrains:
                            score += 0.75;
                            break;
                        case HeavyIceCrystals:
                            score += 0.75;
                            break;
                        case HeavyIcePellets:
                            score += 0.75;
                            break;
                        case HeavyHail:
                            score += 0.75;
                            break;
                        case HeavyFog:
                            score += 0.75;
                            break;
                        case HeavyFogPatches:
                            score += 0.75;
                            break;
                        case HeavyBlowingSnow:
                            score += 0.75;
                            break;
                        case HeavyRainMist:
                            score += 0.75;
                            break;
                        case HeavyThunderstorm:
                            score += 0.75;
                            break;
                        case HeavyThunderstormsandRain:
                            score += 0.80;
                            break;
                        case HeavyThunderstormsandSnow:
                            score += 0.80;
                            break;
                    }
  
                    if(weather.getWindSpeed() >= 40)
                        score += 0.3;

                    //Score samenstellen en toevoegen aan de databank.
                    if (score > 0) {
                        RouteTrafficJamCause cause = new RouteTrafficJamCause();
                        cause.setCategory(RouteTrafficJamCauseCategoryEnum.Weather);
                        cause.setRouteTrafficJamId(jam.getId());
                        cause.setSubCategory(weather.getWeatherCondition());
                        cause.setProbability(score);
                        cause.setReferenceId(weather.getId());
                        repo.getRouteTrafficJamCauseSet().insert(cause);
                    }
                }
            }
        } catch (Exception ex) {
            LogService.getInstance().insert(LogTypeEnum.Error, RouteService.class.getName(), ex.getMessage());
        }
    }

    /**
     * *
     * Analyseert alle POI's die dicht bij een route liggen en actief waren
     * tijdens de file en kunnen ze als oorzaak van de file gekozen worden
     *
     * @param poiService
     * @param jam
     * @param maxDistanceForPOIRouteMatching
     */
    private void analyzeNearByPOIsForJamCauses(IPOIService poiService, RouteTrafficJam jam, double maxDistanceForPOIRouteMatching) {

        // TODO determine causes
        double jamDurationSeconds = (jam.getTo().getTime() - jam.getFrom().getTime()) / 1000;

        // geef alle poi's dichtbij de route
        List<POIWithDistanceToRoute> pois = poiService.getPOIsNearRoute(jam.getRouteId(), jam.getFrom(), jam.getTo());

        for (POIWithDistanceToRoute poi : pois) {

            double score = 0;

            double secondsFromStartOfJam = Math.abs(poi.getSince().getTime() - jam.getFrom().getTime()) / 1000;
            double distanceFromRoute = poi.getDistance();

            // TODO score tweaken
            //score += 1 - (distanceFromRoute / maxDistanceForPOIRouteMatching);
            switch (poi.getCategory()) {
                case Accident:
                case Hazard:
                case Incident:
                case PoliceTrap:
                    // als er een accident was dat binnen de 25% van de traffic jam valt
                    if (secondsFromStartOfJam < (0.25 * jamDurationSeconds)) {
                        if (null != poi.getCategory()) // binnen de 1e 25% van de traffic jam
                        {
                            switch (poi.getCategory()) {
                                case Hazard:
                                    score += 0.75;
                                    break;
                                case Accident:
                                    score += 1;
                                    break;
                                case Incident:
                                    score += 0.25;
                                    break;
                                case PoliceTrap:
                                    score += 0.10;
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    break;
                case Construction:
                case LaneClosed:
                case RoadClosed:
                    // poi moet bijna OP het traject liggen, anders is het een andere baan waarop gewerkt wordt
                    if (distanceFromRoute < 0.1) {

                        if (LocalDateTime.ofInstant(poi.getSince().toInstant(), ZoneId.systemDefault()).toLocalDate().equals(LocalDateTime.ofInstant(jam.getFrom().toInstant(), ZoneId.systemDefault()).toLocalDate())) {
                            // construction or something of closed on same day
                            score += 0.75;
                        } else {
                            score += 0.5;
                        }
                    }
                    break;
            }

            // er is een score  voor de poi, sla een oorzaak op voor de poi
            if (score > 0) {
                RouteTrafficJamCause cause = new RouteTrafficJamCause();
                cause.setCategory(RouteTrafficJamCauseCategoryEnum.POI);
                cause.setRouteTrafficJamId(jam.getId());
                cause.setSubCategory(poi.getCategory().getValue());
                cause.setProbability(score);
                cause.setReferenceId(poi.getId());
                repo.getRouteTrafficJamCauseSet().insert(cause);
            }
        }
    }
}
