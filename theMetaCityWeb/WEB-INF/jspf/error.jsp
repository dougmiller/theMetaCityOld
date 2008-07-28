<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <meta http-equiv="Content-Type" content="application/xhtml+xml; charset=iso-8859-1"/>
    <link href="/styles/style.css" rel="stylesheet" type="text/css" media="screen"/>
    <title>theMetaCity.com</title>
</head>

<body>

<div id="header">
    <div>
        <div class="headerright">
            <ul>
                <li>Usability</li>
                <li>HTML/CSS</li>
                <li>Java</li>
                <li>Python</li>
                <li>Joomla</li>
                <li>PHP support</li>
            </ul>
        </div>
        <div class="headerleft">
            <span class="bannerTitle"><a href="/">The MetaCity.com</a></span><br/>
            <span class="bannerSubTitle">portfolio of Douglas Miller, Interaction Designer</span>
        </div>
    </div>
</div>

<div id="container">
    <!-- The right nav div -->
    <div id="sidebar">
        <!-- About box -->
        <div>
            <h2><a href="/about">About</a></h2>
        </div>

        <!-- The projects div -->
        <div>
            <h2>Projects</h2>
        </div>

        <!-- The Cool Links (tm) div -->
        <div>
            <h2>Cool Links</h2>
        </div>

        <!-- Site navigation etc -->
        <div>
            <h2>Site</h2>
            <ul>
                <li><a href="/">Home</a></li>
                <li><a href="/about">About</a></li>
                <li><a href="/tags">Tags (New)</a></li>
                <li><a href="/archive">Archive</a></li>
            </ul>
        </div>
        <!-- End of the sidebar -->
    </div>

    <!-- The main content div -->
    <div id="maincontent">
        <h2><c:out value="${param.error}"/></h2>

        <c:choose>

        <c:when test="${empty param.error}">
            <c:redirect url="/"/>
        </c:when>

        <c:when test="${param.error == 404}">
        <p>The file you were looking for has not been found.</p>
        </c:when>

        <c:when test="${param.error == 500}">
        <p>There has been an internal server error.</p>
        </c:when>

        <c:when test="${param.error == 403}">
        <p>This resources has been dened.</p>
        </c:when>

        <c:otherwise>
        <p>An error occured.</p>
        </c:otherwise>

        </c:choose>

<jsp:include page="footer.jspf"/>