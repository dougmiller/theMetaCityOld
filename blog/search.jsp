<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<jsp:useBean id="ArticleProcessBean" scope="page" class="com.themetacity.beans.ArticleProcessBean">
    <jsp:setProperty name="ArticleProcessBean" property="searchString" value="${param.searchString}"/>
</jsp:useBean>

<%@ include file="/WEB-INF/jspf/headertop.jspf" %>   <%-- Clunky as hell --%>
<title>theMetaCity.com Search
    <c:out value="${param.searchString}" />
</title>

<form action="search.jsp">
    <input type="text" size="50" name="searchString" value="${param.searchString}"/>
    <button type="submit" name="submit" value="search" class="submitbutton"><img src="siteimages/tick.png" alt="Search!"/>Submit!</button>
</form>

<c:if test="${not empty param.searchString}">
    <c:forEach var="searchResults" items="${ArticleProcessBean.searchArticles}">
        <c:out value="${searchResults.title}" /><br />
    </c:forEach>

    <form action="search.jsp">
        <input type="text" size="50" name="searchString" value="${param.searchString}"/>
        <button type="submit" name="submit" value="search" class="submitbutton"><img src="siteimages/tick.png" alt="Search!"/>Submit!</button>
    </form>
</c:if>

<form action="https://www.google.com/search">
    <div class="searchdiv">
        <input type="text" size="14" name="q" id="q"/>
        <button type="submit" name="submit" value="search" class="submitbutton">Search!</button><br />
        <label for="blog">blog</label>
        <input type="radio" name="sitesearch" value="themetacity.com/blog" id="blog" checked/>
        <label for="workshop">workshop</label>
        <input type="radio" name="sitesearch" value="themetacity.com/workshop" id="workshop"/>
        <label for="everything">everything</label>
        <input type="radio" name="sitesearch" value="themetacity.com/" id="everything"/>
    </div>
</form>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
