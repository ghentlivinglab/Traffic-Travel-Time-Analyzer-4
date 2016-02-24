package be.ugent.verkeer4.verkeerweb.viewmodels;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RouteEdit {

    private Integer id;

    @NotNull(message = "Naam is verplicht")
    @Size(min = 1, max = 255, message = "Naam is verplicht en mag niet langer dan 255 karakters zijn")
    private String name;

    private String fromAddress;
    @NotNull(message = "Van positie is verplicht")
    @Size(min = 1, max = 255, message = "Van positie is verplicht")
    private String fromLatLng;
    
    private String toAddress;
    
    @NotNull(message = "Naar positie is verplicht")
    @Size(min = 1, max = 255, message = "Naar positie is verplicht")
    private String toLatLng;
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the fromAddress
     */
    public String getFromAddress() {
        return fromAddress;
    }

    /**
     * @param fromAddress the fromAddress to set
     */
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    /**
     * @return the fromLatLng
     */
    public String getFromLatLng() {
        return fromLatLng;
    }

    /**
     * @param fromLatLng the fromLatLng to set
     */
    public void setFromLatLng(String fromLatLng) {
        this.fromLatLng = fromLatLng;
    }

    /**
     * @return the toAddress
     */
    public String getToAddress() {
        return toAddress;
    }

    /**
     * @param toAddress the toAddress to set
     */
    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    /**
     * @return the toLatLng
     */
    public String getToLatLng() {
        return toLatLng;
    }

    /**
     * @param toLatLng the toLatLng to set
     */
    public void setToLatLng(String toLatLng) {
        this.toLatLng = toLatLng;
    }

}
