
package be.ugent.verkeer4.verkeerdomain.provider.bing;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Resource {

    @SerializedName("__type")
    @Expose
    private String Type;
    @SerializedName("bbox")
    @Expose
    private List<Double> bbox = new ArrayList<Double>();
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("distanceUnit")
    @Expose
    private String distanceUnit;
    @SerializedName("durationUnit")
    @Expose
    private String durationUnit;
    @SerializedName("routeLegs")
    @Expose
    private List<RouteLeg> routeLegs = new ArrayList<RouteLeg>();
    @SerializedName("trafficCongestion")
    @Expose
    private String trafficCongestion;
    @SerializedName("trafficDataUsed")
    @Expose
    private String trafficDataUsed;
    @SerializedName("travelDistance")
    @Expose
    private Double travelDistance;
    @SerializedName("travelDuration")
    @Expose
    private Integer travelDuration;
    @SerializedName("travelDurationTraffic")
    @Expose
    private Integer travelDurationTraffic;

    /**
     * 
     * @return
     *     The Type
     */
    public String getType() {
        return Type;
    }

    /**
     * 
     * @param Type
     *     The __type
     */
    public void setType(String Type) {
        this.Type = Type;
    }

    /**
     * 
     * @return
     *     The bbox
     */
    public List<Double> getBbox() {
        return bbox;
    }

    /**
     * 
     * @param bbox
     *     The bbox
     */
    public void setBbox(List<Double> bbox) {
        this.bbox = bbox;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The distanceUnit
     */
    public String getDistanceUnit() {
        return distanceUnit;
    }

    /**
     * 
     * @param distanceUnit
     *     The distanceUnit
     */
    public void setDistanceUnit(String distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    /**
     * 
     * @return
     *     The durationUnit
     */
    public String getDurationUnit() {
        return durationUnit;
    }

    /**
     * 
     * @param durationUnit
     *     The durationUnit
     */
    public void setDurationUnit(String durationUnit) {
        this.durationUnit = durationUnit;
    }

    /**
     * 
     * @return
     *     The routeLegs
     */
    public List<RouteLeg> getRouteLegs() {
        return routeLegs;
    }

    /**
     * 
     * @param routeLegs
     *     The routeLegs
     */
    public void setRouteLegs(List<RouteLeg> routeLegs) {
        this.routeLegs = routeLegs;
    }

    /**
     * 
     * @return
     *     The trafficCongestion
     */
    public String getTrafficCongestion() {
        return trafficCongestion;
    }

    /**
     * 
     * @param trafficCongestion
     *     The trafficCongestion
     */
    public void setTrafficCongestion(String trafficCongestion) {
        this.trafficCongestion = trafficCongestion;
    }

    /**
     * 
     * @return
     *     The trafficDataUsed
     */
    public String getTrafficDataUsed() {
        return trafficDataUsed;
    }

    /**
     * 
     * @param trafficDataUsed
     *     The trafficDataUsed
     */
    public void setTrafficDataUsed(String trafficDataUsed) {
        this.trafficDataUsed = trafficDataUsed;
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

    /**
     * 
     * @return
     *     The travelDurationTraffic
     */
    public Integer getTravelDurationTraffic() {
        return travelDurationTraffic;
    }

    /**
     * 
     * @param travelDurationTraffic
     *     The travelDurationTraffic
     */
    public void setTravelDurationTraffic(Integer travelDurationTraffic) {
        this.travelDurationTraffic = travelDurationTraffic;
    }

}
