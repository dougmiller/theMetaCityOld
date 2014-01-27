<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/jspf/headertop.jspf" %>
    <title>theMetaCity.com Workshop</title>
<%@ include file="/WEB-INF/jspf/headerbottom.jspf" %>

    <jsp:useBean id="workshopProcessBean" class="com.themetacity.beans.WorkshopProcessBean"/>
    <c:set var="workshopEntryList" value="${workshopProcessBean.workshopBlurbs}"/>
    <div id="workshopsearch">
		<input type="text" id="searchinput" placeholder="Type to search the workshop"/>
		<button id="reset">Reset</button>
    </div>
	<main id="workshopBlurbEntries">
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
	</main>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
