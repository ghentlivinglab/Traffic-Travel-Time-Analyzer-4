package be.ugent.verkeer4.verkeerdomain.provider.bing;

import be.ugent.verkeer4.verkeerdomain.Settings;
import java.io.IOException;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 *
 * @author Tomas Bolckmans
 */
public class BingMapsClient {

    public static ResourceSet GetRoute(double vanLat, double vanLng, double totLat, double totLng, boolean includeTraffic) throws IOException {

        String api_key = Settings.getInstance().getBingRoutingAPIKey();
        String waypoint0 = vanLat + "," + vanLng;
        String waypoint1 = totLat + "," + totLng;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://dev.virtualearth.net/REST/V1/Routes/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        
        BingRoutingService service = retrofit.create(BingRoutingService.class);
        
        BingClient client =  service.calculateRoute(api_key, waypoint0, waypoint1).execute().body();
        return client.getResourceSets().get(0);
    }    
}
