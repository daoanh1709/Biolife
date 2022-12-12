/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entitybeans;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "Employees")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employees.findAll", query = "SELECT e FROM Employees e"),
    @NamedQuery(name = "Employees.findByEmployeeID", query = "SELECT e FROM Employees e WHERE e.employeeID = :employeeID"),
    @NamedQuery(name = "Employees.findByEmployeeName", query = "SELECT e FROM Employees e WHERE e.employeeName = :employeeName"),
    @NamedQuery(name = "Employees.findByEmployeeGender", query = "SELECT e FROM Employees e WHERE e.employeeGender = :employeeGender"),
    @NamedQuery(name = "Employees.findByEmployeeAddress", query = "SELECT e FROM Employees e WHERE e.employeeAddress = :employeeAddress"),
    @NamedQuery(name = "Employees.findByEmployeePhone", query = "SELECT e FROM Employees e WHERE e.employeePhone = :employeePhone"),
    @NamedQuery(name = "Employees.findByEmployeeEmail", query = "SELECT e FROM Employees e WHERE e.employeeEmail = :employeeEmail"),
    @NamedQuery(name = "Employees.findByEmployeePassword", query = "SELECT e FROM Employees e WHERE e.employeePassword = :employeePassword"),
    @NamedQuery(name = "Employees.findByEmployeeImageURL", query = "SELECT e FROM Employees e WHERE e.employeeImageURL = :employeeImageURL")})
public class Employees implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "EmployeeID")
    private String employeeID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "EmployeeName")
    private String employeeName;
    @Size(max = 10)
    @Column(name = "EmployeeGender")
    private String employeeGender;
    @Size(max = 255)
    @Column(name = "EmployeeAddress")
    private String employeeAddress;
    @Size(max = 11)
    @Column(name = "EmployeePhone")
    private String employeePhone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "EmployeeEmail")
    private String employeeEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "EmployeePassword")
    private String employeePassword;
    @Size(max = 255)
    @Column(name = "EmployeeImageURL")
    private String employeeImageURL;

    public Employees() {
    }

    public Employees(String employeeID) {
        this.employeeID = employeeID;
    }

    public Employees(String employeeID, String employeeName, String employeeEmail, String employeePassword) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.employeePassword = employeePassword;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeGender() {
        return employeeGender;
    }

    public void setEmployeeGender(String employeeGender) {
        this.employeeGender = employeeGender;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }

    public String getEmployeeImageURL() {
        return employeeImageURL;
    }

    public void setEmployeeImageURL(String employeeImageURL) {
        this.employeeImageURL = employeeImageURL;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employeeID != null ? employeeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employees)) {
            return false;
        }
        Employees other = (Employees) object;
        if ((this.employeeID == null && other.employeeID != null) || (this.employeeID != null && !this.employeeID.equals(other.employeeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entitybeans.Employees[ employeeID=" + employeeID + " ]";
    }
    
}
