
package be.ugent.verkeer4.verkeerdomain.provider.tomtom;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Route {

    @SerializedName("summary")
    @Expose
    private Summary summary;
    @SerializedName("legs")
    @Expose
    private List<Leg> legs = new ArrayList<Leg>();
    @SerializedName("sections")
    @Expose
    private List<Section> sections = new ArrayList<Section>();
    @SerializedName("guidance")
    @Expose
    private Guidance guidance;

    /**
     * 
     * @return
     *     The summary
     */
    public Summary getSummary() {
        return summary;
    }

    /**
     * 
     * @param summary
     *     The summary
     */
    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    /**
     * 
     * @return
     *     The legs
     */
    public List<Leg> getLegs() {
        return legs;
    }

    /**
     * 
     * @param legs
     *     The legs
     */
    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    /**
     * 
     * @return
     *     The sections
     */
    public List<Section> getSections() {
        return sections;
    }

    /**
     * 
     * @param sections
     *     The sections
     */
    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    /**
     * 
     * @return
     *     The guidance
     */
    public Guidance getGuidance() {
        return guidance;
    }

    /**
     * 
     * @param guidance
     *     The guidance
     */
    public void setGuidance(Guidance guidance) {
        this.guidance = guidance;
    }

}
