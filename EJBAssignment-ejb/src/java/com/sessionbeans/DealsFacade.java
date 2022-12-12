/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sessionbeans;

import com.entitybeans.Deals;
import com.entitybeans.Products;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author PC
 */
@Stateless
public class DealsFacade extends AbstractFacade<Deals> implements DealsFacadeLocal {

    @PersistenceContext(unitName = "EJBAssignment-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DealsFacade() {
        super(Deals.class);
    }
    
    @Override
    public List<Deals> getTodayDeals() {
        Query query = em.createQuery("SELECT d FROM Deals d WHERE d.dealStart <= :date1 AND d.dealEnd > :date2");
        Date date = new Date();
        query.setParameter("date1", date, TemporalType.DATE);
        query.setParameter("date2", date, TemporalType.DATE);
        return query.getResultList();
    }
    
    @Override
    public Deals getTodayDealProduct(Products p) {
        Query query = em.createQuery("SELECT d FROM Deals d WHERE d.productID = :productID AND d.dealStart <= :date1 AND d.dealEnd > :date2");
        Date date = new Date();
        query.setParameter("productID", p);
        query.setParameter("date1", date, TemporalType.DATE);
        query.setParameter("date2", date, TemporalType.DATE);
        try {
            return (Deals) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
}
