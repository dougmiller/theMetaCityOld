<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/jspf/headertop.jspf" %>

<jsp:useBean id="workshopProcessBean" class="com.themetacity.beans.WorkshopProcessBean">
    <jsp:setProperty name="workshopProcessBean" property="id" value="${param.id}"/>
</jsp:useBean>
<c:set var="workshopEntryList" value="${workshopProcessBean.workshopEntry}"/>

    <c:choose>
        <c:when test="${empty workshopEntryList}">
                <title>theMetaCity.com Workshop - No project here</title>
            <%@ include file="/WEB-INF/jspf/headerbottom.jspf" %>
            <div id="main">
                <main>
    	    		<p class="ancillaryright">Nothing to be found here.</p>
        </c:when>
        <c:otherwise>
            <c:forEach var="workshopEntry" items="${workshopEntryList}">
                <title>theMetaCity.com Workshop - ${workshopEntry.title}</title>
                <%@ include file="/WEB-INF/jspf/headerbottom.jspf" %>
                <div id="main">
                    <main>
                <tmc:workshopArticle workshop="${workshopEntry}"/>
            </c:forEach>
		</c:otherwise>
	</c:choose>
    </main>
	<p class="ancillaryright"><a href="/workshop">Back to the workshop.</a></p>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
