package com.pahana.controller;

import com.pahana.dao.BillDAO;
import com.pahana.dao.CustomerDAO;
import com.pahana.factory.DAOFactory;
import com.pahana.model.bill.Bill;
import com.pahana.model.customer.Customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/bill-success")
public class BillViewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BillDAO billDAO;
    private CustomerDAO customerDAO;

    public void init() {
        billDAO = DAOFactory.getBillDAO();
        customerDAO = DAOFactory.getCustomerDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int billId = Integer.parseInt(request.getParameter("billId"));
            Bill bill = billDAO.getBillById(billId);
            Customer customer = customerDAO.getCustomerById(bill.getCustomerId());

            request.setAttribute("bill", bill);
            request.setAttribute("customer", customer);

            request.getRequestDispatcher("bill-success.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle error, maybe redirect to an error page
        }
    }
}