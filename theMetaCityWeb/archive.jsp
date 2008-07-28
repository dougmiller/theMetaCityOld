<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<jsp:useBean id="ArticleProcessBean" scope="page" class="com.themetacity.beans.ArticleProcessBean">
    <jsp:setProperty name="ArticleProcessBean" property="year" value="${param.year}"/>
    <jsp:setProperty name="ArticleProcessBean" property="month" value="${param.month}"/>
    <jsp:setProperty name="ArticleProcessBean" property="day" value="${param.day}"/>
    <jsp:setProperty name="ArticleProcessBean" property="title" value="${param.title}"/>
</jsp:useBean>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<c:set var="articleResultList" value="${ArticleProcessBean.filteredArticles}"/>

<c:choose>
    <c:when test="${not empty articleResultList}">
        <c:set var="previousEntry" value="${null}"/>
        <c:forEach var="archiveEntry" items="${articleResultList}">
            <tmc:archive articleBean="${archiveEntry}" previousDate="${previousEntry}"/>
            <c:set var="previousEntry" value="${archiveEntry.dateTime}"/>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test="${not empty param.year or not empty param.title}">
                <p>There are no results for your search.</p>
            </c:when>
            <c:otherwise>
                <p>There are no articles in the archive.</p>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>