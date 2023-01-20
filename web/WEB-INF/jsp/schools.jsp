<%--
  Created by IntelliJ IDEA.
  User: kainv
  Date: 20.01.2023
  Time: 12:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<%@include file="templates/header.jsp" %>
<body>
<div class="container-fluid">
    <c:if test="${sessionScope.USER != null}">
    </c:if>
</div>
</body>
</html>
