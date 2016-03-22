
package be.ugent.verkeer4.verkeerdomain.weather;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Features {

    @SerializedName("conditions")
    @Expose
    private Integer conditions;

    /**
     * 
     * @return
     *     The conditions
     */
    public Integer getConditions() {
        return conditions;
    }

    /**
     * 
     * @param conditions
     *     The conditions
     */
    public void setConditions(Integer conditions) {
        this.conditions = conditions;
    }

}
