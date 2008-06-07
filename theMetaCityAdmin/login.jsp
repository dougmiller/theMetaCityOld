<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <title>Please log in</title>
    <meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8"/>
    <link href="styles/login.css" rel="stylesheet" type="text/css"/>
</head>
<body>

<c:if test="${param.submit == 'submit'}">
    <jsp:useBean id="LoginBean" scope="page" class="com.themetacity.beans.ProfileProcessBean">
        <jsp:setProperty name="LoginBean" property="author" value="${param.username}"/>
        <jsp:setProperty name="LoginBean" property="password" value="${param.password}"/>
    </jsp:useBean>

    <c:choose>
        <c:when test="${LoginBean.validUser == true}">
            <c:set var="loggedIn" value="true" scope="session"/>
            <c:set var="loggedInUser" value="${param.username}" scope="session"/>
            <c:redirect url="index.jsp"/>
        </c:when>
        <c:otherwise>
            <c:set var="invalidUser" value="true" scope="page"/>
        </c:otherwise>
    </c:choose>
</c:if>

<div class="wrapper">
    <div class="formwrapper">
        <c:if test="${invalidUser == true}">
            <div class="alert">Invalid username / passphrase</div>
        </c:if>
        <div class="formbox">
            <form class="loginform" method="post" action="login.jsp">
                <div class="formboxrow">
                    <div class="formboxrowleft">Username:</div>
                    <div class="formboxrowright"><input type="text" name="username" value="${param.username}"/></div>
                </div>
                <div class="formboxrow">
                    <div class="formboxrowleft">Password:</div>
                    <div class="formboxrowright"><input type="password" name="password"/></div>
                </div>

                <!-- Submit/reset buttons -->
                <div class="buttonsbox">
                    <button type="submit" name="submit" value="submit" class="submitbutton"><img src="siteimages/tick.png" alt="Login"/>Login</button>
                    <button type="reset" class="reloadbutton"><img src="siteimages/redo.png" alt="Clear"/>Clear</button>
                </div>
            </form>
            <p class="info">Username and passphrase are case sensitive.</p>
        </div>
    </div>
</div>
</body>
</html>
