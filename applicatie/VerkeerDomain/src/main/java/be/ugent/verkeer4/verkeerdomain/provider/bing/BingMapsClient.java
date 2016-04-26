package be.ugent.verkeer4.verkeerdomain.provider.bing;

import be.ugent.verkeer4.verkeerdomain.Settings;
import be.ugent.verkeer4.verkeerdomain.provider.google.GoogleClient;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *
 * @author Tomas Bolckmans
 */
public class BingMapsClient {

    public static ResourceSet GetRoute(double vanLat, double vanLng, double totLat, double totLng, boolean includeTraffic, boolean avoidHighways) throws IOException {

        String api_key = Settings.getInstance().getBingRoutingAPIKey();
        String waypoint0 = vanLat + "," + vanLng;
        String waypoint1 = totLat + "," + totLng;

        /*Benodigde parameters bij BingMaps API
        Key
        Waypoint 0
        Waypoint 1
        De travelmode is by default Driving
        optimize is by default de snelste route
        viaWaypoint.n vwp.n => extra waypoints toevoegen als de route niet correct is
         */
        String avoid = "";
        if (avoidHighways) {
            avoid = "highways";
        }

        String query = "?key=" + api_key + "&" + "wp.0=" + waypoint0 + "&" + "wp.1=" + waypoint1 +  (avoid.equalsIgnoreCase("") ? "" : "&avoid=" + avoid);
        URL url = new URL("http://dev.virtualearth.net/REST/V1/Routes/Driving" + query);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())))) {

            Gson gson = new Gson();
            BingClient client = gson.fromJson(br, BingClient.class);

            return client.getResourceSets().get(0);
        }

    }
}
