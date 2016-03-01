package be.ugent.verkeer4.verkeerdal;

import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        return this.getRouteDataAlignedTo5min("RouteId = :RouteId AND Timestamp BETWEEN :From and :To", map);
    }

    public List<RouteData> getRouteDataAlignedTo5min(String condition, Map<String, Object> parameters) {

        try (org.sql2o.Connection con = sql2o.open()) {
            Query q = con.createQuery("select rd.id as id, rd.routeId as routeId, rd.provider as provider, " +
                    "FloorToNearest5min(rd.Timestamp) as timestamp, rd.traveltime as traveltime, rd.delay as delay "
                    + "from " + getTableName() + " rd "
                    + "where " + condition);

            for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
                q.addParameter(parameter.getKey(), parameter.getValue());
            }

            return q.executeAndFetch(RouteData.class);
        }
    }

    public List<RouteData> getMostRecentSummaries() {
        return getMostRecentSummaries(null, null);
    }

    private List<RouteData> getMostRecentSummaries(String condition, Map<String, Object> parameters) {
        // http://stackoverflow.com/a/8757062/694640 want inner join is veel te traag door disk seek
        try (org.sql2o.Connection con = sql2o.open()) {

            String query = "select rd.id as id, rd.routeId as routeId, rd.provider as provider, max(FloorToNearest5min(rd.Timestamp)) as timestamp, " +
                    "rd.traveltime as traveltime, rd.delay as delay "
                    + "from " + getTableName() + " rd ";
            if (condition != null) {
                query += "where " + condition + " ";
            }
            query += "group by rd.routeId, rd.provider desc ";

            Query q = con.createQuery(query);

            if (parameters != null) {
                for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
                    q.addParameter(parameter.getKey(), parameter.getValue());
                }
            }

            List<RouteData> lst = q.executeAndFetch(RouteData.class);
            return lst;
        }
    }

    public List<RouteData> getMostRecentSummaryForRoute(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("RouteId", id);
        return getMostRecentSummaries("RouteId = :RouteId", params);
    }
}
