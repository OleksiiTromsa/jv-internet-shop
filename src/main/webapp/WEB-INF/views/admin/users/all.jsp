<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>All users page</h1>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Login</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>
                <c:out value="${user.id}"/>
            </td>
            <td>
                <c:out value="${user.name}"/>
            </td>
            <td>
                <c:out value="${user.login}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/users/orders?id=${user.id}">See orders</a>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/users/delete?id=${user.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<form action="${pageContext.request.contextPath}/" method="get">
    <button type="submit">To main menu</button>
</form>
</body>
</html>
