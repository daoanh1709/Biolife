/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatefulEjbClass.java to edit this template
 */
package com.sessionbeans;

import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateful;

/**
 *
 * @author PC
 */
@Stateful
public class CartSessionBean implements CartSessionBeanLocal {

    Map<String, Integer> myCart = new HashMap();

    @Override
    public void addCart(String id, int quantity) {
        if (myCart.containsKey(id)) {
            myCart.replace(id, myCart.get(id) + quantity);
        } else {
            myCart.put(id, quantity);
        }
    }

    @Override
    public Map<String, Integer> showCart() {
        return myCart;
    }

    @Override
    public int countCart() {
        return myCart.size();
    }

    @Override
    public void removeCart(String id) {
        myCart.remove(id);
    }

    @Override
    public void emptyCart() {
        myCart.clear();
    }

    @Override
    public void updateCart(String id, boolean flag, int max) {
        if (flag) {
            if (myCart.get(id) < max) {
                myCart.replace(id, myCart.get(id) + 1);
            }
        } else {
            if (myCart.get(id) > 1) {
                myCart.replace(id, myCart.get(id) - 1);
            }
        }
    }
}
