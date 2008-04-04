<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<jsp:useBean id="ImportantNoticeBean" scope="page" class="com.themetacity.beans.ImportantNoticeProcessBean"/>

<jsp:useBean id="ArticleProcessBean" scope="page" class="com.themetacity.beans.ArticleProcessBean">
    <jsp:setProperty name="ArticleProcessBean" property="year" value="${param.year}"/>
    <jsp:setProperty name="ArticleProcessBean" property="month" value="${param.month}"/>
    <jsp:setProperty name="ArticleProcessBean" property="day" value="${param.day}"/>
    <jsp:setProperty name="ArticleProcessBean" property="title" value="${param.title}"/>
</jsp:useBean>

<c:choose>
    <c:when test="${empty param.year and empty param.title}">
        <c:set var="articleResultList" value="${ArticleProcessBean.frontpageArticles}"/>
    </c:when>
    <c:otherwise>
        <c:set var="articleResultList" value="${ArticleProcessBean.filteredArticles}"/>
    </c:otherwise>
</c:choose>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<c:forEach var="noticeBean" items="${ImportantNoticeBean.importantNotices}">
    <tmc:notice importantNotice="${noticeBean}"/>
</c:forEach>

<c:choose>
    <c:when test="${not empty articleResultList}">
        <c:forEach var="articleList" items="${articleResultList}">
            <tmc:article article="${articleList}"/>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test="${not empty param.year or not empty param.title}">
                <p>There are no articles for this criteria.</p>

                <p>Perhaps <a href="/<c:url value="archive.jsp"/>">you could try the archive</a> or <a
                        href="/<c:url value="tags.jsp"/>">search via catagory tags.</a></p>
            </c:when>
            <c:otherwise>
                <p>There is nothing in here yet.</p>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>