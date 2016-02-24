package be.ugent.verkeer4.verkeerdomain.provider.here;

import retrofit2.*;
import retrofit2.http.*;


public interface HereRoutingService {
    
  @GET("calculateroute.json")
  Call<HereClient> calculateRoute(@Query("app_id") String app_id, @Query("app_code") String app_code, 
                                               @Query("waypoint0") String waypoint0, @Query("waypoint1") String waypoint1, @Query("mode") String mode);

}