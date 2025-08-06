package com.pahana.controller;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * LECTURE 7: Web Development - Security and Filters.
 * This filter intercepts all requests to the application ('/*').
 * It checks if the user is authenticated before allowing access to protected pages.
 */
// The @WebFilter annotation tells Tomcat to apply this filter to all URLs
@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false); // false means do not create a new session

        // Get the requested URL path (e.g., /login.jsp, /dashboard, /styles/main.css)
        String path = request.getRequestURI().substring(request.getContextPath().length());

        // Check if a user is logged in (i.e., the 'user' object exists in the session)
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        // Define which paths are ALWAYS allowed, even without a login
        boolean isLoginOrRegisterPage = path.equals("/login.jsp") || path.equals("/register.jsp") || path.equals("/login") || path.equals("/register");
        boolean isStaticResource = path.startsWith("/styles/"); // Allow CSS files to load

        if (isLoggedIn || isLoginOrRegisterPage || isStaticResource) {
            // If logged in, OR trying to access login/register, OR requesting a static file,
            // let the request continue to its destination.
            chain.doFilter(req, res);
        } else {
            // User is not logged in and is trying to access a protected page.
            // Redirect them to the login page.
            System.out.println("Blocked unauthorized access to: " + path);
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }
}