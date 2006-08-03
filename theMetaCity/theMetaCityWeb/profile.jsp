<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<jsp:include page="/WEB-INF/jspf/header.jspf" />

<jsp:useBean id="ProfileBean" scope="page" class="com.themetacity.beans.ProfileProcessBean" />

<c:forEach var="profileItem" items="${ProfileBean}">
    <tmc:profile profile="${profileItem}"/>
</c:forEach>

<jsp:include page="/WEB-INF/jspf/footer.jspf" />