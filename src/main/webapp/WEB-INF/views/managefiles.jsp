<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: vmuresanu
  Date: 5/1/2017
  Time: 11:09
  To change this template use File | Settings | File Templates.
--%>



<%@ page contentType="text/html; charset=ISO-8859-1" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Upload/Download/Delete Documents</title>
    <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
    <link rel="stylesheet" type="text/css"
          href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.css"/>
</head>
<body>
    <div class="generic-container">
        <%@include file="authheader.jsp" %>
        <div class="panel panel-default">
            <div class="panel-heading"><span class="lead">File list of ${user.ssoId}</span></div>
            <div class="tablecontainer">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>File Name</th>
                            <th>Type</th>
                            <th>Description</th>
                            <th width="100"></th>
                            <th width="100"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${files}" var="file" varStatus="counter">
                        <tr>
                            <td>${counter.index + 1}</td>
                            <td>${file.name}</td>
                            <td>${file.type}</td>
                            <td>${file.description}</td>
                            <td><a href="<c:url value='/file/download-file-${user.id}-${file.id}'/>" class="btn btn-success">
                                <i class="fa fa-download"></i>&nbsp; Download</a></td>
                            <td><a href="<c:url value="/file/delete-file-${user.id}-${file.id}"/>" class="btn btn-danger custom-width">
                                <i class="fa fa-trash"></i>&nbsp; Delete</a> </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="panel panel-default">

            <div class="panel-heading"><span class="lead">Upload New Document</span> </div>
            <div class="uploadcontainer">
                <form:form method="POST" modelAttribute="fileBucket" enctype="multipart/form-data" class="form-horizontal">
                    <div class="row">
                        <div class="form-group col-md-12">
                            <label class="col-md-3 control-label" for="file">Upload a document</label>
                            <div class="col-md-7">
                                <form:input type="file" path="file" id="file" class="form-control input-sm"/>
                                <div class="has-error">
                                    <form:errors path="file" class="help-inline"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-md-12">
                            <label class="col-md-3 control-label" for="description">Description</label>
                            <div class="col-md-7">
                                <form:input type="text" path="description" id="description" class="form-control input-sm"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-actions floatRight">
                            <button type="submit" class="btn btn-primary btn-sm">
                                <i class="fa fa-upload" aria-hidden="true"></i>&nbsp; Upload
                            </button>
                            <%--<input type="submit" value="Upload" class="btn btn-primary btn-sm"/>--%>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
        <div class="well">
            Go to <a href="<c:url value='/list' />">Users List</a>
        </div>
    </div>
</body>
</html>
