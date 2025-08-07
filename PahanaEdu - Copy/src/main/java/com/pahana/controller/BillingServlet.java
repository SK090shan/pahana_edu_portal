package com.pahana.controller;

import com.pahana.dao.BillDAO;
import com.pahana.dao.CustomerDAO;
import com.pahana.dao.ItemDAO;
import com.pahana.factory.DAOFactory;
import com.pahana.model.bill.Bill;
import com.pahana.model.bill.BillItem;
import com.pahana.model.customer.Customer;
import com.pahana.model.item.Item;
import com.pahana.model.user.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/billing")
public class BillingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDAO;
    private ItemDAO itemDAO;
    private BillDAO billDAO;

    public void init() {
        customerDAO = DAOFactory.getCustomerDAO();
        itemDAO = DAOFactory.getItemDAO();
        billDAO = DAOFactory.getBillDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Customer> customerList = customerDAO.getAllCustomers();
        List<Item> itemList = itemDAO.getAllItems();
        request.setAttribute("customerList", customerList);
        request.setAttribute("itemList", itemList);
        request.getRequestDispatcher("billing.jsp").forward(request, response);
    }
    
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User staffUser = (User) request.getSession().getAttribute("user");
        
        // --- Server-Side Validation ---
        String customerIdParam = request.getParameter("customerId");
        if (customerIdParam == null || customerIdParam.isEmpty()) {
            response.getWriter().println("Error: A customer must be selected.");
            return;
        }
        
        int customerId = Integer.parseInt(customerIdParam);

        String[] itemIds = request.getParameterValues("itemId");
        String[] quantities = request.getParameterValues("quantity");
        String[] prices = request.getParameterValues("price");

        if (itemIds == null || itemIds.length == 0) {
            // Handle case where form is submitted with no items
            request.setAttribute("errorMessage", "Cannot create an empty bill. Please add at least one item.");
            // We must re-populate the customer/item lists before forwarding back to the form
            List<Customer> customerList = customerDAO.getAllCustomers();
            List<Item> itemList = itemDAO.getAllItems();
            request.setAttribute("customerList", customerList);
            request.setAttribute("itemList", itemList);
            request.getRequestDispatcher("billing.jsp").forward(request, response);
            return;
        }

        List<BillItem> billItems = new ArrayList<>();
        BigDecimal grandTotal = BigDecimal.ZERO;

        for (int i = 0; i < itemIds.length; i++) {
            try {
                BillItem billItem = new BillItem();
                billItem.setItemId(Integer.parseInt(itemIds[i]));
                billItem.setQuantity(Integer.parseInt(quantities[i]));
                billItem.setPricePerUnit(new BigDecimal(prices[i]));
                billItems.add(billItem);

                BigDecimal lineTotal = billItem.getPricePerUnit().multiply(new BigDecimal(billItem.getQuantity()));
                grandTotal = grandTotal.add(lineTotal);
            } catch (NumberFormatException e) {
                // This catches any error if the data is not a valid number
                response.getWriter().println("Error: Invalid data submitted for items. Please try again.");
                return;
            }
        }

        Bill bill = new Bill();
        bill.setCustomerId(customerId);
        bill.setStaffId(staffUser.getUserId());
        bill.setTotalAmount(grandTotal);
        bill.setBillItems(billItems);

        int newBillId = billDAO.saveBill(bill);

        if (newBillId != -1) {
            response.sendRedirect("billConfirmation?billId=" + newBillId);
        } else {
            response.getWriter().println("Error: Could not save the bill to the database.");
        }
    }
}