package com.pahana.controller;

import com.pahana.model.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/help")
public class HelpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // --- CRITICAL: SERVER-SIDE SECURITY CHECK ---
        User loggedInUser = (User) request.getSession().getAttribute("user");

        // If the user is not logged in OR their role is NOT 'Admin', block access.
        if (loggedInUser == null || !"Admin".equalsIgnoreCase(loggedInUser.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to access this page.");
            return; // Stop processing immediately
        }

        // If the security check passes, forward to the help page.
        request.getRequestDispatcher("help.jsp").forward(request, response);
    }
}