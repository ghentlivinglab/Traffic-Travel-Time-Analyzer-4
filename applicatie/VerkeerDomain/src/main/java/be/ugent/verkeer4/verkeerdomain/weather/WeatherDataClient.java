/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdomain.weather;

import be.ugent.verkeer4.verkeerdomain.Settings;
import java.io.IOException;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 *
 * @author Niels
 */
public class WeatherDataClient {
    public static CurrentObservation GetWeather(String station) throws IOException {
        String key = Settings.getInstance().getWeatherAPIKey();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.wunderground.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        
        WeatherService service = retrofit.create(WeatherService.class);
        WeatherClient client =  service.getWeather(key, station).execute().body();
        
        
        return client.getCurrentObservation();
    }
}
