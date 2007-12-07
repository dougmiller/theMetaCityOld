<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="tmc" uri="http://com.themetacity" %>

<jsp:useBean id="ProfileProcessBean" scope="page" class="com.themetacity.beans.ProfileProcessBean">
    <jsp:setProperty name="ProfileProcessBean" property="author" value="${param.author}"/>
</jsp:useBean>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<c:set var="profileList" value="${ProfileProcessBean.profiles}" scope="page"/>

<c:choose>
    <c:when test="${not empty profileList}">
        <c:forEach var="filteredProfileList" items="${profileList}">
            <tmc:profile userProfile="${filteredProfileList}"/>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <p>There is nobody here matching your query.</p>
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>