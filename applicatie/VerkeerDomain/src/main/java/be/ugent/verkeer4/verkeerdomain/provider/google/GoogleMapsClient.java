/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdomain.provider.google;

import be.ugent.verkeer4.verkeerdomain.Settings;
import java.io.IOException;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
/**
 *
 * @author Niels
 */
public class GoogleMapsClient {
    public static Row GetRoute(double originLat, double originLng, double destLat, double destLng) throws IOException {
        String key = Settings.getInstance().getGoogleRoutingAPPCode();
        String origins = originLat + "," + originLng;
        String destinations = destLat + "," + destLng;
        String departureTime = Long.toString(System.currentTimeMillis() / 1000);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/distancematrix/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        
        GoogleRoutingService service = retrofit.create(GoogleRoutingService.class);

        GoogleClient client =  service.calculateRoute(key, destinations, origins, departureTime).execute().body();
        
        return client.getRows().get(0);
    }
}
