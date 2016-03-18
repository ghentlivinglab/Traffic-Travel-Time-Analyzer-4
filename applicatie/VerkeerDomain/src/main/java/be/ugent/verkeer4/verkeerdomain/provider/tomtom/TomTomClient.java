package be.ugent.verkeer4.verkeerdomain.provider.tomtom;

import be.ugent.verkeer4.verkeerdomain.Settings;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class TomTomClient {

    public static CalculateRouteResponse GetRoute(double vanLat, double vanLng, double totLat, double totLng, boolean includeTraffic, boolean avoidHighways) throws IOException {

        String apiKey = Settings.getInstance().getTomTomRoutingAPIKey();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.tomtom.com/routing/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TomTomRoutingService service = retrofit.create(TomTomRoutingService.class);

        String locations = vanLat + "," + vanLng + ":" + totLat + "," + totLng;
        List<String> avoid = null;
        if (avoidHighways) {
            avoid = new ArrayList<String>();
            avoid.add("motorways");
        }
        CalculateRouteResponse response = service.calculateRoute(locations, apiKey, includeTraffic, avoid).execute().body();

        return response;
    }
}
