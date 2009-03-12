<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<jsp:useBean id="linkFormAction" class="com.themetacity.beans.LinkProcessBean">
    <jsp:setProperty name="linkFormAction" property="descText" value="${param.descText}"/>
    <jsp:setProperty name="linkFormAction" property="linkURL" value="${param.linkURL}"/>
    <jsp:setProperty name="linkFormAction" property="linkID" value="${param.linkID}"/>
</jsp:useBean>

<c:if test="${not empty param.submit}">
    <c:choose>
        <c:when test="${param.submit == 'newlink'}">
            <c:choose>
                <c:when test="${linkFormAction.addLink == '1'}">
                    <p class="success">New link added successfully.</p>
                </c:when>
                <c:otherwise>
                    <p class="failure">New link NOT added successfully.</p>
                </c:otherwise>
            </c:choose>
        </c:when>

        <c:when test="${param.submit == 'delete'}">
            <c:choose>
                <c:when test="${linkFormAction.deleteLink == '1'}">
                    <p class="success">Successfully deleted link.</p>
                </c:when>
                <c:otherwise>
                    <p class="failure">Did NOT successfully delete link.</p>
                </c:otherwise>
            </c:choose>
        </c:when>

        <c:when test="${param.submit == 'update'}">
            <c:choose>
                <c:when test="${linkFormAction.updateLink == '1'}">
                    <p class="success">Successfully updated link.</p>
                </c:when>
                <c:otherwise>
                    <p class="failure">Did NOT successfully update link.</p>
                </c:otherwise>
            </c:choose>
        </c:when>
    </c:choose>
</c:if>

<p>Add new link:</p>

<form action="links.jsp" method="post">
    Title: <input type="text" name="descText"/><br/>
    Link: <input type="text" name="linkURL"/><br/>
    <button type="submit" name="submit" value="newlink" class="submitbutton"><img src="siteimages/tick.png" alt="Accept and submit"/>Submit!</button>
    <button type="reset" class="reloadbutton"><img src="siteimages/redo.png" alt="Clear the form"/>Clear!</button>
</form>
<br />
<div>
    <c:forEach var="listOfLinks" items="${linkFormAction.allLinks}">
        <form action="links.jsp" method="post">
            <tmc:adminLinks linkBean="${listOfLinks}"/>
        </form>
    </c:forEach>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>