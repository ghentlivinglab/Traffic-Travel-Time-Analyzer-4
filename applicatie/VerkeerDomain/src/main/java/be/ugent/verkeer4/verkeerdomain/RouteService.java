package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.BoundingBox;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import be.ugent.verkeer4.verkeerdomain.data.RouteTrafficJam;
import be.ugent.verkeer4.verkeerdomain.data.RouteWaypoint;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.CalculateRouteResponse;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.Leg;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.Point;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.TomTomClient;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RouteService extends BaseService implements IRouteService {

    public RouteService() throws ClassNotFoundException {
        super();
    }

    @Override
    public List<Route> getRoutes() throws ClassNotFoundException {

        return repo.getRouteSet().getItems();
    }

    @Override
    public Route getRoute(int id) throws ClassNotFoundException {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("Id", id);
        return repo.getRouteSet().getItem("Id = :Id", parameters);
    }

    @Override
    public void insertRouteWaypoint(RouteWaypoint wp) {
        repo.getRouteWaypointSet().insert(wp);
    }

    @Override
    public void updateRoute(Route r, boolean updateWaypoints) {
        try {
            repo.getRouteSet().update(r);

            if (updateWaypoints) {
                this.updateWayPoints(r);
            }
        } catch (Exception ex) {
            Logger.getLogger(RouteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateWayPoints(Route r) throws IOException, Exception {
        repo.getRouteWaypointSet().deleteFromRoute(r.getId());
        CalculateRouteResponse response = TomTomClient.GetRoute(r.getFromLatitude(), r.getFromLongitude(), r.getToLatitude(), r.getToLongitude(), false, r.getAvoidHighwaysOrUseShortest());
        be.ugent.verkeer4.verkeerdomain.provider.tomtom.Route tomtomRoute = response.getRoutes().get(0);
        r.setDistance(tomtomRoute.getSummary().getLengthInMeters());
        r.setDefaultTravelTime(tomtomRoute.getSummary().getTravelTimeInSeconds());

        repo.getRouteSet().update(r);

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

    @Override
    public List<RouteWaypoint> getRouteWaypoints() {
        return repo.getRouteWaypointSet().getItems();
    }

    @Override
    public List<RouteWaypoint> getRouteWaypointsForRoute(int routeId) {
        return repo.getRouteWaypointSet().getWaypointsForRoute(routeId);
    }

    @Override
    public List<RouteData> getMostRecentRouteSummaries() {
        return repo.getRouteDataSet().getMostRecentSummaries();
    }

    @Override
    public List<RouteData> getMostRecentRouteSummariesForRoute(int id) {
        return repo.getRouteDataSet().getMostRecentSummaryForRoute(id);
    }

    @Override
    public BoundingBox getBoundingBoxOfAllRoutes() {
        return repo.getRouteWaypointSet().getBoundingBox();
    }

    @Override
    public List<RouteTrafficJam> getRouteTrafficJamsForRouteBetween(int routeId, Date from, Date until) {
        List<RouteTrafficJam> jams;
        jams = repo.getRouteTrafficJamSet().getRouteTrafficJamsForRouteBetween(routeId, from, until);

        if (new Date().before(until)) {
            // alle jams van vandaag zijn nog niet in de RouteTrafficJam opgeslagen
            jams.addAll(getTrafficJamsForDay(routeId, new Date()));
        }
        return jams;
    }

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

    private final Date initialTrafficJamStartPoint =  new GregorianCalendar(2016, 01, 01).getTime();
    
    @Override
    public void finalizeTrafficJams(Route route, Date today) {
        Date lastTrafficJamCheck = route.getLastTrafficJamCheck();
        if (lastTrafficJamCheck == null) {
            lastTrafficJamCheck = (Date) initialTrafficJamStartPoint.clone();
        }

        while (lastTrafficJamCheck.getTime() < today.getTime()) {

            Date from = lastTrafficJamCheck;
            Date until = new Date(from.getTime() + 24 * 60 * 60 * 1000);

            List<RouteTrafficJam> jams =  repo.getRouteDataSet().calculateTrafficJams(route.getId(), from, until, Settings.getInstance().getMinimumDelayFromTrafficJam(), Settings.getInstance().getTrafficJamMovingAverageOverXMin());
            for (RouteTrafficJam jam : jams) {
                
                // TODO determine causes
                
                repo.getRouteTrafficJamSet().insert(jam);
            }
            
            // next day
            lastTrafficJamCheck  = until;
        }

        route.setLastTrafficJamCheck(lastTrafficJamCheck);
        try {
            repo.getRouteSet().update(route);
        } catch (Exception ex) {
            Logger.getLogger(RouteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
