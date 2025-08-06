<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- 
    This is a simplified, "static" version of the sidebar.
    It removes the Expression Language ('${...}') that was causing the library errors.
    The links will work perfectly, but the "active" page will not be highlighted.
    This is a stable workaround to allow us to continue development.
--%>
<div class="sidebar bg-dark p-3 d-flex flex-column">
    <div>
        <a href="dashboard" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-white text-decoration-none">
            <span class="fs-4">Pahana Edu V2</span>
        </a>
        <hr class="text-secondary">
        <ul class="nav nav-pills flex-column mb-auto">
            <li class="nav-item">
                <%-- The complex EL code has been removed from the class attribute --%>
                <a href="dashboard" class="nav-link text-white">
                    Dashboard
                </a>
            </li>
            <li>
                <a href="customer?action=list" class="nav-link text-white">
                    Customers
                </a>
            </li>
            <li>
                <a href="item?action=list" class="nav-link text-white">
                    Products
                </a>
            </li>
            <li>
                <a href="billing" class="nav-link text-white">
                    Invoices
                </a>
            </li>
            <li>
                <a href="reports" class="nav-link text-white">
                    Reports
                </a>
            </li>
        </ul>
    </div>
    <div class="mt-auto">
        <hr class="text-secondary">
        <%-- We will add the logged-in user's name back later using a different method if needed --%>
        <a href="logout" class="nav-link text-white">Logout</a>
    </div>
</div>