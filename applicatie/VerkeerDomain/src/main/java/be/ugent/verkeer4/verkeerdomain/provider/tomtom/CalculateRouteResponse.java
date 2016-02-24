
package be.ugent.verkeer4.verkeerdomain.provider.tomtom;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class CalculateRouteResponse {

    @SerializedName("formatVersion")
    @Expose
    private String formatVersion;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("privacy")
    @Expose
    private String privacy;
    @SerializedName("routes")
    @Expose
    private List<Route> routes = new ArrayList<Route>();
    @SerializedName("optimizedWaypoints")
    @Expose
    private List<OptimizedWaypoint> optimizedWaypoints = new ArrayList<OptimizedWaypoint>();
    @SerializedName("report")
    @Expose
    private Report report;

    /**
     * 
     * @return
     *     The formatVersion
     */
    public String getFormatVersion() {
        return formatVersion;
    }

    /**
     * 
     * @param formatVersion
     *     The formatVersion
     */
    public void setFormatVersion(String formatVersion) {
        this.formatVersion = formatVersion;
    }

    /**
     * 
     * @return
     *     The copyright
     */
    public String getCopyright() {
        return copyright;
    }

    /**
     * 
     * @param copyright
     *     The copyright
     */
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    /**
     * 
     * @return
     *     The privacy
     */
    public String getPrivacy() {
        return privacy;
    }

    /**
     * 
     * @param privacy
     *     The privacy
     */
    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    /**
     * 
     * @return
     *     The routes
     */
    public List<Route> getRoutes() {
        return routes;
    }

    /**
     * 
     * @param routes
     *     The routes
     */
    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    /**
     * 
     * @return
     *     The optimizedWaypoints
     */
    public List<OptimizedWaypoint> getOptimizedWaypoints() {
        return optimizedWaypoints;
    }

    /**
     * 
     * @param optimizedWaypoints
     *     The optimizedWaypoints
     */
    public void setOptimizedWaypoints(List<OptimizedWaypoint> optimizedWaypoints) {
        this.optimizedWaypoints = optimizedWaypoints;
    }

    /**
     * 
     * @return
     *     The report
     */
    public Report getReport() {
        return report;
    }

    /**
     * 
     * @param report
     *     The report
     */
    public void setReport(Report report) {
        this.report = report;
    }

}
