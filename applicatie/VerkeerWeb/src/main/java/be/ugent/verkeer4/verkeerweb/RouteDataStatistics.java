package be.ugent.verkeer4.verkeerweb;

import be.ugent.verkeer4.verkeerdomain.data.RouteData;

import java.util.List;

public class RouteDataStatistics {

    public static double mean(List<RouteData> data) {
        int sum = 0;
        for (RouteData ele : data) {
            sum += ele.getTravelTime();
        }
        return ((double) sum) / data.size();
    }

    public static double variance(double mean, List<RouteData> data) {
        int sum = 0;
        for (RouteData ele : data) {
            double diff = ele.getTravelTime() - mean;
            sum += diff * diff;
        }
        return ((double) sum) / data.size();
    }

    public static boolean withinStd(int value, double mean, double stdev, int stdevCount) {
        double low = mean - (stdev * stdevCount);
        double high = mean + (stdev * stdevCount);
        return (value > low) && (value < high);
    }
}
