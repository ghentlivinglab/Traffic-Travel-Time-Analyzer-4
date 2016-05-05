package be.ugent.verkeer4.verkeerdomain.provider.tomtom;

import be.ugent.verkeer4.verkeerdomain.Settings;
import be.ugent.verkeer4.verkeerdomain.provider.bing.BingClient;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class TomTomClient {

    public static CalculateRouteResponse GetRoute(double vanLat, double vanLng, double totLat, double totLng, boolean includeTraffic, boolean avoidHighways) throws IOException {

        String apiKey = Settings.getInstance().getTomTomRoutingAPIKey();

        String locations = vanLat + "," + vanLng + ":" + totLat + "," + totLng;
        String avoid = "";
        if (avoidHighways) {
            avoid = "motorways";
        }

        String query = "?key=" + apiKey + "&" + "traffic=" + (includeTraffic ? "true" : "false") + (avoid.equalsIgnoreCase("") ? "" : "&avoid=" + avoid);
        URL url = new URL("https://api.tomtom.com/routing/1/calculateRoute/" + locations + "/json/" + query);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())))) {

            Gson gson = new Gson();
            CalculateRouteResponse response = gson.fromJson(br, CalculateRouteResponse.class);
            return response;
        }
    }
}
