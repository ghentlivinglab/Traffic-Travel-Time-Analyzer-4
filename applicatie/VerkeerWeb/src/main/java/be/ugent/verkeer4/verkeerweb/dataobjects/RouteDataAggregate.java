package be.ugent.verkeer4.verkeerweb.dataobjects;

import java.util.List;

public class RouteDataAggregate {
    private List<RouteRestData> travelTimes;
    private List<RouteRestData> delayData;

    public RouteDataAggregate(List<RouteRestData> travelTimes, List<RouteRestData> delayData) {
        this.travelTimes = travelTimes;
        this.delayData = delayData;
    }

    public List<RouteRestData> getTravelTimes() {
        return travelTimes;
    }

    public List<RouteRestData> getDelayData() {
        return delayData;
    }
}
