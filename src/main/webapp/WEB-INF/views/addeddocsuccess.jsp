<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Document Success Page</title>
    <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>
<body>
<div class="generic-container">
    <%@include file="authheader.jsp" %>

    <div class="alert alert-success lead">
        ${successDoc}
    </div>

    <span class="well pull-left">
            <a href="<c:url value='/downloadPDF-${ssoId}-${docId}' />">Click here to download your document</a>
        </span>
    <span class="well pull-right">
            Go to <a href="<c:url value='/list' />">Users List</a>
        </span>


</div>
</body>

</html>