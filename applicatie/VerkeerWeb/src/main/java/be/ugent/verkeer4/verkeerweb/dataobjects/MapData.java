package be.ugent.verkeer4.verkeerweb.dataobjects;

import java.util.ArrayList;
import java.util.List;

public class MapData {

    public MapData() {
        routes = new ArrayList<>();
        pois = new ArrayList<>();
    }

    private List<MapRoute> routes;
    private List<MapPOI> pois;

    public List<MapRoute> getRoutes() {
        return routes;
    }

    /**
     * @return the pois
     */
    public List<MapPOI> getPois() {
        return pois;
    }

}
