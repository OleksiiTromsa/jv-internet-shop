<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tromsa's first internet shop</title>
</head>
<body>
    <h1>Welcome to our internet shop!</h1>

    <a href="${pageContext.request.contextPath}/users/logout">Logout</a><br />
    <a href="${pageContext.request.contextPath}/users/registration">User registration</a><br />
    <a href="${pageContext.request.contextPath}/users/login">User login</a><br />
    <a href="${pageContext.request.contextPath}/products/all">All products</a><br />
    <a href="${pageContext.request.contextPath}/shopping-carts/get">Show shopping cart</a><br />
    <a href="${pageContext.request.contextPath}/users/orders">Show logged user orders</a><br />
    <br /><a href="${pageContext.request.contextPath}/admin/menu">Admin menu</a><br />

</body>
</html>
