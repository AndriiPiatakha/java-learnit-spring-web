<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Static Resources Demo</title>

<c:url value="/css/style.css" var="css" />
<c:url value="/js/script.js" var="js" />
<link rel="stylesheet" href="${css}">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script type="text/javascript" src="${js}"></script>
</head>
<body>

<div class="text">This file loads .css and JS</div>
<button>Click Me</button>

</body>
</html>