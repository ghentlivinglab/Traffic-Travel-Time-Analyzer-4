package be.ugent.verkeer4.verkeerweb.dataobjects;

import be.ugent.verkeer4.verkeerdomain.data.RouteTrafficJam;
import be.ugent.verkeer4.verkeerdomain.data.RouteTrafficJamCause;
import java.util.List;

public class RouteDetailTrafficJam {

    private RouteTrafficJam trafficJam;
    private List<RouteTrafficJamCause> causes;

    public RouteDetailTrafficJam(RouteTrafficJam j) {
        this.trafficJam = j;
    }

    public RouteTrafficJam getTrafficJam() {
        return trafficJam;
    }

    public void setTrafficJam(RouteTrafficJam trafficJam) {
        this.trafficJam = trafficJam;
    }

    public List<RouteTrafficJamCause>  getCauses() {
        return this.causes;
    }
    
    public void setCauses(List<RouteTrafficJamCause> causes) {
       this.causes = causes;
    }
}
