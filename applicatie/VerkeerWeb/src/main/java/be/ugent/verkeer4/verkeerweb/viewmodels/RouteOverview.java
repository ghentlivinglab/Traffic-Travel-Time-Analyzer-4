package be.ugent.verkeer4.verkeerweb.viewmodels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RouteOverview {

    private Date recentRouteDateFrom;
    
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

    /**
     * @return the recentRouteDateFrom
     */
    public Date getRecentRouteDateFrom() {
        return recentRouteDateFrom;
    }

    /**
     * @param recentRouteDateFrom the recentRouteDateFrom to set
     */
    public void setRecentRouteDateFrom(Date recentRouteDateFrom) {
        this.recentRouteDateFrom = recentRouteDateFrom;
    }

    
    
  
    
    
}
