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
@Table(name = "logging")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Logging.findAll", query = "SELECT l FROM Logging l"),
    @NamedQuery(name = "Logging.findById", query = "SELECT l FROM Logging l WHERE l.id = :id"),
    @NamedQuery(name = "Logging.findByType", query = "SELECT l FROM Logging l WHERE l.type = :type"),
    @NamedQuery(name = "Logging.findByDate", query = "SELECT l FROM Logging l WHERE l.date = :date"),
    @NamedQuery(name = "Logging.findByCategory", query = "SELECT l FROM Logging l WHERE l.category = :category"),
    @NamedQuery(name = "Logging.findByMessage", query = "SELECT l FROM Logging l WHERE l.message = :message")})
public class Logging implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Type")
    private int type;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Category")
    private String category;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Message")
    private String message;

    public Logging() {
    }

    public Logging(Integer id) {
        this.id = id;
    }

    public Logging(Integer id, int type, Date date, String category, String message) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.category = category;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
        if (!(object instanceof Logging)) {
            return false;
        }
        Logging other = (Logging) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "be.ugent.verkeer4.verkeerrest.Logging[ id=" + id + " ]";
    }
    
}
