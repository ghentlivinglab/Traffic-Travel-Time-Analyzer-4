/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdomain.data.composite;

import be.ugent.verkeer4.verkeerdomain.data.POICategoryEnum;

/**
 *
 * @author Tomas Bolckmans
 */
public class POICount {
    private int category;
    private int amount;
    private String iconUrl;
    
    /**
     * @return the category
     */
    public POICategoryEnum getCategory() {
        return POICategoryEnum.fromInt(category);
    }
    
    /*
     *
     * @param category the category to set
     */
    public void setCategory(POICategoryEnum category) {
        this.category = category.getValue();
    }

    /**
     * @return the infoCount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getIconUrl(){
        iconUrl = "/static/images/poi_";
        switch (category) {
            case 0: //Unknown(0),
                iconUrl += "unknown";
                break;
            case 1: //Construction(1),
                iconUrl += "construction";
                break;
            case 2: //Incident(2),
                iconUrl += "incident";
                break;
            case 3: //TrafficJam(3),
                iconUrl += "jam";
                break;
            case 4: //LaneClosed(4),
                iconUrl += "laneclosed";
                break;
            case 5: //RoadClosed(5),
                iconUrl += "roadclosed";
                break;
            case 6: //PoliceTrap(6),
                iconUrl += "police";
                break;
            case 7: //Hazard(7),
                iconUrl += "hazard";
                break;
            case 8: //Accident(8);
                iconUrl += "accident";
                break;
            }
        iconUrl += ".png";
        return iconUrl;
    }
}
