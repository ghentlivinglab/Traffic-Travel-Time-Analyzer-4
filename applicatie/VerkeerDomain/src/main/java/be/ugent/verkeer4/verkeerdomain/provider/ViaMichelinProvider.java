package be.ugent.verkeer4.verkeerdomain.provider;

import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViaMichelinProvider extends BaseProvider implements IProvider {

    public ViaMichelinProvider() {
        super(ProviderEnum.ViaMichelin);
    }

    @Override
    public RouteData poll(Route route) {
        try {
            return scrape(route, "viamichelin.pl");
        } catch (Exception ex) {
            Logger.getLogger(TomTomProvider.class.getName()).log(Level.WARNING, "Scraping failed for route " + route.getId(), ex);

            return null;
        }
    }
}
