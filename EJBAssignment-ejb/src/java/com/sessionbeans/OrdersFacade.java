/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sessionbeans;

import com.entitybeans.Customers;
import com.entitybeans.Orders;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author PC
 */
@Stateless
public class OrdersFacade extends AbstractFacade<Orders> implements OrdersFacadeLocal {

    @PersistenceContext(unitName = "EJBAssignment-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrdersFacade() {
        super(Orders.class);
    }

    @Override
    public Long countOrder(Date date) {
        Query query = em.createQuery("SELECT COUNT(o.orderID) FROM Orders o WHERE o.orderDate >= :orderDate AND o.orderDate < :nextDate");
        query.setParameter("orderDate", date, TemporalType.DATE);
        query.setParameter("nextDate", new Date(date.getTime() + (1000 * 60 * 60 * 24)), TemporalType.DATE);

        return (Long) query.getSingleResult();
    }
    
    @Override
    public Integer countMonthOrder(Date date1, Date date2) {
        Query query = em.createQuery("SELECT COUNT(o.orderID) FROM Orders o WHERE o.orderDate >= :date1 AND o.orderDate < :date2");
        query.setParameter("date1", date1, TemporalType.DATE);
        query.setParameter("date2", date2, TemporalType.DATE);

        return (int) (long) query.getSingleResult();
    }
    
    @Override
    public List<Orders> getOrdersByDate(Date date1, Date date2) {
        Query query = em.createQuery("SELECT o FROM Orders o WHERE o.orderDate >= :date1 AND o.orderDate < :date2");
        query.setParameter("date1", date1, TemporalType.DATE);
        query.setParameter("date2", date2, TemporalType.DATE);
        
        return (List<Orders>) query.getResultList();
    } 
    
    @Override
    public List<Orders> getCustomerOrders(Customers c) {
        Query query = em.createQuery("SELECT o FROM Orders o WHERE o.customerID = :customerID");
        query.setParameter("customerID", c);
        
        return (List<Orders>) query.getResultList();
    } 

}
