package com.pahana.controller;

import java.io.IOException;
import com.pahana.dao.UserDAO;
import com.pahana.factory.DAOFactory;
import com.pahana.model.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// This servlet is mapped to the URL '/login'
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        // Use our factory to get the UserDAO instance
        userDAO = DAOFactory.getUserDAO();
    }

    // This method handles the form submission
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String plainPassword = request.getParameter("password");

        // Use the DAO to validate the user. This method checks the password hash
        // and ensures the user's status is 'ACTIVE'.
        User user = userDAO.validateUser(username, plainPassword);

        if (user != null) {
            // --- SUCCESSFUL LOGIN ---
            // 1. Create a new session for the user.
            HttpSession session = request.getSession();
            
            // 2. Store the authenticated User object in the session.
            // This is how we know the user is "logged in" on subsequent requests.
            session.setAttribute("user", user);
            
            // 3. Redirect the user to the main application dashboard.
            response.sendRedirect("dashboard");

        } else {
            // --- FAILED LOGIN ---
            // 1. Set an error message in the request scope.
            request.setAttribute("errorMessage", "Invalid username or password, or account is not active.");
            
            // 2. Forward the request back to the login page to display the error.
            // We use 'forward' so the request (containing the error message) is maintained.
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}