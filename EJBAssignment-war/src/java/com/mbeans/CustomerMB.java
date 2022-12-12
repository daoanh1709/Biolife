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
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;

/**
 *
 * @author PC
 */
@Named(value = "customerMB")
@SessionScoped
public class CustomerMB implements Serializable {

    @EJB
    private DeliveryAddressFacadeLocal deliveryAddressFacade;

    @EJB
    private CustomersFacadeLocal customersFacade;

    private Customers customers;

    private String name;
    
    private String address;

    private String email;

    private String password;

    private String confirmPassword;

    private String nameMessage;

    private String genderMessage;

    private String phoneMessage;
    
    private String addressMessage;

    private String emailMessage;

    private String passwordMessage;

    private String cfPasswordMessage;

    private boolean showAlert = false;

    public CustomerMB() {
        customers = new Customers();
    }
    
    public String createNewAddressID() {
        List<DeliveryAddress> listAddress = deliveryAddressFacade.findAll();
        String lastID = listAddress.get(listAddress.size() - 1).getAddressID();
        String id;
        int num = Integer.parseInt(lastID.substring(3)) + 1;
        if (num < 10) {
            id = "ADD00" + num;
        } else if (num >= 10 && num < 100) {
            id = "ADD0" + num;
        } else {
            id = "ADD" + num;
        }
        return id;
    }

    public String register() {
        resetMessage();

        if (!checkSignUpForm()) {
            return "register";
        }

        customers.setCustomerName(name);
        customers.setCustomerEmail(email);
        customers.setCustomerPassword(password);

        customersFacade.create(customers);
        
        Customers c = customersFacade.find(customers.getCustomerID());

        DeliveryAddress deliveryAddress = new DeliveryAddress();
        deliveryAddress.setAddressID(createNewAddressID());
        deliveryAddress.setCustomerID(c);
        deliveryAddress.setAddressName(name);
        deliveryAddress.setAddressPhone(customers.getCustomerPhone());
        deliveryAddress.setAddressDetail(address);
        deliveryAddress.setAddressStatus("Enable");
        deliveryAddress.setIsDefault(Short.valueOf(String.valueOf(0)));
        
        deliveryAddressFacade.create(deliveryAddress);
        
        showAlert = true;

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("register.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(CustomerMB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public boolean checkSignUpForm() {
        boolean valid = true;
        if (name.equals("")) {
            nameMessage = "This field is required";
            valid = false;
        } else if (!Pattern.matches("^\\s*([A-Za-z]{1,}([\\.,] |[-']| ))+[A-Za-z]+\\.?\\s*$", name)) {
            nameMessage = "Must contain only letters, whitespace and must be fullname";
            valid = false;
        } else {
            nameMessage = "";
        }

        if (customers.getCustomerGender() == null) {
            genderMessage = "Please choose this field";
            valid = false;
        }

        if (customers.getCustomerPhone().equals("")) {
            phoneMessage = "This field is required";
            valid = false;
        } else if (!Pattern.matches("^(\\(0\\d{1,3}\\)\\d{7})|(0\\d{9,10})$", customers.getCustomerPhone())) {
            phoneMessage = "Must be a 10 to 11 digit number. Ex: 0358283336";
            valid = false;
        } else {
            phoneMessage = "";
        }

        if (address.equals("")) {
            addressMessage = "This field is required";
            valid = false;
        } else {
            addressMessage = "";
        }
        
        if (email.equals("")) {
            emailMessage = "This field is required";
            valid = false;
        } else if (!Pattern.matches("^[a-zA-Z]\\w*(\\.\\w+)*\\@\\w+(\\.\\w{2,3})+$", email)) {
            emailMessage = "Email is incorrect. Ex: info1234@gmail.com";
            valid = false;
        } else {
            Customers c = customersFacade.login(email);
            if (c != null) {
                emailMessage = "This email is connected to another account";
                valid = false;
            } else {
                emailMessage = "";
            }
        }

        if (password.equals("")) {
            passwordMessage = "This field is required";
            valid = false;
        } else if (!Pattern.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}", password)) {
            passwordMessage = "Password must contain at least 1 number, 1 uppercase letter, 1 lowercase letter, and at least 8 characters";
            valid = false;
        } else {
            passwordMessage = "";
        }

        if (confirmPassword.equals("")) {
            cfPasswordMessage = "This field is required";
            valid = false;
        } else if (!confirmPassword.equals(password)) {
            cfPasswordMessage = "Passwords don't match";
            valid = false;
        } else {
            cfPasswordMessage = "";
        }

        return valid;
    }

    public void resetMessage() {
        nameMessage = "";
        genderMessage = "";
        phoneMessage = "";
        addressMessage = "";
        emailMessage = "";
        passwordMessage = "";
        cfPasswordMessage = "";
    }

    public String loadSignUpForm() {
        showAlert = false;
        resetMessage();
        customers = new Customers();
        name = "";
        address = "";
        email = "";
        password = "";
        confirmPassword = "";
        customers.setCustomerID(createNewCustomerID());
        customers.setCustomerImageURL("images/user/user_default.jpg");
        return "register";
    }

    public String createNewCustomerID() {
        List<Customers> listCustomer = customersFacade.findAll();
        String lastID = listCustomer.get(listCustomer.size() - 1).getCustomerID();
        String id;
        int num = Integer.parseInt(lastID.substring(3)) + 1;
        if (num < 10) {
            id = "CUS00" + num;
        } else if (num >= 10 && num < 100) {
            id = "CUS0" + num;
        } else {
            id = "CUS" + num;
        }
        return id;
    }

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getNameMessage() {
        return nameMessage;
    }

    public void setNameMessage(String nameMessage) {
        this.nameMessage = nameMessage;
    }

    public String getGenderMessage() {
        return genderMessage;
    }

    public void setGenderMessage(String genderMessage) {
        this.genderMessage = genderMessage;
    }

    public String getPhoneMessage() {
        return phoneMessage;
    }

    public void setPhoneMessage(String phoneMessage) {
        this.phoneMessage = phoneMessage;
    }

    public String getEmailMessage() {
        return emailMessage;
    }

    public void setEmailMessage(String emailMessage) {
        this.emailMessage = emailMessage;
    }

    public String getPasswordMessage() {
        return passwordMessage;
    }

    public void setPasswordMessage(String passwordMessage) {
        this.passwordMessage = passwordMessage;
    }

    public String getCfPasswordMessage() {
        return cfPasswordMessage;
    }

    public void setCfPasswordMessage(String cfPasswordMessage) {
        this.cfPasswordMessage = cfPasswordMessage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isShowAlert() {
        return showAlert;
    }

    public void setShowAlert(boolean showAlert) {
        this.showAlert = showAlert;
    }

    public String getAddressMessage() {
        return addressMessage;
    }

    public void setAddressMessage(String addressMessage) {
        this.addressMessage = addressMessage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
