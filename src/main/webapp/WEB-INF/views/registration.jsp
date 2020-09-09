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



<form method="post" action="${pageContext.request.contextPath}/registration">
    Please provide your name: <input type="text" name="name">
    <br />Please provide your login: <input type="text" name="login">
    <br />Please provide your password: <input type="password" name="pwd">
    <br />Please repeat your password: <input type="password" name="pwd-repeat">

    <br /><button type="submit">Register</button>
</form>

<h4 style="color:red">${message}</h4>
<h4 style="color:red">${nullInputMessage}</h4>

</body>
</html>
