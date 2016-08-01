
package be.ugent.verkeer4.verkeerdomain.data;

public class POINearRoute {
    
    private int poiId;
    private int routeId;
    private double distance;

    /**
     * @return the poiId
     */
    public int getPoiId() {
        return poiId;
    }

    /**
     * @param poiId the poiId to set
     */
    public void setPoiId(int poiId) {
        this.poiId = poiId;
    }

    /**
     * @return the routeId
     */
    public int getRouteId() {
        return routeId;
    }

    /**
     * @param routeId the routeId to set
     */
    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    /**
     * @return the distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }
    
    
}
