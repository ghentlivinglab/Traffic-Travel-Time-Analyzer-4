package be.ugent.verkeer4.verkeerweb.viewmodels;

import java.util.ArrayList;
import java.util.List;

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
