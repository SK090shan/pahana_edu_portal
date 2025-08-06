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
                    <div class="col-md-6">
                        <label for="itemSelect" class="form-label">Product</label>
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
                    <div class="col-md-3">
                        <label for="itemQuantity" class="form-label">Quantity</label>
                        <input type="number" id="itemQuantity" class="form-control" value="1" min="1">
                    </div>
                    <div class="col-md-3">
                        <button type="button" id="addItemBtn" class="btn btn-secondary w-100">Add Item</button>
                    </div>
                </div>

                <hr class="my-4">

                <h6 class="mb-3">Invoice Items</h6>
                <table class="table">
                    <thead class="table-light">
                        <tr>
                            <th>Product Name</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Total</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody id="billItemsTableBody">
                        <!-- Items will be added here by JavaScript -->
                    </tbody>
                </table>
                <div class="text-end">
                    <h4>Grand Total: <span id="grandTotal">0.00</span></h4>
                </div>
            </div>

            <!-- Submit Button -->
            <div class="mt-4 text-end">
                <button type="submit" class="btn btn-primary btn-lg">Generate Bill & Receipt</button>
            </div>
        </form>
    </div>
</div>

<!-- ======================= JAVASCRIPT LOGIC ======================= -->
<script>
    document.addEventListener('DOMContentLoaded', function() {
        // --- Get all the necessary HTML elements ---
        const addItemBtn = document.getElementById('addItemBtn');
        const itemSelect = document.getElementById('itemSelect');
        const itemQuantityInput = document.getElementById('itemQuantity');
        const tableBody = document.getElementById('billItemsTableBody');
        const grandTotalSpan = document.getElementById('grandTotal');
        let itemCounter = 0;

        // --- Event Listener for the 'Add Item' button ---
        addItemBtn.addEventListener('click', function() {
            const selectedOption = itemSelect.options[itemSelect.selectedIndex];
            if (!selectedOption || !selectedOption.value) {
                alert('Please select a valid item.');
                return;
            }

            const itemId = selectedOption.value;
            const itemName = selectedOption.text;
            const itemPrice = parseFloat(selectedOption.dataset.price);
            const quantity = parseInt(itemQuantityInput.value, 10);

            if (isNaN(itemPrice) || quantity <= 0) {
                alert('Invalid quantity. Please enter a number greater than 0.');
                return;
            }

            const lineTotal = itemPrice * quantity;

            // --- Create the new row and cells ---
            const newRow = tableBody.insertRow();
            const cell1 = newRow.insertCell(0); // Product Name & Hidden Inputs
            const cell2 = newRow.insertCell(1); // Quantity
            const cell3 = newRow.insertCell(2); // Price
            const cell4 = newRow.insertCell(3); // Total
            const cell5 = newRow.insertCell(4); // Action

            // --- Add back the crucial hidden input fields for form submission ---
            const hiddenInputs = `
                <input type="hidden" name="items[${itemCounter}].id" value="${itemId}">
                <input type="hidden" name="items[${itemCounter}].quantity" value="${quantity}">
                <input type="hidden" name="items[${itemCounter}].price" value="${itemPrice.toFixed(2)}">
            `;
            
            // Populate the cells with data
            cell1.innerHTML = itemName + hiddenInputs; // Add hidden inputs here
            cell2.textContent = quantity;
            cell3.textContent = itemPrice.toFixed(2);
            cell4.textContent = lineTotal.toFixed(2);
            cell5.innerHTML = `<button type="button" class="btn btn-danger btn-sm remove-item-btn">Remove</button>`;
            
            itemCounter++;
            updateGrandTotal(); // Call the function to update the total
        });

        // --- Event Listener for the 'Remove' button ---
        tableBody.addEventListener('click', function(event) {
            if (event.target.classList.contains('remove-item-btn')) {
                // Find the closest parent row 'tr' and remove it
                event.target.closest('tr').remove();
                updateGrandTotal(); // Update the total after removing an item
            }
        });

        // --- Function to calculate and display the grand total ---
        function updateGrandTotal() {
            let total = 0;
            const rows = tableBody.querySelectorAll('tr');
            rows.forEach(row => {
                // Get the text from the 4th cell (index 3), which is the line total
                const lineTotalValue = parseFloat(row.cells[3].textContent);
                if (!isNaN(lineTotalValue)) {
                    total += lineTotalValue;
                }
            });
            // Update the grand total span with the new calculated total
            grandTotalSpan.textContent = total.toFixed(2);
        }
    });
</script>

<jsp:include page="footer.jsp" />