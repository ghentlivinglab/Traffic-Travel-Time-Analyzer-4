package be.ugent.verkeer4.verkeerweb.viewmodels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RouteOverviewVM {

    private Date recentRouteDateFrom;
    
    public RouteOverviewVM() {
        summaries = new ArrayList<>();
    }
     private List<RouteSummaryEntryVM> summaries;
   
    /**
     * @return the summaries
     */
    public List<RouteSummaryEntryVM> getSummaries() {
        return summaries;
    }

    /**
     * @param summaries the summaries to set
     */
    public void setSummaries(List<RouteSummaryEntryVM> summaries) {
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
