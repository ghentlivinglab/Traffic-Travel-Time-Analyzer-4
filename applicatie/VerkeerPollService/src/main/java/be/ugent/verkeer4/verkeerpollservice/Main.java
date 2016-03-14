package be.ugent.verkeer4.verkeerpollservice;

import be.ugent.verkeer4.verkeerdomain.IPOIService;
import be.ugent.verkeer4.verkeerdomain.IProviderService;
import be.ugent.verkeer4.verkeerdomain.IRouteService;
import be.ugent.verkeer4.verkeerdomain.IWeatherService;
import be.ugent.verkeer4.verkeerdomain.POIService;
import be.ugent.verkeer4.verkeerdomain.ProviderService;
import be.ugent.verkeer4.verkeerdomain.RouteService;
import be.ugent.verkeer4.verkeerdomain.WeatherService;
import be.ugent.verkeer4.verkeerdomain.data.BoundingBox;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final int EVERY_MILLIS = 300000;

    public static void main(String[] args) throws ClassNotFoundException {

        IRouteService routeService = new RouteService();
        IPOIService poiService = new POIService();
        IWeatherService weatherService = new WeatherService();
        
        IProviderService providerService = new ProviderService(routeService, poiService, weatherService);

        long curTime = new Date().getTime() - EVERY_MILLIS;

        while (true) {

            if (new Date().getTime() - curTime > EVERY_MILLIS) {
                curTime = new Date().getTime();

                try {
                    Logger.getLogger(Main.class.getName()).log(Level.INFO, "Starting poll for POI..");
                    BoundingBox bbox = routeService.getBoundingBoxOfAllRoutes();
                    providerService.pollPOI(bbox);

                } catch (Exception ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    Logger.getLogger(Main.class.getName()).log(Level.INFO, "Starting poll..");
                    providerService.poll();

                } catch (Exception ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                try {
                    Logger.getLogger(Main.class.getName()).log(Level.INFO, "Starting poll for weather..");
                    providerService.pollWeather(51.009651,3.731060);    // 51.009651,3.731060 merelbeke
                    providerService.pollWeather(51.060933,3.797765);    // 51.060933,3.797765 destelbergen
                    providerService.pollWeather(51.050480,3.663300);    // 51.050480,3.663300 drongen
                    providerService.pollWeather(51.195697,3.806934);    // 51.195697,3.806934 zelzate
                    
                } catch (Exception ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }
}
