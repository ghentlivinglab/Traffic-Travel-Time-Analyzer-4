package be.ugent.verkeer4.service;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final int EVERY_MILLIS = 300000;

    public static void main(String[] args) throws ClassNotFoundException {

        TrafficJamAnalysisService trafficJamAnalysisService  = new TrafficJamAnalysisService();
        trafficJamAnalysisService.start();
        
        /*
        BackgroundPOIRouteMatcherService poiMatchingService = new BackgroundPOIRouteMatcherService();
        poiMatchingService.start();

        DataScrapingService dataScrapingService = new DataScrapingService();
        dataScrapingService.start();
*/
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
