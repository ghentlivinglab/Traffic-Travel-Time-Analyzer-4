
package be.ugent.verkeer4.verkeerdomain.provider.bing;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class EndWaypoint {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("coordinates")
    @Expose
    private List<Double> coordinates = new ArrayList<Double>();
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("isVia")
    @Expose
    private Boolean isVia;
    @SerializedName("locationIdentifier")
    @Expose
    private String locationIdentifier;
    @SerializedName("routePathIndex")
    @Expose
    private Integer routePathIndex;

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The coordinates
     */
    public List<Double> getCoordinates() {
        return coordinates;
    }

    /**
     * 
     * @param coordinates
     *     The coordinates
     */
    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The isVia
     */
    public Boolean getIsVia() {
        return isVia;
    }

    /**
     * 
     * @param isVia
     *     The isVia
     */
    public void setIsVia(Boolean isVia) {
        this.isVia = isVia;
    }

    /**
     * 
     * @return
     *     The locationIdentifier
     */
    public String getLocationIdentifier() {
        return locationIdentifier;
    }

    /**
     * 
     * @param locationIdentifier
     *     The locationIdentifier
     */
    public void setLocationIdentifier(String locationIdentifier) {
        this.locationIdentifier = locationIdentifier;
    }

    /**
     * 
     * @return
     *     The routePathIndex
     */
    public Integer getRoutePathIndex() {
        return routePathIndex;
    }

    /**
     * 
     * @param routePathIndex
     *     The routePathIndex
     */
    public void setRoutePathIndex(Integer routePathIndex) {
        this.routePathIndex = routePathIndex;
    }

}
