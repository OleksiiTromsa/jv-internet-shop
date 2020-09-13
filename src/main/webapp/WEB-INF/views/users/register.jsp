<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Hello! Please provide your user details</h1>

<form method="post" action="${pageContext.request.contextPath}/users/registration">
    Please provide your name: <input required type="text" name="name">
    <br />Please provide your login: <input required type="text" name="login">
    <br />Please provide your password: <input required type="password" name="pwd">
    <br />Please repeat your password: <input required type="password" name="pwd-repeat">

    <br /><button type="submit">Register</button>
</form>

<h4 style="color:red">${message}</h4>

<form action="${pageContext.request.contextPath}/" method="get">
    <button type="submit">To main menu</button>
</form>
</body>
</html>
