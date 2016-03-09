package be.ugent.verkeer4.verkeerdal;

import be.ugent.verkeer4.verkeerdomain.data.BoundingBox;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import org.sql2o.Query;
import org.sql2o.Sql2o;

public class RouteDbSet extends DbSet<Route> {

    public RouteDbSet(Sql2o sql2o) {
        super(Route.class, sql2o);
    }
}
