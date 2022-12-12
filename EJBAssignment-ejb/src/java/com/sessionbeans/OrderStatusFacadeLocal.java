/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sessionbeans;

import com.entitybeans.OrderStatus;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author PC
 */
@Local
public interface OrderStatusFacadeLocal {

    void create(OrderStatus orderStatus);

    void edit(OrderStatus orderStatus);

    void remove(OrderStatus orderStatus);

    OrderStatus find(Object id);

    List<OrderStatus> findAll();

    List<OrderStatus> findRange(int[] range);

    int count();
    
}
