<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<jsp:useBean id="ArticleProcessBean" scope="page" class="com.themetacity.beans.ArticleProcessBean"/>

<c:set var="randomArticle" value="${ArticleProcessBean.randomArticle}"/>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<tmc:article article="${randomArticle}"/>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>
