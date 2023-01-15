<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Redirect Demo</title>
</head>
<body>
<p>Redirect Demo Example</p>
<p>Attribute from Model during the redirect (request parameters): ${param.testParam}</p>
<p>Attribute from Model during the forwarding (request attributes): ${requestScope.testParam}</p>
</body>
</html>