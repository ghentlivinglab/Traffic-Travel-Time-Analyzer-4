package be.ugent.verkeer4.verkeerdal;

public interface IUnitOfWork {

    public RouteDbSet getRouteSet();    
    
    public RouteDataDbSet getRouteDataSet();    
    
    public RouteWaypointDbSet getRouteWaypointSet();
}
