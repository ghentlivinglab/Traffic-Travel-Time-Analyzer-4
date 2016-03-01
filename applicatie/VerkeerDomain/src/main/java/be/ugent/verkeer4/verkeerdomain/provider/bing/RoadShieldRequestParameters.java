
package be.ugent.verkeer4.verkeerdomain.provider.bing;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class RoadShieldRequestParameters {

    @SerializedName("bucket")
    @Expose
    private Integer bucket;
    @SerializedName("shields")
    @Expose
    private List<Shield> shields = new ArrayList<Shield>();

    /**
     * 
     * @return
     *     The bucket
     */
    public Integer getBucket() {
        return bucket;
    }

    /**
     * 
     * @param bucket
     *     The bucket
     */
    public void setBucket(Integer bucket) {
        this.bucket = bucket;
    }

    /**
     * 
     * @return
     *     The shields
     */
    public List<Shield> getShields() {
        return shields;
    }

    /**
     * 
     * @param shields
     *     The shields
     */
    public void setShields(List<Shield> shields) {
        this.shields = shields;
    }

}
