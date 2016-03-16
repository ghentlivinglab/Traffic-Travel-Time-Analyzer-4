package be.ugent.verkeer4.verkeerdal;

import be.ugent.verkeer4.verkeerdomain.data.composite.BoundingBox;
import be.ugent.verkeer4.verkeerdomain.data.RouteWaypoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.sql2o.Query;
import org.sql2o.Sql2o;

public class RouteWaypointDbSet extends DbSet<RouteWaypoint> {

    public RouteWaypointDbSet(Sql2o sql2o) {
        super(RouteWaypoint.class, sql2o);
    }

    public List<RouteWaypoint> getWaypointsForRoute(int routeId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("RouteId", routeId);

        return this.getItems("RouteId = :RouteId", map);
    }

    public void deleteFromRoute(int routeId) {
        Map<String, Object> map = new HashMap<>();
        map.put("RouteId", routeId);
        this.deleteWhere("RouteId = :RouteId", map);
    }
    
      public BoundingBox getBoundingBox() {
        try (org.sql2o.Connection con = sql2o.open()) {

            Query q = con.createQuery("SELECT min(latitude) as minLatitude, min(longitude) as minLongitude, max(latitude) as maxLatitude, max(longitude) as maxLongitude FROM " + this.getTableName());
            return q.executeAndFetchFirst(BoundingBox.class);
        }
    }
}
