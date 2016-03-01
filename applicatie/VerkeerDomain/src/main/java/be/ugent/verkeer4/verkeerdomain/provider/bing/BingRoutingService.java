package be.ugent.verkeer4.verkeerdomain.provider.bing;

import retrofit2.*;
import retrofit2.http.*;

/**
 *
 * @author Tomas Bolckmans
 */
public interface BingRoutingService { 
  @GET("Driving")
  Call<BingClient> calculateRoute(@Query("key") String api_key, @Query("wp.0") String waypoint0, @Query("wp.1") String waypoint1);    
}