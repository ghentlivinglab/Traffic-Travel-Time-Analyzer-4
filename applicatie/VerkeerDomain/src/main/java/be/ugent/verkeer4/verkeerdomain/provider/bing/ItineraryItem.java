
package be.ugent.verkeer4.verkeerdomain.provider.bing;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ItineraryItem {

    @SerializedName("compassDirection")
    @Expose
    private String compassDirection;
    @SerializedName("details")
    @Expose
    private List<Detail> details = new ArrayList<Detail>();
    @SerializedName("exit")
    @Expose
    private String exit;
    @SerializedName("iconType")
    @Expose
    private String iconType;
    @SerializedName("instruction")
    @Expose
    private Instruction instruction;
    @SerializedName("maneuverPoint")
    @Expose
    private ManeuverPoint maneuverPoint;
    @SerializedName("sideOfStreet")
    @Expose
    private String sideOfStreet;
    @SerializedName("tollZone")
    @Expose
    private String tollZone;
    @SerializedName("towardsRoadName")
    @Expose
    private String towardsRoadName;
    @SerializedName("transitTerminus")
    @Expose
    private String transitTerminus;
    @SerializedName("travelDistance")
    @Expose
    private double travelDistance;
    @SerializedName("travelDuration")
    @Expose
    private double travelDuration;
    @SerializedName("travelMode")
    @Expose
    private String travelMode;
    @SerializedName("hints")
    @Expose
    private List<Hint> hints = new ArrayList<Hint>();

    /**
     * 
     * @return
     *     The compassDirection
     */
    public String getCompassDirection() {
        return compassDirection;
    }

    /**
     * 
     * @param compassDirection
     *     The compassDirection
     */
    public void setCompassDirection(String compassDirection) {
        this.compassDirection = compassDirection;
    }

    /**
     * 
     * @return
     *     The details
     */
    public List<Detail> getDetails() {
        return details;
    }

    /**
     * 
     * @param details
     *     The details
     */
    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    /**
     * 
     * @return
     *     The exit
     */
    public String getExit() {
        return exit;
    }

    /**
     * 
     * @param exit
     *     The exit
     */
    public void setExit(String exit) {
        this.exit = exit;
    }

    /**
     * 
     * @return
     *     The iconType
     */
    public String getIconType() {
        return iconType;
    }

    /**
     * 
     * @param iconType
     *     The iconType
     */
    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    /**
     * 
     * @return
     *     The instruction
     */
    public Instruction getInstruction() {
        return instruction;
    }

    /**
     * 
     * @param instruction
     *     The instruction
     */
    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    /**
     * 
     * @return
     *     The maneuverPoint
     */
    public ManeuverPoint getManeuverPoint() {
        return maneuverPoint;
    }

    /**
     * 
     * @param maneuverPoint
     *     The maneuverPoint
     */
    public void setManeuverPoint(ManeuverPoint maneuverPoint) {
        this.maneuverPoint = maneuverPoint;
    }

    /**
     * 
     * @return
     *     The sideOfStreet
     */
    public String getSideOfStreet() {
        return sideOfStreet;
    }

    /**
     * 
     * @param sideOfStreet
     *     The sideOfStreet
     */
    public void setSideOfStreet(String sideOfStreet) {
        this.sideOfStreet = sideOfStreet;
    }

    /**
     * 
     * @return
     *     The tollZone
     */
    public String getTollZone() {
        return tollZone;
    }

    /**
     * 
     * @param tollZone
     *     The tollZone
     */
    public void setTollZone(String tollZone) {
        this.tollZone = tollZone;
    }

    /**
     * 
     * @return
     *     The towardsRoadName
     */
    public String getTowardsRoadName() {
        return towardsRoadName;
    }

    /**
     * 
     * @param towardsRoadName
     *     The towardsRoadName
     */
    public void setTowardsRoadName(String towardsRoadName) {
        this.towardsRoadName = towardsRoadName;
    }

    /**
     * 
     * @return
     *     The transitTerminus
     */
    public String getTransitTerminus() {
        return transitTerminus;
    }

    /**
     * 
     * @param transitTerminus
     *     The transitTerminus
     */
    public void setTransitTerminus(String transitTerminus) {
        this.transitTerminus = transitTerminus;
    }

    /**
     * 
     * @return
     *     The travelDistance
     */
    public double getTravelDistance() {
        return travelDistance;
    }

    /**
     * 
     * @param travelDistance
     *     The travelDistance
     */
    public void setTravelDistance(Integer travelDistance) {
        this.travelDistance = travelDistance;
    }

    /**
     * 
     * @return
     *     The travelDuration
     */
    public double getTravelDuration() {
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

    /**
     * 
     * @return
     *     The hints
     */
    public List<Hint> getHints() {
        return hints;
    }

    /**
     * 
     * @param hints
     *     The hints
     */
    public void setHints(List<Hint> hints) {
        this.hints = hints;
    }

}
