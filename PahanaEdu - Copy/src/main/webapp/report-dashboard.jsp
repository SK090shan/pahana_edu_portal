<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.pahana.model.user.User" %>
<%@ page import="com.pahana.model.customer.Customer" %>
<%@ page import="com.pahana.model.item.Item" %>
<%@ page import="com.pahana.model.bill.Bill" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>

<jsp:include page="header.jsp" />

<div class="d-flex">
    <jsp:include page="sidebar.jsp" />

    <div class="main-content p-4">
        <h1 class="mb-4">System Reports</h1>

        <!-- Staff Report Section -->
        <div class="card mb-4">
            <div class="card-header d-flex justify-content-between align-items-center">
                <h5>Staff / User Details</h5>
                <a href="reports?action=download_staff" class="btn btn-sm btn-outline-success">Download CSV</a>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-sm table-hover">
                        <thead><tr><th>ID</th><th>Full Name</th><th>Username</th><th>Email</th><th>Role</th><th>Status</th></tr></thead>
                        <tbody>
                            <% for (User u : (List<User>) request.getAttribute("userList")) { %>
                            <tr><td><%= u.getUserId() %></td><td><%= u.getFullName() %></td><td><%= u.getUsername() %></td><td><%= u.getEmail() %></td><td><%= u.getRole() %></td><td><%= u.getStatus() %></td></tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- Customer Report Section -->
        <div class="card mb-4">
            <div class="card-header d-flex justify-content-between align-items-center">
                <h5>Customer Details</h5>
                <a href="reports?action=download_customers" class="btn btn-sm btn-outline-success">Download CSV</a>
            </div>
            <div class="card-body">
                 <div class="table-responsive">
                    <table class="table table-sm table-hover">
                       <thead><tr><th>ID</th><th>Account #</th><th>Full Name</th><th>Address</th><th>Telephone</th></tr></thead>
                       <tbody>
                            <% for (Customer c : (List<Customer>) request.getAttribute("customerList")) { %>
                            <tr><td><%= c.getCustomerId() %></td><td><%= c.getAccountNumber() %></td><td><%= c.getFullName() %></td><td><%= c.getAddress() %></td><td><%= c.getTelephone() %></td></tr>
                            <% } %>
                       </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- Item Report Section -->
        <div class="card">
            <div class="card-header d-flex justify-content-between align-items-center">
                <h5>Inventory Details</h5>
                <a href="reports?action=download_items" class="btn btn-sm btn-outline-success">Download CSV</a>
            </div>
            <div class="card-body">
                 <div class="table-responsive">
                    <table class="table table-sm table-hover">
                       <thead><tr><th>ID</th><th>Item Name</th><th>Price</th><th>Stock</th></tr></thead>
                       <tbody>
                            <% for (Item i : (List<Item>) request.getAttribute("itemList")) { %>
                            <tr><td><%= i.getItemId() %></td><td><%= i.getItemName() %></td><td><%= i.getPrice() %></td><td><%= i.getStock() %></td></tr>
                            <% } %>
                       </tbody>
                    </table>
                </div>
            </div>
        </div>
        
              <div class="card">
            <div class="card-header d-flex justify-content-between align-items-center">
                <h5>Billing History</h5>
                <%-- We can add a download button for this report later --%>
            </div>
            <div class="card-body">
                 <div class="table-responsive" style="max-height: 300px;">
                    <table class="table table-sm table-hover">
                       <thead><tr><th>Bill ID</th><th>Date</th><th>Customer</th><th>Handled By (Staff)</th><th>Total Amount</th></tr></thead>
                       <tbody>
                            <%
                                // Get the billList from the servlet
                                List<Bill> billList = (List<Bill>) request.getAttribute("billList");
                                // Initialize a variable to calculate the total income
                                BigDecimal totalIncome = BigDecimal.ZERO;
                                if (billList != null) {
                                    for (Bill b : billList) {
                                        // Add each bill's total to our running total
                                        totalIncome = totalIncome.add(b.getTotalAmount());
                            %>
                            <tr>
                                <td><%= b.getBillId() %></td>
                                <td><%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(b.getBillDate()) %></td>
                                <td><%= b.getCustomerName() %></td>
                                <td><%= b.getStaffName() %></td>
                                <td><%= b.getTotalAmount() %></td>
                            </tr>
                            <%
                                    } // End of for loop
                                } // End of if
                            %>
                       </tbody>
                    </table>
                </div>
                <hr>
                <div class="text-end">
                    <%-- Display the final calculated total income --%>
                    <h4>Total Income: <%= totalIncome %></h4>
                </div>
            </div>
        </div>
        
        
        
        

    </div>
</div>

<jsp:include page="footer.jsp" />