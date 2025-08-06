<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="header.jsp" />

<div class="d-flex">
    <jsp:include page="sidebar.jsp" />

    <div class="main-content p-4">
        <c:set var="isEditMode" value="${not empty item}" />

        <h1 class="mb-4">
            <c:if test="${isEditMode}">Edit Item</c:if>
            <c:if test="${!isEditMode}">Add New Item</c:if>
        </h1>
        
        <div class="form-card" style="margin: 0;">
            <form action="item" method="post">
                <input type="hidden" name="action" value="${isEditMode ? 'update' : 'insert'}" />
                <c:if test="${isEditMode}">
                    <input type="hidden" name="itemId" value="${item.itemId}" />
                </c:if>

                <div class="mb-3">
                    <label for="itemName" class="form-label">Item Name</label>
                    <input type="text" class="form-control" id="itemName" name="itemName" 
                           value="<c:out value='${isEditMode ? item.itemName : ""}' />" required>
                </div>
                <div class="mb-3">
                    <label for="price" class="form-label">Price</label>
                    <input type="number" step="0.01" min="0" class="form-control" id="price" name="price"
                           value="<c:out value='${isEditMode ? item.price : ""}' />" required>
                </div>
                <div class="mb-3">
                    <label for="stock" class="form-label">Stock</label>
                    <input type="number" min="0" class="form-control" id="stock" name="stock"
                           value="<c:out value='${isEditMode ? item.stock : "0"}' />" required>
                </div>
                
                <button type="submit" class="btn btn-primary">Save Item</button>
                <a href="item?action=list" class="btn btn-secondary">Cancel</a>
            </form>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />