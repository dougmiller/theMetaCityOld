<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<jsp:useBean id="ProfileTestBean" scope="page" class="com.themetacity.beans.ProfileTestBean" />

<jsp:include page="/WEB-INF/jspf/header.jspf" />

<c:forEach var="profileList" items="${ProfileTestBean.testProfile}">
   <tmc:profile userProfile="${profileList}"/>
</c:forEach>

<jsp:include page="/WEB-INF/jspf/footer.jspf" />