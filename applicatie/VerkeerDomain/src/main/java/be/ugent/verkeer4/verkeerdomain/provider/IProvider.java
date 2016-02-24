package be.ugent.verkeer4.verkeerdomain.provider;

import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;


public interface IProvider {
    
    /***
     * 
     * @param route
     * @return 
     */
    public RouteData poll(Route route);
}
