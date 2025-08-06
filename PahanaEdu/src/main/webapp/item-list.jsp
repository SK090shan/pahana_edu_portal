<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Import the necessary Java classes we need to use in our scriptlets --%>
<%@ page import="java.util.List" %>
<%@ page import="com.pahana.model.item.Item" %>
<%@ page import="com.pahana.model.user.User" %>

<jsp:include page="header.jsp" />

<div class="d-flex">
    <jsp:include page="sidebar.jsp" />

    <div class="main-content p-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1>Item Management</h1>
            <a href="item?action=new" class="btn btn-primary">Add New Item</a>
        </div>

        <div class="table-responsive">
            <table class="table table-striped table-bordered table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Item Name</th>
                        <th>Price</th>
                        <th>Stock</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        // Get the list of items sent from the ItemServlet
                        List<Item> itemList = (List<Item>) request.getAttribute("itemList");
                    
                        // Always check if the list is not null to avoid errors
                        if (itemList != null && !itemList.isEmpty()) {
                            // Start a standard Java 'for' loop
                            for (Item item : itemList) {
                    %>
                                <%-- Inside the loop, we write the HTML for each table row --%>
                                <tr>
                                    <%-- Use JSP Expressions <%= ... %> to print out the values --%>
                                    <td><%= item.getItemId() %></td>
                                    <td><%= item.getItemName() %></td>
                                    <td><%= item.getPrice() %></td>
                                    <td><%= item.getStock() %></td>
                                    <td>
                                        <a href="item?action=edit&id=<%= item.getItemId() %>" class="btn btn-sm btn-warning">Edit</a>
                                        <%
                                            // Get the logged-in user from the session
                                            User loggedInUser = (User) session.getAttribute("user");
                                            // Check if user exists and has the 'Admin' role
                                            if (loggedInUser != null && "Admin".equals(loggedInUser.getRole())) {
                                        %>
                                            <%-- If they are an Admin, print the Delete button --%>
                                            <a href="item?action=delete&id=<%= item.getItemId() %>" class="btn btn-sm btn-danger" 
                                               onclick="return confirm('Are you sure you want to delete this item?');">Delete</a>
                                        <%
                                            } // End of the Admin role check 'if' statement
                                        %>
                                    </td>
                                </tr>
                    <%
                            } // End of the 'for' loop
                        } else {
                    %>
                        <%-- If the list is empty, show a message --%>
                        <tr>
                            <td colspan="5" class="text-center">No items found.</td>
                        </tr>
                    <%
                        } // End of the 'if (itemList != null)' check
                    %>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />