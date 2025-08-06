package com.pahana.controller;

import com.pahana.dao.CustomerDAO;
import com.pahana.factory.DAOFactory;
import com.pahana.model.customer.Customer;
import com.pahana.model.user.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDAO;

    public void init() {
        customerDAO = DAOFactory.getCustomerDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("insert".equals(action)) {
            addCustomer(request, response);
        } else if ("update".equals(action)) {
            updateCustomer(request, response);
        } else {
            listCustomers(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteCustomer(request, response);
                break;
            default:
                listCustomers(request, response);
                break;
        }
    }

    private void listCustomers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Customer> customerList = customerDAO.getAllCustomers();
        request.setAttribute("customerList", customerList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Customer existingCustomer = customerDAO.getCustomerById(id);
            request.setAttribute("customer", existingCustomer);
            RequestDispatcher dispatcher = request.getRequestDispatcher("customer-form.jsp");
            dispatcher.forward(request, response);
        } catch (NumberFormatException e) {
            System.err.println("Invalid customer ID format: " + e.getMessage());
            response.sendRedirect("customer?action=list");
        }
    }

    private void addCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Customer newCustomer = new Customer();
        newCustomer.setAccountNumber(request.getParameter("accountNumber"));
        newCustomer.setFullName(request.getParameter("fullName"));
        newCustomer.setAddress(request.getParameter("address"));
        newCustomer.setTelephone(request.getParameter("telephone"));
        customerDAO.addCustomer(newCustomer);
        response.sendRedirect("customer?action=list");
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Customer customer = new Customer();
        customer.setCustomerId(Integer.parseInt(request.getParameter("customerId")));
        customer.setAccountNumber(request.getParameter("accountNumber"));
        customer.setFullName(request.getParameter("fullName"));
        customer.setAddress(request.getParameter("address"));
        customer.setTelephone(request.getParameter("telephone"));
        customerDAO.updateCustomer(customer);
        response.sendRedirect("customer?action=list");
    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User loggedInUser = (User) request.getSession().getAttribute("user");

        if (loggedInUser != null && "Admin".equalsIgnoreCase(loggedInUser.getRole())) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                customerDAO.deleteCustomer(id);
                response.sendRedirect("customer?action=list");
            } catch (NumberFormatException e) {
                System.err.println("Invalid customer ID for delete: " + e.getMessage());
                response.sendRedirect("customer?action=list");
            }
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to perform this action.");
        }
    }
}