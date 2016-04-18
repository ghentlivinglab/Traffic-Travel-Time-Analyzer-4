package be.ugent.verkeer4.verkeerdal;

import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import be.ugent.verkeer4.verkeerdomain.data.RouteTrafficJam;
import java.util.ArrayList;
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

    // Todo refactor beide overloads zodat andere opties dan where beter geregeld worden
    public List<RouteData> getItemsForRoute(int routeId, Date from, Date to, String order) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("RouteId", routeId);
        map.put("From", from);
        map.put("To", to);
        return this.getItems("RouteId = :RouteId AND Timestamp BETWEEN :From and :To", map, order);
    }

    public List<RouteData> getItemsForRoute(int routeId, Date from, Date to, String order, int[] providers) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("RouteId", routeId);
        map.put("From", from);
        map.put("To", to);

        StringBuilder builder = new StringBuilder();
        for (int i=0; i<providers.length-1; i++) {
            builder.append(i);
            builder.append(',');
        }
        builder.append(providers[providers.length-1]);
        map.put("providers", builder.toString());

        return this.getItems("RouteId = :RouteId AND Timestamp BETWEEN :From and :To AND Provider in (:providers)", map, order);
    }

    public List<RouteData> getRouteDataAlignedTo5min(String condition, Map<String, Object> parameters) {
        // Opgelet: door de functie is dit stukken trager!
        try (org.sql2o.Connection con = sql2o.open()) {
            Query q = con.createQuery("select rd.id as id, rd.routeId as routeId, rd.provider as provider, "
                    + "FloorToNearest5min(rd.Timestamp) as timestamp, rd.traveltime as traveltime, rd.delay as delay "
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
        try (org.sql2o.Connection con = sql2o.open()) {

            String where = "";
            if (condition != null) {
                where += "WHERE " + condition + " ";
            }
            String query;// = "select * from " + getTableName() + " where id in (select max(rd.id) from " + getTableName() + " rd " + where + " group by routeId, provider) ";
            query = "select r.* from routedata r join (select max(rd.id) as mid from routedata rd " + where + " group by routeId, provider) as tmp on tmp.mid = r.id";
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

    private class RouteDataTrafficJamEntry {

        private Date timestamp;
        private double avgDelay;
        private double movingAverage;
        private boolean trafficjam;

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }

        public double getAvgDelay() {
            return avgDelay;
        }

        public void setAvgDelay(double avgDelay) {
            this.avgDelay = avgDelay;
        }

        public double getMovingAverage() {
            return movingAverage;
        }

        public void setMovingAverage(double movingAverage) {
            this.movingAverage = movingAverage;
        }

        public boolean isTrafficjam() {
            return trafficjam;
        }

        public void setTrafficjam(boolean trafficjam) {
            this.trafficjam = trafficjam;
        }

    }

    public List<RouteTrafficJam> calculateTrafficJams(int routeId, Date from, Date until, double minDelayForTrafficJam, double movingAverageOverXMin) {
        try (org.sql2o.Connection con = sql2o.open()) {
            String query = "SELECT x.Timestamp as Timestamp, x.avgDelay as avgDelay, avg(y.avgDelay) as movingAverage, CASE WHEN avg(y.avgDelay) > :MinDelayForTrafficJam THEN 1 ELSE 0 END AS trafficjam "
                    + " FROM (SELECT TIMESTAMP, avg(delay) AS avgDelay FROM routedata "
                    + "       WHERE routeid = :RouteId AND Timestamp BETWEEN :From AND :Until "
                    + "       GROUP BY FloorToNearest5min(TIMESTAMP)) AS x "
                    + " JOIN"
                    + "       (SELECT TIMESTAMP, avg(delay) AS avgDelay FROM routedata "
                    + "        WHERE routeid = :RouteId AND TIMESTAMP BETWEEN :From AND :Until "
                    + "        GROUP BY FloorToNearest5min(TIMESTAMP)) AS y "
                    + " ON y.Timestamp BETWEEN x.Timestamp - INTERVAL :WindowSizeMin MINUTE AND x.Timestamp + INTERVAL :WindowSizeMin MINUTE "
                    + " GROUP BY x.Timestamp, x.avgDelay "
                    + " ORDER BY x.Timestamp";

            Query q = con.createQuery(query);

            q.addParameter("From", from);
            q.addParameter("Until", until);
            q.addParameter("RouteId", routeId);
            q.addParameter("MinDelayForTrafficJam", minDelayForTrafficJam);
            q.addParameter("WindowSizeMin", movingAverageOverXMin / 2);

            List<RouteDataTrafficJamEntry> lst = q.executeAndFetch(RouteDataTrafficJamEntry.class);

            List<RouteTrafficJam> jams = new ArrayList<>();

            if (lst.size() <= 0) {
                return jams;
            }

            boolean curTrafficJam = lst.get(0).trafficjam;
            Date trafficJamStart = (Date) from.clone();
            double totalTrafficDelay = curTrafficJam ? lst.get(0).avgDelay : 0;
            int nrOfItems = curTrafficJam ? 1 : 0;
            double maxTrafficDelay = 0;

            for (int i = 1; i < lst.size(); i++) {
                RouteDataTrafficJamEntry entry = lst.get(i);
                if (curTrafficJam) {
                    if (entry.isTrafficjam()) {
                        totalTrafficDelay += entry.getAvgDelay();
                        if (maxTrafficDelay < entry.getAvgDelay()) {
                            maxTrafficDelay = entry.getAvgDelay();
                        }
                        nrOfItems++;
                    } else {
                        // end of traffic jam

                        RouteTrafficJam jam = new RouteTrafficJam();
                        jam.setRouteId(routeId);
                        jam.setFrom(trafficJamStart);
                        jam.setTo(entry.getTimestamp());
                        jam.setAvgDelay(totalTrafficDelay / nrOfItems);
                        jam.setMaxDelay(maxTrafficDelay);
                        jams.add(jam);
                        curTrafficJam = entry.isTrafficjam();
                    }
                } else if (entry.isTrafficjam()) {
                    // start of traffic jam, reset current traffic variables
                    totalTrafficDelay = entry.getAvgDelay();
                    nrOfItems = 1;
                    maxTrafficDelay = entry.getAvgDelay();
                    trafficJamStart = entry.getTimestamp();
                    curTrafficJam = entry.isTrafficjam();
                } else {
                    // geen traffic jam, was ook geen traffic jam
                }

            }

            if (curTrafficJam) {
                // finalize pending traffic jam
                RouteTrafficJam jam = new RouteTrafficJam();
                jam.setRouteId(routeId);
                jam.setFrom(trafficJamStart);
                jam.setTo(lst.get(lst.size() - 1).getTimestamp());
                jam.setAvgDelay(totalTrafficDelay / nrOfItems);
                jam.setMaxDelay(maxTrafficDelay);
                jams.add(jam);
            }

            return jams;
        }
    }
}
