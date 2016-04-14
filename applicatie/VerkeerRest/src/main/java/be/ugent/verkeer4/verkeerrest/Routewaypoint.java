/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerrest;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Niels
 */
@Entity
@Table(name = "routewaypoint")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Routewaypoint.findAll", query = "SELECT r FROM Routewaypoint r"),
    @NamedQuery(name = "Routewaypoint.findById", query = "SELECT r FROM Routewaypoint r WHERE r.id = :id"),
    @NamedQuery(name = "Routewaypoint.findByRouteId", query = "SELECT r FROM Routewaypoint r WHERE r.routeId = :routeId"),
    @NamedQuery(name = "Routewaypoint.findByIndex", query = "SELECT r FROM Routewaypoint r WHERE r.index = :index"),
    @NamedQuery(name = "Routewaypoint.findByLatitude", query = "SELECT r FROM Routewaypoint r WHERE r.latitude = :latitude"),
    @NamedQuery(name = "Routewaypoint.findByLongitude", query = "SELECT r FROM Routewaypoint r WHERE r.longitude = :longitude")})
public class Routewaypoint implements Serializable {

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
    @Column(name = "Index")
    private int index;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Latitude")
    private double latitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Longitude")
    private double longitude;

    public Routewaypoint() {
    }

    public Routewaypoint(Integer id) {
        this.id = id;
    }

    public Routewaypoint(Integer id, int routeId, int index, double latitude, double longitude) {
        this.id = id;
        this.routeId = routeId;
        this.index = index;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Routewaypoint)) {
            return false;
        }
        Routewaypoint other = (Routewaypoint) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "be.ugent.verkeer4.verkeerrest.Routewaypoint[ id=" + id + " ]";
    }
    
}
