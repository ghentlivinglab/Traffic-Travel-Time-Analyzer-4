/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerweb.viewmodels;

import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class RouteDataVM {

    public RouteDataVM(RouteData data) {
        this.setId(data.getId());
        
        if(data.getTimestamp().before(Date.from(LocalDateTime.now().minusMinutes(30).atZone(ZoneId.systemDefault()).toInstant())))
            this.setDelay(-1);
        else {
            if(data.getDelay() < 0)
                this.setDelay(0);
            else
                this.setDelay(data.getDelay());
        }
        
        this.setProvider(data.getProvider());
        this.setDistance(data.getDistance());
        this.setRouteId(data.getRouteId());
        this.setTimestamp(data.getTimestamp());
        this.setTravelTime(data.getTravelTime());
    }
    
    
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
