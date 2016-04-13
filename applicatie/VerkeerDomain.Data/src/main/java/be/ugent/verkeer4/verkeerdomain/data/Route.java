package be.ugent.verkeer4.verkeerdomain.data;

import java.util.Date;


public class Route {
    
    private int id;
    
    private String name;
    private double distance;

    private double fromLatitude;
    private double fromLongitude;
    private String fromAddress;
    
    private double toLatitude;
    private double toLongitude;
    private String toAddress;
    
    private double defaultTravelTime;
    
    private boolean avoidHighwaysOrUseShortest;
    
    private Date lastTrafficJamCheck;
    
    /**
     * @return the Id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the Id to set
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
     * @return the fromLatitude
     */
    public double getFromLatitude() {
        return fromLatitude;
    }

    /**
     * @param fromLatitude the fromLatitude to set
     */
    public void setFromLatitude(double fromLatitude) {
        this.fromLatitude = fromLatitude;
    }

    /**
     * @return the fromLongitude
     */
    public double getFromLongitude() {
        return fromLongitude;
    }

    /**
     * @param fromLongitude the fromLongitude to set
     */
    public void setFromLongitude(double fromLongitude) {
        this.fromLongitude = fromLongitude;
    }

    /**
     * @return the fromAddress
     */
    public String getFromAddress() {
        return fromAddress;
    }

    /**
     * @param fromAddress the fromAddress to set
     */
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    /**
     * @return the toLatitude
     */
    public double getToLatitude() {
        return toLatitude;
    }

    /**
     * @param toLatitude the toLatitude to set
     */
    public void setToLatitude(double toLatitude) {
        this.toLatitude = toLatitude;
    }

    /**
     * @return the toLongitude
     */
    public double getToLongitude() {
        return toLongitude;
    }

    /**
     * @param toLongitude the toLongitude to set
     */
    public void setToLongitude(double toLongitude) {
        this.toLongitude = toLongitude;
    }

    /**
     * @return the toAddress
     */
    public String getToAddress() {
        return toAddress;
    }

    /**
     * @param toAddress the toAddress to set
     */
    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    /**
     * @return the defaultTravelTime
     */
    public double getDefaultTravelTime() {
        return defaultTravelTime;
    }

    /**
     * @param defaultTravelTime the defaultTravelTime to set
     */
    public void setDefaultTravelTime(double defaultTravelTime) {
        this.defaultTravelTime = defaultTravelTime;
    }

    /**
     * @return the avoidHighwaysOrUseShortest
     */
    public boolean getAvoidHighwaysOrUseShortest() {
        return avoidHighwaysOrUseShortest;
    }

    /**
     * @param avoidHighwaysOrUseShortest the avoidHighwaysOrUseShortest to set
     */
    public void setAvoidHighwaysOrUseShortest(boolean avoidHighwaysOrUseShortest) {
        this.avoidHighwaysOrUseShortest = avoidHighwaysOrUseShortest;
    }

    public Date getLastTrafficJamCheck() {
        return lastTrafficJamCheck;
    }

    public void setLastTrafficJamCheck(Date lastTrafficJamCheck) {
        this.lastTrafficJamCheck = lastTrafficJamCheck;
    }
}
