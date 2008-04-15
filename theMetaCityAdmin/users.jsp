<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://com.themetacity" prefix="tmc" %>

<jsp:useBean id="manageUsersBean" class="com.themetacity.beans.ProfileProcessBean">
    <jsp:setProperty name="manageUsersBean" property="author" value="${loggedInUser}"/>  <%-- Session variable --%>
    <jsp:setProperty name="manageUsersBean" property="password" value="${param.password}"/>    
    <jsp:setProperty name="manageUsersBean" property="contact" value="${param.contact}"/>
    <jsp:setProperty name="manageUsersBean" property="about" value="${param.about}"/>
    <jsp:setProperty name="manageUsersBean" property="pseudonym" value="${param.pseudonym}"/>
</jsp:useBean>

<c:if test="${param.submit == 'update'}">
    <c:set var="updateResult" value="${manageUsersBean.updateProfile}" />
    <c:choose>
        <c:when test="${updateResult == 'true'}">
            <p>Successfully updated profile.</p>
        </c:when>
        <c:otherwise>
            <p>Did NOT successfully update profile.</p>
        </c:otherwise>
    </c:choose>
</c:if>

<c:set var="profilesList" value="${manageUsersBean.profiles}" />

<c:forEach var="profiles" items="${profilesList}">
    <tmc:adminProfile userProfile="${profiles}"/>    
</c:forEach>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>