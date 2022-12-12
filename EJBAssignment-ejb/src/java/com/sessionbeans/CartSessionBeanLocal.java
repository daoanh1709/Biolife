/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.sessionbeans;

import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author PC
 */
@Local
public interface CartSessionBeanLocal {

    void addCart(String id, int quantity);

    Map<String, Integer> showCart();

    int countCart();

    void removeCart(String id);

    void emptyCart();
    
    public void updateCart(String id, boolean flag, int max);
}
