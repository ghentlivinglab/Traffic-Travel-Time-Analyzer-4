package be.ugent.verkeer4.verkeerdomain.data.composite;

import be.ugent.verkeer4.verkeerdomain.data.RouteTrafficJamCauseCategoryEnum;

public class GroupedRouteTrafficJamCause {

    private String description;
    private int routeTrafficJamId;
    private int category;
    private int subCategory;
    private double avgProbability;
    private String providers;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRouteTrafficJamId() {
        return routeTrafficJamId;
    }

    public void setRouteTrafficJamId(int routeTrafficJamId) {
        this.routeTrafficJamId = routeTrafficJamId;
    }

    public RouteTrafficJamCauseCategoryEnum getCategory() {
        return RouteTrafficJamCauseCategoryEnum.fromInt(category);
    }

    public void setCategory(RouteTrafficJamCauseCategoryEnum category) {
        this.category = category.getValue();
    }

    public int getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(int subCategory) {
        this.subCategory = subCategory;
    }

    public double getAvgProbability() {
        return avgProbability;
    }

    public void setAvgProbability(double avgProbability) {
        this.avgProbability = avgProbability;
    }

    public String getProviders() {
        return providers;
    }

    public void setProviders(String providers) {
        this.providers = providers;
    }

}
