<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<sql:query var="picResults" >
    SELECT id, title, date FROM picture ORDER BY id ASC
</sql:query>

<jsp:include page="/WEB-INF/jspf/header.jspf" />

<c:forEach items="picResults.row" var="picRow">
    <tmc:archive title="${picRow.title}" picID="${picRow.id}" date="${picRow.date}" />
</c:forEach>

<jsp:include page="/WEB-INF/jspf/footer.jspf" />