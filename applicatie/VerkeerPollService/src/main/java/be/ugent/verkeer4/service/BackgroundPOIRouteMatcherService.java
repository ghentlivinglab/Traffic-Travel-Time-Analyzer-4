package be.ugent.verkeer4.service;

import be.ugent.verkeer4.verkeerdomain.IPOIService;
import be.ugent.verkeer4.verkeerdomain.IRouteService;
import be.ugent.verkeer4.verkeerdomain.LogService;
import be.ugent.verkeer4.verkeerdomain.POIService;
import be.ugent.verkeer4.verkeerdomain.RouteService;
import be.ugent.verkeer4.verkeerdomain.data.LogTypeEnum;
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
            LogService.getInstance().insert(LogTypeEnum.Info, BackgroundPOIRouteMatcherService.class.getName(), "Starting POI matching");
            poiService.matchPOIsWithRoute();
            LogService.getInstance().insert(LogTypeEnum.Info, BackgroundPOIRouteMatcherService.class.getName(), "Finished POI matching");

        } catch (Exception ex) {
            LogService.getInstance().insert(LogTypeEnum.Error, BackgroundPOIRouteMatcherService.class.getName(), ex.getMessage());
        }
    }
}
