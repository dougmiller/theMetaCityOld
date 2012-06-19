<%@ include file="/WEB-INF/jspf/headertop.jspf" %>
<script type="text/javascript" src="/media/js/searcher.js"></script>
<%@ include file="/WEB-INF/jspf/headerbottom.jspf" %>

<div id="main">
    <jsp:useBean id="workshopProcessBean" class="com.themetacity.beans.WorkshopProcessBean"/>
    <c:set var="workshopEntryList" value="${workshopProcessBean.workshopBlurbs}"/>

		<input type="text" id="searchinput" placeholder="Type to search the workshop"/>
		<button id="reset">Reset</button>
		<br />
	<div id="entries">
	<c:choose>
        <c:when test="${empty workshopEntryList}">
            <h2 id="noentries">No projects at the moment. Come back again soon.</h2>
        </c:when>
        <c:otherwise>
            <c:forEach var="workshopEntry" items="${workshopEntryList}">
                <tmc:workshopBlurb workshop="${workshopEntry}"/>
            </c:forEach>

    		<h2 id="noresults">No matches. Please try again.</h2>
        </c:otherwise>
    </c:choose>
	</div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
