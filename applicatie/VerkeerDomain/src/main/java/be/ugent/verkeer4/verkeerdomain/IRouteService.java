package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.composite.BoundingBox;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import be.ugent.verkeer4.verkeerdomain.data.RouteTrafficJam;
import be.ugent.verkeer4.verkeerdomain.data.RouteTrafficJamCause;
import be.ugent.verkeer4.verkeerdomain.data.RouteWaypoint;
import be.ugent.verkeer4.verkeerdomain.data.composite.GroupedRouteTrafficJamCause;
import java.util.Date;
import java.util.List;

public interface IRouteService {

    public List<Route> getRoutes() throws ClassNotFoundException;

    public Route getRoute(int id) throws ClassNotFoundException;
    
    public void insertRouteWaypoint(RouteWaypoint wp);

    public void updateRoute(Route r, boolean updateWaypoints);

    public List<RouteWaypoint> getRouteWaypoints();
    
    public List<RouteData> getMostRecentRouteSummaries();

    public List<RouteWaypoint> getRouteWaypointsForRoute(int id);

    public List<RouteData> getMostRecentRouteSummariesForRoute(int id);

    public BoundingBox getBoundingBoxOfAllRoutes();
    
    public List<RouteTrafficJam> getRouteTrafficJamsForRouteBetween(int routeId, Date from, Date until);

    public void finalizeTrafficJams(Route route, Date today);

    public List<GroupedRouteTrafficJamCause> getRouteTrafficJamCausesForRouteBetween(int id, Date startDate, Date endDate);
}
