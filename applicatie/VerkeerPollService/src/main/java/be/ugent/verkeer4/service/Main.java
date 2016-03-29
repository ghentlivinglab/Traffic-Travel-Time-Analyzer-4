package be.ugent.verkeer4.service;

import be.ugent.verkeer4.verkeerdomain.LogService;
import be.ugent.verkeer4.verkeerdomain.data.LogTypeEnum;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {

        TrafficJamAnalysisService trafficJamAnalysisService  = new TrafficJamAnalysisService();
        trafficJamAnalysisService.start();
        
        
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
                LogService.getInstance().insert(LogTypeEnum.Error, "Main Pollservice Error", ex.getMessage());
            }
        }
    }
}
