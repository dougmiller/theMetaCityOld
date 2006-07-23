<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://ja" prefix="tmc" %>

<jsp:useBean id="DataListBean" scope="page" class="com.themetacity.beans.DataAccessBean"/>

<jsp:include page="/WEB-INF/jspf/header.jspf"/>

<c:forEach var="newsItem" items="${DataListBean.newsResults}">
    <tmc:news newsArticle="${newsItem}"/>
</c:forEach>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>