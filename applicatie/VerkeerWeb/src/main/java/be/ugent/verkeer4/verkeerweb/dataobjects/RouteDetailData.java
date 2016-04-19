
package be.ugent.verkeer4.verkeerweb.dataobjects;

import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import be.ugent.verkeer4.verkeerweb.viewmodels.RouteDataVM;
import java.util.List;


public class RouteDetailData {
    
    private List<RouteData> values;
    
    private List<RouteDetailTrafficJam> jams;

    public List<RouteData> getValues() {
        return values;
    }

    public void setValues(List<RouteData> values) {
        this.values = values;
    }

    public List<RouteDetailTrafficJam> getJams() {
        return jams;
    }

    public void setJams(List<RouteDetailTrafficJam> jams) {
        this.jams = jams;
    }
    
    
    
}
