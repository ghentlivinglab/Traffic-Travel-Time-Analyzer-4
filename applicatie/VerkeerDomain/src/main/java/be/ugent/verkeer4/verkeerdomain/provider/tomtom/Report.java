
package be.ugent.verkeer4.verkeerdomain.provider.tomtom;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Report {

    @SerializedName("effectiveSettings")
    @Expose
    private List<EffectiveSetting> effectiveSettings = new ArrayList<EffectiveSetting>();

    /**
     * 
     * @return
     *     The effectiveSettings
     */
    public List<EffectiveSetting> getEffectiveSettings() {
        return effectiveSettings;
    }

    /**
     * 
     * @param effectiveSettings
     *     The effectiveSettings
     */
    public void setEffectiveSettings(List<EffectiveSetting> effectiveSettings) {
        this.effectiveSettings = effectiveSettings;
    }

}
