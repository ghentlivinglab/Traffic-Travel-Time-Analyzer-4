/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.LogTypeEnum;
import be.ugent.verkeer4.verkeerdomain.data.WeatherData;
import be.ugent.verkeer4.verkeerdomain.provider.IWeatherProvider;
import be.ugent.verkeer4.verkeerdomain.provider.WeatherProvider;
import java.util.List;

/**
 *
 * @author Niels
 */
public class WeatherProviderService extends BaseService{
    
    private final IWeatherProvider weatherProvider;
    
    public WeatherProviderService() throws ClassNotFoundException
    {
        super();
        weatherProvider = new WeatherProvider();
    }
    
    public void pollWeather(List<String> stations) throws ClassNotFoundException {
        
        for(String station : stations)
        {
            WeatherData data = weatherProvider.poll(station);
            if (data != null) {
                repo.getWeatherSet().insert(data);
            } 
            else {
                LogService.getInstance().insert(LogTypeEnum.Warning, WeatherProviderService.class.getName(), "Could not fetch weather for station: " + station);
            } 
        }      
    }
}
