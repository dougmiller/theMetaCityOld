<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<jsp:useBean id="importantNoticeFormAction" class="com.themetacity.beans.ImportantNoticeProcessBean">
    <jsp:setProperty name="importantNoticeFormAction" property="author" value="${param.author}"/>
    <jsp:setProperty name="importantNoticeFormAction" property="message" value="${param.message}"/>
    <jsp:setProperty name="importantNoticeFormAction" property="dateFrom" value="${param.dateFrom}"/>
    <jsp:setProperty name="importantNoticeFormAction" property="dateTo" value="${param.dateTo}"/>
    <jsp:setProperty name="importantNoticeFormAction" property="messageID" value="${param.messageID}"/>
</jsp:useBean>

<c:if test="${not empty param.submit}">
    <c:choose>
        <c:when test="${param.submit == 'newmessage'}">
            <c:choose>
                <c:when test="${importantNoticeFormAction.addMessage == '1'}">
                    <p class="success">New message added successfully.</p>
                </c:when>
                <c:otherwise>
                    <p class="failure">New message NOT added successfully.</p>
                </c:otherwise>
            </c:choose>
        </c:when>

        <c:when test="${param.submit == 'delete'}">
            <c:choose>
                <c:when test="${importantNoticeFormAction.deleteMessage == '1'}">
                    <p class="success">Successfully deleted article.</p>
                </c:when>
                <c:otherwise>
                    <p class="failure">Did NOT successfully delete article.</p>
                </c:otherwise>
            </c:choose>
        </c:when>

        <c:when test="${param.submit == 'update'}">
            <c:choose>
                <c:when test="${importantNoticeFormAction.updateMessage == '1'}">
                    <p class="success">Successfully updated message.</p>
                </c:when>
                <c:otherwise>
                    <p class="failure">Did NOT successfully update message.</p>
                </c:otherwise>
            </c:choose>
        </c:when>
    </c:choose>
</c:if>

<c:choose>
    <c:when test="${param.submit != 'edit'}">
        <p>Add new article:</p>

        <form action="importantnotices.jsp" method="post">
            Date from: <input type="text" size="20" name="dateFrom"/><br/>
            Date to: <input type="text" size="20" name="dateTo"/><br/>
            <textarea name="message" cols="65" rows="5"></textarea>
            <input type="hidden" name="author" value="${sessionScope.loggedInUser}"/>
            <button type="submit" name="submit" value="newmessage" class="submitbutton"><img src="siteimages/tick.png"
                                                                                             alt="Accept and submit"/>Submit!
            </button>
            <button type="reset" class="reloadbutton"><img src="siteimages/redo.png" alt="Clear the form"/>Clear!
            </button>
        </form>
    </c:when>
    <c:otherwise>
        <c:forEach var="listOfMessages" items="${importantNoticeFormAction.importantNoticeToEdit}">
            <p>Edit message:</p>

            <form action="importantnotices.jsp" method="post">
                <p>Date from: <input type="text" size="20" name="dateFrom" value="${listOfMessages.dateFrom}"/></p>
                <p>Date to: <input type="text" size="20" name="dateTo" value="${listOfMessages.dateTo}"/></p>
                <textarea name="message" cols="65" rows="5">${listOfMessages.message}</textarea>
                <input type="hidden" name="messageID" value="${listOfMessages.messageID}"/>
                <input type="hidden" name="author" value="${sessionScope.loggedInUser}"/>
                <button type="submit" name="submit" value="update" class="submitbutton"><img src="siteimages/tick.png" alt="Accept and submit"/>Submit!</button>
                <button type="reset" class="reloadbutton"><img src="siteimages/redo.png" alt="Clear the form"/>Clear!</button>
            </form>
        </c:forEach>
    </c:otherwise>
</c:choose>
<br/>

<jsp:useBean id="importantNoticeAll" class="com.themetacity.beans.ImportantNoticeProcessBean"/>

<c:forEach var="listOfMessagesEntry" items="${importantNoticeAll.allImportantNotices}">
    <form action="importantnotices.jsp" method="post">
        <tmc:adminNotices noticeBean="${listOfMessagesEntry}"/>
    </form>
</c:forEach>


<%@ include file="/WEB-INF/jspf/footer.jspf" %>