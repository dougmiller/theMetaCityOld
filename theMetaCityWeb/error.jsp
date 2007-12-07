<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isErrorPage="true" %>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<span class="errorNumber"><c:out value="${param.error}"/></span>

<%--<c:choose>

    <c:when test="${empty error}">
        <p>You shouldnt see this</p>
    </c:when>

    <c:when test="${error.equals(\"404\")}">
        <p>File not found</p>
    </c:when>

    <c:when test="${error == \"500\"}">
        <p>Resource Denied! (I think)</p>
    </c:when>

    <c:when test="${error == \"403\"}">
        <p>Some other error</p>
    </c:when>

    <c:otherwise>
        <p>An error occured.</p>
    </c:otherwise>

</c:choose></>

<c:out value="${Exception}" />  --%>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>