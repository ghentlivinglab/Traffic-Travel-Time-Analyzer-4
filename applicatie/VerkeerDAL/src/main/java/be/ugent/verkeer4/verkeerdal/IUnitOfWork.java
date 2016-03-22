package be.ugent.verkeer4.verkeerdal;

public interface IUnitOfWork {

    public RouteDbSet getRouteSet();

    public RouteDataDbSet getRouteDataSet();

    public RouteWaypointDbSet getRouteWaypointSet();

    public POIDbSet getPOISet();
    
    public LogEntryDbSet getLogEntrySet();

    public WeatherDbSet getWeatherSet();
    
    public RouteTrafficJamDbSet getRouteTrafficJamSet();
    
    public RouteTrafficJamCauseDbSet getRouteTrafficJamCauseSet();
    
}

