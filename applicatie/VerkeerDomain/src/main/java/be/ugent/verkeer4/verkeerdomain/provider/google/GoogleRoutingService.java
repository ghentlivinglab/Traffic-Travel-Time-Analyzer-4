/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdomain.provider.google;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *
 * @author Niels
 */
public interface GoogleRoutingService {
  //https://maps.googleapis.com/maps/api/distancematrix/json?key=AIzaSyDSFudE3RU-uIFK4ID8i32bnq-rGRRtpGw&destinations=51.056146%2C3.695183&origins=51.038663%2C3.725996&departure_time=1456416000 
            
  @GET("json")
  Call<GoogleClient> calculateRoute(@Query("key") String key, @Query("destinations") String destionations, 
                                               @Query("origins") String origins, @Query("departure_time") String departureTime, @Query("avoid") String avoid);
}
