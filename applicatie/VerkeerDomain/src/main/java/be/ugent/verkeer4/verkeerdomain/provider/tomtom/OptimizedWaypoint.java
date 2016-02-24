
package be.ugent.verkeer4.verkeerdomain.provider.tomtom;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class OptimizedWaypoint {

    @SerializedName("providedIndex")
    @Expose
    private Integer providedIndex;
    @SerializedName("optimizedIndex")
    @Expose
    private Integer optimizedIndex;

    /**
     * 
     * @return
     *     The providedIndex
     */
    public Integer getProvidedIndex() {
        return providedIndex;
    }

    /**
     * 
     * @param providedIndex
     *     The providedIndex
     */
    public void setProvidedIndex(Integer providedIndex) {
        this.providedIndex = providedIndex;
    }

    /**
     * 
     * @return
     *     The optimizedIndex
     */
    public Integer getOptimizedIndex() {
        return optimizedIndex;
    }

    /**
     * 
     * @param optimizedIndex
     *     The optimizedIndex
     */
    public void setOptimizedIndex(Integer optimizedIndex) {
        this.optimizedIndex = optimizedIndex;
    }

}
