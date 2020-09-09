<%--
  Created by IntelliJ IDEA.
  User: Rijenson
  Date: 09.09.2020
  Time: 19:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Hello! Please provide your user details</h1>

<h4 style="color:red">${message}</h4>

<form method="post" action="${pageContext.request.contextPath}/registration">
    Please provide your login: <input type="text" name="login">
    Please provide your password: <input type="password" name="pwd">
    Please repeat your password: <input type="password" name="pwd-repeat">

    <button type="submit">Register</button>
</form>
</body>
</html>
