package be.ugent.verkeer4.verkeerdomain.provider.here;

import be.ugent.verkeer4.verkeerdomain.Settings;
import java.io.IOException;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class HereMapsClient {

    public static Response GetRoute(double vanLat, double vanLng, double totLat, double totLng, boolean includeTraffic, boolean avoidHighways) throws IOException {

        String appId = Settings.getInstance().getHereRoutingAPPId();
        String appCode = Settings.getInstance().getHereRoutingAPPCode();
        String waypoint0 = "geo!" + vanLat + "," + vanLng;
        String waypoint1 = "geo!" + totLat + "," + totLng;
        String mode = "fastest;car";
        if(includeTraffic) {
            mode += ";traffic:enabled";
        }
        if(avoidHighways) {
            mode += ";motorway:-2";
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://route.cit.api.here.com/routing/7.2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        
        HereRoutingService service = retrofit.create(HereRoutingService.class);
        
        HereClient client =  service.calculateRoute(appId, appCode, waypoint0, waypoint1, mode).execute().body();
        
        return client.getResponse();
    }
}