<%@ page import="java.util.LinkedList" %>
<%@ page import="java.io.*" %>
<%@ include file="/WEB-INF/jspf/headertop.jspf" %>
<%@ include file="/WEB-INF/jspf/headerbottom.jspf" %>

<h1>Warehouse</h1>
<%
    String root = "/users/nf";
    java.io.File dir = new java.io.File(root);

    File[] listOfDirs = dir.listFiles();

    LinkedList<File> goodDirs = new LinkedList<File>();

    for (File dirToCheck : listOfDirs) {
        if (dirToCheck.isDirectory()) {
            goodDirs.add(dirToCheck);
        }
    }

    if (goodDirs.size() == 0) {
%>
<p>There is nothing here yet.</p>
<%
} else {

    for (File goodDir : goodDirs) {
        java.io.File readme = new java.io.File(goodDir + "/README");

        BufferedReader br = new BufferedReader(new FileReader(readme));

        String strLine;
        String allOfReadme = "";

        while ((strLine = br.readLine()) != null) {
            allOfReadme += strLine;
        }

        br.close();

%>
<div class="entry">
    <div class="image"><img src="wares/<%=goodDir.getName()%>/default.jpg" alt="<%=goodDir.getName()%> default image."/></div>
    <div class="text">
        <h2><a href="wares/<%=goodDir.getName()%>"><%=goodDir.getName()%></a></h2>
        <%=allOfReadme%>
    </div>
</div>
<%
        }
    }
%>


<%@ include file="/WEB-INF/jspf/footer.jspf" %>
