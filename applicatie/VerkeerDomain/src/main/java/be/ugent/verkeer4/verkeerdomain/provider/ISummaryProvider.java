package be.ugent.verkeer4.verkeerdomain.provider;

import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import java.util.List;


public interface ISummaryProvider {
    
    public List<RouteData> poll();
}
