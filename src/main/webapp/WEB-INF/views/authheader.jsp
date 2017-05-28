<%--
  Created by IntelliJ IDEA.
  User: vmuresanu
  Date: 4/10/2017
  Time: 13:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=ISO-8859-1" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div class="authbar">
        <span>Dear <strong>${loggedinuser}</strong>, Welcome to OurSite!.</span>
        <span class="floatRight"><a class="btn btn-primary btn-sm" href="<c:url value="/logout" />">
            <i class="fa fa-power-off" aria-hidden="true"></i>&nbsp; Logout</a></span>
    </div>
</body>
</html>
