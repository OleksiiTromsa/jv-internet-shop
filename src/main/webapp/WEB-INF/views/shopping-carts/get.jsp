<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping Cart</title>
</head>
<body>
<h1>Shopping cart</h1>

<form method="post" action="${pageContext.request.contextPath}/shopping-carts/get">
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>
                <c:out value="${product.id}"/>
            </td>
            <td>
                <c:out value="${product.name}"/>
            </td>
            <td>
                <c:out value="${product.price}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/shopping-carts/products/delete?id=${product.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<br /><button style="color: green" type="submit">Create order</button>
</form>
</body>
</html>
