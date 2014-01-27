<?xml version="1.0" encoding="UTF-8"?>
<%@ page contentType="text/xml; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="d" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<jsp:useBean id="BlogLatestUpdateProcessBean" scope="page" class="com.themetacity.beans.ArticleProcessBean"/>
<c:set var="blogUpdateDate" value="${BlogLatestUpdateProcessBean.lastUpdateDate}"/>

<jsp:useBean id="WorkshopLatestUpdateProcessBean" scope="page" class="com.themetacity.beans.WorkshopProcessBean"/>
<c:set var="workshopUpdateDate" value="${WorkshopLatestUpdateProcessBean.lastUpdateDate}"/>

<sitemapindex xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
    <sitemap>
       <loc>http://www.themetacity.com/blog/sitemap.xml</loc>
       <lastmod><d:formatDate value="${blogUpdateDate}" pattern="yyyy-MM-dd"/></lastmod>
    </sitemap>

    <sitemap>
       <loc>http://www.themetacity.com/workshop/sitemap.xml</loc>
       <lastmod><d:formatDate value="${workshopUpdateDate}" pattern="yyyy-MM-dd"/></lastmod>
    </sitemap>
</sitemapindex>
