
package be.ugent.verkeer4.verkeerdomain.provider.google;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Element {

    @SerializedName("distance")
    @Expose
    private Distance distance;
    @SerializedName("duration")
    @Expose
    private Duration duration;
    @SerializedName("duration_in_traffic")
    @Expose
    private DurationInTraffic durationInTraffic;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * 
     * @return
     *     The distance
     */
    public Distance getDistance() {
        return distance;
    }

    /**
     * 
     * @param distance
     *     The distance
     */
    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Element withDistance(Distance distance) {
        this.distance = distance;
        return this;
    }

    /**
     * 
     * @return
     *     The duration
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * 
     * @param duration
     *     The duration
     */
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Element withDuration(Duration duration) {
        this.duration = duration;
        return this;
    }

    /**
     * 
     * @return
     *     The durationInTraffic
     */
    public DurationInTraffic getDurationInTraffic() {
        return durationInTraffic;
    }

    /**
     * 
     * @param durationInTraffic
     *     The duration_in_traffic
     */
    public void setDurationInTraffic(DurationInTraffic durationInTraffic) {
        this.durationInTraffic = durationInTraffic;
    }

    public Element withDurationInTraffic(DurationInTraffic durationInTraffic) {
        this.durationInTraffic = durationInTraffic;
        return this;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public Element withStatus(String status) {
        this.status = status;
        return this;
    }

}
