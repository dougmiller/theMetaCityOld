<%@ include file="/WEB-INF/jspf/headertop.jspf" %>
<script type="text/javascript" src="js/searcher.js"></script>
<%@ include file="/WEB-INF/jspf/headerbottom.jspf" %>
<div id="main">
    <jsp:useBean id="workshopProcessBean" class="com.themetacity.beans.WorkshopProcessBean"/>
    <c:set var="workshopEntryList" value="${workshopProcessBean.workshopBlurbs}"/>


    <div id="header">Workshop</div>
    <div id="search">
        <input type="text" id="searchinput"/><br />
        <button id="reset">Reset the search.</button>
    </div>
    <c:choose>
        <c:when test="${empty workshopEntryList}">
            <p>No projects at the moment. Check back later</p>
        </c:when>
        <c:otherwise>
            <c:forEach var="workshopEntry" items="${workshopEntryList}">
                <tmc:workshopBlurb workshop="${workshopEntry}"/>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>