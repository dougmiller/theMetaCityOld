<?xml version="1.0" encoding="UTF-8"?>
<%@ page contentType="text/xml" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="d" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd">
    <jsp:useBean id="WorkshopProcessBean" scope="page" class="com.themetacity.beans.WorkshopProcessBean"/>
    <jsp:useBean id="WorkshopLatestUpdateProcessBean" scope="page" class="com.themetacity.beans.WorkshopProcessBean"/>
    <c:set var="workshopList" value="${WorkshopProcessBean.workshopBlurbs}"/>
    <c:set var="workshopUpdateDate" value="${WorkshopLatestUpdateProcessBean.lastUpdateDate}"/>

    <url>
        <loc>http://www.themetacity.com/workshop</loc>
        <lastmod><d:formatDate value="${workshopUpdateDate}" pattern="yyyy-MM-dd"/></lastmod>
        <changefreq>weekly</changefreq>
        <priority>1</priority>
    </url>
    <c:forEach var="sitemapWorkshopEntry" items="${workshopList}">
        <tmc:sitemapWorkshop workshop="${sitemapWorkshopEntry}"/>
    </c:forEach>
</urlset>