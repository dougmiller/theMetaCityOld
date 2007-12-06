<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<jsp:useBean id="ImportantNoticeBean" scope="page" class="com.themetacity.beans.ImportantNoticeProcessBean"/>

<jsp:useBean id="ArticleProcessBean" scope="page" class="com.themetacity.beans.ArticleProcessBean">
    <jsp:setProperty name="ArticleProcessBean" property="year" value="${param.year}"/>
    <jsp:setProperty name="ArticleProcessBean" property="month" value="${param.month}"/>
    <jsp:setProperty name="ArticleProcessBean" property="day" value="${param.day}"/>
    <jsp:setProperty name="ArticleProcessBean" property="title" value="${param.title}"/>
</jsp:useBean>

<c:set var="articleResultList" value="${ArticleProcessBean.articles}"/>

<jsp:include page="/WEB-INF/jspf/header.jspf"/>
<c:forEach var="noticeBean" items="${ImportantNoticeBean.notices}">
    <tmc:notice importantNotice="${noticeBean}"/>
</c:forEach>

<c:choose>
    <c:when test="${not empty articleResultList}">
        <c:forEach var="articleList" items="${articleResultList}">
            <tmc:article article="${articleList}"/>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <p>There are no articles for this criteria.</p>
        <p>Perhaps <a href="/<c:url value="archive.jsp"/>">you could try the archive</a> or <a href="/<c:url value="tags.jsp"/>">search via catagory tags.</a></p>
    </c:otherwise>
</c:choose>


<jsp:include page="/WEB-INF/jspf/footer.jspf"/>