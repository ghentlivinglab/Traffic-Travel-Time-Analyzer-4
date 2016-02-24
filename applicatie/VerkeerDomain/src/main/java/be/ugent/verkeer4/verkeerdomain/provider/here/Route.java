
package be.ugent.verkeer4.verkeerdomain.provider.here;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Route {

    @SerializedName("waypoint")
    @Expose
    private List<Waypoint> waypoint = new ArrayList<Waypoint>();
    @SerializedName("mode")
    @Expose
    private Mode mode;
    @SerializedName("leg")
    @Expose
    private List<Leg> leg = new ArrayList<Leg>();
    @SerializedName("summary")
    @Expose
    private Summary summary;

    /**
     * 
     * @return
     *     The waypoint
     */
    public List<Waypoint> getWaypoint() {
        return waypoint;
    }

    /**
     * 
     * @param waypoint
     *     The waypoint
     */
    public void setWaypoint(List<Waypoint> waypoint) {
        this.waypoint = waypoint;
    }

    /**
     * 
     * @return
     *     The mode
     */
    public Mode getMode() {
        return mode;
    }

    /**
     * 
     * @param mode
     *     The mode
     */
    public void setMode(Mode mode) {
        this.mode = mode;
    }

    /**
     * 
     * @return
     *     The leg
     */
    public List<Leg> getLeg() {
        return leg;
    }

    /**
     * 
     * @param leg
     *     The leg
     */
    public void setLeg(List<Leg> leg) {
        this.leg = leg;
    }

    /**
     * 
     * @return
     *     The summary
     */
    public Summary getSummary() {
        return summary;
    }

    /**
     * 
     * @param summary
     *     The summary
     */
    public void setSummary(Summary summary) {
        this.summary = summary;
    }

}
