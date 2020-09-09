<%--
  Created by IntelliJ IDEA.
  User: Rijenson
  Date: 09.09.2020
  Time: 23:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Adding products</title>
</head>
<body>
<h1>Please, provide good details</h1>

<form method="post" action="${pageContext.request.contextPath}/products/add">
    <br />Please provide name of product: <input type="text" name="name">
    <br />Please provide price of product: <input type="price" name="price">
    <br /><button type="submit">Add</button>
</form>

<h4 style="color:lawngreen">${productAddedMessage}</h4>
<h4 style="color:red">${nullInputMessage}</h4>
<h4 style="color:red">${notNumberPriceMessage}</h4>
</body>
</html>
