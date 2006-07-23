<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<sql:transaction>
    <sql:query var="viewGreatest">
        SELECT MAX(id) FROM picture
    </sql:query>

    <sql:query var="viewResults">
        SELECT * FROM picture
        WHERE id = ?
        <sql:param value="${viewid}"/>
    </sql:query>
</sql:transaction>

<c:set var="viewid" value="${param.id}" scope="page"/>
<c:set var="greatest" value="${viewGreatest.row.id}" scope="page"/>

<c:if test="${viewid == 'null'}">
    <c:set var="viewid" value="${greatest}" scope="page"/>
</c:if>

<jsp:include page="/WEB-INF/jspf/header.jspf"/>

<tmc:view picID="viewResults.id" imgSrc="viewResults.imgurl" altTag="alttag" maxId="greatest"/>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>

