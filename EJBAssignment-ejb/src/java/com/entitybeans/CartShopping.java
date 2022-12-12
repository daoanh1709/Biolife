/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entitybeans;

/**
 *
 * @author PC
 */
public class CartShopping {
    private String proID;
    private String proName;
    private int quantity;
    private double unitPrice;
    private double price;
    private String image;

    public CartShopping() {
    }

    public CartShopping(String proID, String proName, int quantity, double unitPrice, double price, String image) {
        this.proID = proID;
        this.proName = proName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.price = price;
        this.image = image;
    }

    public String getProID() {
        return proID;
    }

    public void setProID(String proID) {
        this.proID = proID;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
