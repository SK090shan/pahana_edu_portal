package com.pahana.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the current session, but don't create a new one if it doesn't exist.
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // Invalidate the session, removing all attributes (like the 'user' object).
            session.invalidate();
        }
        
        // Redirect the user back to the login page.
        response.sendRedirect("login.jsp");
    }
}