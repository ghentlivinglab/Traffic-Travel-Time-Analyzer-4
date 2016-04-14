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
@Table(name = "routetrafficjam")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Routetrafficjam.findAll", query = "SELECT r FROM Routetrafficjam r"),
    @NamedQuery(name = "Routetrafficjam.findById", query = "SELECT r FROM Routetrafficjam r WHERE r.id = :id"),
    @NamedQuery(name = "Routetrafficjam.findByRouteId", query = "SELECT r FROM Routetrafficjam r WHERE r.routeId = :routeId"),
    @NamedQuery(name = "Routetrafficjam.findByJamFrom", query = "SELECT r FROM Routetrafficjam r WHERE r.jamFrom = :jamFrom"),
    @NamedQuery(name = "Routetrafficjam.findByJamUntil", query = "SELECT r FROM Routetrafficjam r WHERE r.jamUntil = :jamUntil"),
    @NamedQuery(name = "Routetrafficjam.findByMaxDelay", query = "SELECT r FROM Routetrafficjam r WHERE r.maxDelay = :maxDelay"),
    @NamedQuery(name = "Routetrafficjam.findByAvgDelay", query = "SELECT r FROM Routetrafficjam r WHERE r.avgDelay = :avgDelay")})
public class Routetrafficjam implements Serializable {

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
    @Column(name = "JamFrom")
    @Temporal(TemporalType.TIMESTAMP)
    private Date jamFrom;
    @Basic(optional = false)
    @NotNull
    @Column(name = "JamUntil")
    @Temporal(TemporalType.TIMESTAMP)
    private Date jamUntil;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MaxDelay")
    private double maxDelay;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AvgDelay")
    private double avgDelay;

    public Routetrafficjam() {
    }

    public Routetrafficjam(Integer id) {
        this.id = id;
    }

    public Routetrafficjam(Integer id, int routeId, Date jamFrom, Date jamUntil, double maxDelay, double avgDelay) {
        this.id = id;
        this.routeId = routeId;
        this.jamFrom = jamFrom;
        this.jamUntil = jamUntil;
        this.maxDelay = maxDelay;
        this.avgDelay = avgDelay;
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

    public Date getJamFrom() {
        return jamFrom;
    }

    public void setJamFrom(Date jamFrom) {
        this.jamFrom = jamFrom;
    }

    public Date getJamUntil() {
        return jamUntil;
    }

    public void setJamUntil(Date jamUntil) {
        this.jamUntil = jamUntil;
    }

    public double getMaxDelay() {
        return maxDelay;
    }

    public void setMaxDelay(double maxDelay) {
        this.maxDelay = maxDelay;
    }

    public double getAvgDelay() {
        return avgDelay;
    }

    public void setAvgDelay(double avgDelay) {
        this.avgDelay = avgDelay;
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
        if (!(object instanceof Routetrafficjam)) {
            return false;
        }
        Routetrafficjam other = (Routetrafficjam) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "be.ugent.verkeer4.verkeerrest.Routetrafficjam[ id=" + id + " ]";
    }
    
}
