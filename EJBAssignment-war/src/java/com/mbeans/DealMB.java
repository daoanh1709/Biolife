/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.entitybeans.Categories;
import com.entitybeans.Deals;
import com.entitybeans.OrderDetails;
import com.entitybeans.Products;
import com.sessionbeans.DealsFacadeLocal;
import com.sessionbeans.ProductsFacadeLocal;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;

/**
 *
 * @author PC
 */
@Named(value = "dealMB")
@SessionScoped
public class DealMB implements Serializable {

    @EJB
    private ProductsFacadeLocal productsFacade;

    @EJB
    private DealsFacadeLocal dealsFacade;

    private String formTitle;

    private Deals deal;

    private String dealStart;

    private String dealEnd;

    public DealMB() {
        deal = new Deals();
    }

    public boolean checkProductDeal(String id) {
        Deals d = dealsFacade.getTodayDealProduct(productsFacade.find(id));
        if (d == null) {
            return false;
        } else {
            return true;
        }
    }

    public String convertDateString(Date date) {
        System.out.println((date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate());
        return (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate();
    }

    public List<Deals> showTodayDeals() {
        return dealsFacade.getTodayDeals();
    }

    public String deleteDeal(String id) {
        Deals d = dealsFacade.find(id);

        dealsFacade.remove(d);

        return "deals";
    }

    public String loadUpdateForm(String id) {
        formTitle = "Edit Deal";

        deal = dealsFacade.find(id);

        dealStart = new SimpleDateFormat("dd/MM/yyyy").format(deal.getDealStart());
        dealEnd = new SimpleDateFormat("dd/MM/yyyy").format(deal.getDealEnd());

        return "dealManage";
    }

    public void saveDeal() {
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = new SimpleDateFormat("dd/MM/yyyy").parse(dealStart);
            endDate = new SimpleDateFormat("dd/MM/yyyy").parse(dealEnd);
        } catch (ParseException ex) {
            Logger.getLogger(DealMB.class.getName()).log(Level.SEVERE, null, ex);
        }

        deal.setDealStart(startDate);
        deal.setDealEnd(endDate);

        Deals d = dealsFacade.find(deal.getDealID());

        if (d == null) {
            dealsFacade.create(deal);
        } else {
            dealsFacade.edit(deal);
        }

        deal = new Deals();

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("deals.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(CategoriesMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Products> showAllProduct() {
        return productsFacade.findAll();
    }

    public String loadInsertForm(String proID) {
        Products p = productsFacade.find(proID);
        formTitle = "Add New Deal";
        deal = new Deals();
        deal.setDealID(createNewDealID());
        deal.setProductID(p);
        dealStart = null;
        dealEnd = null;
        return "dealManage";
    }

    public String createNewDealID() {
        List<Deals> listDeal = dealsFacade.findAll();
        String lastID = listDeal.get(listDeal.size() - 1).getDealID();
        String id;
        int num = Integer.parseInt(lastID.substring(3)) + 1;
        if (num < 10) {
            id = "DEA00" + num;
        } else if (num >= 10 && num < 100) {
            id = "DEA0" + num;
        } else {
            id = "DEA" + num;
        }
        return id;
    }

    public String backView() {
        return "deals";
    }

    public List<Deals> showAllDeals() {
        List<Deals> listDeal = dealsFacade.findAll();
        List<Deals> listDealReverse = new ArrayList<Deals>();
        for (int i = listDeal.size() - 1; i >= 0; i--) {
            listDealReverse.add(listDeal.get(i));
        }
        return listDealReverse;
    }

    public String getFormTitle() {
        return formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public Deals getDeal() {
        return deal;
    }

    public void setDeal(Deals deal) {
        this.deal = deal;
    }

    public String getDealStart() {
        return dealStart;
    }

    public void setDealStart(String dealStart) {
        this.dealStart = dealStart;
    }

    public String getDealEnd() {
        return dealEnd;
    }

    public void setDealEnd(String dealEnd) {
        this.dealEnd = dealEnd;
    }

}
