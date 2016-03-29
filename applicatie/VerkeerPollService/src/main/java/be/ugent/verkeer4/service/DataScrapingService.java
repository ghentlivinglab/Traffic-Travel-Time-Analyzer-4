package be.ugent.verkeer4.service;

import be.ugent.verkeer4.verkeerdomain.IPOIService;
import be.ugent.verkeer4.verkeerdomain.IProviderService;
import be.ugent.verkeer4.verkeerdomain.IRouteService;
import be.ugent.verkeer4.verkeerdomain.LogService;
import be.ugent.verkeer4.verkeerdomain.POIService;
import be.ugent.verkeer4.verkeerdomain.ProviderService;
import be.ugent.verkeer4.verkeerdomain.RouteService;
import be.ugent.verkeer4.verkeerdomain.data.LogTypeEnum;
import be.ugent.verkeer4.verkeerdomain.data.composite.BoundingBox;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataScrapingService  extends BaseService {
    
    
    private static final int EVERY_MILLIS = 300000;

    private final IRouteService routeService;
    private final IPOIService poiService;
    private final IProviderService providerService;

    public DataScrapingService() throws ClassNotFoundException {
        super(EVERY_MILLIS, "Data scraping");
        routeService = new RouteService();
        poiService = new POIService(routeService);
        providerService = new ProviderService(routeService,poiService);
    }

    @Override
    protected void action() {
          try {
            LogService.getInstance().insert(LogTypeEnum.Info, "DataScrapingService Error", "Start poll for POI...");
            BoundingBox bbox = routeService.getBoundingBoxOfAllRoutes();
            bbox.inflate(0.002); // increase lat,lng with 0.002 padding on each side
            providerService.pollPOI(bbox);
            
        } catch (Exception ex) {
            LogService.getInstance().insert(LogTypeEnum.Error, "DataScrapingService Error", ex.getMessage());
        }
        
        try {
            LogService.getInstance().insert(LogTypeEnum.Info, "DataScrapingService Error", "Start poll...");
            providerService.poll();
            
        } catch (Exception ex) {
            LogService.getInstance().insert(LogTypeEnum.Error, "DataScrapingService Error", ex.getMessage());
        }
    }
}
