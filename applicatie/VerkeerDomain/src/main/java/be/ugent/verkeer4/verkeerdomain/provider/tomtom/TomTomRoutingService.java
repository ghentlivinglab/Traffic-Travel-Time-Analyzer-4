package be.ugent.verkeer4.verkeerdomain.provider.tomtom;

import retrofit2.*;
import retrofit2.http.*;


public interface TomTomRoutingService {
    
  @GET("calculateRoute/{locations}/json/")
  Call<CalculateRouteResponse> calculateRoute(@Path("locations") String locations, @Query("key") String apiKey, @Query("traffic") boolean traffic);

}
