/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entitybeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "Products")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Products.findAll", query = "SELECT p FROM Products p"),
    @NamedQuery(name = "Products.findByProductID", query = "SELECT p FROM Products p WHERE p.productID = :productID"),
    @NamedQuery(name = "Products.findByProductName", query = "SELECT p FROM Products p WHERE p.productName = :productName"),
    @NamedQuery(name = "Products.findByUnitPrice", query = "SELECT p FROM Products p WHERE p.unitPrice = :unitPrice"),
    @NamedQuery(name = "Products.findByUnitInStock", query = "SELECT p FROM Products p WHERE p.unitInStock = :unitInStock"),
    @NamedQuery(name = "Products.findByProductImageURL", query = "SELECT p FROM Products p WHERE p.productImageURL = :productImageURL"),
    @NamedQuery(name = "Products.findByProductFeatured", query = "SELECT p FROM Products p WHERE p.productFeatured = :productFeatured"),
    @NamedQuery(name = "Products.findByProductStatus", query = "SELECT p FROM Products p WHERE p.productStatus = :productStatus")})
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "ProductID")
    private String productID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ProductName")
    private String productName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "UnitPrice")
    private BigDecimal unitPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UnitInStock")
    private Integer unitInStock;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 2147483647)
    @Column(name = "ProductDescription")
    private String productDescription;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ProductImageURL")
    private String productImageURL;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ProductFeatured")
    private boolean productFeatured;
    @Size(max = 255)
    @Column(name = "ProductStatus")
    private String productStatus;
    @JoinColumn(name = "CategoryID", referencedColumnName = "CategoryID")
    @ManyToOne
    private Categories categoryID;
    @OneToMany(mappedBy = "productID")
    private Collection<ProductImages> productImagesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "products")
    private Collection<OrderDetails> orderDetailsCollection;
    @OneToMany(mappedBy = "productID")
    private Collection<Deals> dealsCollection;
    @OneToMany(mappedBy = "productID")
    private Collection<Cart> cartCollection;

    public Products() {
    }

    public Products(String productID) {
        this.productID = productID;
    }

    public Products(String productID, String productName, BigDecimal unitPrice, int unitInStock, String productDescription, String productImageURL, boolean productFeatured) {
        this.productID = productID;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.unitInStock = unitInStock;
        this.productDescription = productDescription;
        this.productImageURL = productImageURL;
        this.productFeatured = productFeatured;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getUnitInStock() {
        return unitInStock;
    }

    public void setUnitInStock(Integer unitInStock) {
        this.unitInStock = unitInStock;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImageURL() {
        return productImageURL;
    }

    public void setProductImageURL(String productImageURL) {
        this.productImageURL = productImageURL;
    }

    public boolean getProductFeatured() {
        return productFeatured;
    }

    public void setProductFeatured(boolean productFeatured) {
        this.productFeatured = productFeatured;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public Categories getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Categories categoryID) {
        this.categoryID = categoryID;
    }

    @XmlTransient
    public Collection<ProductImages> getProductImagesCollection() {
        return productImagesCollection;
    }

    public void setProductImagesCollection(Collection<ProductImages> productImagesCollection) {
        this.productImagesCollection = productImagesCollection;
    }

    @XmlTransient
    public Collection<OrderDetails> getOrderDetailsCollection() {
        return orderDetailsCollection;
    }

    public void setOrderDetailsCollection(Collection<OrderDetails> orderDetailsCollection) {
        this.orderDetailsCollection = orderDetailsCollection;
    }

    @XmlTransient
    public Collection<Deals> getDealsCollection() {
        return dealsCollection;
    }

    public void setDealsCollection(Collection<Deals> dealsCollection) {
        this.dealsCollection = dealsCollection;
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
        hash += (productID != null ? productID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Products)) {
            return false;
        }
        Products other = (Products) object;
        if ((this.productID == null && other.productID != null) || (this.productID != null && !this.productID.equals(other.productID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entitybeans.Products[ productID=" + productID + " ]";
    }
    
}
