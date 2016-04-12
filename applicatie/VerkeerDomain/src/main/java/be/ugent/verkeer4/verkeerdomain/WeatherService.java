/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.LogTypeEnum;
import be.ugent.verkeer4.verkeerdomain.data.WeatherData;
import java.util.List;

public class WeatherService extends BaseService implements IWeatherService {

    public WeatherService() throws ClassNotFoundException {
        super();
    }
    
    /**
     * Wijzigen van een entry in de weer tabel (weatherdata)
     * @param data 
     */
    @Override
    public void update(WeatherData data) {
        try {
            repo.getWeatherSet().update(data);
        } catch (Exception ex) {
            LogService.getInstance().insert(LogTypeEnum.Error, WeatherService.class.getName(), ex.getMessage());
        }
    }

    /**
     * Voegt een entry toe aan de weer tabel
     * @param data 
     */
    @Override
    public void insert(WeatherData data) {
        repo.getWeatherSet().insert(data);
    }

    /**
     * geeft een lijst van alle entries terug uit de weer tabel
     * @return 
     */
    @Override
    public List<WeatherData> getWeatherData() {
        return repo.getWeatherSet().getItems();
    }
    
}
