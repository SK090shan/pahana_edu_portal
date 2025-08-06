package com.pahana.controller;

import com.pahana.dao.UserDAO;
import com.pahana.factory.DAOFactory;
import com.pahana.model.user.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/staff")
public class StaffManagementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        userDAO = DAOFactory.getUserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // --- CRITICAL: SERVER-SIDE SECURITY CHECK ---
        User loggedInUser = (User) request.getSession().getAttribute("user");
        if (loggedInUser == null || !"Admin".equalsIgnoreCase(loggedInUser.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to access this page.");
            return; // Stop processing immediately
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "list"; // Default action
        }

        try {
            switch (action) {
                case "activate":
                    activateStaff(request, response);
                    break;
                case "delete":
                    deleteStaff(request, response);
                    break;
                default: // "list"
                    listStaff(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void listStaff(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> userList = userDAO.getAllUsers();
        request.setAttribute("userList", userList);
        request.getRequestDispatcher("staff-management.jsp").forward(request, response);
    }

    private void activateStaff(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        userDAO.updateUserStatus(userId, "ACTIVE");
        response.sendRedirect("staff?action=list");
    }

    private void deleteStaff(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userIdToDelete = Integer.parseInt(request.getParameter("id"));
        User loggedInUser = (User) request.getSession().getAttribute("user");
        
        // Admins should not be able to delete their own account
        if (loggedInUser.getUserId() != userIdToDelete) {
            userDAO.deleteUser(userIdToDelete);
        }
        response.sendRedirect("staff?action=list");
    }
}