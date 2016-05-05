package be.ugent.verkeer4.service;

import be.ugent.verkeer4.verkeerdomain.LogService;
import be.ugent.verkeer4.verkeerdomain.Settings;
import be.ugent.verkeer4.verkeerdomain.data.LogTypeEnum;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.provider.BingMapsProvider;
import be.ugent.verkeer4.verkeerdomain.provider.GoogleProvider;
import be.ugent.verkeer4.verkeerdomain.provider.HereMapsProvider;
import be.ugent.verkeer4.verkeerdomain.provider.TomTomProvider;
import be.ugent.verkeer4.verkeerdomain.provider.WeatherProvider;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

        /*  for (int i = 0; i < 1000000; i++) {
            Thread t = new Thread(() -> {
                HereMapsProvider provider = new HereMapsProvider();
                Route r = new Route();
                r.setFromLatitude(51.038663);
                r.setFromLongitude(3.725996);
                r.setToLatitude(51.056146);
                r.setToLongitude(3.695193);

                provider.poll(r);
            });
            t.start();
            t.join();
            
            
        }

        System.exit(0);
*/
    
    public static void main(String[] args) throws ClassNotFoundException, InterruptedException {
        TrafficJamAnalysisService trafficJamAnalysisService = new TrafficJamAnalysisService();

        BackgroundPOIRouteMatcherService poiMatchingService = new BackgroundPOIRouteMatcherService();
        DataScrapingService dataScrapingService = new DataScrapingService();
        WeatherPollService weatherPollService = new WeatherPollService();

        trafficJamAnalysisService.start();
        poiMatchingService.start();
        dataScrapingService.start();
        weatherPollService.start();

        // run in infinite loop, de services runnen in background threads
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                LogService.getInstance().insert(LogTypeEnum.Error, Main.class.getName() + ": PollService", ex.getMessage());
            }
        }
    }
}
