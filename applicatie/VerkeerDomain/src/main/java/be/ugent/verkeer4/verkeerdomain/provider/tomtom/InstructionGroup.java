
package be.ugent.verkeer4.verkeerdomain.provider.tomtom;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class InstructionGroup {

    @SerializedName("firstInstructionIndex")
    @Expose
    private Integer firstInstructionIndex;
    @SerializedName("lastInstructionIndex")
    @Expose
    private Integer lastInstructionIndex;
    @SerializedName("groupLengthInMeters")
    @Expose
    private Integer groupLengthInMeters;
    @SerializedName("groupMessage")
    @Expose
    private String groupMessage;

    /**
     * 
     * @return
     *     The firstInstructionIndex
     */
    public Integer getFirstInstructionIndex() {
        return firstInstructionIndex;
    }

    /**
     * 
     * @param firstInstructionIndex
     *     The firstInstructionIndex
     */
    public void setFirstInstructionIndex(Integer firstInstructionIndex) {
        this.firstInstructionIndex = firstInstructionIndex;
    }

    /**
     * 
     * @return
     *     The lastInstructionIndex
     */
    public Integer getLastInstructionIndex() {
        return lastInstructionIndex;
    }

    /**
     * 
     * @param lastInstructionIndex
     *     The lastInstructionIndex
     */
    public void setLastInstructionIndex(Integer lastInstructionIndex) {
        this.lastInstructionIndex = lastInstructionIndex;
    }

    /**
     * 
     * @return
     *     The groupLengthInMeters
     */
    public Integer getGroupLengthInMeters() {
        return groupLengthInMeters;
    }

    /**
     * 
     * @param groupLengthInMeters
     *     The groupLengthInMeters
     */
    public void setGroupLengthInMeters(Integer groupLengthInMeters) {
        this.groupLengthInMeters = groupLengthInMeters;
    }

    /**
     * 
     * @return
     *     The groupMessage
     */
    public String getGroupMessage() {
        return groupMessage;
    }

    /**
     * 
     * @param groupMessage
     *     The groupMessage
     */
    public void setGroupMessage(String groupMessage) {
        this.groupMessage = groupMessage;
    }

}
