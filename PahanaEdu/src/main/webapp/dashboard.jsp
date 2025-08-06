<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- We don't need the User import here unless we use it in a scriptlet --%>

<jsp:include page="header.jsp" />

<body> <%-- The body tag starts here --%>

    <div class="page-wrapper">

        <jsp:include page="sidebar.jsp" />

        <div class="main-content">
            <h1 class="mb-4">Dashboard</h1>
            <div class="row">
                
                <!-- Total Products Card -->
                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="stat-card bg-primary">
                        <%-- Switched from EL to a reliable scriptlet --%>
                        <h3><%= request.getAttribute("totalItems") %></h3>
                        <p>Total Products</p>
                    </div>
                </div>
                
                <!-- Total Customers Card -->
                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="stat-card bg-success">
                        <%-- Switched from EL to a reliable scriptlet --%>
                        <h3><%= request.getAttribute("totalCustomers") %></h3>
                        <p>Total Customers</p>
                    </div>
                </div>
                
                <!-- Low Stock Items Card -->
                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="stat-card bg-warning text-dark">
                        <h3><%= request.getAttribute("lowStockCount") %></h3>
                        <p>Low Stock Items</p>
                    </div>
                </div>
                
            </div>
        </div> <%-- End of main-content --%>

    </div> <%-- End of page-wrapper --%>

    <jsp:include page="footer.jsp" />
    
</body>
</html>