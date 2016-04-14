/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerrest;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Niels
 */
@Embeddable
public class PoinearroutePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "POIId")
    private int pOIId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RouteId")
    private int routeId;

    public PoinearroutePK() {
    }

    public PoinearroutePK(int pOIId, int routeId) {
        this.pOIId = pOIId;
        this.routeId = routeId;
    }

    public int getPOIId() {
        return pOIId;
    }

    public void setPOIId(int pOIId) {
        this.pOIId = pOIId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) pOIId;
        hash += (int) routeId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PoinearroutePK)) {
            return false;
        }
        PoinearroutePK other = (PoinearroutePK) object;
        if (this.pOIId != other.pOIId) {
            return false;
        }
        if (this.routeId != other.routeId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "be.ugent.verkeer4.verkeerrest.PoinearroutePK[ pOIId=" + pOIId + ", routeId=" + routeId + " ]";
    }
    
}
