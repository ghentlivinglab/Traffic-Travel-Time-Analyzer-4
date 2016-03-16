package be.ugent.verkeer4.verkeerdal;

import be.ugent.verkeer4.verkeerdomain.data.RouteTrafficJam;
import be.ugent.verkeer4.verkeerdomain.data.RouteTrafficJamCause;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.sql2o.Query;
import org.sql2o.Sql2o;

public class RouteTrafficJamCauseDbSet extends DbSet<RouteTrafficJamCause> {

    public RouteTrafficJamCauseDbSet(Sql2o sql2o) {
        super(RouteTrafficJamCause.class, sql2o);
    }

    public List<RouteTrafficJamCause> getRouteTrafficJamsCausesForRouteBetween(int routeId, Date from, Date until) {
        try (org.sql2o.Connection con = sql2o.open()) {
            String query = "SELECT c.* from routetrafficjamcause c "
                    + "JOIN routetrafficjam j ON j.Id = c.RouteTrafficJamId AND j.RouteId = :RouteId AND JamFrom <= :Until AND JamUntil >= :From";

            Query q = con.createQuery(query);

            q.addParameter("From", from);
            q.addParameter("Until", until);
            q.addParameter("RouteId", routeId);

            return q.executeAndFetch(RouteTrafficJamCause.class);
        }
    }

}
