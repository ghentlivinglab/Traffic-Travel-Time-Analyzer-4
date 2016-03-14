/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdomain.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 *
 * @author Niels
 */
public interface WeatherService {
    ///api/9cd758a0fe0cfc1d/conditions/q/50.997336,3.756638.json
  @GET("api/{key}/conditions/q/{latitude},{longitude}.json")
  Call<WeatherClient> getWeather(@Path("key") String apiKey, @Path("latitude") float latitude, @Path("longitude") float longitude);
}
