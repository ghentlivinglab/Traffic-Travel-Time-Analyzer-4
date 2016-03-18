package be.ugent.verkeer4.verkeerweb.dataobjects;

import be.ugent.verkeer4.verkeerdomain.data.RouteTrafficJam;
import be.ugent.verkeer4.verkeerdomain.data.RouteTrafficJamCause;
import be.ugent.verkeer4.verkeerdomain.data.composite.GroupedRouteTrafficJamCause;
import java.util.List;

public class RouteDetailTrafficJam {

    private RouteTrafficJam trafficJam;
    private List<GroupedRouteTrafficJamCause> causes;

    public RouteDetailTrafficJam(RouteTrafficJam j) {
        this.trafficJam = j;
    }

    public RouteTrafficJam getTrafficJam() {
        return trafficJam;
    }

    public void setTrafficJam(RouteTrafficJam trafficJam) {
        this.trafficJam = trafficJam;
    }

    public List<GroupedRouteTrafficJamCause>  getCauses() {
        return this.causes;
    }
    
    public void setCauses(List<GroupedRouteTrafficJamCause> causes) {
       this.causes = causes;
    }
}
