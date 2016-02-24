
package be.ugent.verkeer4.verkeerdomain.provider.tomtom;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Summary {

    @SerializedName("lengthInMeters")
    @Expose
    private Integer lengthInMeters;
    @SerializedName("travelTimeInSeconds")
    @Expose
    private Integer travelTimeInSeconds;
    @SerializedName("trafficDelayInSeconds")
    @Expose
    private Integer trafficDelayInSeconds;
    @SerializedName("departureTime")
    @Expose
    private String departureTime;
    @SerializedName("arrivalTime")
    @Expose
    private String arrivalTime;

    /**
     * 
     * @return
     *     The lengthInMeters
     */
    public Integer getLengthInMeters() {
        return lengthInMeters;
    }

    /**
     * 
     * @param lengthInMeters
     *     The lengthInMeters
     */
    public void setLengthInMeters(Integer lengthInMeters) {
        this.lengthInMeters = lengthInMeters;
    }

    /**
     * 
     * @return
     *     The travelTimeInSeconds
     */
    public Integer getTravelTimeInSeconds() {
        return travelTimeInSeconds;
    }

    /**
     * 
     * @param travelTimeInSeconds
     *     The travelTimeInSeconds
     */
    public void setTravelTimeInSeconds(Integer travelTimeInSeconds) {
        this.travelTimeInSeconds = travelTimeInSeconds;
    }

    /**
     * 
     * @return
     *     The trafficDelayInSeconds
     */
    public Integer getTrafficDelayInSeconds() {
        return trafficDelayInSeconds;
    }

    /**
     * 
     * @param trafficDelayInSeconds
     *     The trafficDelayInSeconds
     */
    public void setTrafficDelayInSeconds(Integer trafficDelayInSeconds) {
        this.trafficDelayInSeconds = trafficDelayInSeconds;
    }

    /**
     * 
     * @return
     *     The departureTime
     */
    public String getDepartureTime() {
        return departureTime;
    }

    /**
     * 
     * @param departureTime
     *     The departureTime
     */
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * 
     * @return
     *     The arrivalTime
     */
    public String getArrivalTime() {
        return arrivalTime;
    }

    /**
     * 
     * @param arrivalTime
     *     The arrivalTime
     */
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

}
