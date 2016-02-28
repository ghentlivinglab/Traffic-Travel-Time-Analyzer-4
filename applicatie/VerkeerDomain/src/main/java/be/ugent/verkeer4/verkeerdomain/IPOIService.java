package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.POI;
import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import java.util.List;
import java.util.Map;


public interface IPOIService {

    Map<String, POI> getActivePOIPerReferenceIdForProvider(ProviderEnum provider);

    public void update(POI poi);

    public void insert(POI poi);

    public List<POI> getActivePOIs();
    
}
