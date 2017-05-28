<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>User Registration Form</title>
    <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"/>
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css"
          href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.css"/>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>


    <script>
        $(function(){
            var $form = $('form');
            $form.submit(function(){
                var $select = $form.find('select[name="userProfiles"]');
                var disabled = $select.attr('disabled');
                console.log(disabled);
                if (typeof disabled !== typeof undefined && disabled !== false) {
                    $select.removeAttr('disabled');
                }

            });
        });
    </script>

</head>

<body>
<div class="generic-container">
    <%@include file="authheader.jsp" %>

    <div class="well lead">User Registration Form</div>
    <form:form method="POST" modelAttribute="user" class="form-horizontal">
        <form:input type="hidden" path="id" id="id"/>

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="firstName">First Name</label>
                <div class="col-md-7">
                    <form:input type="text" path="firstName" id="firstName" class="form-control input-sm"/>
                    <div class="has-error">
                        <form:errors path="firstName" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="lastName">Last Name</label>
                <div class="col-md-7">
                    <form:input type="text" path="lastName" id="lastName" class="form-control input-sm"/>
                    <div class="has-error">
                        <form:errors path="lastName" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="ssoId">SSO ID</label>
                <div class="col-md-7">
                    <c:choose>
                        <c:when test="${edit}">
                            <form:input type="text" path="ssoId" id="ssoId" class="form-control input-sm"
                                        disabled="true"/>
                        </c:when>
                        <c:otherwise>
                            <form:input type="text" path="ssoId" id="ssoId" class="form-control input-sm"/>
                            <div class="has-error">
                                <form:errors path="ssoId" class="help-inline"/>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="password">Password</label>
                <div class="col-md-7">
                    <form:input type="password" path="password" id="password" class="form-control input-sm"/>
                    <div class="has-error">
                        <form:errors path="password" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="email">Email</label>
                <div class="col-md-7">
                    <form:input type="text" path="email" id="email" class="form-control input-sm"/>
                    <div class="has-error">
                        <form:errors path="email" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>

        <%--<sec:authentication var="principal" property="principal" />
        <c:if test="${fn:contains(principal.authorities, 'ROLE_ADMIN')}">--%>
        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="userProfiles">Roles</label>
                <div class="col-md-7">
                    <sec:authentication var="principal" property="principal" />
                    <form:select path="userProfiles" items="${roles}" multiple="false" itemValue="id" itemLabel="type"
                                 class="form-control input-sm" disabled="${fn:contains(principal.authorities, 'ROLE_DBA')}"/>
                    <div class="has-error">
                        <form:errors path="userProfiles" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="state">State</label>
                <div class="col-md-7">
                    <form:select path="state" items="${states}" multiple="false" class="form-control input-sm"/>
                    <div class="has-error">
                        <form:errors path="state" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-actions floatRight">
                <c:choose>
                    <c:when test="${edit}">
                        <button type="submit" class="btn btn-primary btn-sm">
                            <i class="fa fa-refresh" aria-hidden="true"></i>&nbsp; Update
                        </button>
                        or <a href="<c:url value='/list' />">Cancel</a>
                    </c:when>
                    <c:otherwise>
                        <button type="submit" class="btn btn-primary btn-sm">
                            <i class="glyphicon glyphicon-plus-sign" aria-hidden="true"></i>&nbsp; Register
                        </button>
                        <%--<input type="submit" value="Register" class="btn btn-primary btn-sm"/>--%> or <a
                            href="<c:url value='/list' />">Cancel</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

    </form:form>
</div>
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</body>
</html>