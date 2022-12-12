/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.entitybeans.Employees;
import com.sessionbeans.EmployeesFacadeLocal;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
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
@Named(value = "employeeMB")
@SessionScoped
public class EmployeeMB implements Serializable {

    @EJB
    private EmployeesFacadeLocal employeesFacade;

    private String email;

    private String password;

    private String messageEmail;

    private String messagePassword;

    private String name;

    private String imageURL;

    private boolean remember;

    private boolean flag;

    public EmployeeMB() {
    }
    
    public void checkCookie() {
        System.out.println("Aster");
        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Cookie cookie = null;

        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0) {
            for (int i = 0; i < userCookies.length; i++) {
            System.out.println(userCookies[i].getName());
                if (userCookies[i].getName().equals("email")) {
                    email = userCookies[i].getValue();
                    if (email.equals("")) {
                        remember = false;
                    } else {
                        remember = true;
                    }
                }
                if (userCookies[i].getName().equals("password")) {
                    password = userCookies[i].getValue();
                }
            }
        }
    }

    public void checkLogin() {
        System.out.println(flag);
        if (flag == false) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(EmployeeMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String logout() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Cookie cookie = null;

        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0) {
            for (int i = 0; i < userCookies.length; i++) {
                if (userCookies[i].getName().equals("email")) {
                    email = userCookies[i].getValue();
                }
                if (userCookies[i].getName().equals("password")) {
                    password = userCookies[i].getValue();
                }
            }
        }
        flag = false;
        return "login";
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

        Employees e = employeesFacade.login(email);

        if (e == null) {
            messageEmail = "Incorrect email";
            return "login";
        } else {
            messageEmail = "";
            if (!e.getEmployeePassword().equals(password)) {
                messagePassword = "Incorrect password";
                return "login";
            } else {
                messagePassword = "";
            }
        }

        if (remember == true) {
            setCookie("email", email, 31536000);
            setCookie("password", password, 31536000);
        } else {
            setCookie("email", "", 31536000);
            setCookie("password", "", 31536000);
        }

        name = e.getEmployeeName();
        imageURL = e.getEmployeeImageURL();

        flag = true;
        return "dashboard";
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

//    public boolean isFlag() {
//        return flag;
//    }
//
//    public void setFlag(boolean flag) {
//        this.flag = flag;
//    }
}
