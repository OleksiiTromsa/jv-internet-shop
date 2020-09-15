<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin actions</title>
</head>
<body>
    <a href="${pageContext.request.contextPath}/admin/users/all">All users</a><br />
    <a href="${pageContext.request.contextPath}/admin/orders/all">See all orders</a><br />
    <a href="${pageContext.request.contextPath}/admin/products/all">See all products</a>
    <form action="${pageContext.request.contextPath}/" method="get">
        <button type="submit">To main menu</button>
    </form>
</body>
</html>
