package be.ugent.verkeer4.verkeerdomain.data.composite;

public class BoundingBox {
 
    private double minLatitude;
    private double minLongitude;
    private double maxLatitude;
    private double maxLongitude;

    /**
     * @return the minLatitude
     */
    public double getMinLatitude() {
        return minLatitude;
    }

    /**
     * @param minLatitude the minLatitude to set
     */
    public void setMinLatitude(double minLatitude) {
        this.minLatitude = minLatitude;
    }

    /**
     * @return the minLongitude
     */
    public double getMinLongitude() {
        return minLongitude;
    }

    /**
     * @param minLongitude the minLongitude to set
     */
    public void setMinLongitude(double minLongitude) {
        this.minLongitude = minLongitude;
    }

    /**
     * @return the maxLatitute
     */
    public double getMaxLatitude() {
        return maxLatitude;
    }

    /**
     * @param maxLatitute the maxLatitute to set
     */
    public void setMaxLatitude(double maxLatitute) {
        this.maxLatitude = maxLatitute;
    }

    /**
     * @return the maxLongitude
     */
    public double getMaxLongitude() {
        return maxLongitude;
    }

    /**
     * @param maxLongitude the maxLongitude to set
     */
    public void setMaxLongitude(double maxLongitude) {
        this.maxLongitude = maxLongitude;
    }

    public void inflate(double padding) {
        // zolang greenwitch niet in de regio zit is dit geen probleem
        setMinLatitude(minLatitude - padding);
        setMaxLatitude(maxLatitude + padding);
        setMinLongitude(minLongitude - padding);
        setMaxLongitude(maxLongitude + padding);
    }
    
}
