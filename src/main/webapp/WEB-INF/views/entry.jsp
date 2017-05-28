<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: vmuresanu
  Date: 5/8/2017
  Time: 00:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=ISO-8859-1" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Customer Registration Form</title>
    <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"/>
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css"
          href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.css"/>
</head>
<body>
<c:url var="saveOrUpdateUrl" value="/newDoc-${pageContext.request.userPrincipal.name}"/>
<div class="generic-container">
    <%@include file="authheader.jsp" %>

    <div class="well lead">New Document Form</div>
    <form:form action="${saveOrUpdateUrl}" method="POST" modelAttribute="userDocument" class="form-horizontal">
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
                <label class="col-md-3 control-lable" for="email">Email</label>
                <div class="col-md-7">
                    <form:input type="text" path="email" id="email" class="form-control input-sm"/>
                    <div class="has-error">
                        <form:errors path="email" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="phone">Phone Number</label>
                <div class="col-md-7">
                    <form:input type="text" path="phone" id="phone" class="form-control input-sm"/>
                    <div class="has-error">
                        <form:errors path="phone" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="documentType">Document Type</label>
                <div class="col-md-7">
                    <form:select path="documentType" items="${documents}" multiple="false"
                                 class="form-control input-sm"/>
                    <div class="has-error">
                        <form:errors path="documentType" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="needNotary">Notary</label>
                <div class="input-group input-sm">
                    <div class="checkbox">
                        <label><input type="checkbox" id="needNotary" name="needNotary" value="Yes"> Need
                            Notary?</label>
                        <input type="hidden" id="needNotary" name="needNotary" value="No">
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="startDate">Start Date</label>
                <div class="col-md-7">
                    <form:input type="datetime-local" path="startDate" id="startDate" class="form-control input-sm"/>
                    <div class="has-error">
                        <form:errors path="startDate" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="deadline">Deadline</label>
                <div class="col-md-7">
                    <form:input type="datetime-local" path="deadline" id="deadline" class="form-control input-sm"/>
                    <div class="has-error">
                        <form:errors path="deadline" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>


        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="price">Price</label>
                <div class="col-md-7">
                    <form:input type="text" path="price" id="price" class="form-control input-sm"/>
                    <div class="has-error">
                        <form:errors path="price" class="help-inline"/>
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
                        <input type="submit" value="Register" name="saveBtn" class="btn btn-primary btn-sm"/> or <a
                            href="<c:url value='/list' />">Cancel</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <%--<input name="saveBtn" value="Save" type="submit">--%>
    </form:form>
</div>

<%--<form:form action="${saveOrUpdateUrl}" method="post" modelAttribute="userDocument" id="userDocument">
    <form:input type="text" path="firstName" id="firstName"/>
    <form:input type="text" path="lastName" id="lastName"/>
    <form:input type="text" path="email" id="emailName"/>
    // Other input fields
    <input name="saveBtn" value="Save" type="submit">
</form:form>--%>
</body>
</html>