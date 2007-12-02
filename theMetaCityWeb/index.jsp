<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<jsp:useBean id="ArticleProcessBean" scope="page" class="com.themetacity.beans.ArticleProcessBean">
    <jsp:setProperty name="ArticleProcessBean" property="year" value="${param.year}"/>
    <jsp:setProperty name="ArticleProcessBean" property="month" value="${param.month}"/>
    <jsp:setProperty name="ArticleProcessBean" property="day" value="${param.day}"/>
    <jsp:setProperty name="ArticleProcessBean" property="title" value="${param.title}"/>
</jsp:useBean>
<jsp:include page="/WEB-INF/jspf/header.jspf"/>

<c:forEach var="articleList" items="${ArticleProcessBean.articles}">
    <tmc:article article="${articleList}"/>
</c:forEach>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>