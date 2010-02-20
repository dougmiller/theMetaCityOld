<?xml version="1.0" encoding="UTF-8"?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title>theMetaCity.com</title>
        <meta http-equiv="content-type" content="application/xhtml+xml;"/>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js"></script>
        <script type="text/javascript" src="/js/dropper.js"></script>
        <link href="/css/style.css" rel="stylesheet" type="text/css" media="screen"/>
    </head>
    <body>
        <div id="container">
            <div id="left" class="contentdiv">
                <object data="site-images/logo.svg" type="image/svg+xml" id="logo">
                    <img alt="The MetaCity Logo" src="site-images/logo.png"/>
                </object>
            </div>
            <div id="right" class="contentdiv">
                <h1>The MetaCity.com</h1>
                <h3><a href="/blog">Blog</a></h3>
                <p>News, events and happenings dealing with the MetaCity and surrounding detritus.</p>
                <h3><a href="/workshop">Workshop</a></h3>
                <p>Where all good ideas go to live. A collection of some of my better projects and achievements.</p>
                <h3 id="abouth3">About</h3>
                <p>What is going on here.</p>
            </div>
        </div>

        <div id="aboutdiv" class="contentdiv">
            <p>My name is Douglas Miller. I make technology easier to use. I design interactions. I write software. Sometimes I make webpages. Other times I make interactive art.</p>
            <p>If you want to contact me <a href="javascript:location='mailto:\u0064\u006f\u0075\u0067\u006d\u0069\u006c\u006c\u0065\u0072\u0040\u0074\u0068\u0065\u006d\u0065\u0074\u0061\u0063\u0069\u0074\u0079\u002e\u0063\u006f\u006d';void 0">you can do it at here.</a></p>
            <p id="aboutclose">Close</p>
        </div>
        
        <!-- This div should be hidden if you have javascript enabled. -->
        <div id="nojavascript" class="contentdiv">Hi there. It looks like you might not have javascript running. That is OK, but somethings may not work for you on this site.</div>
    </body>
</html>
