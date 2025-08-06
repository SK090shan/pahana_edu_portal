<jsp:include page="header.jsp" />

<div class="d-flex">
    <%-- 1. Include the reusable sidebar --%>
    <jsp:include page="sidebar.jsp" />

    <%-- 2. Main content area --%>
    <div class="main-content p-4">
        <h1 class="mb-4">Dashboard</h1>
        <div class="row">
            
            <!-- Total Products Card -->
            <div class="col-lg-4 col-md-6 mb-4">
                <div class="stat-card bg-primary text-white">
                    <%-- 3. Display the 'totalItems' variable set by DashboardServlet --%>
                    <h3>${totalItems}</h3>
                    <p>Total Products</p>
                </div>
            </div>
            
            <!-- Total Customers Card -->
            <div class="col-lg-4 col-md-6 mb-4">
                <div class="stat-card bg-success text-white">
                    <%-- 4. Display the 'totalCustomers' variable set by DashboardServlet --%>
                    <h3>${totalCustomers}</h3>
                    <p>Total Customers</p>
                </div>
            </div>
            
            <!-- Placeholder Card for Low Stock -->
            <div class="col-lg-4 col-md-6 mb-4">
                <div class="stat-card bg-warning text-dark">
                    <h3>0</h3>
                    <p>Low Stock Items</p>
                </div>
            </div>
            
        </div>
        <%-- You can add more rows for charts or recent activity tables here later --%>
    </div>
</div>

<jsp:include page="footer.jsp" />