/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sessionbeans;

import com.entitybeans.Categories;
import com.entitybeans.Products;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author PC
 */
@Local
public interface ProductsFacadeLocal {

    void create(Products products);

    void edit(Products products);

    void remove(Products products);

    Products find(Object id);

    List<Products> findAll();

    List<Products> findRange(int[] range);

    int count();
    
    public List<Products> showFeaturedProduct();
    
    public List<Products> showUnfeaturedProduct();
    
    public List<Products> showPagination(int pageNumber);
    
    public List<Products> showCategoryPagination(Categories cate, int pageNumber);
    
    public List<Products> findBestSeller(Categories cate);
    
    public List<Products> showSortProduct(int pageNumber, String sort);
    
    public List<Products> showSortCategoryProduct(Categories cate, int pageNumber, String sort);
    
}
