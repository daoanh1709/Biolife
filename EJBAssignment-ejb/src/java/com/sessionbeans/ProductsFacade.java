/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sessionbeans;

import com.entitybeans.Categories;
import com.entitybeans.Products;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author PC
 */
@Stateless
public class ProductsFacade extends AbstractFacade<Products> implements ProductsFacadeLocal {

    @PersistenceContext(unitName = "EJBAssignment-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductsFacade() {
        super(Products.class);
    }
    
    @Override
    public List<Products> showFeaturedProduct() {
        Query query = em.createQuery("SELECT p FROM Products p WHERE p.productFeatured = :productFeatured");
        query.setParameter("productFeatured", true);
        try {
            return (List<Products>) query.getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    @Override
    public List<Products> showUnfeaturedProduct() {
        Query query = em.createQuery("SELECT p FROM Products p WHERE p.productFeatured = :productFeatured");
        query.setParameter("productFeatured", false);
        try {
            return (List<Products>) query.getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    @Override
    public List<Products> showPagination(int pageNumber) {
        Query query = em.createQuery("SELECT p FROM Products p");
        int pageSize = 7;
        query.setMaxResults(pageSize);
        query.setFirstResult((pageNumber - 1) * pageSize);
        return query.getResultList();
    }
    
    @Override
    public List<Products> showCategoryPagination(Categories cate, int pageNumber) {
        Query query = em.createQuery("SELECT p FROM Products p WHERE p.categoryID = :categoryID");
        query.setParameter("categoryID", cate);
        int pageSize = 7;
        query.setMaxResults(pageSize);
        query.setFirstResult((pageNumber - 1) * pageSize);
        return query.getResultList();
    }
    
    @Override
    public List<Products> showSortProduct(int pageNumber, String sort) {
        System.out.println("SELECT p FROM Products p ORDER BY p.unitPrice " + sort);
        Query query = em.createQuery("SELECT p FROM Products p ORDER BY p.unitPrice " + sort);
        int pageSize = 7;
        query.setMaxResults(pageSize);
        query.setFirstResult((pageNumber - 1) * pageSize);
        return query.getResultList();
    }
    
    @Override
    public List<Products> showSortCategoryProduct(Categories cate, int pageNumber, String sort) {
        Query query = em.createQuery("SELECT p FROM Products p WHERE p.categoryID = :categoryID ORDER BY p.unitPrice " + sort);
        query.setParameter("categoryID", cate);
        int pageSize = 7;
        query.setMaxResults(pageSize);
        query.setFirstResult((pageNumber - 1) * pageSize);
        return query.getResultList();
    }
    
    @Override
    public List<Products> findBestSeller(Categories cate) {
        Query query = em.createQuery("SELECT o.orderDetailsPK.productID, SUM(o.quantity) AS SumQuantity FROM OrderDetails o GROUP BY o.orderDetailsPK.productID ORDER BY SumQuantity DESC");
        List<Object[]> listSell = query.getResultList();
        List<Products> listProduct = new ArrayList<>();
        List<Products> list = (List<Products>) cate.getProductsCollection();
        for (int i = 0; i < listSell.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (listSell.get(i)[0].equals(list.get(j).getProductID())) {
                    listProduct.add(list.get(j));
                    break;
                }
            }
            if (listProduct.size() == 3) {
                break;
            }
        }
        return listProduct;
    }
    
}
