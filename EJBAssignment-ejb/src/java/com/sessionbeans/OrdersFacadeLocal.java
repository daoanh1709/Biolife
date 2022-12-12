/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sessionbeans;

import com.entitybeans.Customers;
import com.entitybeans.Orders;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author PC
 */
@Local
public interface OrdersFacadeLocal {

    void create(Orders orders);

    void edit(Orders orders);

    void remove(Orders orders);

    Orders find(Object id);

    List<Orders> findAll();

    List<Orders> findRange(int[] range);

    int count();
    
    public Long countOrder(Date date);
    
    public Integer countMonthOrder(Date date1, Date date2);
    
    public List<Orders> getOrdersByDate(Date date1, Date date2);
    
    public List<Orders> getCustomerOrders(Customers c);
    
}
