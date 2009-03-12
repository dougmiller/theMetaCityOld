<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ page contentType="application/json; charset=utf-8" %> 
<c:choose>
    <c:when test="${empty param.title}">
    <p>Please submit a 'title' to check against.</p>
    </c:when>
    <c:otherwise>
        <jsp:useBean id="JSONgetUsedTitle" class="com.themetacity.beans.ArticleProcessBean">
            <jsp:setProperty name="JSONgetUsedTitle" property="title"/>
        </jsp:useBean>
        <c:set var="listOfArticles" value="${JSONgetUsedTitle.matchURLByTitle}"/>
        <json:object>
            <json:array name="articles" var="article" items="${listOfArticles}">
                    <json:object>
                        <json:property name="title" value="${article.title}"/>
                        <json:property name="URL" value="${article.URL}"/>
                    </json:object>
            </json:array>
        </json:object>
    </c:otherwise>
</c:choose>

