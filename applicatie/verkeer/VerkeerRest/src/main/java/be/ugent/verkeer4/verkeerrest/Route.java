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
@Table(name = "route")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Route.findAll", query = "SELECT r FROM Route r"),
    @NamedQuery(name = "Route.findById", query = "SELECT r FROM Route r WHERE r.id = :id"),
    @NamedQuery(name = "Route.findByName", query = "SELECT r FROM Route r WHERE r.name = :name"),
    @NamedQuery(name = "Route.findByDistance", query = "SELECT r FROM Route r WHERE r.distance = :distance"),
    @NamedQuery(name = "Route.findByFromAddress", query = "SELECT r FROM Route r WHERE r.fromAddress = :fromAddress"),
    @NamedQuery(name = "Route.findByFromLatitude", query = "SELECT r FROM Route r WHERE r.fromLatitude = :fromLatitude"),
    @NamedQuery(name = "Route.findByFromLongitude", query = "SELECT r FROM Route r WHERE r.fromLongitude = :fromLongitude"),
    @NamedQuery(name = "Route.findByToAddress", query = "SELECT r FROM Route r WHERE r.toAddress = :toAddress"),
    @NamedQuery(name = "Route.findByToLatitude", query = "SELECT r FROM Route r WHERE r.toLatitude = :toLatitude"),
    @NamedQuery(name = "Route.findByToLongitude", query = "SELECT r FROM Route r WHERE r.toLongitude = :toLongitude"),
    @NamedQuery(name = "Route.findByDefaultTravelTime", query = "SELECT r FROM Route r WHERE r.defaultTravelTime = :defaultTravelTime"),
    @NamedQuery(name = "Route.findByAvoidHighwaysOrUseShortest", query = "SELECT r FROM Route r WHERE r.avoidHighwaysOrUseShortest = :avoidHighwaysOrUseShortest"),
    @NamedQuery(name = "Route.findByLastTrafficJamCheck", query = "SELECT r FROM Route r WHERE r.lastTrafficJamCheck = :lastTrafficJamCheck")})
public class Route implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Distance")
    private double distance;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "FromAddress")
    private String fromAddress;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FromLatitude")
    private double fromLatitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FromLongitude")
    private double fromLongitude;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ToAddress")
    private String toAddress;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ToLatitude")
    private double toLatitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ToLongitude")
    private double toLongitude;
    @Column(name = "DefaultTravelTime")
    private Integer defaultTravelTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AvoidHighwaysOrUseShortest")
    private short avoidHighwaysOrUseShortest;
    @Column(name = "LastTrafficJamCheck")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastTrafficJamCheck;

    public Route() {
    }

    public Route(Integer id) {
        this.id = id;
    }

    public Route(Integer id, String name, double distance, String fromAddress, double fromLatitude, double fromLongitude, String toAddress, double toLatitude, double toLongitude, short avoidHighwaysOrUseShortest) {
        this.id = id;
        this.name = name;
        this.distance = distance;
        this.fromAddress = fromAddress;
        this.fromLatitude = fromLatitude;
        this.fromLongitude = fromLongitude;
        this.toAddress = toAddress;
        this.toLatitude = toLatitude;
        this.toLongitude = toLongitude;
        this.avoidHighwaysOrUseShortest = avoidHighwaysOrUseShortest;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public double getFromLatitude() {
        return fromLatitude;
    }

    public void setFromLatitude(double fromLatitude) {
        this.fromLatitude = fromLatitude;
    }

    public double getFromLongitude() {
        return fromLongitude;
    }

    public void setFromLongitude(double fromLongitude) {
        this.fromLongitude = fromLongitude;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public double getToLatitude() {
        return toLatitude;
    }

    public void setToLatitude(double toLatitude) {
        this.toLatitude = toLatitude;
    }

    public double getToLongitude() {
        return toLongitude;
    }

    public void setToLongitude(double toLongitude) {
        this.toLongitude = toLongitude;
    }

    public Integer getDefaultTravelTime() {
        return defaultTravelTime;
    }

    public void setDefaultTravelTime(Integer defaultTravelTime) {
        this.defaultTravelTime = defaultTravelTime;
    }

    public short getAvoidHighwaysOrUseShortest() {
        return avoidHighwaysOrUseShortest;
    }

    public void setAvoidHighwaysOrUseShortest(short avoidHighwaysOrUseShortest) {
        this.avoidHighwaysOrUseShortest = avoidHighwaysOrUseShortest;
    }

    public Date getLastTrafficJamCheck() {
        return lastTrafficJamCheck;
    }

    public void setLastTrafficJamCheck(Date lastTrafficJamCheck) {
        this.lastTrafficJamCheck = lastTrafficJamCheck;
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
        if (!(object instanceof Route)) {
            return false;
        }
        Route other = (Route) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "be.ugent.verkeer4.verkeerrest.Route[ id=" + id + " ]";
    }
    
}
