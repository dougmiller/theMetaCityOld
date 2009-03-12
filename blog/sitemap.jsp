<?xml version="1.0" encoding="UTF-8"?>
<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
<%@ page contentType="text/xml" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<jsp:useBean id="ArticleProcessBean" scope="page" class="com.themetacity.beans.ArticleProcessBean"/>
<jsp:useBean id="TagProcessBean" scope="page" class="com.themetacity.beans.TagProcessBean"/>

    <url>
        <loc>http://www.themetacity.com/</loc>
        <lastmod>2005-01-01</lastmod>
        <changefreq>weekly</changefreq>
        <priority>1</priority>
    </url>
<c:forEach var="sitemapArticleEntry" items="${ArticleProcessBean.siteMapArticles}">
<tmc:sitemapArticle article="${sitemapArticleEntry}"/>
</c:forEach>
    <url>
        <loc>http://www.themetacity.com/about</loc>
        <lastmod>2005-01-01</lastmod>
        <changefreq>weekly</changefreq>
        <priority>.7</priority>
    </url>
    <url>
        <loc>http://www.themetacity.com/archive</loc>
        <lastmod>2005-01-01</lastmod>
        <changefreq>weekly</changefreq>
        <priority>.5</priority>
    </url>
    <url>
        <loc>http://www.themetacity.com/tags</loc>
        <lastmod>2005-01-01</lastmod>
        <changefreq>weekly</changefreq>
        <priority>.6</priority>
    </url>
<c:forEach var="sitemapTagEntry" items="${TagProcessBean.siteMapTags}">
<tmc:sitemapTag tag="${sitemapTagEntry}"/>
</c:forEach>
</urlset>
