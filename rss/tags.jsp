<?xml version="1.0" ?>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<jsp:useBean id="ArticleProcessBean" scope="page" class="com.themetacity.beans.ArticleProcessBean">
    <jsp:setProperty name="ArticleProcessBean" property="searchTag" value="${param.tag}"/>
</jsp:useBean>
<c:set var="itemList" value="${ArticleProcessBean.articlesWithTagForRSSFeed}"/>

<%@ page contentType="application/rss+xml; charset=utf-8" %>

<rss version="2.0">
    <channel>
        <title>${ArticleProcessBean.RSSFeedTagTitle}</title>
        <link>${ArticleProcessBean.RSSFeedTagLink}</link>
        <description>News and events from the MetaCity</description>
        <language>en-au</language>
        <docs>http://blogs.law.harvard.edu/tech/rss</docs>
        <webMaster>dougmiller@themetacity.com</webMaster>
        <ttl>4320</ttl>

        <c:forEach var="item" items="${itemList}">
            <tmc:RSSItem article="${item}"/>
        </c:forEach>
    </channel>
</rss>