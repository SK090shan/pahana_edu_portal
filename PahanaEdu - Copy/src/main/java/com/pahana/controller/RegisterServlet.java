package com.pahana.controller;

import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt; // Import for password hashing
import com.pahana.dao.UserDAO;
import com.pahana.factory.DAOFactory;
import com.pahana.model.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        // LECTURE 3: Using the DAOFactory to get the DAO instance.
        // This decouples the servlet from the concrete implementation (UserDAOImpl).
        userDAO = DAOFactory.getUserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Get user input from the registration form
        String fullName = request.getParameter("fullName");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String plainPassword = request.getParameter("password");
        
        // 2. Hash the plain-text password using jBCrypt
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));

        // 3. Create a new User object with the details
        User newUser = new User();
        newUser.setFullName(fullName);
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPasswordHash(hashedPassword); // Set the HASH, not the plain password
        newUser.setRole("Staff"); // Default role for new registrations
        
        // 4. Attempt to register the user via the DAO
        boolean isRegistered = userDAO.registerUser(newUser);
        
        // 5. Set a message and forward back to the JSP
        if (isRegistered) {
            request.setAttribute("successMessage", "Registration successful! An admin will activate your account shortly.");
        } else {
            request.setAttribute("errorMessage", "Registration failed. Username or email may already be in use.");
        }
        
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
}