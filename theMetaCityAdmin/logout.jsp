<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ include file="/WEB-INF/jspf/security.jspf" %>

<c:set var="loggedIn" value="false" scope="session" />
<c:redirect url="login.jsp" />