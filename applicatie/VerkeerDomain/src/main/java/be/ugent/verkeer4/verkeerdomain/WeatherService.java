/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.LogTypeEnum;
import be.ugent.verkeer4.verkeerdomain.data.WeatherData;
import java.util.List;

/**
 *
 * @author Niels
 */
public class WeatherService extends BaseService implements IWeatherService {

    public WeatherService() throws ClassNotFoundException {
        super();
    }
    
    @Override
    public void update(WeatherData data) {
        try {
            repo.getWeatherSet().update(data);
        } catch (Exception ex) {
            LogService.getInstance().insert(LogTypeEnum.Error, WeatherService.class.getName(), ex.getMessage());
        }
    }

    @Override
    public void insert(WeatherData data) {
        repo.getWeatherSet().insert(data);
    }

    @Override
    public List<WeatherData> getWeatherData() {
        return repo.getWeatherSet().getItems();
    }
    
}
