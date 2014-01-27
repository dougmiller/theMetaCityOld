<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>


<%@ include file="/WEB-INF/jspf/headertop.jspf" %>
<%@ include file="/WEB-INF/jspf/headertop.jspf" %>   <%-- Clunky as hell --%>
<title>theMetaCity.com Blog Tags
    <c:out value="${param.tag}" />
</title>
<%@ include file="/WEB-INF/jspf/headerbottom.jspf" %>

<jsp:useBean id="TagProcessBean" scope="page" class="com.themetacity.beans.TagProcessBean"/>

<c:choose>
    <c:when test="${not empty param.tag}">
        <jsp:useBean id="ArticleProcessBean" scope="page" class="com.themetacity.beans.ArticleProcessBean">
            <jsp:setProperty name="ArticleProcessBean" property="searchTag" value="${param.tag}"/>
        </jsp:useBean>

        <c:set var="resultList" value="${ArticleProcessBean.articlesWithTag}"/>
        <p>These are the articles posted under the tag: ${param.tag}</p>

        <c:choose>
            <c:when test="${not empty resultList}">
                <c:set var="previousEntry" value="${null}"/>
                <c:forEach var="articleList" items="${resultList}">
                    <tmc:archive articleBean="${articleList}" previousDate="${previousEntry}"/>                    
                    <c:set var="previousEntry" value="${articleList.modifiedDate}"/>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p>Nothing has been posted under that tag. Please choose from the list below:</p>
                <c:set var="allTagsList" value="${TagProcessBean.allTags}"/>
                <c:choose>
                    <c:when test="${empty allTagsList}">
                        <p>There are no tags currently posted.</p>
                    </c:when>
                    <c:otherwise>
                        <tmc:allTags tagsList="${allTagsList}"/>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
        <p><a href="/blog/tags">Back to tags.</a></p>
    </c:when>
    <c:otherwise>
        <!-- Display all the tags used on the site -->
        <c:set var="allTagsList" value="${TagProcessBean.allTags}"/>
        <c:choose>
            <c:when test="${empty allTagsList}">
                <p>There are no tags currently posted.</p>
            </c:when>
            <c:otherwise>
                <tmc:allTags tagsList="${allTagsList}"/>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>