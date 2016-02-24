
package be.ugent.verkeer4.verkeerdomain.provider.tomtom;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Section {

    @SerializedName("startPointIndex")
    @Expose
    private Integer startPointIndex;
    @SerializedName("endPointIndex")
    @Expose
    private Integer endPointIndex;
    @SerializedName("travelMode")
    @Expose
    private String travelMode;

    /**
     * 
     * @return
     *     The startPointIndex
     */
    public Integer getStartPointIndex() {
        return startPointIndex;
    }

    /**
     * 
     * @param startPointIndex
     *     The startPointIndex
     */
    public void setStartPointIndex(Integer startPointIndex) {
        this.startPointIndex = startPointIndex;
    }

    /**
     * 
     * @return
     *     The endPointIndex
     */
    public Integer getEndPointIndex() {
        return endPointIndex;
    }

    /**
     * 
     * @param endPointIndex
     *     The endPointIndex
     */
    public void setEndPointIndex(Integer endPointIndex) {
        this.endPointIndex = endPointIndex;
    }

    /**
     * 
     * @return
     *     The travelMode
     */
    public String getTravelMode() {
        return travelMode;
    }

    /**
     * 
     * @param travelMode
     *     The travelMode
     */
    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

}
