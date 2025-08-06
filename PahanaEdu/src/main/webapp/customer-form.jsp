<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pahana.model.customer.Customer" %>

<%
    Customer customer = (Customer) request.getAttribute("customer");
%>

<jsp:include page="header.jsp" />

<div class="d-flex">
    <jsp:include page="sidebar.jsp" />

    <div class="main-content p-4">

        <h1 class="mb-4">
            <% if (customer != null) { %>
                Edit Customer
            <% } else { %>
                Add New Customer
            <% } %>
        </h1>
        
        <div class="form-card" style="margin: 0;">
            <form action="customer" method="post">
                
                <input type="hidden" name="action" value="<%= (customer != null) ? "update" : "insert" %>" />
                <% if (customer != null) { %>
                    <input type="hidden" name="customerId" value="<%= customer.getCustomerId() %>" />
                <% } %>

                <div class="mb-3">
                    <label for="accountNumber" class="form-label">Account Number</label>
                    <input type="text" class="form-control" id="accountNumber" name="accountNumber" 
                           value="<%= (customer != null) ? customer.getAccountNumber() : "" %>" required>
                </div>
                <div class="mb-3">
                    <label for="fullName" class="form-label">Full Name</label>
                    <input type="text" class="form-control" id="fullName" name="fullName"
                           value="<%= (customer != null) ? customer.getFullName() : "" %>" required>
                </div>
                <div class="mb-3">
                    <label for="address" class="form-label">Address</label>
                    <input type="text" class="form-control" id="address" name="address"
                           value="<%= (customer != null) ? customer.getAddress() : "" %>">
                </div>
                <div class="mb-3">
                    <label for="telephone" class="form-label">Telephone</label>
                    <input type="text" class="form-control" id="telephone" name="telephone"
                           value="<%= (customer != null) ? customer.getTelephone() : "" %>">
                </div>
                
                <button type="submit" class="btn btn-primary">Save Customer</button>
                <a href="customer?action=list" class="btn btn-secondary">Cancel</a>
            </form>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />