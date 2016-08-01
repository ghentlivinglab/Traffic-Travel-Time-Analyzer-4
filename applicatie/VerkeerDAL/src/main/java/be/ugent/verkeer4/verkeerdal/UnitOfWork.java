package be.ugent.verkeer4.verkeerdal;

public class UnitOfWork extends BaseRepository implements IUnitOfWork {

    private RouteDbSet routes;
    private RouteDataDbSet routeDatas;
    private RouteWaypointDbSet routeWaypoints;
    private POIDbSet pois;
    private WeatherDbSet weather;
    private LogEntryDbSet logs;

    private RouteTrafficJamDbSet routeTrafficJams;
    private RouteTrafficJamCauseDbSet routeTrafficJamCauses;
    
    public UnitOfWork(String connectionString, String user, String password) throws ClassNotFoundException {
        super(connectionString, user, password);
    }

    @Override
    protected void initializeSets() {
        this.routes = new RouteDbSet(sql2o);
        this.routeDatas = new RouteDataDbSet(sql2o);
        this.routeWaypoints = new RouteWaypointDbSet(sql2o);
        this.pois = new POIDbSet(sql2o);
        this.weather = new WeatherDbSet(sql2o);
        this.routeTrafficJams = new RouteTrafficJamDbSet(sql2o);
        this.routeTrafficJamCauses = new RouteTrafficJamCauseDbSet(sql2o);
        this.logs = new LogEntryDbSet(sql2o);
    }

    @Override
    public RouteDbSet getRouteSet() {
        return this.routes;
    }

    @Override
    public RouteDataDbSet getRouteDataSet() {
        return this.routeDatas;
    }

    @Override
    public RouteWaypointDbSet getRouteWaypointSet() {
        return this.routeWaypoints;
    }
    
    @Override
    public POIDbSet getPOISet() {
        return this.pois;
    }
    
    @Override
    public WeatherDbSet getWeatherSet() {
        return this.weather;
    }
    
    @Override
    public RouteTrafficJamDbSet getRouteTrafficJamSet() {
        return this.routeTrafficJams;
    }
    
    @Override
    public RouteTrafficJamCauseDbSet getRouteTrafficJamCauseSet() {
        return this.routeTrafficJamCauses;
    }
  
    @Override
    public LogEntryDbSet getLogEntrySet() {
        return this.logs;
    }

}
