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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final int EVERY_MILLIS = 300000;
    private static List<String> stations = new ArrayList<>();

    public static void main(String[] args) throws ClassNotFoundException {

        BackgroundPOIRouteMatcherService poiMatchingService = new BackgroundPOIRouteMatcherService();
        DataScrapingService dataScrapingService = new DataScrapingService();
        WeatherPollService weatherPollService = new WeatherPollService();
        
        poiMatchingService.start();
        dataScrapingService.start();
        weatherPollService.start();

        // run in infinite loop, de services runnen in background threads
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
