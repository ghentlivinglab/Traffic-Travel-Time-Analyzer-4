package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.composite.BoundingBox;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import java.util.Date;
import java.util.List;

public interface IProviderService {

    List<RouteData> getRouteDataForRoute(int routeId, Date from, Date to, String order);
    List<RouteData> getRouteDataForRoute(int routeId, Date from, Date to, String order, int[] providers);

    void poll() throws ClassNotFoundException;
    
    
    public void pollPOI(BoundingBox bbox) throws ClassNotFoundException;
    
}
