package be.ugent.verkeer4.verkeerweb.dataobjects;

import java.util.ArrayList;
import java.util.List;

public class MapRoute {

    private int id;
    private String name;
    private double distance;
    private double currentDelay;
    private double averageCurrentTravelTime;
    private double trafficDelayPercentage;

    private List<MapWaypoint> waypoints;

    public MapRoute() {
        waypoints = new ArrayList<>();
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

    /**
     * @return the trafficDelayPercentage
     */
    public double getTrafficDelayPercentage() {
        return trafficDelayPercentage;
    }

    /**
     * @param trafficDelayPercentage the trafficDelayPercentage to set
     */
    public void setTrafficDelayPercentage(double trafficDelayPercentage) {
        this.trafficDelayPercentage = trafficDelayPercentage;
    }

    /**
     * @return the waypoints
     */
    public List<MapWaypoint> getWaypoints() {
        return waypoints;
    }

    /**
     * @param waypoints the waypoints to set
     */
    public void setWaypoints(List<MapWaypoint> waypoints) {
        this.waypoints = waypoints;
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
     * @return the currentDelay
     */
    public double getCurrentDelay() {
        return currentDelay;
    }

    /**
     * @param currentDelay the currentDelay to set
     */
    public void setCurrentDelay(double currentDelay) {
        this.currentDelay = currentDelay;
    }

    /**
     * @return the averageCurrentTravelTime
     */
    public double getAverageCurrentTravelTime() {
        return averageCurrentTravelTime;
    }

    /**
     * @param averageCurrentTravelTime the averageCurrentTravelTime to set
     */
    public void setAverageCurrentTravelTime(double averageCurrentTravelTime) {
        this.averageCurrentTravelTime = averageCurrentTravelTime;
    }
}
