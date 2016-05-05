/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdomain.weather;

import be.ugent.verkeer4.verkeerdomain.Settings;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.CalculateRouteResponse;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 *
 * @author Niels
 */
public class WeatherDataClient {

    public static CurrentObservation GetWeather(String station) throws IOException {
        String key = Settings.getInstance().getWeatherAPIKey();

        String query = "";
        URL url = new URL("http://api.wunderground.com/api/" + key + "/conditions/q/pws:" + station + ".json" + query);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())))) {

            Gson gson = new Gson();
            WeatherClient response = gson.fromJson(br, WeatherClient.class);
            return response.getCurrentObservation();
        }
    }
}
