package be.ugent.verkeer4.verkeerdal;

import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import be.ugent.verkeer4.verkeerdomain.data.composite.RouteSummary;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.sql2o.Query;
import org.sql2o.Sql2o;

public class RouteDataDbSet extends DbSet<RouteData> {

    public RouteDataDbSet(Sql2o sql2o) {
        super(RouteData.class, sql2o);
    }

    public List<RouteData> getItemsForRoute(int routeId, Date from, Date to) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("RouteId", routeId);
        map.put("From", from);
        map.put("To", to);
        
        return this.getItems("RouteId = :RouteId AND Timestamp BETWEEN :From and :To", map);
    }
    
    public List<RouteSummary> getMostRecentSummaries() {
        // http://stackoverflow.com/a/8757062/694640 want inner join is veel te traag door disk seek
        try (org.sql2o.Connection con = sql2o.open()) {
           Query q = con.createQuery("select rd.routeId as routeId, rd.provider as provider, max(FloorToNearest5min(rd.Timestamp)) as timestamp, rd.traveltime as traveltime " +
                                     "from " + getTableName() + " rd " +
                                     "group by rd.routeId, rd.provider desc");
            
            List<RouteSummary> lst = q.executeAndFetch(RouteSummary.class); 
            return lst;
        }
    }
}
