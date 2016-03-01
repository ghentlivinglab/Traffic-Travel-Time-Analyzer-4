package be.ugent.verkeer4.verkeerdomain.provider.bing;

import be.ugent.verkeer4.verkeerdomain.Settings;
import be.ugent.verkeer4.verkeerdomain.provider.BingMapsProvider;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        
        BingClient client = null;
        try{
            client =  service.calculateRoute(api_key, waypoint0, waypoint1).execute().body();
        } catch(Exception ex){
            Logger.getLogger(BingMapsClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        return client.getResourceSets().get(0);
    }    
}
