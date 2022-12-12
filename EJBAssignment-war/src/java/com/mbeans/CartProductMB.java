/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.entitybeans.Cart;
import com.entitybeans.CartShopping;
import com.entitybeans.Deals;
import com.entitybeans.OrderDetails;
import com.entitybeans.OrderDetailsPK;
import com.entitybeans.Orders;
import com.entitybeans.Products;
import com.sessionbeans.CartFacadeLocal;
import com.sessionbeans.CartSessionBeanLocal;
import com.sessionbeans.CustomersFacadeLocal;
import com.sessionbeans.DealsFacadeLocal;
import com.sessionbeans.DeliveryAddressFacadeLocal;
import com.sessionbeans.OrderDetailsFacadeLocal;
import com.sessionbeans.OrderStatusFacadeLocal;
import com.sessionbeans.OrdersFacadeLocal;
import com.sessionbeans.ProductsFacadeLocal;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;

/**
 *
 * @author PC
 */
@Named(value = "cartProductMB")
@SessionScoped
public class CartProductMB implements Serializable {

    @EJB
    private CartFacadeLocal cartFacade;

    @EJB
    private OrderStatusFacadeLocal orderStatusFacade;

    @EJB
    private DeliveryAddressFacadeLocal deliveryAddressFacade;

    @EJB
    private CustomersFacadeLocal customersFacade;

    @EJB
    private OrdersFacadeLocal ordersFacade;

    @EJB
    private OrderDetailsFacadeLocal orderDetailsFacade;

    @EJB
    private DealsFacadeLocal dealsFacade;

    @EJB
    private ProductsFacadeLocal productsFacade;

    @EJB
    private CartSessionBeanLocal cartSessionBean;

    List<Integer> numCart = new ArrayList<>();

    private double totalAmount;

    private int totalQuantity;

    private double totalDealAmount;

    private String userID;

    private String addID;

    private String payment;

    private String note;

    private Orders order;

    private int quantity;

    public CartProductMB() {
        order = new Orders();
    }

    @PreDestroy
    public void destroy() {
        saveCart();
    }
    
    public void addCart(String proID) {
        if (!userID.equals("")) {
            cartSessionBean.addCart(proID, quantity);
            calculateCart();
        }
    }

    public void resetQuantity() {
        quantity = 1;
        System.out.println(quantity);
    }

    public void updateQuantity(String id, boolean flag) {
        int max = productsFacade.find(id).getUnitInStock();
        if (flag == true) {
            if (quantity < max) {
                quantity = quantity + 1;
            }
        } else {
            if (quantity > 1) {
                quantity = quantity - 1;
            }
        }
    }

