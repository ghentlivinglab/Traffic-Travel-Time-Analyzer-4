/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.service;

import be.ugent.verkeer4.verkeerdomain.LogService;
import be.ugent.verkeer4.verkeerdomain.WeatherProviderService;
import be.ugent.verkeer4.verkeerdomain.data.LogTypeEnum;
import java.util.ArrayList;
import java.util.List;

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
            LogService.getInstance().insert(LogTypeEnum.Info, WeatherPollService.class.getName(), "Starting weahter poll...");
            weatherService.pollWeather(stations);
        } catch (ClassNotFoundException ex) {
            LogService.getInstance().insert(LogTypeEnum.Error, WeatherPollService.class.getName(), ex.getMessage());
        }
    }    
}
