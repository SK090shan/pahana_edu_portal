package com.pahana.dao;

import com.pahana.model.customer.Customer;
import java.util.List;

/**
 * DAO Interface for Customer operations. Defines the contract for any class
 * that will handle customer data persistence.
 */
public interface CustomerDAO {
    boolean addCustomer(Customer customer);
    Customer getCustomerById(int customerId);
    List<Customer> getAllCustomers();
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer(int customerId);
    int getTotalCustomerCount(); // For the dashboard
}