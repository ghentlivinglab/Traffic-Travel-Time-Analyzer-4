/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdomain.provider;

import be.ugent.verkeer4.verkeerdomain.data.WeatherData;

/**
 *
 * @author Niels
 */
public interface IWeatherProvider {
    
    public WeatherData poll(String station);
    
}
