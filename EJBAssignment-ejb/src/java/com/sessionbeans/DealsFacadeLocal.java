/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sessionbeans;

import com.entitybeans.Deals;
import com.entitybeans.Products;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author PC
 */
@Local
public interface DealsFacadeLocal {

    void create(Deals deals);

    void edit(Deals deals);

    void remove(Deals deals);

    Deals find(Object id);

    List<Deals> findAll();

    List<Deals> findRange(int[] range);

    int count();
    
    public List<Deals> getTodayDeals();
    
    public Deals getTodayDealProduct(Products p);
    
}
