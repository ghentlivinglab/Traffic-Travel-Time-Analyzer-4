package be.ugent.verkeer4.verkeerdomain.provider;

import be.ugent.verkeer4.verkeerdomain.LogService;
import be.ugent.verkeer4.verkeerdomain.data.LogTypeEnum;
import be.ugent.verkeer4.verkeerdomain.data.composite.BoundingBox;
import be.ugent.verkeer4.verkeerdomain.data.POI;
import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import be.ugent.verkeer4.verkeerdomain.provider.here.HereMapsClient;
import be.ugent.verkeer4.verkeerdomain.provider.here.Response;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HereMapsProvider extends BaseProvider implements IProvider, IPOIProvider {

    public HereMapsProvider() {
        super(ProviderEnum.HereMaps);
    }

    @Override
    public RouteData poll(Route route) {

        try {
            RouteData result = scrape(route, "here.pl");
            if (result == null) {
                return useAPI(route);
            } else {
                return result;
            }
        } catch (Exception ex) {
            LogService.getInstance().insert(LogTypeEnum.Warning, "HereMapsProvider Error", "Scraping failed for route " + route.getId() + ", falling back to API" + ex);
            return useAPI(route);
        }
    }

    public RouteData useAPI(Route route) {
        try {
            // haal route gegevens op
            Response response = HereMapsClient.GetRoute(route.getFromLatitude(), route.getFromLongitude(), route.getToLatitude(), route.getToLongitude(), true, route.getAvoidHighwaysOrUseShortest());

            if (response.getRoute().size() > 0) {
                be.ugent.verkeer4.verkeerdomain.provider.here.Route hereRoute = response.getRoute().get(0);

                int delay = hereRoute.getSummary().getTrafficTime() - hereRoute.getSummary().getBaseTime();

                RouteData rd = setRouteData(route,
                        hereRoute.getSummary().getDistance(),
                        hereRoute.getSummary().getTrafficTime(),
                        delay);

                return rd;
            }

            return null;
        } catch (IOException ex) {
            LogService.getInstance().insert(LogTypeEnum.Error, "HereMapsProvider Error", ex.getMessage());
            return null;
        }
    }

    @Override
    public List<POI> pollPOI(BoundingBox bbox) {
        try {
            return POIHelper.scrapePOI(bbox, ProviderEnum.HereMaps, "herepoi.pl");
        } catch (IOException ex) {
            LogService.getInstance().insert(LogTypeEnum.Error, "HereMapsProvider Error", ex.getMessage());
            return null;
        } catch (Exception ex) {
            LogService.getInstance().insert(LogTypeEnum.Error, "HereMapsProvider Error", ex.getMessage());
            return null;
        }
    }

    @Override
    public ProviderEnum getProvider() {
        return ProviderEnum.HereMaps;
    }
}
