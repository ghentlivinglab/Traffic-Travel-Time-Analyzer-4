/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.service;

import be.ugent.verkeer4.verkeerdomain.WeatherProviderService;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Niels
 */
public class WeatherPollService extends BaseService {

    private static final int EVERY_MILLIS = 900000;
    private static List<String> stations = new ArrayList<>();
    private WeatherProviderService weatherService;

    /**
     *
     * @throws ClassNotFoundException
     */
    public WeatherPollService() throws ClassNotFoundException {
        
        super(EVERY_MILLIS, "Weather Polling");
        
        weatherService = new WeatherProviderService();
        stations.add("IVLAAMSG97");
        stations.add("IVLAAMSG88");
        stations.add("IVLAANDE2");
        stations.add("IVLAAMSG120");
        stations.add("IFLANDER2");
    }

    @Override
    protected void action() {
        try {
            Logger.getLogger(Main.class.getName()).log(Level.INFO, "Starting weather poll..");
            weatherService.pollWeather(stations);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WeatherPollService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
