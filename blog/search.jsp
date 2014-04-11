<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<form action="https://www.google.com/search">
    <div class="searchdiv">
        <input type="text" size="14" name="q" id="q"/>
        <button type="submit" name="submit" value="search" class="submitbutton">Search!</button><br />
        <label for="blog">blog</label>
        <input type="radio" name="sitesearch" value="themetacity.com/blog" id="blog" checked/>
        <label for="workshop">workshop</label>
        <input type="radio" name="sitesearch" value="themetacity.com/workshop" id="workshop"/>
        <label for="everything">everything</label>
        <input type="radio" name="sitesearch" value="themetacity.com/" id="everything"/>
    </div>
</form>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
