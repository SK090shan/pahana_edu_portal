<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.pahana.model.customer.Customer, com.pahana.model.item.Item" %>

<jsp:include page="header.jsp" />

<div class="d-flex">
    <jsp:include page="sidebar.jsp" />

    <div class="main-content p-4">
        <h1 class="mb-4">Create New Invoice</h1>

        <form action="billing" method="post" id="billingForm">
            <!-- Customer Selection -->
            <div class="form-card mb-4">
                <h5 class="mb-3">1. Select Customer</h5>
                <select class="form-select" name="customerId" required>
                    <option value="">Choose a customer...</option>
                    <%
                        List<Customer> customers = (List<Customer>) request.getAttribute("customerList");
                        if (customers != null) {
                            for (Customer c : customers) {
                    %>
                        <option value="<%= c.getCustomerId() %>"><%= c.getFullName() %> (<%= c.getAccountNumber() %>)</option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>

            <!-- Item Selection & Added Items Table -->
            <div class="form-card">
                <h5 class="mb-3">2. Add Items to Bill</h5>
                <div class="row g-3 align-items-end">
                    <div class="col-md-6"><label for="itemSelect" class="form-label">Product</label>
                        <select id="itemSelect" class="form-select">
                            <%
                                List<Item> items = (List<Item>) request.getAttribute("itemList");
                                if (items != null) {
                                    for (Item i : items) {
                            %>
                                <option value="<%= i.getItemId() %>" data-price="<%= i.getPrice() %>" data-stock="<%= i.getStock() %>"><%= i.getItemName() %></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                    <div class="col-md-3"><label for="itemQuantity" class="form-label">Quantity</label>
                        <input type="number" id="itemQuantity" class="form-control" value="1" min="1">
                    </div>
                    <div class="col-md-3"><button type="button" id="addItemBtn" class="btn btn-secondary w-100">Add Item</button></div>
                </div>
                <hr class="my-4">
                <h6 class="mb-3">Invoice Items</h6>
                <table class="table">
                    <thead class="table-light">
                        <tr><th>Product Name</th><th>Quantity</th><th>Price</th><th>Total</th><th>Action</th></tr>
                    </thead>
                    <tbody id="billItemsTableBody"></tbody>
                </table>
                <div class="text-end"><h4>Grand Total: <span id="grandTotal">0.00</span></h4></div>
            </div>

            <div class="mt-4 text-end"><button type="submit" class="btn btn-primary btn-lg">Finalize Bill</button></div>
        </form>
    </div>
</div>

<script>
document.addEventListener('DOMContentLoaded', function() {
    const addItemBtn = document.getElementById('addItemBtn');
    const itemSelect = document.getElementById('itemSelect');
    const itemQuantityInput = document.getElementById('itemQuantity');
    const tableBody = document.getElementById('billItemsTableBody');
    const grandTotalSpan = document.getElementById('grandTotal');

    addItemBtn.addEventListener('click', function() {
        console.log("Add Item button clicked."); // DEBUG
        const selectedOption = itemSelect.options[itemSelect.selectedIndex];
        if (!selectedOption || !selectedOption.value) { return; }

        const itemId = selectedOption.value;
        const itemName = selectedOption.text;
        const itemPrice = parseFloat(selectedOption.getAttribute('data-price')); // Use getAttribute for reliability
        const quantity = parseInt(itemQuantityInput.value, 10);
        
        console.log("Data read:", {itemId, itemName, itemPrice, quantity}); // DEBUG

        if (isNaN(itemPrice) || quantity <= 0) {
            alert('Invalid quantity or price. Please check your selection.');
            return;
        }
        const lineTotal = itemPrice * quantity;

        // --- Manually build the row and its children ---
        const newRow = document.createElement('tr');
        
        // Cell 1: Name and Hidden Inputs
        const nameCell = document.createElement('td');
        nameCell.textContent = itemName; // Set visible text
        
        const hiddenId = document.createElement('input');
        hiddenId.type = 'hidden'; hiddenId.name = 'itemId'; hiddenId.value = itemId;
        nameCell.appendChild(hiddenId);

        const hiddenQty = document.createElement('input');
        hiddenQty.type = 'hidden'; hiddenQty.name = 'quantity'; hiddenQty.value = quantity;
        nameCell.appendChild(hiddenQty);

        const hiddenPrice = document.createElement('input');
        hiddenPrice.type = 'hidden'; hiddenPrice.name = 'price'; hiddenPrice.value = itemPrice.toFixed(2);
        nameCell.appendChild(hiddenPrice);
        
        newRow.appendChild(nameCell);

        // Other cells
        const qtyCell = document.createElement('td');
        qtyCell.textContent = quantity;
        newRow.appendChild(qtyCell);
        
        const priceCell = document.createElement('td');
        priceCell.textContent = itemPrice.toFixed(2);
        newRow.appendChild(priceCell);
        
        const totalCell = document.createElement('td');
        totalCell.textContent = lineTotal.toFixed(2);
        newRow.appendChild(totalCell);
        
        const actionCell = document.createElement('td');
        const removeBtn = document.createElement('button');
        removeBtn.type = 'button';
        removeBtn.className = 'btn btn-danger btn-sm remove-item-btn';
        removeBtn.textContent = 'Remove';
        actionCell.appendChild(removeBtn);
        newRow.appendChild(actionCell);

        tableBody.appendChild(newRow);
        console.log("Row added to table."); // DEBUG
        updateGrandTotal();
    });

    tableBody.addEventListener('click', function(event) {
        if (event.target.classList.contains('remove-item-btn')) {
            event.target.closest('tr').remove();
            updateGrandTotal();
        }
    });
    
    function updateGrandTotal() {
        let total = 0;
        const rows = tableBody.querySelectorAll('tr');
        rows.forEach(row => {
            const lineTotalValue = parseFloat(row.cells[3].textContent);
            if (!isNaN(lineTotalValue)) { total += lineTotalValue; }
        });
        grandTotalSpan.textContent = total.toFixed(2);
    }
});
</script>

<jsp:include page="footer.jsp" />