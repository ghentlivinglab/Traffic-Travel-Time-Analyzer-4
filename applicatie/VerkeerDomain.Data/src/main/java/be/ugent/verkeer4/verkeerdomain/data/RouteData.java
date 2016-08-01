package be.ugent.verkeer4.verkeerdomain.data;

import java.util.Date;

public class RouteData {

    private int id;
    private int routeId;
    private int provider;
    private int travelTime;
    private int delay;
    private int distance;
    private Date timestamp;

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
     * @return the routeId
     */
    public int getRouteId() {
        return routeId;
    }

    /**
     * @param routeId the routeId to set
     */
    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    /**
     * @return the provider
     */
    public ProviderEnum getProvider() {
        return ProviderEnum.fromInt(provider);
    }

    /**
     * @param provider the provider to set
     */
    public void setProvider(ProviderEnum provider) {
        this.provider = provider.getValue();
    }

    /**
     * Tijd inclusief traffic delay
     * @return the travelTime
     */
    public int getTravelTime() {
        return travelTime;
    }

    /**
     * Basis tijd om route af te leggen zonder delay
     * @return 
     */
    public double getBaseTime() {
        return travelTime - delay;
    }
    
    /**
     * @param travelTime the travelTime to set
     */
    public void setTravelTime(int travelTime) {
        this.travelTime = travelTime;
    }

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    /**
     * Traffic delay (in seconden) 
     * @return the delay
     */
    public int getDelay() {
        return delay;
    }

    /**
     * @return the distance
     */
    public int getDistance() {
        return distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

}
