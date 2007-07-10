<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="tmc" uri="http://com.themetacity" %>

<jsp:useBean id="ProfileProcessBean" scope="page" class="com.themetacity.beans.ProfileProcessBean"/>

<jsp:include page="/WEB-INF/jspf/header.jspf"/>
Now in page.
<c:forEach var="profileList" items="${ProfileProcessBean.profiles}">
    In the loop.
    <tmc:profile userProfile="${profileList}"/>
</c:forEach>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>