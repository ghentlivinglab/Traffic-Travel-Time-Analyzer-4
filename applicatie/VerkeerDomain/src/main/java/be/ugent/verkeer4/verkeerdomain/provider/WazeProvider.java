package be.ugent.verkeer4.verkeerdomain.provider;

import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WazeProvider extends BaseProvider implements IProvider {

    public WazeProvider() {
        super(ProviderEnum.Waze);
    }

    @Override
    public RouteData poll(Route route) {
        try {
            return scrape(route, "waze.pl");
        } catch (Exception ex) {
            Logger.getLogger(WazeProvider.class.getName()).log(Level.WARNING, "Scraping failed for route " + route.getId(), ex);

            return null;
        }
    }
}
