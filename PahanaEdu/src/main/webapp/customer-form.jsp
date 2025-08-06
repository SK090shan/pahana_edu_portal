<jsp:include page="header.jsp" />

<div class="d-flex">
    <jsp:include page="sidebar.jsp" />

    <div class="main-content p-4">
        <%-- 1. Logic to determine if we are in 'Edit' or 'Add' mode --%>
        <c:set var="isEditMode" value="${not empty customer}" />

        <h1 class="mb-4">
            <c:if test="${isEditMode}">Edit Customer</c:if>
            <c:if test="${!isEditMode}">Add New Customer</c:if>
        </h1>
        
        <div class="form-card" style="margin: 0;"> <%-- Reusing form-card style with no margin --%>
            <form action="customer" method="post">
                
                <%-- 2. Hidden input to tell the servlet if this is an 'insert' or 'update' --%>
                <input type="hidden" name="action" value="${isEditMode ? 'update' : 'insert'}" />
                
                <%-- 3. If in edit mode, include the customerId so the servlet knows WHICH customer to update --%>
                <c:if test="${isEditMode}">
                    <input type="hidden" name="customerId" value="${customer.customerId}" />
                </c:if>

                <div class="mb-3">
                    <label for="accountNumber" class="form-label">Account Number</label>
                    <input type="text" class="form-control" id="accountNumber" name="accountNumber" 
                           value="<c:out value='${isEditMode ? customer.accountNumber : ""}' />" required>
                </div>
                <div class="mb-3">
                    <label for="fullName" class="form-label">Full Name</label>
                    <input type="text" class="form-control" id="fullName" name="fullName"
                           value="<c:out value='${isEditMode ? customer.fullName : ""}' />" required>
                </div>
                <div class="mb-3">
                    <label for="address" class="form-label">Address</label>
                    <input type="text" class="form-control" id="address" name="address"
                           value="<c:out value='${isEditMode ? customer.address : ""}' />">
                </div>
                <div class="mb-3">
                    <label for="telephone" class="form-label">Telephone</label>
                    <input type="text" class="form-control" id="telephone" name="telephone"
                           value="<c:out value='${isEditMode ? customer.telephone : ""}' />">
                </div>
                
                <button type="submit" class="btn btn-primary">Save Customer</button>
                <a href="customer?action=list" class="btn btn-secondary">Cancel</a>
            </form>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />