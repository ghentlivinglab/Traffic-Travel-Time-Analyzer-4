
package be.ugent.verkeer4.verkeerdal;

import be.ugent.verkeer4.verkeerdomain.data.RouteTrafficJam;
import be.ugent.verkeer4.verkeerdomain.data.RouteTrafficJamCause;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.sql2o.Sql2o;

public class RouteTrafficJamDbSet extends DbSet<RouteTrafficJam> {
    
    public RouteTrafficJamDbSet(Sql2o sql2o) {
        super(RouteTrafficJam.class, sql2o);
    }

    public List<RouteTrafficJam> getRouteTrafficJamsForRouteBetween(int routeId, Date from, Date until) {
        Map<String, Object> parameters= new HashMap<>();
        parameters.put("RouteId", routeId);
        parameters.put("From", from);
        parameters.put("Until", until);
        return this.getItems("RouteId = :RouteId AND JamFrom <= :Until AND JamUntil >= :From", parameters);
    }
}
