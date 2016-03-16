package be.ugent.verkeer4.verkeerdal;

import be.ugent.verkeer4.verkeerdomain.data.POI;
import be.ugent.verkeer4.verkeerdomain.data.POINearRoute;
import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.composite.POIWithDistanceToRoute;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.sql2o.Query;
import org.sql2o.ResultSetHandler;
import org.sql2o.Sql2o;

public class POIDbSet extends DbSet<POI> {

    public POIDbSet(Sql2o sql2o) {
        super(POI.class, sql2o);
    }

    public List<POI> getActivePOI() {
        HashMap<String, Object> map = new HashMap<>();
        return this.getItems("Until is NULL", map);
    }

    public Map<String, POI> getActivePOIPerReferenceIdForProvider(ProviderEnum provider) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Provider", provider.getValue());
        List<POI> pois = this.getItems("Provider = :Provider AND Until is NULL", map);

        HashMap<String, POI> poiByReferenceId = new HashMap<>();
        for (POI poi : pois) {
            poiByReferenceId.put(poi.getReferenceId(), poi);
        }
        return poiByReferenceId;
    }

    public List<POI> getUnmatchedPOIsWithinBoundingBoxWithMarginOfRoute(double maxDistance, int routeId) {
        // vb 0.002 radialen marge =>  40000km/360° * 0.0002 = 0.222km of 222m
        // dus maxDistance * 360° / 40000km = marge 

        double margin = maxDistance * 360 / 40000;
        try (org.sql2o.Connection con = sql2o.open()) {
            Query q = con.createQuery("SELECT p.* FROM poi p "
                    + "JOIN (select min(latitude) - :Margin AS minLat, min(longitude) - :Margin AS minLng, max(latitude) + :Margin AS maxLat, "
                    + "max(longitude) + :Margin AS maxLng from routewaypoint where RouteId = :RouteId) AS bbox "
                    + "ON p.Latitude >= bbox.minLat AND p.Longitude >= bbox.minLng AND p.Latitude < bbox.maxLat AND p.Longitude < bbox.maxLng "
                    + "WHERE p.MatchedWithRoutes = 0 or p.MatchedWithRoutes IS NULL");

            q.addParameter("RouteId", routeId);
            q.addParameter("Margin", margin);
            List<POI> lst = q.executeAndFetch(POI.class);
            return lst;
        }
    }

    public List<POIWithDistanceToRoute> getPOIsNearRoute(int routeId, Date from, Date to, boolean includeActive) {
        try (org.sql2o.Connection con = sql2o.open()) {

            String query = "SELECT p.*, pnr.distance FROM poinearroute pnr "
                    + "JOIN poi p ON pnr.poiid = p.id AND pnr.routeId = :RouteId AND p.Since <= :To and (p.Until >= :From ";

            if (includeActive) {
                query += "or p.Until is null)";
            } else {
                query += ")";
            }

            Query q = con.createQuery(query);

            q.addParameter("RouteId", routeId);
            q.addParameter("From", from);
            q.addParameter("To", to);
            
            List<POIWithDistanceToRoute> lst = q.executeAndFetch(POIWithDistanceToRoute.class);
            return lst;
        }
    }

    public void insertPOINearRoute(POINearRoute near) {
        try (org.sql2o.Connection con = sql2o.open()) {
            Query q = con.createQuery("INSERT INTO poinearroute (POIId, RouteId, Distance) VALUES (:POIId, :RouteId, :Distance)");
            q.addParameter("POIId", near.getPoiId());
            q.addParameter("RouteId", near.getRouteId());
            q.addParameter("Distance", near.getDistance());

            q.executeUpdate();
        }
    }

    public void updatePOIMatchedWithRoute(int poiId, boolean matchedWithRoutes) {
        try (org.sql2o.Connection con = sql2o.open()) {
            Query q = con.createQuery("UPDATE poi SET MatchedWithRoutes = :MatchedWithRoutes WHERE Id = :POIId");
            q.addParameter("MatchedWithRoutes", matchedWithRoutes);
            q.addParameter("POIId", poiId);

            q.executeUpdate();
        }
    }

    public List<POI> getUnmatchedPOIs() {
        return getItems("MatchedWithRoutes = 0 or MatchedWithRoutes is null", null);
    }

}
