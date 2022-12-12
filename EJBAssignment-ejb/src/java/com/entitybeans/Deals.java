/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entitybeans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author PC
 */
@Entity
@Table(name = "Deals")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deals.findAll", query = "SELECT d FROM Deals d"),
    @NamedQuery(name = "Deals.findByDealID", query = "SELECT d FROM Deals d WHERE d.dealID = :dealID"),
    @NamedQuery(name = "Deals.findByDealStart", query = "SELECT d FROM Deals d WHERE d.dealStart = :dealStart"),
    @NamedQuery(name = "Deals.findByDealEnd", query = "SELECT d FROM Deals d WHERE d.dealEnd = :dealEnd"),
    @NamedQuery(name = "Deals.findByDealDiscount", query = "SELECT d FROM Deals d WHERE d.dealDiscount = :dealDiscount")})
public class Deals implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "DealID")
    private String dealID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DealStart")
    @Temporal(TemporalType.DATE)
    private Date dealStart;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DealEnd")
    @Temporal(TemporalType.DATE)
    private Date dealEnd;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "DealDiscount")
    private Double dealDiscount;
    @JoinColumn(name = "ProductID", referencedColumnName = "ProductID")
    @ManyToOne
    private Products productID;

    public Deals() {
    }

    public Deals(String dealID) {
        this.dealID = dealID;
    }

    public Deals(String dealID, Date dealStart, Date dealEnd) {
        this.dealID = dealID;
        this.dealStart = dealStart;
        this.dealEnd = dealEnd;
    }

    public String getDealID() {
        return dealID;
    }

    public void setDealID(String dealID) {
        this.dealID = dealID;
    }

    public Date getDealStart() {
        return dealStart;
    }

    public void setDealStart(Date dealStart) {
        this.dealStart = dealStart;
    }

    public Date getDealEnd() {
        return dealEnd;
    }

    public void setDealEnd(Date dealEnd) {
        this.dealEnd = dealEnd;
    }

    public Double getDealDiscount() {
        return dealDiscount;
    }

    public void setDealDiscount(Double dealDiscount) {
        this.dealDiscount = dealDiscount;
    }

    public Products getProductID() {
        return productID;
    }

    public void setProductID(Products productID) {
        this.productID = productID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dealID != null ? dealID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deals)) {
            return false;
        }
        Deals other = (Deals) object;
        if ((this.dealID == null && other.dealID != null) || (this.dealID != null && !this.dealID.equals(other.dealID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entitybeans.Deals[ dealID=" + dealID + " ]";
    }
    
}
