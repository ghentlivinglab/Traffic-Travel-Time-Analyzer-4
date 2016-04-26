/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdomain.provider.google;

import be.ugent.verkeer4.verkeerdomain.Settings;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.Query;

/**
 *
 * @author Niels
 */
public class GoogleMapsClient {

    public static Row GetRoute(double originLat, double originLng, double destLat, double destLng, boolean avoidHighways) throws IOException {
        String key = Settings.getInstance().getGoogleRoutingAPPCode();
        String origins = originLat + "," + originLng;
        String destinations = destLat + "," + destLng;
        String departureTime = Long.toString(System.currentTimeMillis() / 1000);
        String avoid = null;
        if (avoidHighways) {
            avoid = "highways";
        }

        String query = "?key=" + key + "&" + "destinations=" + destinations + "&" + "origins=" + origins + "&" + "departure_time=" + departureTime + "&" + "avoid=" + avoid;
        URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json" + query);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())))) {

            Gson gson = new Gson();
            GoogleClient client = gson.fromJson(br, GoogleClient.class);

            if (client.getStatus().equalsIgnoreCase("OVER_QUERY_LIMIT")) {
                return null;
            } else {
                return client.getRows().get(0);
            }
        }
    }
}
