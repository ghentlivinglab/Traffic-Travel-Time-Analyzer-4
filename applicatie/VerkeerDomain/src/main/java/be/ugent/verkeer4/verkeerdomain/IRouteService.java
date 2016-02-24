package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteWaypoint;
import be.ugent.verkeer4.verkeerdomain.data.composite.RouteSummary;
import java.util.List;

public interface IRouteService {

    public List<Route> getRoutes() throws ClassNotFoundException;

    public Route getRoute(int id) throws ClassNotFoundException;
    
    public void insertRouteWaypoint(RouteWaypoint wp);

    public void updateRoute(Route r, boolean updateWaypoints);

    public List<RouteWaypoint> getRouteWaypoints();
    
    public List<RouteSummary> getMostRecentRouteSummaries();

    public List<RouteWaypoint> getRouteWaypointsForRoute(int id);

    public List<RouteSummary> getMostRecentRouteSummariesForRoute(int id);
}
