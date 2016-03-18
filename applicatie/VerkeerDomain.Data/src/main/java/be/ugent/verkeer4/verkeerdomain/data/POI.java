package be.ugent.verkeer4.verkeerdomain.data;

import java.util.Date;


public class POI {
    
    private int id;
    private String referenceId;
    
    private int provider;
    
    private Date since;
    private Date until;
    
    private double latitude;
    private double longitude;
    
    private String info;
    private int category;
    
    private int matchedWithRoutes;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the referenceId
     */
    public String getReferenceId() {
        return referenceId;
    }

    /**
     * @param referenceId the referenceId to set
     */
    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    /**
     * @return the since
     */
    public Date getSince() {
        return since;
    }

    /**
     * @param since the since to set
     */
    public void setSince(Date since) {
        this.since = since;
    }

    /**
     * @return the until
     */
    public Date getUntil() {
        return until;
    }

    /**
     * @param until the until to set
     */
    public void setUntil(Date until) {
        this.until = until;
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the info
     */
    public String getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * @return the category
     */
    public POICategoryEnum getCategory() {
        return POICategoryEnum.fromInt(category);
    }

    /**
     * @param category the category to set
     */
    public void setCategory(POICategoryEnum category) {
        this.category = category.getValue();
    }

      /**
     * @return the provider
     */
    public ProviderEnum getProvider() {
        return ProviderEnum.fromInt(provider);
    }

    /**
     * @param provider the provider to set
     */
    public void setProvider(ProviderEnum provider) {
        this.provider = provider.getValue();
    }

    /**
     * @return the matchedWithRoutes
     */
    public boolean isMatchedWithRoutes() {
        return matchedWithRoutes != 0;
    }

    /**
     * @param matchedWithRoutes the matchedWithRoutes to set
     */
    public void setMatchedWithRoutes(boolean matchedWithRoutes) {
        this.matchedWithRoutes = matchedWithRoutes ? 1 : 0;
    }
    
    
            
}
