<%@ include file="/WEB-INF/jspf/headertop.jspf" %>
<%@ include file="/WEB-INF/jspf/headerbottom.jspf" %>
<div id="main">
    <jsp:useBean id="workshopProcessBean" class="com.themetacity.beans.WorkshopProcessBean">
        <jsp:setProperty name="workshopProcessBean" property="id" value="${param.id}"/>
    </jsp:useBean>
    <c:set var="workshopEntryList" value="${workshopProcessBean.workshopEntry}"/>
    <main>
    <c:choose>
        <c:when test="${empty workshopEntryList}">
			<p class="ancillaryright">Nothing to be found here.</p>
        </c:when>
        <c:otherwise>
            <c:forEach var="workshopEntry" items="${workshopEntryList}">
                <tmc:workshopArticle workshop="${workshopEntry}"/>
            </c:forEach>
		</c:otherwise>
	</c:choose>
    </main>
	<p class="ancillaryright"><a href="/workshop">Back to the workshop.</a></p>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
