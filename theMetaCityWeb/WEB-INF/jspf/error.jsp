<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isErrorPage="true" %>

<%@ include file="header.jspf" %>

<h2><c:out value="${param.error}"/></h2>

<c:choose>

    <c:when test="${empty param.error}">
        <c:redirect url="/" />
    </c:when>

    <c:when test="${param.error == 404}">
        <p>The file you were looking for has not been found.</p>
    </c:when>

    <c:when test="${param.error == 500}">
        <p>There has been an internal server error.</p>
    </c:when>

    <c:when test="${param.error == 403}">
        <p>This resources has been dened.</p>
    </c:when>

    <c:otherwise>
        <p>An error occured.</p>
    </c:otherwise>

</c:choose>

<jsp:include page="footer.jspf"/>