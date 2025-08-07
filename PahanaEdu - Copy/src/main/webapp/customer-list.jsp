<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.pahana.model.customer.Customer" %>
<%@ page import="com.pahana.model.user.User" %>

<jsp:include page="header.jsp" />

<div class="d-flex">
    <jsp:include page="sidebar.jsp" />

    <div class="main-content p-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1>Customer Management</h1>
            <a href="customer?action=new" class="btn btn-primary">Add New Customer</a>
        </div>

        <div class="table-responsive">
            <table class="table table-striped table-bordered table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>Account #</th>
                        <th>Full Name</th>
                        <th>Address</th>
                        <th>Telephone</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Customer> customerList = (List<Customer>) request.getAttribute("customerList");
                        if (customerList != null && !customerList.isEmpty()) {
                            for (Customer customer : customerList) {
                    %>
                                <tr>
                                    <td><%= customer.getAccountNumber() %></td>
                                    <td><%= customer.getFullName() %></td>
                                    <td><%= customer.getAddress() %></td>
                                    <td><%= customer.getTelephone() %></td>
                                    <td>
                                        <a href="customer?action=edit&id=<%= customer.getCustomerId() %>" class="btn btn-sm btn-warning">Edit</a>
                                        
                                        <%-- START: ROLE-BASED CHECK (Copied from item-list.jsp) --%>
                                        <%
                                            User loggedInUser = (User) session.getAttribute("user");
                                            if (loggedInUser != null && "Admin".equalsIgnoreCase(loggedInUser.getRole())) {
                                        %>
                                            <a href="customer?action=delete&id=<%= customer.getCustomerId() %>" class="btn btn-sm btn-danger" 
                                               onclick="return confirm('Are you sure you want to delete this customer?');">Delete</a>
                                        <%
                                            } // End of the 'if' statement
                                        %>
                                        <%-- END: ROLE-BASED CHECK --%>
                                    </td>
                                </tr>
                    <%
                            }
                        } else {
                    %>
                        <tr>
                            <td colspan="5" class="text-center">No customers found.</td>
                        </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />