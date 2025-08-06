<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="header.jsp" />

<div class="d-flex">
    <jsp:include page="sidebar.jsp" />

    <div class="main-content p-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1>Item Management</h1>
            <a href="item?action=new" class="btn btn-primary">Add New Item</a>
        </div>

        <div class="table-responsive">
            <table class="table table-striped table-bordered table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Item Name</th>
                        <th>Price</th>
                        <th>Stock</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${itemList}">
                        <tr>
                            <td><c:out value="${item.itemId}" /></td>
                            <td><c:out value="${item.itemName}" /></td>
                            <td><c:out value="${item.price}" /></td>
                            <td><c:out value="${item.stock}" /></td>
                            <td>
                                <a href="item?action=edit&id=${item.itemId}" class="btn btn-sm btn-warning">Edit</a>
                                
                                <c:if test="${sessionScope.user.role == 'Admin'}">
                                    <a href="item?action=delete&id=${item.itemId}" class="btn btn-sm btn-danger" 
                                       onclick="return confirm('Are you sure you want to delete this item?');">Delete</a>
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