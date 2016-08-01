package be.ugent.verkeer4.verkeerdal;

import be.ugent.verkeer4.verkeerdomain.data.Route;
import org.sql2o.Sql2o;

import java.util.List;

public class RouteDbSet extends DbSet<Route> {

    public RouteDbSet(Sql2o sql2o) {
        super(Route.class, sql2o);
    }

    public List<Route> getRoutesInfo() {
        try (org.sql2o.Connection con = sql2o.open()) {
            List<Route> lst = con.createQuery("SELECT Id, Name from " + getTableName() + " ORDER BY Name")
                    .executeAndFetch(Route.class);
            return lst;
        }
    }
}
