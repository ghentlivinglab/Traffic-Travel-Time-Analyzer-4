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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Niels
 */
@Entity
@Table(name = "routedata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Routedata.findAll", query = "SELECT r FROM Routedata r"),
    @NamedQuery(name = "Routedata.findById", query = "SELECT r FROM Routedata r WHERE r.id = :id"),
    @NamedQuery(name = "Routedata.findByRouteId", query = "SELECT r FROM Routedata r WHERE r.routeId = :routeId"),
    @NamedQuery(name = "Routedata.findByTimestamp", query = "SELECT r FROM Routedata r WHERE r.timestamp = :timestamp"),
    @NamedQuery(name = "Routedata.findByProvider", query = "SELECT r FROM Routedata r WHERE r.provider = :provider"),
    @NamedQuery(name = "Routedata.findByTravelTime", query = "SELECT r FROM Routedata r WHERE r.travelTime = :travelTime"),
    @NamedQuery(name = "Routedata.findByDistance", query = "SELECT r FROM Routedata r WHERE r.distance = :distance"),
    @NamedQuery(name = "Routedata.findByDelay", query = "SELECT r FROM Routedata r WHERE r.delay = :delay")})
public class Routedata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RouteId")
    private int routeId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Provider")
    private short provider;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TravelTime")
    private int travelTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Distance")
    private int distance;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Delay")
    private int delay;

    public Routedata() {
    }

    public Routedata(Integer id) {
        this.id = id;
    }

    public Routedata(Integer id, int routeId, Date timestamp, short provider, int travelTime, int distance, int delay) {
        this.id = id;
        this.routeId = routeId;
        this.timestamp = timestamp;
        this.provider = provider;
        this.travelTime = travelTime;
        this.distance = distance;
        this.delay = delay;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public short getProvider() {
        return provider;
    }

    public void setProvider(short provider) {
        this.provider = provider;
    }

    public int getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(int travelTime) {
        this.travelTime = travelTime;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
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
        if (!(object instanceof Routedata)) {
            return false;
        }
        Routedata other = (Routedata) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "be.ugent.verkeer4.verkeerrest.Routedata[ id=" + id + " ]";
    }
    
}