    public void emptyCart() {
        cartSessionBean.emptyCart();
        calculateCart();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("shoppingcart.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(CartProductMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadCart(String id) {
        List<Cart> listCart = cartFacade.showCartByCustomerID(customersFacade.find(id));
        for (int i = 0; i < listCart.size(); i++) {
            Cart c = listCart.get(i);
            cartSessionBean.addCart(c.getProductID().getProductID(), c.getCartQuantity());
            cartFacade.remove(c);
        }
    }

    public void saveCart() {
        List<CartShopping> listCart = new ArrayList<>();
        listCart = showCartMB();
        for (int i = 0; i < listCart.size(); i++) {
            Cart c = new Cart();
            c.setCartQuantity(listCart.get(i).getQuantity());
            c.setCustomerID(customersFacade.find(userID));
            c.setProductID(productsFacade.find(listCart.get(i).getProID()));

            cartFacade.create(c);
        }

        cartSessionBean.emptyCart();
        totalAmount = 0;
        totalDealAmount = 0;
        totalQuantity = 0;
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String createNewOrderID() {
        List<Orders> listOrder = ordersFacade.findAll();
        String lastID;
        String id;
        if (listOrder.size() == 0) {
            id = "ORD001";
        } else {
            lastID = listOrder.get(listOrder.size() - 1).getOrderID();
            int num = Integer.parseInt(lastID.substring(3)) + 1;
            if (num < 10) {
                id = "ORD00" + num;
            } else if (num >= 10 && num < 100) {
                id = "ORD0" + num;
            } else {
                id = "ORD" + num;
            }
        }
        return id;
    }

    public void placeOrder() {
        order = new Orders();
        order.setOrderID(createNewOrderID());
        order.setCustomerID(customersFacade.find(userID));
        order.setAddressID(deliveryAddressFacade.find(addID));
        order.setOrderDate(new Date());
        order.setOrderPaymentType(payment);
        order.setStatusID(orderStatusFacade.find(0));
        order.setOrderNote(note);

        ordersFacade.create(order);

        List<CartShopping> listCart = showCartMB();
        System.out.println(listCart.size());
        for (int i = 0; i < listCart.size(); i++) {
            OrderDetails details = new OrderDetails();
            OrderDetailsPK oPK = new OrderDetailsPK();
            oPK.setOrderID(order.getOrderID());
            oPK.setProductID(listCart.get(i).getProID());
            details.setOrderDetailsPK(oPK);
            details.setQuantity(listCart.get(i).getQuantity());
            details.setUnitPrice(new BigDecimal(listCart.get(i).getUnitPrice()));
            details.setDiscount(dealDiscount(listCart.get(i).getProID()));

            orderDetailsFacade.create(details);

            Products p = productsFacade.find(listCart.get(i).getProID());
            p.setUnitInStock(p.getUnitInStock() - listCart.get(i).getQuantity());
            productsFacade.edit(p);
        }

        cartSessionBean.emptyCart();
        calculateCart();

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("thankyou.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(CartProductMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadCheckOut() {
        payment = "cash";
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("checkout.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(CartProductMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void checkAddID(String id) {
        addID = id;
    }

    public void removeCart(String id) {
        System.out.println(id);
        cartSessionBean.removeCart(id);
        calculateCart();
        System.out.println(totalQuantity);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("shoppingcart.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(CartProductMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateCart(String id, boolean flag) {
        cartSessionBean.updateCart(id, flag, productsFacade.find(id).getUnitInStock());
        calculateCart();
    }

    public int countCart() {
        System.out.println("Cart: " + cartSessionBean.countCart());
        return cartSessionBean.countCart();
    }

    public double dealDiscount(String id) {
        Deals d = dealsFacade.getTodayDealProduct(productsFacade.find(id));
        if (d != null) {
//            double unitPrice = d.getProductID().getUnitPrice().doubleValue();
            return d.getDealDiscount();
        }
        return 0;
    }

    public List<CartShopping> showCartMB() {
        List<CartShopping> listCart = new ArrayList<>();
        Set<Map.Entry<String, Integer>> setCart = cartSessionBean.showCart().entrySet();
        totalAmount = 0;
        totalQuantity = 0;
        totalDealAmount = 0;
        for (Map.Entry<String, Integer> e : setCart) {
            String id = e.getKey();
            Integer quantity = e.getValue();
            Products p = productsFacade.find(id);
            double unitPrice = p.getUnitPrice().doubleValue();
            CartShopping cShop = new CartShopping(id, p.getProductName(), quantity, unitPrice, quantity * unitPrice, p.getProductImageURL());
            listCart.add(cShop);
            numCart.add(quantity);
            totalAmount += quantity * unitPrice;
            totalQuantity += quantity;
            totalDealAmount += quantity * unitPrice * (1 - dealDiscount(id));
        }
        return listCart;
    }

    public void calculateCart() {
        totalAmount = 0;
        totalQuantity = 0;
        totalDealAmount = 0;
        List<CartShopping> listCart = new ArrayList<>();
        Set<Map.Entry<String, Integer>> setCart = cartSessionBean.showCart().entrySet();
        if (setCart.size() > 0) {
            for (Map.Entry<String, Integer> e : setCart) {
                String id = e.getKey();
                Integer quantity = e.getValue();
                Products p = productsFacade.find(id);
                double unitPrice = p.getUnitPrice().doubleValue();
                totalAmount += quantity * unitPrice;
                totalQuantity += quantity;
            }
        }
    }

    public double dealTotalAmount(double dealprice, int quantity) {
        return dealprice * quantity;
    }

    public void checkUserID(String id) {
        userID = id;
    }

    public void addCartProduct(String proID, int quantity) {
        if (!userID.equals("")) {
            cartSessionBean.addCart(proID, quantity);
            calculateCart();
        }
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public double getTotalDealAmount() {
        return totalDealAmount;
    }

    public void setTotalDealAmount(double totalDealAmount) {
        this.totalDealAmount = totalDealAmount;
    }

    public String getAddID() {
        return addID;
    }

    public void setAddID(String addID) {
        this.addID = addID;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
