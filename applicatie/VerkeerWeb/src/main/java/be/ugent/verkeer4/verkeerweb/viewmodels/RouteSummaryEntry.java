package be.ugent.verkeer4.verkeerweb.viewmodels;

import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.composite.RouteSummary;
import java.util.HashMap;
import java.util.Map;

public class RouteSummaryEntry {

    private Route route;
    private Map<ProviderEnum, RouteSummary> recentSummaries;

    private double averageCurrentTravelTime;
    private double delay;
    private double trafficPercentage;

    public RouteSummaryEntry() {
        recentSummaries = new HashMap<>();

        // TODO!
        trafficPercentage = Math.random();
    }

    /**
     * @return the route
     */
    public Route getRoute() {
        return route;
    }

    /**
     * @param route the route to set
     */
    public void setRoute(Route route) {
        this.route = route;
    }

    /**
     * @return the recentSummaries
     */
    public Map<ProviderEnum, RouteSummary> getRecentSummaries() {
        return recentSummaries;
    }

    /**
     * @param recentSummaries the recentSummaries to set
     */
    public void setRecentSummaries(Map<ProviderEnum, RouteSummary> recentSummaries) {
        this.recentSummaries = recentSummaries;
    }

    public double getTravelTimeForProvider(int provider) {
        ProviderEnum prov = ProviderEnum.values()[provider];
        if (!recentSummaries.containsKey(prov)) {
            return 0;
        } else {
            return recentSummaries.get(prov).getTravelTime();
        }
    }

    public double getDelayForProvider(int provider) { // spijtig genoeg geen support voor ProviderEnum provider direct
        ProviderEnum prov = ProviderEnum.values()[provider];
        if (!recentSummaries.containsKey(prov)) {
            return 0;
        } else {
            // TODO aanpassen naar .getDelay()!
            double delay = recentSummaries.get(prov).getTravelTime() - this.route.getDefaultTravelTime();
            if (delay < 0) {
                delay = 0;
            }
            return delay;
        }
    }

    public double getTrafficPercentageForProvider(int provider) {
        ProviderEnum prov = ProviderEnum.values()[provider];
        if (!recentSummaries.containsKey(prov)) {
            return 0;
        } else {
            // TODO!!
            return Math.random();
        }
    }

    /**
     * @return the averageCurrentTravelTime
     */
    public double getAverageCurrentTravelTime() {
        return averageCurrentTravelTime;
    }

    /**
     * @param averageCurrentTravelTime the averageCurrentTravelTime to set
     */
    public void setAverageCurrentTravelTime(double averageCurrentTravelTime) {
        this.averageCurrentTravelTime = averageCurrentTravelTime;
    }

    /**
     * @return the delay
     */
    public double getDelay() {
        return delay;
    }

    /**
     * @param delay the delay to set
     */
    public void setDelay(double delay) {
        this.delay = delay;
    }

    /**
     * @return the trafficPercentage
     */
    public double getTrafficPercentage() {
        return trafficPercentage;
    }

    /**
     * @param trafficPercentage the trafficPercentage to set
     */
    public void setTrafficPercentage(double trafficPercentage) {
        this.trafficPercentage = trafficPercentage;
    }

}
