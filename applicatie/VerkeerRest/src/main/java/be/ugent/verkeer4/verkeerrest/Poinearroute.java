/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerrest;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "poinearroute")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Poinearroute.findAll", query = "SELECT p FROM Poinearroute p"),
    @NamedQuery(name = "Poinearroute.findByPOIId", query = "SELECT p FROM Poinearroute p WHERE p.poinearroutePK.pOIId = :pOIId"),
    @NamedQuery(name = "Poinearroute.findByRouteId", query = "SELECT p FROM Poinearroute p WHERE p.poinearroutePK.routeId = :routeId"),
    @NamedQuery(name = "Poinearroute.findByDistance", query = "SELECT p FROM Poinearroute p WHERE p.distance = :distance")})
public class Poinearroute implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PoinearroutePK poinearroutePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Distance")
    private double distance;

    public Poinearroute() {
    }

    public Poinearroute(PoinearroutePK poinearroutePK) {
        this.poinearroutePK = poinearroutePK;
    }

    public Poinearroute(PoinearroutePK poinearroutePK, double distance) {
        this.poinearroutePK = poinearroutePK;
        this.distance = distance;
    }

    public Poinearroute(int pOIId, int routeId) {
        this.poinearroutePK = new PoinearroutePK(pOIId, routeId);
    }

    public PoinearroutePK getPoinearroutePK() {
        return poinearroutePK;
    }

    public void setPoinearroutePK(PoinearroutePK poinearroutePK) {
        this.poinearroutePK = poinearroutePK;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (poinearroutePK != null ? poinearroutePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Poinearroute)) {
            return false;
        }
        Poinearroute other = (Poinearroute) object;
        if ((this.poinearroutePK == null && other.poinearroutePK != null) || (this.poinearroutePK != null && !this.poinearroutePK.equals(other.poinearroutePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "be.ugent.verkeer4.verkeerrest.Poinearroute[ poinearroutePK=" + poinearroutePK + " ]";
    }
    
}
