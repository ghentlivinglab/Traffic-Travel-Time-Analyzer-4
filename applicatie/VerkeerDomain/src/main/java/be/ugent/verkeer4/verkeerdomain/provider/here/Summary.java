
package be.ugent.verkeer4.verkeerdomain.provider.here;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Summary {

    @SerializedName("distance")
    @Expose
    private Integer distance;
    @SerializedName("trafficTime")
    @Expose
    private Integer trafficTime;
    @SerializedName("baseTime")
    @Expose
    private Integer baseTime;
    @SerializedName("flags")
    @Expose
    private List<String> flags = new ArrayList<String>();
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("travelTime")
    @Expose
    private Integer travelTime;
    @SerializedName("_type")
    @Expose
    private String Type;

    /**
     * 
     * @return
     *     The distance
     */
    public Integer getDistance() {
        return distance;
    }

    /**
     * 
     * @param distance
     *     The distance
     */
    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    /**
     * 
     * @return
     *     The trafficTime
     */
    public Integer getTrafficTime() {
        return trafficTime;
    }

    /**
     * 
     * @param trafficTime
     *     The trafficTime
     */
    public void setTrafficTime(Integer trafficTime) {
        this.trafficTime = trafficTime;
    }

    /**
     * 
     * @return
     *     The baseTime
     */
    public Integer getBaseTime() {
        return baseTime;
    }

    /**
     * 
     * @param baseTime
     *     The baseTime
     */
    public void setBaseTime(Integer baseTime) {
        this.baseTime = baseTime;
    }

    /**
     * 
     * @return
     *     The flags
     */
    public List<String> getFlags() {
        return flags;
    }

    /**
     * 
     * @param flags
     *     The flags
     */
    public void setFlags(List<String> flags) {
        this.flags = flags;
    }

    /**
     * 
     * @return
     *     The text
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * @param text
     *     The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 
     * @return
     *     The travelTime
     */
    public Integer getTravelTime() {
        return travelTime;
    }

    /**
     * 
     * @param travelTime
     *     The travelTime
     */
    public void setTravelTime(Integer travelTime) {
        this.travelTime = travelTime;
    }

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
     *     The _type
     */
    public void setType(String Type) {
        this.Type = Type;
    }

}
