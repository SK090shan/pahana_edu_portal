<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.pahana.model.user.User" %>

<jsp:include page="header.jsp" />

<%
    // Get the logged-in user to prevent them from deleting their own account
    User loggedInUser = (User) session.getAttribute("user");
%>

<div class="d-flex">
    <jsp:include page="sidebar.jsp" />

    <div class="main-content p-4">
        <h1 class="mb-4">Staff Management</h1>

        <div class="table-responsive">
            <table class="table table-striped table-bordered table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Full Name</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<User> userList = (List<User>) request.getAttribute("userList");
                        if (userList != null && !userList.isEmpty()) {
                            for (User user : userList) {
                    %>
                                <tr>
                                    <td><%= user.getUserId() %></td>
                                    <td><%= user.getFullName() %></td>
                                    <td><%= user.getUsername() %></td>
                                    <td><%= user.getEmail() %></td>
                                    <td><%= user.getRole() %></td>
                                    <td>
                                        <%-- Use a Bootstrap badge for better visual status --%>
                                        <% if ("ACTIVE".equals(user.getStatus())) { %>
                                            <span class="badge bg-success">ACTIVE</span>
                                        <% } else if ("PENDING".equals(user.getStatus())) { %>
                                            <span class="badge bg-warning text-dark">PENDING</span>
                                        <% } else { %>
                                            <span class="badge bg-secondary">INACTIVE</span>
                                        <% } %>
                                    </td>
                                    <td>
                                        <%-- The "Activate" button only shows for PENDING users --%>
                                        <% if ("PENDING".equals(user.getStatus())) { %>
                                            <a href="staff?action=activate&id=<%= user.getUserId() %>" class="btn btn-sm btn-success">Activate</a>
                                        <% } %>

                                        <%-- The "Delete" button shows for all users EXCEPT the currently logged-in admin --%>
                                        <% if (loggedInUser != null && loggedInUser.getUserId() != user.getUserId()) { %>
                                            <a href="staff?action=delete&id=<%= user.getUserId() %>" class="btn btn-sm btn-danger"
                                               onclick="return confirm('Are you sure you want to permanently delete this user?');">Delete</a>
                                        <% } %>
                                    </td>
                                </tr>
                    <%
                            }
                        } else {
                    %>
                        <tr>
                            <td colspan="7" class="text-center">No staff members found.</td>
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