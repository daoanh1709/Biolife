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
@Table(name = "Customers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customers.findAll", query = "SELECT c FROM Customers c"),
    @NamedQuery(name = "Customers.findByCustomerID", query = "SELECT c FROM Customers c WHERE c.customerID = :customerID"),
    @NamedQuery(name = "Customers.findByCustomerName", query = "SELECT c FROM Customers c WHERE c.customerName = :customerName"),
    @NamedQuery(name = "Customers.findByCustomerGender", query = "SELECT c FROM Customers c WHERE c.customerGender = :customerGender"),
    @NamedQuery(name = "Customers.findByCustomerPhone", query = "SELECT c FROM Customers c WHERE c.customerPhone = :customerPhone"),
    @NamedQuery(name = "Customers.findByCustomerEmail", query = "SELECT c FROM Customers c WHERE c.customerEmail = :customerEmail"),
    @NamedQuery(name = "Customers.findByCustomerPassword", query = "SELECT c FROM Customers c WHERE c.customerPassword = :customerPassword"),
    @NamedQuery(name = "Customers.findByCustomerImageURL", query = "SELECT c FROM Customers c WHERE c.customerImageURL = :customerImageURL")})
public class Customers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "CustomerID")
    private String customerID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CustomerName")
    private String customerName;
    @Size(max = 10)
    @Column(name = "CustomerGender")
    private String customerGender;
    @Size(max = 11)
    @Column(name = "CustomerPhone")
    private String customerPhone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CustomerEmail")
    private String customerEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "CustomerPassword")
    private String customerPassword;
    @Size(max = 255)
    @Column(name = "CustomerImageURL")
    private String customerImageURL;
    @OneToMany(mappedBy = "customerID")
    private Collection<Orders> ordersCollection;
    @OneToMany(mappedBy = "customerID")
    private Collection<DeliveryAddress> deliveryAddressCollection;
    @OneToMany(mappedBy = "customerID")
    private Collection<Cart> cartCollection;

    public Customers() {
    }

    public Customers(String customerID) {
        this.customerID = customerID;
    }

    public Customers(String customerID, String customerName, String customerEmail, String customerPassword) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPassword = customerPassword;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerGender() {
        return customerGender;
    }

    public void setCustomerGender(String customerGender) {
        this.customerGender = customerGender;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public String getCustomerImageURL() {
        return customerImageURL;
    }

    public void setCustomerImageURL(String customerImageURL) {
        this.customerImageURL = customerImageURL;
    }

    @XmlTransient
    public Collection<Orders> getOrdersCollection() {
        return ordersCollection;
    }

    public void setOrdersCollection(Collection<Orders> ordersCollection) {
        this.ordersCollection = ordersCollection;
    }

    @XmlTransient
    public Collection<DeliveryAddress> getDeliveryAddressCollection() {
        return deliveryAddressCollection;
    }

    public void setDeliveryAddressCollection(Collection<DeliveryAddress> deliveryAddressCollection) {
        this.deliveryAddressCollection = deliveryAddressCollection;
    }

    @XmlTransient
    public Collection<Cart> getCartCollection() {
        return cartCollection;
    }

    public void setCartCollection(Collection<Cart> cartCollection) {
        this.cartCollection = cartCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerID != null ? customerID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customers)) {
            return false;
        }
        Customers other = (Customers) object;
        if ((this.customerID == null && other.customerID != null) || (this.customerID != null && !this.customerID.equals(other.customerID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entitybeans.Customers[ customerID=" + customerID + " ]";
    }
    
}
