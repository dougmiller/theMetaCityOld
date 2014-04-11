<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<form action="https://www.google.com/search">
    <div class="searchdiv">
        <input type="text" name="q" placeholder="Search:"/>
        <input type="submit" name="submit" value="search"/><br>
        <div>
            <input type="radio" name="sitesearch" value="themetacity.com/blog" id="blog" checked/>
            <label for="blog">blog</label>
        </div>
        <div>
            <input type="radio" name="sitesearch" value="themetacity.com/workshop" id="workshop"/>
            <label for="workshop">workshop</label>
        </div>
        <div>
            <input type="radio" name="sitesearch" value="themetacity.com/" id="everything"/>
            <label for="everything">everything</label>
        </div>
    </div>
</form>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
