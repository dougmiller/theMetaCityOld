<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<% session.invalidate(); %>
<c:redirect url="index.jsp" />