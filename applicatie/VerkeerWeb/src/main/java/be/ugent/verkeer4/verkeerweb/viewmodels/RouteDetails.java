package be.ugent.verkeer4.verkeerweb.viewmodels;

import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;

import java.util.List;

public class RouteDetails {

    private int id;
    private String name;
    private double distance;
    private String fromAddress;
    private String toAddress;
    private double defaultTravelTime;
    private List<RouteData> summaries;

    public RouteDetails(Route route, List<RouteData> summaries) {
        setId(route.getId());
        setName(route.getName());
        setDistance(route.getDistance());
        setFromAddress(route.getFromAddress());
        setToAddress(route.getToAddress());
        setDefaultTravelTime(route.getDefaultTravelTime());
        setSummaries(summaries);
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public double getDefaultTravelTime() {
        return defaultTravelTime;
    }

    public void setDefaultTravelTime(double defaultTravelTime) {
        this.defaultTravelTime = defaultTravelTime;
    }

    public List<RouteData> getSummaries() {
        return summaries;
    }

    public void setSummaries(List<RouteData> summaries) {
        this.summaries = summaries;
    }
}
