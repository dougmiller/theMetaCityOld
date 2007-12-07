<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<jsp:useBean id="TagSearchProcessBean" scope="page" class="com.themetacity.beans.TagSearchProcessBean">
    <jsp:setProperty name="TagSearchProcessBean" property="tag" value="${param.tag}"/>
</jsp:useBean>

<%@ include file="/WEB-INF/jspf/header.jspf" %>
<!-- Display all the tags used on the site -->

<c:set var="resultList" value="${TagSearchProcessBean.articles}" scope="page"/>

<c:choose>
    <%-- Check if a tag argument was given --%>
    <c:when test="${not empty resultList}">
        <p>These are the articles posted under the tag: ${param.tag}</p>
        <c:forEach var="finalTagList" items="${resultList}">
            <tmc:archive archiveEntry="${finalTagList}"/>
        </c:forEach>
    </c:when>

    <%-- If no tag argument then show all he tags --%>
    <c:otherwise>
        <c:if test="${not empty param.tag}">
            <p>Nothing has been posted under that tag. Please choose from the list below.</p>
        </c:if>
        <jsp:useBean id="ShowAllTagsBean" scope="page" class="com.themetacity.beans.TagProcessBean"/>
        <tmc:allTags tagsList="${ShowAllTagsBean.tags}"/>
    </c:otherwise>
</c:choose>


<jsp:include page="/WEB-INF/jspf/footer.jspf"/>