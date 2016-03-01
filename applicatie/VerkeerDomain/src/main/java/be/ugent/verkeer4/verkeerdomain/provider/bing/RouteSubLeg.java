
package be.ugent.verkeer4.verkeerdomain.provider.bing;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class RouteSubLeg {

    @SerializedName("endWaypoint")
    @Expose
    private EndWaypoint endWaypoint;
    @SerializedName("startWaypoint")
    @Expose
    private StartWaypoint startWaypoint;
    @SerializedName("travelDistance")
    @Expose
    private Double travelDistance;
    @SerializedName("travelDuration")
    @Expose
    private Integer travelDuration;

    /**
     * 
     * @return
     *     The endWaypoint
     */
    public EndWaypoint getEndWaypoint() {
        return endWaypoint;
    }

    /**
     * 
     * @param endWaypoint
     *     The endWaypoint
     */
    public void setEndWaypoint(EndWaypoint endWaypoint) {
        this.endWaypoint = endWaypoint;
    }

    /**
     * 
     * @return
     *     The startWaypoint
     */
    public StartWaypoint getStartWaypoint() {
        return startWaypoint;
    }

    /**
     * 
     * @param startWaypoint
     *     The startWaypoint
     */
    public void setStartWaypoint(StartWaypoint startWaypoint) {
        this.startWaypoint = startWaypoint;
    }

    /**
     * 
     * @return
     *     The travelDistance
     */
    public Double getTravelDistance() {
        return travelDistance;
    }

    /**
     * 
     * @param travelDistance
     *     The travelDistance
     */
    public void setTravelDistance(Double travelDistance) {
        this.travelDistance = travelDistance;
    }

    /**
     * 
     * @return
     *     The travelDuration
     */
    public Integer getTravelDuration() {
        return travelDuration;
    }

    /**
     * 
     * @param travelDuration
     *     The travelDuration
     */
    public void setTravelDuration(Integer travelDuration) {
        this.travelDuration = travelDuration;
    }

}
