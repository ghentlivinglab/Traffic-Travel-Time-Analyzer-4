/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdal;

import be.ugent.verkeer4.verkeerdomain.data.WeatherData;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.sql2o.Sql2o;

/**
 *
 * @author Niels
 */
public class WeatherDbSet extends DbSet<WeatherData>{
    
    public WeatherDbSet(Sql2o sql2o) {
        super(WeatherData.class, sql2o);
    }
    
    public List<WeatherData> getItemsForPosition(double lat, double lng, Date from, Date to) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Latitude", lat);
        map.put("Longitude", lng);
        map.put("From", from);
        map.put("To", to);

        return this.getItems("Latitude = :Latitude AND Longitude = :Longitude AND Timestamp BETWEEN :From and :To", map);
    }
    
}
