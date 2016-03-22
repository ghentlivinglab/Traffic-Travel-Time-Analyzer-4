/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.WeatherData;
import java.util.List;

/**
 *
 * @author Niels
 */
public interface IWeatherService {
    
    public void update(WeatherData data);

    public void insert(WeatherData data);
    
    public List<WeatherData> getWeatherData();
}
