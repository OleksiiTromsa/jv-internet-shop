<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Login page</h1>

<form action="${pageContext.request.contextPath}/users/login" method="post">
    Please provide your login: <input required type="text" name="login"><br />
    Please provide your password: <input required type="password" name="pwd"><br />

    <button type="submit">Register</button><br />
</form>

<h4 style="color:red">${errorMsg}</h4>

<a href="${pageContext.request.contextPath}/users/registration">User registration</a><br />
</body>
</html>
