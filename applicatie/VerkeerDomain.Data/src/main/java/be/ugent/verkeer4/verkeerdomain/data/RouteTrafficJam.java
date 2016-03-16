package be.ugent.verkeer4.verkeerdomain.data;

import java.util.Date;


public class RouteTrafficJam {
    
    private int id;
    
    private int routeId;
    
    private Date jamFrom;
    private Date jamUntil;
    
    private double maxDelay;
    private double avgDelay;
    private double totalDelay;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id jamUntil set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the routeId
     */
    public int getRouteId() {
        return routeId;
    }

    /**
     * @param routeId the routeId jamUntil set
     */
    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    /**
     * @return the jamFrom
     */
    public Date getFrom() {
        return jamFrom;
    }

    /**
     * @param from the jamFrom jamUntil set
     */
    public void setFrom(Date from) {
        this.jamFrom = from;
    }

    /**
     * @return the jamUntil
     */
    public Date getTo() {
        return jamUntil;
    }

    /**
     * @param to the jamUntil jamUntil set
     */
    public void setTo(Date to) {
        this.jamUntil = to;
    }

    /**
     * @return the maxDelay
     */
    public double getMaxDelay() {
        return maxDelay;
    }

    /**
     * @param maxDelay the maxDelay jamUntil set
     */
    public void setMaxDelay(double maxDelay) {
        this.maxDelay = maxDelay;
    }

    /**
     * @return the avgDelay
     */
    public double getAvgDelay() {
        return avgDelay;
    }

    /**
     * @param avgDelay the avgDelay jamUntil set
     */
    public void setAvgDelay(double avgDelay) {
        this.avgDelay = avgDelay;
    }

    public double getTotalDelay() {
        return totalDelay;
    }

    public void setTotalDelay(double totalDelay) {
        this.totalDelay = totalDelay;
    }
    
    
}
