<%@ include file="/WEB-INF/jspf/headertop.jspf" %>
<link href="/workshop/css/projectoverride.css" rel="stylesheet" media="screen, projection" type="text/css"/>
<%@ include file="/WEB-INF/jspf/headerbottom.jspf" %>
<div id="main">
    <jsp:useBean id="workshopProcessBean" class="com.themetacity.beans.WorkshopProcessBean">
        <jsp:setProperty name="workshopProcessBean" property="id" value="${param.id}"/>
    </jsp:useBean>
    <c:set var="workshopEntryList" value="${workshopProcessBean.workshopEntry}"/>

    <c:choose>
        <c:when test="${empty workshopEntryList}">
            <p>No projects at the moment. Check back later</p>
        </c:when>
        <c:otherwise>
            <c:forEach var="workshopEntry" items="${workshopEntryList}">
                <tmc:workshopArticle workshop="${workshopEntry}"/>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>