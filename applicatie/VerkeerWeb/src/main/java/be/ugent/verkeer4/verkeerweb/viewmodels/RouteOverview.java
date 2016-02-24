package be.ugent.verkeer4.verkeerweb.viewmodels;

import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.composite.RouteSummary;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.Route;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class RouteOverview {

    public RouteOverview() {
        summaries = new ArrayList<>();
    }
     private List<RouteSummaryEntry> summaries;
   
    /**
     * @return the summaries
     */
    public List<RouteSummaryEntry> getSummaries() {
        return summaries;
    }

    /**
     * @param summaries the summaries to set
     */
    public void setSummaries(List<RouteSummaryEntry> summaries) {
        this.summaries = summaries;
    }

  
    
    
}
