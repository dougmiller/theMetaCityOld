<?xml version="1.0" encoding="UTF-8"?>
<%@ page contentType="application/xml; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="d" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
    <jsp:useBean id="ArticleLatestUpdateProcessBean" class="com.themetacity.beans.ArticleProcessBean"/>
    <c:set var="articleUpdateDate" value="${ArticleLatestUpdateProcessBean.lastUpdateDate}"/>

    <jsp:useBean id="ArticleProcessBean" class="com.themetacity.beans.ArticleProcessBean"/>
    <c:set var="sitemapArticles" value="${ArticleProcessBean.sitemapArticles}"/>

    <jsp:useBean id="TagProcessBean" class="com.themetacity.beans.TagProcessBean"/>
    <c:set var="sitemapArticleTags" value="${TagProcessBean.sitemapTags}"/>

    <url>
        <loc>https://www.themetacity.com/blog</loc>
        <lastmod><d:formatDate value="${articleUpdateDate}" pattern="yyyy-MM-dd"/></lastmod>
        <changefreq>weekly</changefreq>
        <priority>1</priority>
    </url>
    <c:forEach var="sitemapArticleEntry" items="${sitemapArticles}">
        <tmc:sitemapArticle article="${sitemapArticleEntry}"/>
    </c:forEach>
    <url>
        <loc>https://www.themetacity.com/blog/archive</loc>
        <lastmod><d:formatDate value="${articleUpdateDate}" pattern="yyyy-MM-dd"/></lastmod>
        <changefreq>weekly</changefreq>
        <priority>.5</priority>
    </url>
    <url>
        <loc>https://www.themetacity.com/blog/tags</loc>
        <lastmod><d:formatDate value="${articleUpdateDate}" pattern="yyyy-MM-dd"/></lastmod>
        <changefreq>weekly</changefreq>
        <priority>.6</priority>
    </url>
    <c:forEach var="sitemapTagEntry" items="${sitemapArticleTags}">
        <tmc:sitemapTag tag="${sitemapTagEntry}"/>
    </c:forEach>
</urlset>
