/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.entitybeans.Customers;
import com.entitybeans.DeliveryAddress;
import com.sessionbeans.CustomersFacadeLocal;
import com.sessionbeans.DeliveryAddressFacadeLocal;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author PC
 */
@Named(value = "loginMB")
@SessionScoped
public class LoginMB implements Serializable {

    @EJB
    private DeliveryAddressFacadeLocal deliveryAddressFacade;

    @EJB
    private CustomersFacadeLocal customersFacade;

    private String email;

    private String password;

    private String messageEmail;

    private String messagePassword;

    private Customers user;

    private DeliveryAddress deliveryAddress;

    boolean flag;

    private boolean remember;

    public LoginMB() {

    }

    public void checkCookie() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();

        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0) {
            for (int i = 0; i < userCookies.length; i++) {
                System.out.println(userCookies[i].getName());
                if (userCookies[i].getName().equals("userEmail")) {
                    email = userCookies[i].getValue();
                    if (email.equals("")) {
                        remember = false;
                    } else {
                        remember = true;
                    }
                }
                if (userCookies[i].getName().equals("userPassword")) {
                    password = userCookies[i].getValue();
                }
            }
        }
    }

    public void checkLogin() {
        if (flag == false) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(EmployeeMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void logout() {

        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Cookie cookie = null;

        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0) {
            for (int i = 0; i < userCookies.length; i++) {
                if (userCookies[i].getName().equals("userEmail")) {
                    email = userCookies[i].getValue();
                }
                if (userCookies[i].getName().equals("userPassword")) {
                    password = userCookies[i].getValue();
                }
            }
        }
        user = new Customers();
        flag = false;
    }

    public String login() {
        messageEmail = "";
        messagePassword = "";

        if (email.equals("") && password.equals("")) {
            messageEmail = "This field is required";
            messagePassword = "This field is required";
            return "login";
        } else if (email.equals("")) {
            messageEmail = "This field is required";
            return "login";
        } else if (password.equals("")) {
            messagePassword = "This field is required";
            return "login";
        }

        Customers c = customersFacade.login(email);

        if (c == null) {
            messageEmail = "Email is not connected with any account";
            return "login";
        } else {
            messageEmail = "";
            if (!c.getCustomerPassword().equals(password)) {
                messagePassword = "Incorrect password";
                return "login";
            } else {
                messagePassword = "";
            }
        }

        if (remember == true) {
            setCookie("userEmail", email, 31536000);
            setCookie("userPassword", password, 31536000);
        } else {
            setCookie("userEmail", "", 31536000);
            setCookie("userPassword", "", 31536000);
        }

        user = c;
        List<DeliveryAddress> listAddress = deliveryAddressFacade.findAll();
        for (int i = 0; i < listAddress.size(); i++) {
            if (listAddress.get(i).getCustomerID().getCustomerID().equals(user.getCustomerID())) {
                deliveryAddress = listAddress.get(i);
            }
        }

        flag = true;
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginMB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public void setCookie(String name, String value, int expiry) {

        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Cookie cookie = null;

        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0) {
            for (int i = 0; i < userCookies.length; i++) {
                if (userCookies[i].getName().equals(name)) {
                    cookie = userCookies[i];
                }
            }
        }

        if (cookie != null) {
            cookie.setValue(value);
        } else {
            cookie = new Cookie(name, value);
//            cookie.setPath(request.getContextPath());
        }

        cookie.setMaxAge(expiry);

        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.addCookie(cookie);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessageEmail() {
        return messageEmail;
    }

    public void setMessageEmail(String messageEmail) {
        this.messageEmail = messageEmail;
    }

    public String getMessagePassword() {
        return messagePassword;
    }

    public void setMessagePassword(String messagePassword) {
        this.messagePassword = messagePassword;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public Customers getUser() {
        return user;
    }

    public void setUser(Customers user) {
        this.user = user;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

}
