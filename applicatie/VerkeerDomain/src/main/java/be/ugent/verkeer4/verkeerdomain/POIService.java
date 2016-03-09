package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.POI;
import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class POIService extends BaseService implements IPOIService {

    public POIService() throws ClassNotFoundException {
        super();
    }

    @Override
    public Map<String, POI> getActivePOIPerReferenceIdForProvider(ProviderEnum provider) {
        return repo.getPOISet().getActivePOIPerReferenceIdForProvider(provider);
    }

    @Override
    public void update(POI poi) {
        try {
            repo.getPOISet().update(poi);
        } catch (Exception ex) {
            Logger.getLogger(POIService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insert(POI poi) {
        repo.getPOISet().insert(poi);
    }

    @Override
    public List<POI> getActivePOIs() {
        return repo.getPOISet().getActivePOI();
    }
}
