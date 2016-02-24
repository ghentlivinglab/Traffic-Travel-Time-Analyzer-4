
package be.ugent.verkeer4.verkeerdomain.provider.tomtom;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Instruction {

    @SerializedName("routeOffsetInMeters")
    @Expose
    private Integer routeOffsetInMeters;
    @SerializedName("travelTimeInSeconds")
    @Expose
    private Integer travelTimeInSeconds;
    @SerializedName("point")
    @Expose
    private Point_ point;
    @SerializedName("instructionType")
    @Expose
    private String instructionType;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("countryCode")
    @Expose
    private String countryCode;
    @SerializedName("possibleCombineWithNext")
    @Expose
    private Boolean possibleCombineWithNext;
    @SerializedName("drivingSide")
    @Expose
    private String drivingSide;
    @SerializedName("maneuver")
    @Expose
    private String maneuver;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * 
     * @return
     *     The routeOffsetInMeters
     */
    public Integer getRouteOffsetInMeters() {
        return routeOffsetInMeters;
    }

    /**
     * 
     * @param routeOffsetInMeters
     *     The routeOffsetInMeters
     */
    public void setRouteOffsetInMeters(Integer routeOffsetInMeters) {
        this.routeOffsetInMeters = routeOffsetInMeters;
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
     *     The point
     */
    public Point_ getPoint() {
        return point;
    }

    /**
     * 
     * @param point
     *     The point
     */
    public void setPoint(Point_ point) {
        this.point = point;
    }

    /**
     * 
     * @return
     *     The instructionType
     */
    public String getInstructionType() {
        return instructionType;
    }

    /**
     * 
     * @param instructionType
     *     The instructionType
     */
    public void setInstructionType(String instructionType) {
        this.instructionType = instructionType;
    }

    /**
     * 
     * @return
     *     The street
     */
    public String getStreet() {
        return street;
    }

    /**
     * 
     * @param street
     *     The street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * 
     * @return
     *     The countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * 
     * @param countryCode
     *     The countryCode
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * 
     * @return
     *     The possibleCombineWithNext
     */
    public Boolean getPossibleCombineWithNext() {
        return possibleCombineWithNext;
    }

    /**
     * 
     * @param possibleCombineWithNext
     *     The possibleCombineWithNext
     */
    public void setPossibleCombineWithNext(Boolean possibleCombineWithNext) {
        this.possibleCombineWithNext = possibleCombineWithNext;
    }

    /**
     * 
     * @return
     *     The drivingSide
     */
    public String getDrivingSide() {
        return drivingSide;
    }

    /**
     * 
     * @param drivingSide
     *     The drivingSide
     */
    public void setDrivingSide(String drivingSide) {
        this.drivingSide = drivingSide;
    }

    /**
     * 
     * @return
     *     The maneuver
     */
    public String getManeuver() {
        return maneuver;
    }

    /**
     * 
     * @param maneuver
     *     The maneuver
     */
    public void setManeuver(String maneuver) {
        this.maneuver = maneuver;
    }

    /**
     * 
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
