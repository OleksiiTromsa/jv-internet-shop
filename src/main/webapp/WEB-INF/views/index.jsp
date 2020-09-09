<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tromsa's first internet shop</title>
</head>
<body>
    <h1>Welcome to our internet shop!</h1>

    <a href="${pageContext.request.contextPath}/registration">User registration</a>
    <br /><a href="${pageContext.request.contextPath}/users/all">All users</a>
    <br /><a href="${pageContext.request.contextPath}/products/add">Add product</a>
    <br /><a href="${pageContext.request.contextPath}/products/all">All products</a>
    <br /><a href="${pageContext.request.contextPath}/shopping-carts/get">Show shopping cart</a>
    <br /><a href="${pageContext.request.contextPath}/injectData">Inject test data into the DB</a>

</body>
</html>
