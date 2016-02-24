package be.ugent.verkeer4.verkeerdomain.provider;

import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import be.ugent.verkeer4.verkeerdomain.provider.here.HereMapsClient;
import be.ugent.verkeer4.verkeerdomain.provider.here.Response;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HereMapsProvider extends BaseProvider implements IProvider {

    public HereMapsProvider() {
        super(ProviderEnum.HereMaps);
    }
    
    @Override
    public RouteData poll(Route route) {

        try {
            RouteData result =  scrape(route, "here.pl");
            if(result == null)
                return useAPI(route);
            else
                return result;
        } catch (Exception ex) {
            Logger.getLogger(HereMapsProvider.class.getName()).log(Level.WARNING, "Scraping failed for route " + route.getId() + ", falling back to API", ex);

            return useAPI(route);
        }
    }

    public RouteData useAPI(Route route) {
        try {
            // haal route gegevens op
            Response response = HereMapsClient.GetRoute(route.getFromLatitude(), route.getFromLongitude(), route.getToLatitude(), route.getToLongitude(), true);

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
            Logger.getLogger(HereMapsProvider.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
   
}
