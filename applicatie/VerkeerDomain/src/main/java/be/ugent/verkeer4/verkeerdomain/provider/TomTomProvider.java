package be.ugent.verkeer4.verkeerdomain.provider;

import be.ugent.verkeer4.verkeerdomain.LogService;
import be.ugent.verkeer4.verkeerdomain.data.LogTypeEnum;
import be.ugent.verkeer4.verkeerdomain.data.composite.BoundingBox;
import be.ugent.verkeer4.verkeerdomain.data.POI;
import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.CalculateRouteResponse;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.TomTomClient;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TomTomProvider extends BaseProvider implements IProvider, IPOIProvider {

    public TomTomProvider() {
        super(ProviderEnum.TomTom);
    }

    @Override
    public RouteData poll(Route route) {

        try {
            RouteData result = scrape(route, "tomtom.pl");
            if (result == null) {
                return useAPI(route);
            } else {
                return result;
            }
        } catch (Exception ex) {
            LogService.getInstance().insert(LogTypeEnum.Warning, TomTomProvider.class.getName(), "Scraping failed for route " + route.getId() + ", falling back to API" + ex.getMessage());

            return useAPI(route);
        }
    }

    private RouteData useAPI(Route route) {
        try {
            // haal route gegevens op
            CalculateRouteResponse response = TomTomClient.GetRoute(route.getFromLatitude(), route.getFromLongitude(), route.getToLatitude(), route.getToLongitude(), true, route.getAvoidHighwaysOrUseShortest());

            if (response.getRoutes().size() > 0) {
                be.ugent.verkeer4.verkeerdomain.provider.tomtom.Route tomtomRoute = response.getRoutes().get(0);

                RouteData rd = setRouteData(route,
                        tomtomRoute.getSummary().getLengthInMeters(),
                        tomtomRoute.getSummary().getTravelTimeInSeconds(),
                        tomtomRoute.getSummary().getTrafficDelayInSeconds());

                return rd;
            }

            return null;
        } catch (IOException ex) {
            LogService.getInstance().insert(LogTypeEnum.Error, TomTomProvider.class.getName(), ex.getMessage());
            return null;
        }
    }

    @Override
    public List<POI> pollPOI(BoundingBox bbox) {
        try {
            return POIHelper.scrapePOI(bbox, ProviderEnum.TomTom, "tomtompoi.pl");
        } catch (IOException ex) {
            LogService.getInstance().insert(LogTypeEnum.Error, TomTomProvider.class.getName(), ex.getMessage());
            return null;
        } catch (Exception ex) {
            LogService.getInstance().insert(LogTypeEnum.Error, TomTomProvider.class.getName(), ex.getMessage());
            return null;
        }
    }

    @Override
    public ProviderEnum getProvider() {
        return ProviderEnum.TomTom;
    }

}


