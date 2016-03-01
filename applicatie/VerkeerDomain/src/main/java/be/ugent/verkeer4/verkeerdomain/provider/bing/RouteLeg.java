
package be.ugent.verkeer4.verkeerdomain.provider.bing;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class RouteLeg {

    @SerializedName("actualEnd")
    @Expose
    private ActualEnd actualEnd;
    @SerializedName("actualStart")
    @Expose
    private ActualStart actualStart;
    @SerializedName("alternateVias")
    @Expose
    private List<Object> alternateVias = new ArrayList<Object>();
    @SerializedName("cost")
    @Expose
    private Integer cost;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("itineraryItems")
    @Expose
    private List<ItineraryItem> itineraryItems = new ArrayList<ItineraryItem>();
    @SerializedName("routeRegion")
    @Expose
    private String routeRegion;
    @SerializedName("routeSubLegs")
    @Expose
    private List<RouteSubLeg> routeSubLegs = new ArrayList<RouteSubLeg>();
    @SerializedName("travelDistance")
    @Expose
    private Double travelDistance;
    @SerializedName("travelDuration")
    @Expose
    private Integer travelDuration;

    /**
     * 
     * @return
     *     The actualEnd
     */
    public ActualEnd getActualEnd() {
        return actualEnd;
    }

    /**
     * 
     * @param actualEnd
     *     The actualEnd
     */
    public void setActualEnd(ActualEnd actualEnd) {
        this.actualEnd = actualEnd;
    }

    /**
     * 
     * @return
     *     The actualStart
     */
    public ActualStart getActualStart() {
        return actualStart;
    }

    /**
     * 
     * @param actualStart
     *     The actualStart
     */
    public void setActualStart(ActualStart actualStart) {
        this.actualStart = actualStart;
    }

    /**
     * 
     * @return
     *     The alternateVias
     */
    public List<Object> getAlternateVias() {
        return alternateVias;
    }

    /**
     * 
     * @param alternateVias
     *     The alternateVias
     */
    public void setAlternateVias(List<Object> alternateVias) {
        this.alternateVias = alternateVias;
    }

    /**
     * 
     * @return
     *     The cost
     */
    public Integer getCost() {
        return cost;
    }

    /**
     * 
     * @param cost
     *     The cost
     */
    public void setCost(Integer cost) {
        this.cost = cost;
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
     *     The itineraryItems
     */
    public List<ItineraryItem> getItineraryItems() {
        return itineraryItems;
    }

    /**
     * 
     * @param itineraryItems
     *     The itineraryItems
     */
    public void setItineraryItems(List<ItineraryItem> itineraryItems) {
        this.itineraryItems = itineraryItems;
    }

    /**
     * 
     * @return
     *     The routeRegion
     */
    public String getRouteRegion() {
        return routeRegion;
    }

    /**
     * 
     * @param routeRegion
     *     The routeRegion
     */
    public void setRouteRegion(String routeRegion) {
        this.routeRegion = routeRegion;
    }

    /**
     * 
     * @return
     *     The routeSubLegs
     */
    public List<RouteSubLeg> getRouteSubLegs() {
        return routeSubLegs;
    }

    /**
     * 
     * @param routeSubLegs
     *     The routeSubLegs
     */
    public void setRouteSubLegs(List<RouteSubLeg> routeSubLegs) {
        this.routeSubLegs = routeSubLegs;
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
