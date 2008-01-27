<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Simple jsp page</title>
    <link href="/theMetaCityAdmin/styles/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>

<div class="loginform">
    <form action="login.jsp" method="post">
        <div><div class="loginlabeldiv">Username: </div><div class="logininputdiv"><input type="text" name="username"/></div></div>
        <div><div class="loginlabeldiv">Password: </div><div class="logininputdiv"><input type="password" name="password"/></div></div>
        <div><input type="button" value="reset" name="reset"/><input type="button" value="submit"/></div>
    </form>
</div>

</body>
</html>