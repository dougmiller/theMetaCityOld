<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<jsp:useBean id="NewsProcessBean" scope="page" class="com.themetacity.beans.NewsProcessBean"/>

<jsp:include page="/WEB-INF/jspf/header.jspf"/>

<c:forEach var="newsItem" items="${NewsProcessBean.ProcessNews}">
    <tmc:news newsArticle="${newsItem}"/>
</c:forEach>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>