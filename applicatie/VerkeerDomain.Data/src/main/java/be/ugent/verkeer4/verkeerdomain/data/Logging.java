/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdomain.data;

import java.util.Date;

/**
 * Dit is de klasse voor een Logging.
 * @author Tomas Bolckmans
 */
public class Logging {
        
    private int id;
    private int type;
    private Date date;
    private String category;
    private String message;
    //=> zie RouteData voor voorbeeld

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
     * SQL2o kan niet goed overweg met ENUMS, daarom moet hier manueel de int omgezet worden naar een Enum
     * @return the type
     */
    public LogTypeEnum getType() {
        return LogTypeEnum.fromInt(type);
    }

    /**
     * SQL2o kan niet goed overweg met ENUMS, daarom moet hier manueel de Enum omgezet worden naar een int
     * @param type the type to set
     */
    public void setType(LogTypeEnum type) {
        this.type = type.getValue();
    }

    /**
     * @return the timestamp
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the timestamp to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Opgelet:
     * Hier moet een substring tot 255 geïmplementeerd worden aangezien we een varchar(255) gebruiken
     * @param message the message to set
     */
    public void setMessage(String message) {
        if(message.length() > 254){
            this.message = message.substring(0,254);
        } else{
            this.message = message;
        }
    }
}
