package be.ugent.verkeer4.verkeerweb.viewmodels;

import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import java.util.List;

public class RouteDetails {

    private int id;
    private String name;
    private double distance;

    private List<RouteData> data;

    public RouteDetails(Route route, List<RouteData> data) {
        setId(route.getId());
        setName(route.getName());
        setDistance(route.getDistance());
        setData(data);
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

    /**
     * @return the data
     */
    public List<RouteData> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<RouteData> data) {
        this.data = data;
    }

}
