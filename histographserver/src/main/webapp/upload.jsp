<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    <%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Histograph Image Upload</title>
</head>
<body>
<form action="<%= blobstoreService.createUploadUrl("/uploadimages") %>" method="post" enctype="multipart/form-data">
            <label>Year</label>
            <input type="text" name="Year" title="Year">
            <label>Lat</label>
            <input type="text" name="Lat" title="Lat">
            <label>Long</label>
            <input type="text" name="Long" title="Long">
            <label>Direction</label>
            <input type="text" name="Direction" title="Direction">
            <label>User No.</label>
            <input type="text" name="UserNo" title="UserNo">
            <label>Description</label>
            <input type="text" name="Description" title="Description">
            <input type="file" name="imgUpload"> <!--  This will be the name of the param, gotten in the servlet that handles the blobstore redirect -->
            <input type="submit" value="Submit">
        </form>
</body>
</html>