
package be.ugent.verkeer4.verkeerdomain.provider.bing;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Shield {

    @SerializedName("labels")
    @Expose
    private List<String> labels = new ArrayList<String>();
    @SerializedName("roadShieldType")
    @Expose
    private Integer roadShieldType;

    /**
     * 
     * @return
     *     The labels
     */
    public List<String> getLabels() {
        return labels;
    }

    /**
     * 
     * @param labels
     *     The labels
     */
    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    /**
     * 
     * @return
     *     The roadShieldType
     */
    public Integer getRoadShieldType() {
        return roadShieldType;
    }

    /**
     * 
     * @param roadShieldType
     *     The roadShieldType
     */
    public void setRoadShieldType(Integer roadShieldType) {
        this.roadShieldType = roadShieldType;
    }

}
