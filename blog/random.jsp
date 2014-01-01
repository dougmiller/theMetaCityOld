<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<jsp:useBean id="ArticleProcessBean" scope="page" class="com.themetacity.beans.ArticleProcessBean"/>

<c:set var="randomArticle" value="${ArticleProcessBean.randomArticle}"/>

<%@ include file="/WEB-INF/jspf/headertop.jspf" %>
    <title>theMetaCity.com Blog - Random</title>
<%@ include file="/WEB-INF/jspf/headerbottom.jspf" %>

<tmc:article article="${randomArticle}"/>

<p>Want more? <a href="/blog/random">Get a new random article here</a> and <a href="/blog/archive">check out the archive here.</a></p>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>
