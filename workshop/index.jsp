<%@ include file="/WEB-INF/jspf/headertop.jspf" %>
<script type="text/javascript" src="js/searcher.js"></script>
<%@ include file="/WEB-INF/jspf/headerbottom.jspf" %>
<div id="main">
    <jsp:useBean id="workshopProcessBean" class="com.themetacity.beans.WorkshopProcessBean"/>
    <c:set var="workshopEntryList" value="${workshopProcessBean.workshopBlurbs}"/>
    <div id="search">
        <p>Just type to search: </p>
        <input type="text" id="searchinput" class="bgimg"/><br />
        <button id="reset">Reset the search.</button>
    </div>
    <c:choose>
        <c:when test="${empty workshopEntryList}">
            <h2 id="noresults">No projects at the moment. Come back again soon.</h2>
        </c:when>
        <c:otherwise>
            <c:forEach var="workshopEntry" items="${workshopEntryList}">
                <tmc:workshopBlurb workshop="${workshopEntry}"/>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>