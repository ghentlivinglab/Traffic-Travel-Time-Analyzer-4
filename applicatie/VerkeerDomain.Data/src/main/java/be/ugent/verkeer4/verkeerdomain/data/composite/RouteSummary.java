package be.ugent.verkeer4.verkeerdomain.data.composite;

import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import java.util.Date;


public class RouteSummary {
    
    private int routeId;
    private ProviderEnum provider;
    private Date timestamp;
    private int travelTime;

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
        return provider;
    }

    /**
     * @param provider the provider to set
     */
    public void setProvider(ProviderEnum provider) {
        this.provider = provider;
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

    /**
     * @return the travelTime
     */
    public int getTravelTime() {
        return travelTime;
    }

    /**
     * @param travelTime the travelTime to set
     */
    public void setTravelTime(int travelTime) {
        this.travelTime = travelTime;
    }
    
}
