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

    // This method prepares the data for the billing form
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Customer> customerList = customerDAO.getAllCustomers();
        List<Item> itemList = itemDAO.getAllItems();
        
        request.setAttribute("customerList", customerList);
        request.setAttribute("itemList", itemList);
        
        request.getRequestDispatcher("billing.jsp").forward(request, response);
    }
    
    // This method processes the submitted bill
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User staffUser = (User) request.getSession().getAttribute("user");
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        
        List<BillItem> billItems = new ArrayList<>();
        BigDecimal grandTotal = BigDecimal.ZERO;

        // The JavaScript creates parameters like items[0].id, items[1].id, etc.
        // We loop until we can't find the next item in the sequence.
        for (int i = 0; ; i++) {
            String idParam = "items[" + i + "].id";
            if (request.getParameter(idParam) == null) {
                break; // No more items
            }
            
            int itemId = Integer.parseInt(request.getParameter(idParam));
            int quantity = Integer.parseInt(request.getParameter("items[" + i + "].quantity"));
            BigDecimal price = new BigDecimal(request.getParameter("items[" + i + "].price"));
            
            BillItem billItem = new BillItem();
            billItem.setItemId(itemId);
            billItem.setQuantity(quantity);
            billItem.setPricePerUnit(price);
            
            billItems.add(billItem);
            grandTotal = grandTotal.add(price.multiply(new BigDecimal(quantity)));
        }

        Bill bill = new Bill();
        bill.setCustomerId(customerId);
        bill.setStaffId(staffUser.getUserId());
        bill.setTotalAmount(grandTotal);
        bill.setBillItems(billItems);
        
        // Save the bill and get its newly generated ID
        int newBillId = billDAO.saveBill(bill);
        
        if (newBillId != -1) {
            // Success! Redirect to a receipt servlet to generate the PDF.
            response.sendRedirect("receipt?billId=" + newBillId);
        } else {
            // Handle error - maybe redirect to an error page
            response.getWriter().println("Error saving the bill.");
        }
    }
}