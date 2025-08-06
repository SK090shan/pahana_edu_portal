<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.pahana.model.bill.Bill, com.pahana.model.bill.BillItem, com.pahana.model.customer.Customer" %>

<jsp:include page="header.jsp" />

<div class="d-flex">
    <jsp:include page="sidebar.jsp" />

    <div class="main-content p-4">
        <%
            Bill bill = (Bill) request.getAttribute("bill");
            Customer customer = (Customer) request.getAttribute("customer");
        %>
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1>Bill Finalized (ID: <%= bill.getBillId() %>)</h1>
            <%-- This is the button that will generate the PDF --%>
            <a href="receipt?billId=<%= bill.getBillId() %>" class="btn btn-success btn-lg">Print Receipt</a>
        </div>

        <div class="form-card">
            <h5 class="mb-3">Customer: <%= customer.getFullName() %></h5>
            
            <table class="table">
                <thead class="table-light">
                    <tr><th>Product Name</th><th>Quantity</th><th>Price</th><th>Total</th></tr>
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

            <hr>
            <div class="text-end">
                <h3>Grand Total: <%= bill.getTotalAmount() %></h3>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />