package be.ugent.verkeer4.verkeerweb.dataobjects;

import java.util.List;

public class CompareData {
    // First route
    private List<long[]> route1TravelTime;
    private List<long[]> route1Delay;
    // Second route
    private List<long[]> route2TravelTime;
    private List<long[]> route2Delay;

    public List<long[]> getRoute1TravelTime() {
        return route1TravelTime;
    }

    public void setRoute1TravelTime(List<long[]> route1TravelTime) {
        this.route1TravelTime = route1TravelTime;
    }

    public List<long[]> getRoute1Delay() {
        return route1Delay;
    }

    public void setRoute1Delay(List<long[]> route1Delay) {
        this.route1Delay = route1Delay;
    }

    public List<long[]> getRoute2TravelTime() {
        return route2TravelTime;
    }

    public void setRoute2TravelTime(List<long[]> route2TravelTime) {
        this.route2TravelTime = route2TravelTime;
    }

    public List<long[]> getRoute2Delay() {
        return route2Delay;
    }

    public void setRoute2Delay(List<long[]> route2Delay) {
        this.route2Delay = route2Delay;
    }
}
