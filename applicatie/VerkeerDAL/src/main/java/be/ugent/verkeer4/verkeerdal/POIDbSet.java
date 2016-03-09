package be.ugent.verkeer4.verkeerdal;

import be.ugent.verkeer4.verkeerdomain.data.POI;
import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.sql2o.Sql2o;

public class POIDbSet extends DbSet<POI> {

    public POIDbSet(Sql2o sql2o) {
        super(POI.class, sql2o);
    }

    public List<POI> getActivePOI() {
        HashMap<String, Object> map = new HashMap<>();
        return this.getItems("Until is NULL", map);
    }
    
    public Map<String, POI> getActivePOIPerReferenceIdForProvider(ProviderEnum provider) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Provider", provider.getValue());
        List<POI> pois = this.getItems("Provider = :Provider AND Until is NULL", map);
        
        HashMap<String, POI> poiByReferenceId = new HashMap<>();
        for (POI poi : pois) {
            poiByReferenceId.put(poi.getReferenceId(), poi);
        }
        return poiByReferenceId;
    }
    
}
