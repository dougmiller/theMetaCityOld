<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/jspf/headertop.jspf" %>
<script type="text/javascript" src="/js/jquery-1.2.5.min.js"></script>
<script type="text/javascript" src="/js/admin_articles.js"></script>
<%@ include file="/WEB-INF/jspf/headerbottom.jspf" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<jsp:useBean id="articleFormAction" class="com.themetacity.beans.ArticleProcessBean">
    <jsp:setProperty name="articleFormAction" property="author" value="${param.author}"/>
    <jsp:setProperty name="articleFormAction" property="title" value="${param.title}"/>
    <jsp:setProperty name="articleFormAction" property="articleText" value="${param.articleText}"/>
    <jsp:setProperty name="articleFormAction" property="articleID" value="${param.articleID}"/>
    <jsp:setProperty name="articleFormAction" property="articleTags" value="${paramValues.tag}"/>
    <jsp:setProperty name="articleFormAction" property="articleOtherTags" value="${param.articleOtherTags}"/>
</jsp:useBean>
<c:if test="${not empty param.submit}">
    <c:choose>
        <c:when test="${param.submit == 'newarticle'}">
            <c:choose>
                <c:when test="${articleFormAction.addArticle == '1'}">
                    <p class="success">New article added successfully.</p>
                </c:when>
                <c:otherwise>
                    <p class="failure">New article NOT added successfully.</p>
                </c:otherwise>
            </c:choose>
        </c:when>

        <c:when test="${param.submit == 'delete'}">
            <c:choose>
                <c:when test="${articleFormAction.deleteArticle == '1'}">
                    <p class="success">Successfully deleted article.</p>
                </c:when>
                <c:otherwise>
                    <p class="failure">Did NOT successfully delete article.</p>
                </c:otherwise>
            </c:choose>
        </c:when>

        <c:when test="${param.submit == 'update'}">
            <c:choose>
                <c:when test="${articleFormAction.updateArticle == '1'}">
                    <p class="success">Successfully updated article.</p>
                </c:when>
                <c:otherwise>
                    <p class="failure">Did NOT successfully update article.</p>
                </c:otherwise>
            </c:choose>
        </c:when>
    </c:choose>
</c:if>
<jsp:useBean id="tagProcessBean" class="com.themetacity.beans.TagProcessBean"/>
<c:set var="listOfAllTags" value="${tagProcessBean.allTags}"/>
<c:choose>
    <c:when test="${param.submit != 'edit'}">
        <p>Add new article:</p>

        <form action="articles.jsp" method="post">
            <p>Title: <input type="text" size="50" name="title" id="titleinput"/></p>

            <div id="titlecheckbox"><a href="#">sss</a></div>
            <div><textarea name="articleText" cols="65" rows="50"></textarea></div>
            <tmc:adminTags tagsList="${listOfAllTags}"/>
            <p>Other tags:</p>
            <div><input type="text" name="articleOtherTags"/></div>

            <p class="smallnote">Enter a comma delimited list.</p>
            <div><input type="hidden" name="author" value="${sessionScope.loggedInUser}"/></div>
            <div><button type="submit" name="submit" value="newarticle" class="submitbutton"><img src="siteimages/tick.png" alt="Accept and submit"/>Submit!</button></div>
            <div><button type="reset" class="reloadbutton"><img src="siteimages/redo.png" alt="Clear the form"/>Clear!</button></div>
        </form>
    </c:when>
    <c:otherwise>
        <c:forEach var="listOfArticles" items="${articleFormAction.articlesByID}">
            <jsp:useBean id="tagProcessBeanForEdit" class="com.themetacity.beans.TagProcessBean">
                <jsp:setProperty name="tagProcessBeanForEdit" property="articleID" value="${listOfArticles.articleID}"/>
            </jsp:useBean>
            <p>Edit article: ${listOfArticles.title}</p>

            <form action="articles.jsp" method="post">
                <p>Title: <input type="text" name="title" size="50" value="${listOfArticles.title}" id="titleinput"/></p>

                <div id="titlecheckbox"></div>
                <div><textarea name="articleText" cols="65" rows="50">${listOfArticles.articleText}</textarea></div>
                <tmc:adminTags tagsList="${listOfAllTags}" usedTagsList="${tagProcessBeanForEdit.articleTags}"/>
                <p>Other tags:</p>
                <div><input type="text" name="articleOtherTags"/></div>

                <p class="smallnote">Enter a comma delimited list.</p>
                <div><input type="hidden" name="articleID" value="${listOfArticles.articleID}"/></div>
                <div><button type="submit" name="submit" value="update" class="submitbutton"><img src="siteimages/tick.png" alt="Accept and submit"/>Submit!</button></div>
                <div><button type="reset" class="reloadbutton"><img src="siteimages/redo.png" alt="Clear the form"/>Clear!</button></div>
            </form>
        </c:forEach>
    </c:otherwise>
</c:choose>
<br/>

<div>
    <jsp:useBean id="articleAll" class="com.themetacity.beans.ArticleProcessBean"/>


    <c:forEach var="listOfArticles" items="${articleAll.allAdminArticles}">
        <form action="articles.jsp" method="post">
            <tmc:adminArticles articleBean="${listOfArticles}"/>
        </form>
    </c:forEach>


</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>