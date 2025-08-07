package com.pahana.factory;

import com.pahana.dao.ItemDAO;
import com.pahana.dao.UserDAO;
import com.pahana.dao.impl.ItemDAOImpl;
import com.pahana.dao.impl.UserDAOImpl;
import com.pahana.dao.CustomerDAO;
import com.pahana.dao.BillDAO;
import com.pahana.dao.impl.BillDAOImpl;



import com.pahana.dao.impl.CustomerDAOImpl;


/**
 * LECTURE 3: Implementing the Factory Design Pattern.
 * This class provides a centralized way to get instances of our DAO implementations.
 * It decouples the controllers (clients) from the concrete DAO classes. If we ever want 
 * to switch to a different implementation (e.g., for a different database),
 * we only need to change the code in this one file.
 */
public class DAOFactory {

    public static UserDAO getUserDAO() {
        return new UserDAOImpl();
    }

    public static ItemDAO getItemDAO() {
        return new ItemDAOImpl();
    }
    
    // Add similar methods for CustomerDAO and BillDAO as you create them
    public static CustomerDAO getCustomerDAO() {
        return new CustomerDAOImpl();
    }
    
    public static BillDAO getBillDAO() {
        return new BillDAOImpl();
    }
}