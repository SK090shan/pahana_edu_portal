<%-- We are using a scriptlet here to display the error message as a workaround
     for the JSTL/EL issues in the current Eclipse environment. --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="header.jsp" />

<div class="container">
    <div class="form-card">
        
        <h2 class="form-card-title">System Login</h2>

        <%
            // Get the error message from the request (if the LoginServlet sent one)
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null) {
        %>
            <%-- If an error message exists, display it in a styled alert box --%>
            <div class="alert alert-danger" role="alert">
                <%= errorMessage %>
            </div>
        <%
            }
        %>

        <%-- The form action points to our LoginServlet's mapped URL ('/login') --%>
        <form action="login" method="post">
            <div class="mb-3">
                <label for="username" class="form-label">Username</label>
                <input type="text" class="form-control" id="username" name="username" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
            
            <button type="submit" class="btn btn-primary w-100">Login</button>
        </form>
        
        <a href="register.jsp" class="login-link">Need an account? Register</a>
    </div>
</div>

<jsp:include page="footer.jsp" />