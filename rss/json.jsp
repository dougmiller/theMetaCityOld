<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<jsp:useBean id="ArticleProcessBean" scope="page" class="com.themetacity.beans.ArticleProcessBean">
    <jsp:setProperty name="ArticleProcessBean" property="year" value="${param.year}"/>
    <jsp:setProperty name="ArticleProcessBean" property="month" value="${param.month}"/>
    <jsp:setProperty name="ArticleProcessBean" property="day" value="${param.day}"/>
    <jsp:setProperty name="ArticleProcessBean" property="URL" value="${param.url}"/>
</jsp:useBean>

<c:set var="listOfArticles" value="${ArticleProcessBean.filteredArticles}"/>

<%@ page contentType="application/json; charset=utf-8" %>

<json:object name="channel">
    <json:property name="title" value="The Metacity.com"/>
    <json:property name="link" value="Http://www.themetacity.com/"/>
    <json:property name="description" value="Interaction design and the "/>
    <json:property name="language" value="en-au"/>
    <json:array name="articles" var="articleEntry" items="${listOfArticles}">
        <json:object>
            <json:property name="title" value="${articleEntry.title}"/>
            <json:property name="link" value="${articleEntry.URL}"/>
            <json:property name="descrition" value="Some sample text here"/>
            <json:property name="pubDate" value="${articleEntry.dateTime}"/>
            <json:property name="author" value="${articleEntry.author}"/>
            <json:array name="articletags" var="articleTags" items="${articleEntry.tags}">
                <json:property name="tag" value="${articleTags.tag}"/>
            </json:array>
        </json:object>
    </json:array>
</json:object>
