/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sessionbeans;

import com.entitybeans.DeliveryAddress;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author PC
 */
@Local
public interface DeliveryAddressFacadeLocal {

    void create(DeliveryAddress deliveryAddress);

    void edit(DeliveryAddress deliveryAddress);

    void remove(DeliveryAddress deliveryAddress);

    DeliveryAddress find(Object id);

    List<DeliveryAddress> findAll();

    List<DeliveryAddress> findRange(int[] range);

    int count();
    
}
