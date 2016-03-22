package be.ugent.verkeer4.service;

import be.ugent.verkeer4.verkeerdomain.IPOIService;
import be.ugent.verkeer4.verkeerdomain.IRouteService;
import be.ugent.verkeer4.verkeerdomain.POIService;
import be.ugent.verkeer4.verkeerdomain.RouteService;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BackgroundPOIRouteMatcherService extends BaseService {

    private static final int EVERY_MILLIS = 300000;

    private final IRouteService routeService;
    private final IPOIService poiService;

    public BackgroundPOIRouteMatcherService() throws ClassNotFoundException {
        super(EVERY_MILLIS, "POI Matcher");
        routeService = new RouteService();
        poiService = new POIService(routeService);
    }

    @Override
    protected void action() {
        try {
            Logger.getLogger(Main.class.getName()).log(Level.INFO, "Starting POI matching");
            poiService.matchPOIsWithRoute();
            Logger.getLogger(Main.class.getName()).log(Level.INFO, "Finished POI matching");

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
