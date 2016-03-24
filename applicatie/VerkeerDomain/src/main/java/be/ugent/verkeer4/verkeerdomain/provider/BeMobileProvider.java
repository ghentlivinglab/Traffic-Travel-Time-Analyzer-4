package be.ugent.verkeer4.verkeerdomain.provider;

import be.ugent.verkeer4.verkeerdomain.LogService;
import be.ugent.verkeer4.verkeerdomain.data.LogTypeEnum;
import be.ugent.verkeer4.verkeerdomain.data.composite.BoundingBox;
import be.ugent.verkeer4.verkeerdomain.data.POI;
import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BeMobileProvider extends BaseProvider implements IProvider, IPOIProvider {

    public BeMobileProvider() {
        super(ProviderEnum.BeMobile);
    }

    @Override
    public RouteData poll(Route route) {
        try {
            return scrape(route, "bemobile.pl");
        } catch (Exception ex) {
            LogService.getInstance().insert(LogTypeEnum.Warning, "BeMobileProvider Error", "Scraping failed for route " + route.getId() + ex.getMessage());
            return null;
        }
    }
    
      @Override
    public List<POI> pollPOI(BoundingBox bbox) {
        try {
            return POIHelper.scrapePOI(bbox, ProviderEnum.BeMobile,"bemobilepoi.pl");
        } catch (IOException ex) {
            LogService.getInstance().insert(LogTypeEnum.Error, "BeMobileProvider Error", ex.getMessage());
            return null;
        } catch (Exception ex) {
            LogService.getInstance().insert(LogTypeEnum.Error, "BeMobileProvider Error", ex.getMessage());
            return null;
        }
    }

    @Override
    public ProviderEnum getProvider() {
        return ProviderEnum.BeMobile;
    }
}
