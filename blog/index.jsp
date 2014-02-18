<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<jsp:useBean id="ArticleProcessBean" scope="page" class="com.themetacity.beans.ArticleProcessBean">
    <jsp:setProperty name="ArticleProcessBean" property="year" value="${param.year}"/>
    <jsp:setProperty name="ArticleProcessBean" property="month" value="${param.month}"/>
    <jsp:setProperty name="ArticleProcessBean" property="day" value="${param.day}"/>
    <jsp:setProperty name="ArticleProcessBean" property="URL" value="${param.url}"/>
</jsp:useBean>

<c:choose>
    <c:when test="${empty param.year and empty param.url}">
        <c:set var="articleResultList" value="${ArticleProcessBean.frontpageArticles}"/>
        <c:set var="showNotices" value="true"/>
        <%@ include file="/WEB-INF/jspf/headertop.jspf" %>
            <title>theMetaCity.com Blog</title>
        <%@ include file="/WEB-INF/jspf/headerbottom.jspf" %>
    </c:when>
    <c:otherwise>
        <c:set var="articleResultList" value="${ArticleProcessBean.filteredArticles}"/>
        <c:set var="showNotices" value="false"/>
        <%@ include file="/WEB-INF/jspf/headertop.jspf" %>   <%-- Clunky as hell --%>
        <title>theMetaCity.com Blog
            <c:out value="${param.year}" />
            <c:out value="${param.month}" />
            <c:out value="${param.day}" />
            <c:out value="${f:replace(param.url,'-',' ')}" />
         </title>
        <%@ include file="/WEB-INF/jspf/headerbottom.jspf" %>
    </c:otherwise>
</c:choose>


<c:if test="${showNotices == true}">
    <jsp:useBean id="ImportantNoticeBean" scope="page" class="com.themetacity.beans.ImportantNoticeProcessBean"/>
    <c:forEach var="noticeBean" items="${ImportantNoticeBean.importantNotices}">
        <tmc:notice importantNotice="${noticeBean}"/>
    </c:forEach>
</c:if>
<c:choose>
    <c:when test="${not empty articleResultList}"> <%-- If there are results --%>
        <c:forEach var="articleList" items="${articleResultList}">
            <tmc:article article="${articleList}"/>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test="${not empty param.year or not empty param.url}"> <%-- There are no results from a search --%>
                <p>There are no articles for this criteria.</p>

                <p>Perhaps <a href="/blog/archive">you could try the archive</a> or <a href="/blog/tags/>">search via category tags</a>.</p>
            </c:when>
            <c:otherwise>  <%-- There is nothing in the database --%>
                <p>There is nothing in here yet.</p>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>


<p id="archivelink">Want more? <a href="/blog/archive">check out the archive here.</a></p>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>
