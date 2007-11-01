<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<jsp:useBean id="ArticleProcessBean" scope="page" class="com.themetacity.beans.ArticleProcessBean"/>

<jsp:include page="/WEB-INF/jspf/header.jspf" />

<c:forEach var="articleList" items="${ArticleProcessBean.articles}">
    <tmc:article newsArticle="${articleList}"/>
</c:forEach>


<tmc:quote quoteAuthor="Dingo McGraw">
This is my quote, use it wisely. Again and again it come around looking for you, targeted in your sights it fughts back, eating you inside.
</tmc:quote>

<jsp:include page="/WEB-INF/jspf/footer.jspf" />