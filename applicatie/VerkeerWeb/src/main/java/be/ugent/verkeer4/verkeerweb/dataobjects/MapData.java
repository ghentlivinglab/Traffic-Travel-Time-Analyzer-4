package be.ugent.verkeer4.verkeerweb.dataobjects;

import java.util.ArrayList;
import java.util.List;

public class MapData {

    public MapData() {
        routes = new ArrayList<>();
    }
    
    private List<MapRoute> routes;

   
    public List<MapRoute> getRoutes() {
        return routes;
    }
  

}
