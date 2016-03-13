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

    public static ResourceSet GetRoute(double vanLat, double vanLng, double totLat, double totLng, boolean includeTraffic, boolean avoidHighways) throws IOException {

        String api_key = Settings.getInstance().getBingRoutingAPIKey();
        String waypoint0 = vanLat + "," + vanLng;
        String waypoint1 = totLat + "," + totLng;
        
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://dev.virtualearth.net/REST/V1/Routes/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        
        BingRoutingService service = retrofit.create(BingRoutingService.class);
        
        /*Benodigde parameters bij BingMaps API
        Key
        Waypoint 0
        Waypoint 1
        De travelmode is by default Driving
        optimize is by default de snelste route
        viaWaypoint.n vwp.n => extra waypoints toevoegen als de route niet correct is
        */
        
        String avoid = null;
        if(avoidHighways) {
            avoid = "highways";
        }
        
        BingClient client =  service.calculateRoute(api_key, waypoint0, waypoint1, avoid).execute().body();


        return client.getResourceSets().get(0);
    }    
}
