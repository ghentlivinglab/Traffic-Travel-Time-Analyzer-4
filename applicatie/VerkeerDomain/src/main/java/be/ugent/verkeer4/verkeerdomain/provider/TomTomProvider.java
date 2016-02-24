package be.ugent.verkeer4.verkeerdomain.provider;

import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.CalculateRouteResponse;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.TomTomClient;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TomTomProvider extends BaseProvider implements IProvider {

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
            Logger.getLogger(TomTomProvider.class.getName()).log(Level.WARNING, "Scraping failed for route " + route.getId() + ", falling back to API", ex);

            return useAPI(route);
        }
    }

    private RouteData useAPI(Route route) {
        try {
            // haal route gegevens op
            CalculateRouteResponse response = TomTomClient.GetRoute(route.getFromLatitude(), route.getFromLongitude(), route.getToLatitude(), route.getToLongitude(), true);

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
            Logger.getLogger(TomTomProvider.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
