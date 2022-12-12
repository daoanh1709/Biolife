/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entitybeans;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "DeliveryAddress")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DeliveryAddress.findAll", query = "SELECT d FROM DeliveryAddress d"),
    @NamedQuery(name = "DeliveryAddress.findByAddressID", query = "SELECT d FROM DeliveryAddress d WHERE d.addressID = :addressID"),
    @NamedQuery(name = "DeliveryAddress.findByAddressName", query = "SELECT d FROM DeliveryAddress d WHERE d.addressName = :addressName"),
    @NamedQuery(name = "DeliveryAddress.findByAddressPhone", query = "SELECT d FROM DeliveryAddress d WHERE d.addressPhone = :addressPhone"),
    @NamedQuery(name = "DeliveryAddress.findByAddressDetail", query = "SELECT d FROM DeliveryAddress d WHERE d.addressDetail = :addressDetail"),
    @NamedQuery(name = "DeliveryAddress.findByAddressStatus", query = "SELECT d FROM DeliveryAddress d WHERE d.addressStatus = :addressStatus"),
    @NamedQuery(name = "DeliveryAddress.findByIsDefault", query = "SELECT d FROM DeliveryAddress d WHERE d.isDefault = :isDefault")})
public class DeliveryAddress implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "AddressID")
    private String addressID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "AddressName")
    private String addressName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "AddressPhone")
    private String addressPhone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "AddressDetail")
    private String addressDetail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "AddressStatus")
    private String addressStatus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsDefault")
    private short isDefault;
    @OneToMany(mappedBy = "addressID")
    private Collection<Orders> ordersCollection;
    @JoinColumn(name = "CustomerID", referencedColumnName = "CustomerID")
    @ManyToOne
    private Customers customerID;

    public DeliveryAddress() {
    }

    public DeliveryAddress(String addressID) {
        this.addressID = addressID;
    }

    public DeliveryAddress(String addressID, String addressName, String addressPhone, String addressDetail, String addressStatus, short isDefault) {
        this.addressID = addressID;
        this.addressName = addressName;
        this.addressPhone = addressPhone;
        this.addressDetail = addressDetail;
        this.addressStatus = addressStatus;
        this.isDefault = isDefault;
    }

    public String getAddressID() {
        return addressID;
    }

    public void setAddressID(String addressID) {
        this.addressID = addressID;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getAddressPhone() {
        return addressPhone;
    }

    public void setAddressPhone(String addressPhone) {
        this.addressPhone = addressPhone;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getAddressStatus() {
        return addressStatus;
    }

    public void setAddressStatus(String addressStatus) {
        this.addressStatus = addressStatus;
    }

    public short getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(short isDefault) {
        this.isDefault = isDefault;
    }

    @XmlTransient
    public Collection<Orders> getOrdersCollection() {
        return ordersCollection;
    }

    public void setOrdersCollection(Collection<Orders> ordersCollection) {
        this.ordersCollection = ordersCollection;
    }

    public Customers getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Customers customerID) {
        this.customerID = customerID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (addressID != null ? addressID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeliveryAddress)) {
            return false;
        }
        DeliveryAddress other = (DeliveryAddress) object;
        if ((this.addressID == null && other.addressID != null) || (this.addressID != null && !this.addressID.equals(other.addressID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entitybeans.DeliveryAddress[ addressID=" + addressID + " ]";
    }
    
}
