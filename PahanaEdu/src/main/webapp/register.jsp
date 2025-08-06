<%-- Include the header which contains Bootstrap CSS and our custom CSS --%>
<jsp:include page="header.jsp" />

<div class="container">
    <div class="form-card">
        
        <h2 class="form-card-title">Create a New Account</h2>

        <%-- Check for and display a success message from the servlet --%>
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success" role="alert">
                ${successMessage}
            </div>
        </c:if>

        <%-- Check for and display an error message from the servlet --%>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger" role="alert">
                ${errorMessage}
            </div>
        </c:if>

        <form action="register" method="post">
            <div class="mb-3">
                <label for="fullName" class="form-label">Full Name</label>
                <input type="text" class="form-control" id="fullName" name="fullName" required>
            </div>
            <div class="mb-3">
                <label for="username" class="form-label">Username</label>
                <input type="text" class="form-control" id="username" name="username" required>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email address</label>
                <input type="email" class="form-control" id="email" name="email" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
            
            <button type="submit" class="btn btn-primary w-100">Register</button>
        </form>
        
        <a href="login.jsp" class="login-link">Already have an account? Login</a>
    </div>
</div>

<%-- Include the footer which contains Bootstrap JS --%>
<jsp:include page="footer.jsp" />