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
@Table(name = "poi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Poi.findAll", query = "SELECT p FROM Poi p"),
    @NamedQuery(name = "Poi.findById", query = "SELECT p FROM Poi p WHERE p.id = :id"),
    @NamedQuery(name = "Poi.findByReferenceId", query = "SELECT p FROM Poi p WHERE p.referenceId = :referenceId"),
    @NamedQuery(name = "Poi.findByProvider", query = "SELECT p FROM Poi p WHERE p.provider = :provider"),
    @NamedQuery(name = "Poi.findBySince", query = "SELECT p FROM Poi p WHERE p.since = :since"),
    @NamedQuery(name = "Poi.findByUntil", query = "SELECT p FROM Poi p WHERE p.until = :until"),
    @NamedQuery(name = "Poi.findByLatitude", query = "SELECT p FROM Poi p WHERE p.latitude = :latitude"),
    @NamedQuery(name = "Poi.findByLongitude", query = "SELECT p FROM Poi p WHERE p.longitude = :longitude"),
    @NamedQuery(name = "Poi.findByInfo", query = "SELECT p FROM Poi p WHERE p.info = :info"),
    @NamedQuery(name = "Poi.findByCategory", query = "SELECT p FROM Poi p WHERE p.category = :category"),
    @NamedQuery(name = "Poi.findByMatchedWithRoutes", query = "SELECT p FROM Poi p WHERE p.matchedWithRoutes = :matchedWithRoutes")})
public class Poi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ReferenceId")
    private String referenceId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Provider")
    private short provider;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Since")
    @Temporal(TemporalType.TIMESTAMP)
    private Date since;
    @Column(name = "Until")
    @Temporal(TemporalType.TIMESTAMP)
    private Date until;
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
    @Size(min = 1, max = 255)
    @Column(name = "Info")
    private String info;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Category")
    private short category;
    @Column(name = "MatchedWithRoutes")
    private Short matchedWithRoutes;

    public Poi() {
    }

    public Poi(Integer id) {
        this.id = id;
    }

    public Poi(Integer id, String referenceId, short provider, Date since, double latitude, double longitude, String info, short category) {
        this.id = id;
        this.referenceId = referenceId;
        this.provider = provider;
        this.since = since;
        this.latitude = latitude;
        this.longitude = longitude;
        this.info = info;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public short getProvider() {
        return provider;
    }

    public void setProvider(short provider) {
        this.provider = provider;
    }

    public Date getSince() {
        return since;
    }

    public void setSince(Date since) {
        this.since = since;
    }

    public Date getUntil() {
        return until;
    }

    public void setUntil(Date until) {
        this.until = until;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public short getCategory() {
        return category;
    }

    public void setCategory(short category) {
        this.category = category;
    }

    public Short getMatchedWithRoutes() {
        return matchedWithRoutes;
    }

    public void setMatchedWithRoutes(Short matchedWithRoutes) {
        this.matchedWithRoutes = matchedWithRoutes;
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
        if (!(object instanceof Poi)) {
            return false;
        }
        Poi other = (Poi) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "be.ugent.verkeer4.verkeerrest.Poi[ id=" + id + " ]";
    }
    
}
