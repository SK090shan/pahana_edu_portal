<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="header.jsp" />

<body>
    <div class="page-wrapper">
        <jsp:include page="sidebar.jsp" />

        <div class="main-content">
            <h1 class="mb-4">Admin Help & System Guide</h1>
            <p class="lead mb-4">This guide provides instructions on how to use all the administrative features of the Pahana Education Billing System.</p>

            <div class="accordion" id="helpAccordion">

                <!-- Section 1: User Registration & Staff Management -->
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingOne">
                        <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                            <strong>1. How to Manage Staff Accounts</strong>
                        </button>
                    </h2>
                    <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#helpAccordion">
                        <div class="accordion-body">
                            <p>The system allows for two user roles: <strong>Staff</strong> and <strong>Admin</strong>. New users must first register, after which an Admin must activate their account.</p>
                            <ol>
                                <li><strong>New User Registration:</strong> Any person can register for an account using the "Register" link on the login page. They will provide their name, username, email, and password.</li>
                                <li><strong>Pending Status:</strong> After registration, all new accounts are created with a <strong>'PENDING'</strong> status for security. They cannot log in yet.</li>
                                <li><strong>Activating Staff:</strong> As an Admin, navigate to the <strong>Staff Management</strong> page. Here you will see a list of all users. Click the green <strong>"Activate"</strong> button next to a PENDING user to change their status to 'ACTIVE'. They will now be able to log in.</li>
                                <li><strong>Deleting Staff:</strong> You can permanently remove a staff member by clicking the red <strong>"Delete"</strong> button. Be aware that you cannot delete a staff member who has already created bills in the system. You also cannot delete your own admin account.</li>
                            </ol>
                        </div>
                    </div>
                </div>

                <!-- Section 2: Customer and Item Management -->
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingTwo">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                            <strong>2. How to Manage Customers and Products (CRUD)</strong>
                        </button>
                    </h2>
                    <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo" data-bs-parent="#helpAccordion">
                        <div class="accordion-body">
                            <p>Both Admins and Staff can create and edit customers and products, but only Admins can delete them.</p>
                            <ul>
                                <li><strong>To Add:</strong> Navigate to the "Customers" or "Products" page and click the blue <strong>"Add New..."</strong> button. Fill in the form and click "Save".</li>
                                <li><strong>To Edit:</strong> In the list view, click the yellow <strong>"Edit"</strong> button for the desired record. The form will appear with the existing data. Make your changes and click "Save".</li>
                                <li><strong>To Delete (Admin Only):</strong> As an Admin, you will see a red <strong>"Delete"</strong> button in the list view. Clicking this will permanently remove the record. Note: You cannot delete records that are linked to existing bills.</li>
                            </ul>
                        </div>
                    </div>
                </div>

                <!-- Section 3: Invoicing and Receipts -->
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingThree">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                            <strong>3. How to Create an Invoice and Print a Receipt</strong>
                        </button>
                    </h2>
                    <div id="collapseThree" class="accordion-collapse collapse" aria-labelledby="headingThree" data-bs-parent="#helpAccordion">
                        <div class="accordion-body">
                            <p>The invoicing process is a two-stage workflow designed for accuracy.</p>
                            <ol>
                                <li><strong>Navigate to "Invoices":</strong> This will open the "Create New Invoice" page.</li>
                                <li><strong>Select a Customer:</strong> Choose the correct customer from the dropdown list. This is a required step.</li>
                                <li><strong>Add Items:</strong> Use the "Add Items to Bill" section to build the invoice. Select a product, enter a quantity, and click <strong>"Add Item"</strong>. The item will appear in the table below. Repeat this for all products. You can use the "Remove" button to correct mistakes.</li>
                                <li><strong>Finalize the Bill:</strong> Once all items are added, click the blue <strong>"Finalize Bill"</strong> button. This action saves the invoice to the database permanently.</li>
                                <li><strong>Print the Receipt:</strong> After finalizing, you will be taken to a confirmation page. This page shows the final, saved bill. Click the green <strong>"Print Receipt"</strong> button to generate and download a PDF copy, which includes a QR code for quick reference.</li>
                            </ol>
                        </div>
                    </div>
                </div>
                
                <!-- Section 4: Reports -->
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingFour">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                            <strong>4. How to Use the Reports Section</strong>
                        </button>
                    </h2>
                    <div id="collapseFour" class="accordion-collapse collapse" aria-labelledby="headingFour" data-bs-parent="#helpAccordion">
                        <div class="accordion-body">
                            <p>The "Reports" page is visible only to Admins and provides a summary of all data in the system.</p>
                            <ul>
                                <li><strong>Viewing Data:</strong> The page displays tables for all Staff, Customers, Inventory, and Billing History.</li>
                                <li><strong>Calculating Total Income:</strong> At the bottom of the "Billing History" table, the system automatically calculates and displays the total income from all finalized bills.</li>
                                <li><strong>Downloading Data:</strong> For the Staff, Customer, and Inventory sections, you can click the green <strong>"Download CSV"</strong> button to export the data into a spreadsheet-compatible format for offline analysis.</li>
                            </ul>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <jsp:include page="footer.jsp" />
</body>
</html>