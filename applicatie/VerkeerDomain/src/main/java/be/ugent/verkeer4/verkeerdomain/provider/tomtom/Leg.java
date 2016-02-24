
package be.ugent.verkeer4.verkeerdomain.provider.tomtom;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Leg {

    @SerializedName("summary")
    @Expose
    private Summary_ summary;
    @SerializedName("points")
    @Expose
    private List<Point> points = new ArrayList<Point>();

    /**
     * 
     * @return
     *     The summary
     */
    public Summary_ getSummary() {
        return summary;
    }

    /**
     * 
     * @param summary
     *     The summary
     */
    public void setSummary(Summary_ summary) {
        this.summary = summary;
    }

    /**
     * 
     * @return
     *     The points
     */
    public List<Point> getPoints() {
        return points;
    }

    /**
     * 
     * @param points
     *     The points
     */
    public void setPoints(List<Point> points) {
        this.points = points;
    }

}
