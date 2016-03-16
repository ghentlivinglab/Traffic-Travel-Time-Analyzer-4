
package be.ugent.verkeer4.verkeerdomain.data;

public class RouteTrafficJamCause {
    
    private int id;
    private int routeTrafficJamId;
    private int category;
    private int subCategory;
    private int referenceId;
    
    private double probability;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public int getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(int referenceId) {
        this.referenceId = referenceId;
    }
    
}
