
package be.ugent.verkeer4.verkeerdomain.provider.tomtom;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Guidance {

    @SerializedName("instructions")
    @Expose
    private List<Instruction> instructions = new ArrayList<Instruction>();
    @SerializedName("instructionGroups")
    @Expose
    private List<InstructionGroup> instructionGroups = new ArrayList<InstructionGroup>();

    /**
     * 
     * @return
     *     The instructions
     */
    public List<Instruction> getInstructions() {
        return instructions;
    }

    /**
     * 
     * @param instructions
     *     The instructions
     */
    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    /**
     * 
     * @return
     *     The instructionGroups
     */
    public List<InstructionGroup> getInstructionGroups() {
        return instructionGroups;
    }

    /**
     * 
     * @param instructionGroups
     *     The instructionGroups
     */
    public void setInstructionGroups(List<InstructionGroup> instructionGroups) {
        this.instructionGroups = instructionGroups;
    }

}
