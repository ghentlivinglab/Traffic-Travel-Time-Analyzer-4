package be.ugent.verkeer4.verkeerdomain.provider;

import be.ugent.verkeer4.verkeerdomain.IRouteService;
import be.ugent.verkeer4.verkeerdomain.Settings;
import be.ugent.verkeer4.verkeerdomain.data.BoundingBox;
import be.ugent.verkeer4.verkeerdomain.data.POI;
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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CoyoteProvider implements ISummaryProvider, IPOIProvider {

    private final IRouteService routeService;

    public CoyoteProvider(IRouteService routeService) {
        this.routeService = routeService;
    }

    protected List<RouteData> scrape() throws InterruptedException, IOException, Exception {

        Map<String, Route> routeByLowerCaseName = routeService.getRoutes().stream().collect(Collectors.toMap(r -> r.getName().toLowerCase(), r -> r));

        File baseDir = new File(Settings.getInstance().getScrapePath());

        File script = new File(baseDir, "coyote.pl");

        ProcessBuilder pb = new ProcessBuilder("perl", script.toPath().toString());

        pb.directory(baseDir);
        java.lang.Process p = pb.start();
        int errCode = p.waitFor();

        if (errCode == 0) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String headers = reader.readLine();
            String line;

            List<RouteData> lst = new ArrayList<>();

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(";");

                int totalDistance = Integer.parseInt(parts[0]);
                int totalTimeSeconds = Integer.parseInt(parts[1]);
                int totalDelaySeconds = Integer.parseInt(parts[2]);
                String name = parts[3];
                String[] nameParts = name.split(" - ");

                if (routeByLowerCaseName.containsKey(nameParts[0].toLowerCase())) {
                    Route r = routeByLowerCaseName.get(nameParts[0].toLowerCase());

                    RouteData rd = new RouteData();
                    rd.setProvider(ProviderEnum.Coyote);
                    rd.setTimestamp(new Date());
                    rd.setTravelTime(totalTimeSeconds);
                    rd.setDelay(totalDelaySeconds);
                    rd.setDistance(totalDistance);
                    rd.setRouteId(r.getId());
                    lst.add(rd);
                } else {
                    Logger.getLogger(CoyoteProvider.class.getName()).log(Level.WARNING, "''{0}'' IS NOT FOUND IN THE ROUTE LIST", nameParts[0]);
                }
            }

            return lst;
        }

        return null;
    }

    @Override
    public List<RouteData> poll() {
        try {
            return scrape();
        } catch (IOException ex) {
            Logger.getLogger(CoyoteProvider.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (Exception ex) {
            Logger.getLogger(CoyoteProvider.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<POI> pollPOI(BoundingBox bbox) {
        try {
            return POIHelper.scrapePOI(bbox, ProviderEnum.Coyote, "coyotepoi.pl");
        } catch (IOException ex) {
            Logger.getLogger(TomTomProvider.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (Exception ex) {
            Logger.getLogger(TomTomProvider.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public ProviderEnum getProvider() {
        return ProviderEnum.Coyote;
    }
}
