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
@Table(name = "routetrafficjamcause")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Routetrafficjamcause.findAll", query = "SELECT r FROM Routetrafficjamcause r"),
    @NamedQuery(name = "Routetrafficjamcause.findById", query = "SELECT r FROM Routetrafficjamcause r WHERE r.id = :id"),
    @NamedQuery(name = "Routetrafficjamcause.findByRouteTrafficJamId", query = "SELECT r FROM Routetrafficjamcause r WHERE r.routeTrafficJamId = :routeTrafficJamId"),
    @NamedQuery(name = "Routetrafficjamcause.findByCategory", query = "SELECT r FROM Routetrafficjamcause r WHERE r.category = :category"),
    @NamedQuery(name = "Routetrafficjamcause.findBySubCategory", query = "SELECT r FROM Routetrafficjamcause r WHERE r.subCategory = :subCategory"),
    @NamedQuery(name = "Routetrafficjamcause.findByReferenceId", query = "SELECT r FROM Routetrafficjamcause r WHERE r.referenceId = :referenceId"),
    @NamedQuery(name = "Routetrafficjamcause.findByProbability", query = "SELECT r FROM Routetrafficjamcause r WHERE r.probability = :probability")})
public class Routetrafficjamcause implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RouteTrafficJamId")
    private int routeTrafficJamId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Category")
    private short category;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SubCategory")
    private short subCategory;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ReferenceId")
    private int referenceId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Probability")
    private double probability;

    public Routetrafficjamcause() {
    }

    public Routetrafficjamcause(Integer id) {
        this.id = id;
    }

    public Routetrafficjamcause(Integer id, int routeTrafficJamId, short category, short subCategory, int referenceId, double probability) {
        this.id = id;
        this.routeTrafficJamId = routeTrafficJamId;
        this.category = category;
        this.subCategory = subCategory;
        this.referenceId = referenceId;
        this.probability = probability;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRouteTrafficJamId() {
        return routeTrafficJamId;
    }

    public void setRouteTrafficJamId(int routeTrafficJamId) {
        this.routeTrafficJamId = routeTrafficJamId;
    }

    public short getCategory() {
        return category;
    }

    public void setCategory(short category) {
        this.category = category;
    }

    public short getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(short subCategory) {
        this.subCategory = subCategory;
    }

    public int getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(int referenceId) {
        this.referenceId = referenceId;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
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
        if (!(object instanceof Routetrafficjamcause)) {
            return false;
        }
        Routetrafficjamcause other = (Routetrafficjamcause) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "be.ugent.verkeer4.verkeerrest.Routetrafficjamcause[ id=" + id + " ]";
    }
    
}
