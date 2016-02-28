package be.ugent.verkeer4.verkeerdomain.provider;

import be.ugent.verkeer4.verkeerdomain.Settings;
import be.ugent.verkeer4.verkeerdomain.data.BoundingBox;
import be.ugent.verkeer4.verkeerdomain.data.POI;
import be.ugent.verkeer4.verkeerdomain.data.POICategoryEnum;
import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class BaseProvider {

    private final ProviderEnum provider;

    public BaseProvider(ProviderEnum provider) {
        this.provider = provider;
    }

    protected RouteData scrape(Route route, String scriptName) throws InterruptedException, IOException, Exception {

        File baseDir = new File(Settings.getInstance().getScrapePath());

        File script = new File(baseDir, scriptName);

        ProcessBuilder pb = new ProcessBuilder(Settings.getInstance().getPerlPath(), script.toPath().toString(), route.getFromLatitude() + "", route.getFromLongitude() + "",
                route.getToLatitude() + "", route.getToLongitude() + "");

        pb.directory(baseDir);
        java.lang.Process p = pb.start();
        int errCode = p.waitFor();

        if (errCode == 0) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            return parseScrapeOutput(reader, route);
        }

        return null;
    }

    protected RouteData parseScrapeOutput(BufferedReader reader, Route route) throws IOException, Exception, NumberFormatException {
        String headers = reader.readLine();
        String results = reader.readLine();

        if (results.equalsIgnoreCase("")) {
            throw new Exception("Invalid scrape data");
        }

        // totalDistanceMeters;totalTimeSeconds;totalDelaySeconds
        String[] parts = results.split(";");

        int totalDistance = Integer.parseInt(parts[0]);
        int totalTimeSeconds = Integer.parseInt(parts[1]);
        int totalDelaySeconds = Integer.parseInt(parts[2]);

        return setRouteData(route, totalDistance, totalTimeSeconds, totalDelaySeconds);
    }

    protected RouteData setRouteData(Route route, int totalDistance, int totalTimeSeconds, int delay) {
        RouteData rd = new RouteData();
        rd.setProvider(this.provider);
        rd.setTimestamp(new Date());
        rd.setTravelTime(totalTimeSeconds);
        rd.setDelay(delay);
        rd.setDistance(totalDistance);
        rd.setRouteId(route.getId());
        return rd;
    }

    protected List<POI> scrapePOI(BoundingBox bbox, String scriptName) throws InterruptedException, IOException, Exception {

        File baseDir = new File(Settings.getInstance().getScrapePath());

        File script = new File(baseDir, scriptName);

        ProcessBuilder pb = new ProcessBuilder(Settings.getInstance().getPerlPath(), script.toPath().toString(),
                bbox.getMinLatitude() + "", bbox.getMinLongitude() + "", bbox.getMaxLatitude() + "", bbox.getMaxLongitude() + "");

        pb.directory(baseDir);
        java.lang.Process p = pb.start();
        int errCode = p.waitFor();

        if (errCode == 0) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            return parseScrapePOIOutput(reader);
        }

        return null;
    }

    protected List<POI> parseScrapePOIOutput(BufferedReader reader) throws IOException, Exception, NumberFormatException {
        String headers = reader.readLine();

        String line;

        List<POI> lst = new ArrayList<>();
        while ((line = reader.readLine()) != null) {

            // id;lat;lng;type;traffictype;comments;
            String[] parts = line.split(";");

            POI p = new POI();
            p.setReferenceId(parts[0]);
            p.setProvider(this.provider);
            p.setLatitude(Double.parseDouble(parts[1]));
            p.setLongitude(Double.parseDouble(parts[2]));
            p.setCategory(POICategoryEnum.Unknown); //TODO
            p.setSince(new Date());
            p.setInfo(parts[5]);

            lst.add(p);
        }
        return lst;
    }
}
