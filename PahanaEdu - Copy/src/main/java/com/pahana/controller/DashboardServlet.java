package com.pahana.controller;

import java.io.IOException;
import com.pahana.dao.CustomerDAO;
import com.pahana.dao.ItemDAO;
import com.pahana.factory.DAOFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ItemDAO itemDAO;
    private CustomerDAO customerDAO;

    public void init() {
        // Use the factory to get our DAO instances
        itemDAO = DAOFactory.getItemDAO();
        customerDAO = DAOFactory.getCustomerDAO(); 
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch data from DAOs
        int totalItems = itemDAO.getTotalItemCount();
        int totalCustomers = customerDAO.getTotalCustomerCount();
        
        int lowStockThreshold = 5;
        int lowStockCount = itemDAO.getLowStockItemCount(lowStockThreshold);

        // Set data as request attributes to be used by the JSP
        request.setAttribute("totalItems", totalItems);
        request.setAttribute("totalCustomers", totalCustomers);
        request.setAttribute("lowStockCount", lowStockCount); 
        
        // Forward the request to the dashboard JSP
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
    
    
}