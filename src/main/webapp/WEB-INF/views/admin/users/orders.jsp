<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>All <c:out value="${userName}"/> orders</h1>

<table border="1">
    <tr>
        <th>OrderID</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${order.id}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/orders/details?id=${order.id}">Details</a>
            </td>
        </tr>
    </c:forEach>
</table>
<form action="${pageContext.request.contextPath}/" method="get">
    <button type="submit">To main menu</button>
</form>
<a href="${pageContext.request.contextPath}/admin/actions">Admin actions</a><br />
</body>
</html>
