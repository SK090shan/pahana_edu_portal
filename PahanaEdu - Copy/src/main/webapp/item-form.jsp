<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pahana.model.item.Item" %>

<%-- This scriptlet block gets the item object from the request. --%>
<%
    Item item = (Item) request.getAttribute("item");
%>

<jsp:include page="header.jsp" />

<div class="d-flex">
    <jsp:include page="sidebar.jsp" />

    <div class="main-content p-4">

        <h1 class="mb-4">
            <% if (item != null) { %>
                Edit Item
            <% } else { %>
                Add New Item
            <% } %>
        </h1>
        
        <div class="form-card" style="margin: 0;">
            <form action="item" method="post">
                
                <input type="hidden" name="action" value="<%= (item != null) ? "update" : "insert" %>" />
                <% if (item != null) { %>
                    <input type="hidden" name="itemId" value="<%= item.getItemId() %>" />
                <% } %>

                <div class="mb-3">
                    <label for="itemName" class="form-label">Item Name</label>
                    <%-- 
                        THE FIX IS HERE: 
                        Using a simple JSP Expression <%= ... %> is the most direct way to print the value.
                        The Java ternary operator checks if 'item' is null before trying to get its name.
                    --%>
                    <input type="text" class="form-control" id="itemName" name="itemName" 
                           value="<%= (item != null) ? item.getItemName() : "" %>" required>
                </div>
                <div class="mb-3">
                    <label for="price" class="form-label">Price</label>
                    <input type="number" step="0.01" min="0" class="form-control" id="price"
                           name="price" value="<%= (item != null) ? item.getPrice() : "" %>" required>
                </div>
                <div class="mb-3">
                    <label for="stock" class="form-label">Stock</label>
                    <input type="number" min="0" class="form-control" id="stock" name="stock"
                           value="<%= (item != null) ? item.getStock() : "0" %>" required>
                </div>
                
                <button type="submit" class="btn btn-primary">Save Item</button>
                <a href="item?action=list" class="btn btn-secondary">Cancel</a>
            </form>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />