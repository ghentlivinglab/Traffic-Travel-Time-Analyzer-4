
package be.ugent.verkeer4.verkeerweb.dataobjects;

import be.ugent.verkeer4.verkeerdomain.data.RouteTrafficJam;


public class RouteDetailTrafficJam {
    
    private RouteTrafficJam trafficJam;

    public RouteDetailTrafficJam(RouteTrafficJam j) {
        this.trafficJam = j;
    }
    
    public RouteTrafficJam getTrafficJam() {
        return trafficJam;
    }

    public void setTrafficJam(RouteTrafficJam trafficJam) {
        this.trafficJam = trafficJam;
    }
    
    // TODO add causes
}
