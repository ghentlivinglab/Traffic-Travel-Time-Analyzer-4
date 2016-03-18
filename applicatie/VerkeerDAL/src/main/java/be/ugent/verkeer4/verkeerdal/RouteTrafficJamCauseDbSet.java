package be.ugent.verkeer4.verkeerdal;

import be.ugent.verkeer4.verkeerdomain.data.RouteTrafficJam;
import be.ugent.verkeer4.verkeerdomain.data.RouteTrafficJamCause;
import be.ugent.verkeer4.verkeerdomain.data.composite.GroupedRouteTrafficJamCause;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.sql2o.Query;
import org.sql2o.Sql2o;

public class RouteTrafficJamCauseDbSet extends DbSet<RouteTrafficJamCause> {

    public RouteTrafficJamCauseDbSet(Sql2o sql2o) {
        super(RouteTrafficJamCause.class, sql2o);
    }

    public List<GroupedRouteTrafficJamCause> getRouteTrafficJamsCausesForRouteBetween(int routeId, Date from, Date until) {
        try (org.sql2o.Connection con = sql2o.open()) {
            String query = 
                    "SELECT cc.routetrafficjamid, cc.category, cc.subcategory, cc.description, avg(cc.probability) as avgProbability, "
                    + "GROUP_CONCAT(distinct cc.provider SEPARATOR ',') as providers from  "
                    + "	(SELECT c.*, "
                    + "	case  "
                    + "	 	when p.id is not null then p.info "
                    + "	 end as description, "
                    + "	 case  "
                    + "	 	when p.id is not null then p.provider "
                    + "	 end as provider "
                    + "	 FROM routetrafficjamcause c "
                    + "	 JOIN routetrafficjam j ON j.Id = c.RouteTrafficJamId AND j.RouteId = :RouteId AND JamFrom <= :Until AND JamUntil >= :From "
                    + "	 LEFT JOIN poi p on p.id = c.referenceid "
                    + "	  "
                    + "	) as cc  "
                    + "group by cc.routetrafficjamid, cc.category, cc.subcategory, cc.description";

            Query q = con.createQuery(query);

            q.addParameter("From", from);
            q.addParameter("Until", until);
            q.addParameter("RouteId", routeId);

            return q.executeAndFetch(GroupedRouteTrafficJamCause.class);
        }
    }

}
