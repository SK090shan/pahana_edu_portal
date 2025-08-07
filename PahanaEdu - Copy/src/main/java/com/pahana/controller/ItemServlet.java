package com.pahana.controller;

import com.pahana.dao.ItemDAO;
import com.pahana.factory.DAOFactory;
import com.pahana.model.item.Item;
import com.pahana.model.user.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/item")
public class ItemServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ItemDAO itemDAO;

    public void init() {
        itemDAO = DAOFactory.getItemDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("insert".equals(action)) {
            insertItem(request, response);
        } else if ("update".equals(action)) {
            updateItem(request, response);
        } else {
            listItems(request, response);
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
                deleteItem(request, response);
                break;
            default:
                listItems(request, response);
                break;
        }
    }

    private void listItems(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Item> itemList = itemDAO.getAllItems();
        request.setAttribute("itemList", itemList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("item-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("item-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Item existingItem = itemDAO.getItemById(id);
            request.setAttribute("item", existingItem);
            RequestDispatcher dispatcher = request.getRequestDispatcher("item-form.jsp");
            dispatcher.forward(request, response);
        } catch (NumberFormatException e) {
            // This will catch the error if the 'id' parameter is missing or not a number
            System.err.println("Invalid item ID format: " + e.getMessage());
            response.sendRedirect("item?action=list"); // Redirect to the list to prevent the error page
        }
    }

    private void insertItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Item newItem = new Item();
        newItem.setItemName(request.getParameter("itemName"));
        newItem.setPrice(new BigDecimal(request.getParameter("price")));
        newItem.setStock(Integer.parseInt(request.getParameter("stock")));
        itemDAO.addItem(newItem);
        response.sendRedirect("item?action=list");
    }

    private void updateItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Item item = new Item();
        item.setItemId(Integer.parseInt(request.getParameter("itemId")));
        item.setItemName(request.getParameter("itemName"));
        item.setPrice(new BigDecimal(request.getParameter("price")));
        item.setStock(Integer.parseInt(request.getParameter("stock")));
        itemDAO.updateItem(item);
        response.sendRedirect("item?action=list");
    }

    private void deleteItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User loggedInUser = (User) request.getSession().getAttribute("user");

        // SERVER-SIDE SECURITY CHECK: The most important part
        if (loggedInUser != null && "Admin".equalsIgnoreCase(loggedInUser.getRole())) {
            // If the user IS an Admin, proceed with deletion
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                itemDAO.deleteItem(id);
                response.sendRedirect("item?action=list"); // Redirect back to the list
            } catch (NumberFormatException e) {
                // Handle cases where the ID is not a valid number
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid item ID.");
            }
        } else {
            // If the user is NOT an Admin, send a 'Forbidden' error.
            // This stops non-admins even if they guess the URL.
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to perform this action.");
        }
    }
}