package be.ugent.verkeer4.verkeerdomain.provider;

import be.ugent.verkeer4.verkeerdomain.LogService;
import be.ugent.verkeer4.verkeerdomain.data.LogTypeEnum;
import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import be.ugent.verkeer4.verkeerdomain.provider.bing.BingMapsClient;
import be.ugent.verkeer4.verkeerdomain.provider.bing.ResourceSet;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tomas Bolckmans
 */
public class BingMapsProvider extends BaseProvider implements IProvider {
    
    public BingMapsProvider() {
        super(ProviderEnum.Bing);
    }
    
    @Override
    public RouteData poll(Route route) {
        return useAPI(route);
    }
    
    public RouteData useAPI(Route route) {
        try {
            // haal route gegevens op
            ResourceSet resourceSet = BingMapsClient.GetRoute(route.getFromLatitude(), route.getFromLongitude(), route.getToLatitude(), route.getToLongitude(), true, route.getAvoidHighwaysOrUseShortest());

            //controleer of er routes zijn
            if (resourceSet.getResources().size() > 0) {
                //haal eerste route er uit
                be.ugent.verkeer4.verkeerdomain.provider.bing.Resource bingResource = resourceSet.getResources().get(0);
                
                //Data word gereturned als een Double en niet als een double
                //Double kan niet rechtstreeks gecast worden naar een int, maar double wel
                //daarom eerst omzetten naar double en dan naar int.
                int distance = (int) ((double) bingResource.getTravelDistance());   
                int travelDuractionTraffic = (int) ((double) bingResource.getTravelDurationTraffic());
                int delay = (int) (bingResource.getTravelDurationTraffic() - bingResource.getTravelDuration()); //vertraging is de tijd met verkeer - de berekende tijd
                
                RouteData rd = setRouteData(route, distance, travelDuractionTraffic, delay);

                return rd;
            }

            return null;
        } catch (Exception ex) {
            //Error logging
            LogService.getInstance().insert(LogTypeEnum.Warning, BingMapsProvider.class.getName(), "Scraping failed for route " + route.getId() + ex.getMessage());
            return null;
        }
    }    
    
}




 


    
    