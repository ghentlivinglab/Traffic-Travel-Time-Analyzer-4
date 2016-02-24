package be.ugent.verkeer4.verkeerdomain.provider.tomtom;

import be.ugent.verkeer4.verkeerdomain.Settings;
import java.io.IOException;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class TomTomClient {

    public static CalculateRouteResponse GetRoute(double vanLat, double vanLng, double totLat, double totLng, boolean includeTraffic) throws IOException {

        String apiKey = Settings.getInstance().getTomTomRoutingAPIKey();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.tomtom.com/routing/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TomTomRoutingService service = retrofit.create(TomTomRoutingService.class);
        
        String locations = vanLat + "," + vanLng + ":" + totLat + "," + totLng;
        CalculateRouteResponse response =  service.calculateRoute(locations, apiKey, includeTraffic).execute().body();
        
        return response;
    }
}
