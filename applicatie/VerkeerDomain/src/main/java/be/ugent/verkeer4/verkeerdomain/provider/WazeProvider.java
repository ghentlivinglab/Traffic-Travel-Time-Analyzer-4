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

public class WazeProvider extends BaseProvider implements IProvider, IPOIProvider {

    public WazeProvider() {
        super(ProviderEnum.Waze);
    }

    @Override
    public RouteData poll(Route route) {
        try {
            return scrape(route, "waze.pl");
        } catch (Exception ex) {
            LogService.getInstance().insert(LogTypeEnum.Warning, WazeProvider.class.getName(), "Scraping failed for route " + route.getId() + ": " + ex.getMessage());

            return null;
        }
    }

    @Override
    public List<POI> pollPOI(BoundingBox bbox) {
        try {
            return POIHelper.scrapePOI(bbox, ProviderEnum.Waze, "wazepoi.pl");
        } catch (IOException ex) {
            LogService.getInstance().insert(LogTypeEnum.Error, WazeProvider.class.getName(), ex.getMessage());
            return null;
        } catch (Exception ex) {
            LogService.getInstance().insert(LogTypeEnum.Error, WazeProvider.class.getName(), ex.getMessage());
            return null;
        }
    }

    @Override
    public ProviderEnum getProvider() {
        return ProviderEnum.Waze;
    }
}
