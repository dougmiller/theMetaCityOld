<%@ include file="/WEB-INF/jspf/header.jspf" %>

<!-- Update/delete existing links -->

<!-- Add a new link -->

<form action="links.jsp" method="POST">
    Title: <input type="text"/><br />
    Link: <input type="text"/><br />
    <button type="submit" name="submit" value="newlink" class="submitbutton"><img src="siteimages/tick.png" alt="Accept and submit"/>Submit!</button><br />
    <button type="reset" class="reloadbutton"><img src="siteimages/redo.png" alt="Redo the form"/>Clear!</button>
</form>



<%@ include file="/WEB-INF/jspf/footer.jspf" %>