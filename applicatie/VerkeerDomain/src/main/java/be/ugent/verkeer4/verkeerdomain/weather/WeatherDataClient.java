/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdomain.weather;

import java.io.IOException;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 *
 * @author Niels
 */
public class WeatherDataClient {
    public static CurrentObservation GetWeather(double lat, double lng) throws IOException {
        String key = "9cd758a0fe0cfc1d";
        float latitude = (float)lat;
        float longitude = (float)lng;    

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.wunderground.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        
        WeatherService service = retrofit.create(WeatherService.class);
        WeatherClient client =  service.getWeather(key, latitude, longitude).execute().body();
        
        
        return client.getCurrentObservation();
    }
}
