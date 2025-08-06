<jsp:include page="header.jsp" />

<div class="d-flex">
    <jsp:include page="sidebar.jsp" />

    <div class="main-content p-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1>Customer Management</h1>
            <a href="customer?action=new" class="btn btn-primary">Add New Customer</a>
        </div>

        <div class="table-responsive">
            <table class="table table-striped table-bordered table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>Account #</th>
                        <th>Full Name</th>
                        <th>Address</th>
                        <th>Telephone</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%-- 1. Loop through the list of customers sent from the servlet --%>
                    <c:forEach var="customer" items="${customerList}">
                        <tr>
                            <td><c:out value="${customer.accountNumber}" /></td>
                            <td><c:out value="${customer.fullName}" /></td>
                            <td><c:out value="${customer.address}" /></td>
                            <td><c:out value="${customer.telephone}" /></td>
                            <td>
                                <a href="customer?action=edit&id=${customer.customerId}" class="btn btn-sm btn-warning">Edit</a>
                                
                                <%-- 2. ROLE-BASED SECURITY CHECK --%>
                                <%-- This 'Delete' button will ONLY be rendered if the user's role in the session is 'Admin' --%>
                                <c:if test="${sessionScope.user.role == 'Admin'}">
                                    <a href="customer?action=delete&id=${customer.customerId}" class="btn btn-sm btn-danger" 
                                       onclick="return confirm('Are you sure you want to delete this customer?');">Delete</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />