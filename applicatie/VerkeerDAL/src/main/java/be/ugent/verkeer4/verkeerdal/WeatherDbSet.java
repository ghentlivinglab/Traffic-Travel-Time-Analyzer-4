/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdal;

import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.WeatherData;
import be.ugent.verkeer4.verkeerdomain.data.composite.BoundingBox;
import be.ugent.verkeer4.verkeerdomain.data.composite.WeatherWithDistanceToRoute;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.sql2o.Query;
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
    
    public List<WeatherWithDistanceToRoute> getWeatherForRoute(Route route, Date date)
    {
        long midLong = (long) ((route.getToLongitude() + route.getFromLongitude()) /2);
        long midLat = (long) ((route.getToLatitude() + route.getFromLatitude()) /2);

        try (org.sql2o.Connection con = sql2o.open()) {
            Query q = con.createQuery("SELECT *, SQRT( " +
                                        "POW(69.1 * (latitude - :Lat), 2) + " +
                                        "POW(69.1 * (:Long - longitude) * COS(latitude / 57.3), 2)) " +
                                        "AS Distance " +
                                        "FROM weatherdata " +
                                        "WHERE UpdateTime < CURDATE() " +
                                        "ORDER BY distance,UpdateTime desc " +
                                        "LIMIT 1;");
            
            q.addParameter("Lat",midLat);
            q.addParameter("Long", midLong);

            return q.executeAndFetch(WeatherWithDistanceToRoute.class);
        }
    }   
}
