<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Import the User model so we can use it in our Java code --%>
<%@ page import="com.pahana.model.user.User" %>

<%
    // Get the currently logged-in user from the session.
    User loggedInUser = (User) session.getAttribute("user");
%>

<div class="sidebar bg-dark p-3 d-flex flex-column">
    <div>
        <a href="dashboard" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-white text-decoration-none">
            <span class="fs-4">Pahana Education</span>
        </a>
        <hr class="text-secondary">
        <ul class="nav nav-pills flex-column mb-auto">
            <li class="nav-item">
                <a href="dashboard" class="nav-link text-white">Dashboard</a>
            </li>
            <li>
                <a href="customer?action=list" class="nav-link text-white">Customers</a>
            </li>
            <li>
                <a href="item?action=list" class="nav-link text-white">Products</a>
            </li>
            <li>
                <a href="billing" class="nav-link text-white">Invoices</a>
            </li>
            
            <%-- START: ROLE-BASED CHECK --%>
            <%
                // Check if a user is logged in AND if their role is 'Admin' (case-insensitive)
                if (loggedInUser != null && "Admin".equalsIgnoreCase(loggedInUser.getRole())) {
            %>
                    <%-- If they are an Admin, show the Reports link --%>
                    <li>
                        <a href="reports" class="nav-link text-white">Reports</a>
                    </li>
                    
                     <li>
                <a href="staff?action=list" class="nav-link text-white">Staff Management</a>
            </li>
            
             <li>
                        <a href="help" class="nav-link text-white">Help</a>
                    </li>
            
            <%
                } 
            %>
            <%-- END: ROLE-BASED CHECK --%>
        </ul>
    </div>
    <div class="mt-auto">
        <hr class="text-secondary">
        <%-- Display the user's name if they are logged in --%>
        <% if (loggedInUser != null) { %>
             <div class="text-white small mb-2">Welcome, <%= loggedInUser.getFullName() %></div>
        <% } %>
        <a href="logout" class="nav-link text-white">Logout</a>
    </div>
</div>