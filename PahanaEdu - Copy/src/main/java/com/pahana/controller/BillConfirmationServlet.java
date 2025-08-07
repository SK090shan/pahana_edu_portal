package com.pahana.controller;

import com.pahana.dao.BillDAO;
import com.pahana.dao.CustomerDAO;
import com.pahana.factory.DAOFactory;
import com.pahana.model.bill.Bill;
import com.pahana.model.customer.Customer;
// ... other imports
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/billConfirmation")
public class BillConfirmationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BillDAO billDAO;
    private CustomerDAO customerDAO;

    public void init() {
        billDAO = DAOFactory.getBillDAO();
        customerDAO = DAOFactory.getCustomerDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int billId = Integer.parseInt(request.getParameter("billId"));
        
        // Fetch the complete, saved bill from the database
        Bill bill = billDAO.getBillById(billId);
        Customer customer = customerDAO.getCustomerById(bill.getCustomerId());
        
        request.setAttribute("bill", bill);
        request.setAttribute("customer", customer);
        
        // Forward to our new confirmation JSP
        request.getRequestDispatcher("bill-confirmation.jsp").forward(request, response);
    }
}