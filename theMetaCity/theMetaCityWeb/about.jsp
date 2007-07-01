<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<jsp:useBean id="ProfileProcessBean" scope="page" class="com.themetacity.beans.ProfileProcessBean" />

<jsp:include page="/WEB-INF/jspf/header.jspf" />

<c:set var="profiles" value="${ProfileProcessBean.profiles}" />
<%-- Check that its is not empty --%>
    

<c:if test="${not empty ProfileProcessBean.profiles}">
    <c:forEach var="profileList" items="${profiles}">
        <tmc:profile userProfile="${profileList}"/>
    </c:forEach>
</c:if>

<jsp:include page="/WEB-INF/jspf/footer.jspf" />