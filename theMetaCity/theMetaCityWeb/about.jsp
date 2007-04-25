<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<jsp:useBean id="ProfileProcessBean" scope="page" class="com.themetacity.beans.ProfileProcessBean" />

<jsp:include page="/WEB-INF/jspf/header.jspf" />



<%--
<c:forEach var="profileList" items="${ProfileProcessBean.profiles}">
   <tmc:profile userProfile="${profileList}"/>
</c:forEach>
--%>


<jsp:include page="/WEB-INF/jspf/footer.jspf" />