<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://com.themetacityweb" prefix="tmc" %>

<jsp:useBean id="ArchiveProcessBean" scope="page" class="com.themetacityweb.beans.ArchiveProcessBean">
    <jsp:setProperty name="ArchiveProcessBean" property="year" value="${param.year}"/>
    <jsp:setProperty name="ArchiveProcessBean" property="month" value="${param.month}"/>
    <jsp:setProperty name="ArchiveProcessBean" property="day" value="${param.day}"/>
    <jsp:setProperty name="ArchiveProcessBean" property="title" value="${param.title}"/>
</jsp:useBean>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<c:forEach var="archiveList" items="${ArchiveProcessBean.archive}">
    <tmc:archive archiveEntry="${archiveList}"/>
</c:forEach>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>