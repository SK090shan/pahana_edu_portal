package com.pahana.controller;

import com.pahana.dao.BillDAO; // <-- ADD THIS IMPORT
import com.pahana.dao.CustomerDAO;
import com.pahana.dao.ItemDAO;
import com.pahana.dao.UserDAO;
import com.pahana.factory.DAOFactory;
import com.pahana.model.bill.Bill; // <-- ADD THIS IMPORT
import com.pahana.model.customer.Customer;
import com.pahana.model.item.Item;
import com.pahana.model.user.User;
import com.pahana.service.CsvExportService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/reports")
public class ReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    private CustomerDAO customerDAO;
    private ItemDAO itemDAO;
    private BillDAO billDAO; // <-- ADD THIS NEW DAO
    private CsvExportService csvService;

    public void init() {
        userDAO = DAOFactory.getUserDAO();
        customerDAO = DAOFactory.getCustomerDAO();
        itemDAO = DAOFactory.getItemDAO();
        billDAO = DAOFactory.getBillDAO(); // <-- INITIALIZE THE NEW DAO
        csvService = new CsvExportService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User loggedInUser = (User) request.getSession().getAttribute("user");

        if (loggedInUser == null || !"Admin".equalsIgnoreCase(loggedInUser.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to access this page.");
            return;
        }

        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "download_staff":
                    downloadStaffAsCsv(response);
                    break;
                case "download_customers":
                    downloadCustomersAsCsv(response);
                    break;
                case "download_items":
                    downloadItemsAsCsv(response);
                    break;
                // We can add a download for bills later
                default:
                    showReportDashboard(request, response);
                    break;
            }
        } else {
            showReportDashboard(request, response);
        }
    }

    private void showReportDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch all the data needed for the reports page
        List<User> userList = userDAO.getAllUsers();
        List<Customer> customerList = customerDAO.getAllCustomers();
        List<Item> itemList = itemDAO.getAllItems();
        List<Bill> billList = billDAO.getAllBills(); // <-- FETCH THE BILLING HISTORY
        
        // Set all lists as request attributes for the JSP to use
        request.setAttribute("userList", userList);
        request.setAttribute("customerList", customerList);
        request.setAttribute("itemList", itemList);
        request.setAttribute("billList", billList); // <-- PASS THE BILLING HISTORY TO THE JSP
        
        request.getRequestDispatcher("report-dashboard.jsp").forward(request, response);
    }
    
    // ... all download...AsCsv methods remain the same ...
    private void downloadStaffAsCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"staff_report.csv\"");
        List<User> users = userDAO.getAllUsers();
        String csvData = csvService.exportUsers(users);
        PrintWriter writer = response.getWriter();
        writer.write(csvData);
    }

    private void downloadCustomersAsCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"customer_report.csv\"");
        List<Customer> customers = customerDAO.getAllCustomers();
        String csvData = csvService.exportCustomers(customers);
        PrintWriter writer = response.getWriter();
        writer.write(csvData);
    }

    private void downloadItemsAsCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"item_report.csv\"");
        List<Item> items = itemDAO.getAllItems();
        String csvData = csvService.exportItems(items);
        PrintWriter writer = response.getWriter();
        writer.write(csvData);
    }
}