<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pahana.model.bill.Bill, com.pahana.model.bill.BillItem, com.pahana.model.customer.Customer, java.text.SimpleDateFormat" %>

<%
    Bill bill = (Bill) request.getAttribute("bill");
    Customer customer = (Customer) request.getAttribute("customer");
%>

<jsp:include page="header.jsp" />

<div class="d-flex">
    <jsp:include page="sidebar.jsp" />

    <div class="main-content p-4">
        <div class="alert alert-success">
            <strong>Success!</strong> Bill #<%= bill.getBillId() %> has been saved to the database.
        </div>

        <div class="form-card">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2 class="form-card-title mb-0">Invoice Details</h2>
                <%-- This button links to the ReceiptServlet to generate the PDF --%>
                <a href="receipt?billId=<%= bill.getBillId() %>" class="btn btn-primary btn-lg">Print Receipt (PDF)</a>
            </div>

            <p><strong>Bill ID:</strong> <%= bill.getBillId() %></p>
            <p><strong>Customer:</strong> <%= customer.getFullName() %> (<%= customer.getAccountNumber() %>)</p>
            <p><strong>Date:</strong> <%= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(bill.getBillDate()) %></p>

            <hr>

            <table class="table">
                <thead class="table-light">
                    <tr>
                        <th>Product Name</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Total</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (BillItem item : bill.getBillItems()) { %>
                        <tr>
                            <td><%= item.getItemName() %></td>
                            <td><%= item.getQuantity() %></td>
                            <td><%= item.getPricePerUnit() %></td>
                            <td><%= item.getPricePerUnit().multiply(new java.math.BigDecimal(item.getQuantity())) %></td>
                        </tr>
                    <% } %>
                </tbody>
            </table>

            <div class="text-end">
                <h4>Grand Total: <%= bill.getTotalAmount() %></h4>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />