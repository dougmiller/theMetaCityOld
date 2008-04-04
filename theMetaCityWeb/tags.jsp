<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>


<%@ include file="/WEB-INF/jspf/header.jspf" %>
<!-- Display all the tags used on the site -->
<jsp:useBean id="TagProcessBean" scope="page" class="com.themetacity.beans.TagProcessBean"/>

<c:choose>
    <c:when test="${not empty param.tag}">
        <jsp:useBean id="ArticleProcessBean" scope="page" class="com.themetacity.beans.ArticleProcessBean">
            <jsp:setProperty name="ArticleProcessBean" property="tag" value="${param.tag}"/>
        </jsp:useBean>

        <c:set var="resultList" value="${ArticleProcessBean.articlesWithTag}" scope="page"/>
        <p>These are the articles posted under the tag: ${param.tag}</p>

        <c:choose>
            <c:when test="${not empty resultList}">

                <c:forEach var="articleList" items="${resultList}">
                    <tmc:archive articleBean="${articleList}"/>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p>Nothing has been posted under that tag. Please choose from the list below:</p>
                <c:set var="allTagsList" value="${TagProcessBean.allTags}" scope="page"/>
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
    </c:when>
    <c:otherwise>

        <c:set var="allTagsList" value="${TagProcessBean.allTags}" scope="page"/>
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