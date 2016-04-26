/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerrest;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Niels
 */
@Entity
@Table(name = "weatherdata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Weatherdata.findAll", query = "SELECT w FROM Weatherdata w"),
    @NamedQuery(name = "Weatherdata.findById", query = "SELECT w FROM Weatherdata w WHERE w.id = :id"),
    @NamedQuery(name = "Weatherdata.findByLatitude", query = "SELECT w FROM Weatherdata w WHERE w.latitude = :latitude"),
    @NamedQuery(name = "Weatherdata.findByLongitude", query = "SELECT w FROM Weatherdata w WHERE w.longitude = :longitude"),
    @NamedQuery(name = "Weatherdata.findByTimestamp", query = "SELECT w FROM Weatherdata w WHERE w.timestamp = :timestamp"),
    @NamedQuery(name = "Weatherdata.findByUpdateTime", query = "SELECT w FROM Weatherdata w WHERE w.updateTime = :updateTime"),
    @NamedQuery(name = "Weatherdata.findByTemperature", query = "SELECT w FROM Weatherdata w WHERE w.temperature = :temperature"),
    @NamedQuery(name = "Weatherdata.findByWindSpeed", query = "SELECT w FROM Weatherdata w WHERE w.windSpeed = :windSpeed"),
    @NamedQuery(name = "Weatherdata.findByWindDirection", query = "SELECT w FROM Weatherdata w WHERE w.windDirection = :windDirection"),
    @NamedQuery(name = "Weatherdata.findByCondition", query = "SELECT w FROM Weatherdata w WHERE w.weatherCondition = :condition"),
    @NamedQuery(name = "Weatherdata.findByLocation", query = "SELECT w FROM Weatherdata w WHERE w.location = :location")})
public class Weatherdata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Latitude")
    private double latitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Longitude")
    private double longitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UpdateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Temperature")
    private double temperature;
    @Basic(optional = false)
    @NotNull
    @Column(name = "WindSpeed")
    private double windSpeed;
    @Basic(optional = false)
    @NotNull
    @Column(name = "WindDirection")
    private int windDirection;
    @Basic(optional = false)
    @NotNull
    @Column(name = "WeatherCondition")
    private int weatherCondition;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Location")
    private String location;

    public Weatherdata() {
    }

    public Weatherdata(Integer id) {
        this.id = id;
    }

    public Weatherdata(Integer id, double latitude, double longitude, Date timestamp, Date updateTime, double temperature, double windSpeed, int windDirection, int condition, String location) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
        this.updateTime = updateTime;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.weatherCondition = condition;
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    public int getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(int condition) {
        this.weatherCondition = condition;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Weatherdata)) {
            return false;
        }
        Weatherdata other = (Weatherdata) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "be.ugent.verkeer4.verkeerrest.Weatherdata[ id=" + id + " ]";
    }
    
}
