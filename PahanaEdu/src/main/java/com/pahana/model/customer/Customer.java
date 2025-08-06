package com.pahana.model.customer;

/**
 * Model (POJO) representing a Customer.
 * This class uses private fields and public getters/setters, demonstrating
 * the core OOP principle of Encapsulation.
 */
public class Customer {

    private int customerId;
    private String accountNumber;
    private String fullName;
    private String address;
    private String telephone;

    // --- Getters and Setters ---
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}