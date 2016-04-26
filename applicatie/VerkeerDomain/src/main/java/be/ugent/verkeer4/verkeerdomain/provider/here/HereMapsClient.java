package be.ugent.verkeer4.verkeerdomain.provider.here;

import be.ugent.verkeer4.verkeerdomain.Settings;
import be.ugent.verkeer4.verkeerdomain.weather.WeatherClient;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class HereMapsClient {

    public static Response GetRoute(double vanLat, double vanLng, double totLat, double totLng, boolean includeTraffic, boolean avoidHighways) throws IOException {

        String appId = Settings.getInstance().getHereRoutingAPPId();
        String appCode = Settings.getInstance().getHereRoutingAPPCode();
        String waypoint0 = "geo!" + vanLat + "," + vanLng;
        String waypoint1 = "geo!" + totLat + "," + totLng;
        String mode = "fastest;car";
        if (includeTraffic) {
            mode += ";traffic:enabled";
        }
        if (avoidHighways) {
            mode += ";motorway:-2";
        }

        String query = "?app_id=" + appId + "&" + "app_code=" + appCode + "&" + "waypoint0=" + waypoint0 + "&" + "waypoint1=" + waypoint1 + "&" + "mode=" + mode;
        URL url = new URL("https://route.cit.api.here.com/routing/7.2/calculateroute.json" + query);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())))) {

            Gson gson = new Gson();
            HereClient response = gson.fromJson(br, HereClient.class);
            return response.getResponse();
        }
    }
}
