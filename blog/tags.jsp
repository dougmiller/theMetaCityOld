<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<%@ include file="/WEB-INF/jspf/headertop.jspf" %>   <%-- Clunky as hell --%>
<title>theMetaCity.com Blog Tags
    <c:out value="${param.tag}" />
</title>
<%@ include file="/WEB-INF/jspf/headerbottom.jspf" %>

<h1>Tags</h1>

<jsp:useBean id="TagProcessBean" scope="page" class="com.themetacity.beans.TagProcessBean"/>

<c:choose>
    <%-- User supplied a tag (param) --%>
    <c:when test="${not empty param.tag}">
        <jsp:useBean id="ArticleProcessBean" scope="page" class="com.themetacity.beans.ArticleProcessBean">
            <jsp:setProperty name="ArticleProcessBean" property="searchTag" value="${param.tag}"/>
        </jsp:useBean>

        <c:set var="articlesWithTagList" value="${ArticleProcessBean.articlesWithTag}"/>
        <p>These are the articles posted under the tag: ${param.tag}</p>

        <c:choose>
            <%-- Article(s) posted under tag --%>
            <c:when test="${not empty articlesWithTagList}">
                <c:set var="previousEntryDate" value="${null}"/>
                <c:forEach var="articleList" items="${articlesWithTagList}">
                    <tmc:archive articleBean="${articleList}" previousDate="${previousEntryDate}"/>
                    <c:set var="previousEntryDate" value="${articleList.createdDate}"/>
                </c:forEach>
            </c:when>
            <%-- No article(s) posted under tag --%>
            <c:otherwise>
                <p>Nothing has been posted under that tag. Please choose from the list below:</p>
                <c:set var="allTagsList" value="${TagProcessBean.allTags}"/>
                <c:choose>
                    <%-- No tags posted yet --%>
                    <c:when test="${empty allTagsList}">
                        <p>There are no tags currently posted.</p>
                    </c:when>
                    <%-- Show all the tags on the site --%>
                    <c:otherwise>
                        <div class="tagCloud">
                            <tmc:allTags tagsList="${allTagsList}"/>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
        <p><a href="/blog/tags">Back to tags.</a></p>
    </c:when>
    <%-- No tag supplied, display all the tags used on the site --%>
    <c:otherwise>
        <c:set var="allTagsList" value="${TagProcessBean.allTags}"/>
        <c:choose>
            <c:when test="${empty allTagsList}">
                <p>There are no tags currently posted.</p>
            </c:when>
            <c:otherwise>
                <div class="tagCloud">
                    <tmc:allTags tagsList="${allTagsList}"/>
                </div>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>