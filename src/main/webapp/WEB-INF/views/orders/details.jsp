<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order detail</title>
</head>
<body>
<h2>Order <c:out value="${orderId}"/> details</h2>
<table border="1">
    <tr>
        <th>ProductID</th>
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
        </tr>
    </c:forEach>
</table>
<form action="${pageContext.request.contextPath}/" method="get">
    <button type="submit">To main menu</button>
</form>
</body>
</html>
