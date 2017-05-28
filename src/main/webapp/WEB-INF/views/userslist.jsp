<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Users List</title>
    <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"/>
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css"
          href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.css"/>
</head>

<body>
<div class="generic-container">
    <%@include file="authheader.jsp" %>
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">List of Users </span></div>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Firstname</th>
                <th>Lastname</th>
                <th>Email</th>
                <th>SSO ID</th>
                <sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
                    <th>Role</th>
                </sec:authorize>
                <sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
                    <th>State</th>
                </sec:authorize>
                <th width="100"></th>
                <sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
                    <th width="100"></th>
                </sec:authorize>
                <sec:authorize access="hasRole('ADMIN')">
                    <th width="100"></th>
                </sec:authorize>

            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.email}</td>
                    <td>${user.ssoId}</td>
                    <c:forEach items="${user.userProfiles}" var="profile">
                        <sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
                            <td>${profile.type}</td>
                        </sec:authorize>
                    </c:forEach>
                    <sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
                        <c:choose>
                            <c:when test="${fn:contains(user.state, 'INACTIVE')}">
                                <td><i class="fa fa-lock"></i></td>
                            </c:when>
                            <c:otherwise>
                                <td><i class="fa fa-unlock-alt"></i></td>
                            </c:otherwise>
                        </c:choose>

                    </sec:authorize>

                    <td><a href="<c:url value='/file/add-file-${user.id}' />" class="btn btn-warning custom-width" >
                        <i class="fa fa-eye"></i>&nbsp; View</a></td>

                    <sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
                        <sec:authentication var="principal" property="principal" />
                        <c:if test="${fn:contains(principal.authorities, 'ROLE_DBA')}">
                            <c:choose>
                                <c:when test="${fn:contains(user.userProfiles, 'USER')}">
                                    <td><a href="<c:url value='/edit-user-${user.ssoId}' />" class="btn btn-success custom-width">
                                        <i class="fa fa-pencil"></i>&nbsp; Edit</a></td>
                                </c:when>
                                <c:otherwise>
                                    <td><a href="<c:url value='/edit-user-${user.ssoId}' />" class="btn btn-success custom-width" disabled="true">
                                        <i class="fa fa-pencil"></i>&nbsp; Edit</a></td>
                                </c:otherwise>
                            </c:choose>
                        </c:if>

                        <c:if test="${fn:contains(principal.authorities, 'ROLE_ADMIN')}">
                            <td><a href="<c:url value='/edit-user-${user.ssoId}' />" class="btn btn-success custom-width">
                                <i class="fa fa-pencil"></i>&nbsp; Edit</a></td>
                        </c:if>

                    </sec:authorize>
                    <sec:authorize access="hasRole('ADMIN')">
                        <td><a href="<c:url value='/delete-user-${user.ssoId}' />" class="btn btn-danger custom-width">
                            <i class="fa fa-trash"></i>&nbsp; Delete</a></td>
                    </sec:authorize>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <sec:authorize access="hasRole('ADMIN')">
        <div class="well">
            <a href="<c:url value='/newuser' />">Add New User</a>
        </div>
    </sec:authorize>
    <sec:authorize access="hasRole('USER')">
        <div class="well">
            <a href="<c:url value='/newDoc-${pageContext.request.userPrincipal.name}' />">Add New Entry</a>
        </div>
    </sec:authorize>

</div>

</body>
</html>