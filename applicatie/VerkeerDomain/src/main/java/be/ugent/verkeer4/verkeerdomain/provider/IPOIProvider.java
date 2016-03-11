package be.ugent.verkeer4.verkeerdomain.provider;

import be.ugent.verkeer4.verkeerdomain.data.BoundingBox;
import be.ugent.verkeer4.verkeerdomain.data.POI;
import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import java.util.List;

public interface IPOIProvider {

    public List<POI> pollPOI(BoundingBox bbox);
    
    public ProviderEnum getProvider();
    
}
