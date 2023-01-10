<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>L10N & I18N demo</title>
</head>
<body>

<p><spring:message code="random.text"/></p>
<a href="?lang=en"><button><spring:message code="change.lang.btn.en"/></button></a>
<a href="?lang=fr"><button><spring:message code="change.lang.btn.fr"/></button></a>
</body>
</html>