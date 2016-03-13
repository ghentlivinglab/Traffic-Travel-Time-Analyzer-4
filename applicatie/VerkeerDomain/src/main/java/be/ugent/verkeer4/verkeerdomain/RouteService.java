package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.BoundingBox;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import be.ugent.verkeer4.verkeerdomain.data.RouteWaypoint;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.CalculateRouteResponse;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.Leg;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.Point;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.TomTomClient;
import java.io.IOException;
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

}
