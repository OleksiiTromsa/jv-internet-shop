<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Adding products</title>
</head>
<body>
<h1>Please, provide product details</h1>

<form method="post" action="${pageContext.request.contextPath}/admin/products/add">
    <br />Please provide name of product: <input required type="text" name="name">
    <br />Please provide price of product: <input required type="number" name="price">
    <br /><button type="submit">Add</button>
</form>

<h4 style="color:lawngreen">${productAddedMessage}</h4>
<form action="${pageContext.request.contextPath}/" method="get">
    <button type="submit">To main menu</button>
</form>
<a href="${pageContext.request.contextPath}/admin/products/all">See all products</a><br />
<a href="${pageContext.request.contextPath}/admin/menu">Admin menu</a><br />
</body>
</html>
