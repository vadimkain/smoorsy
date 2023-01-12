<%--
  Created by IntelliJ IDEA.
  User: kainv
  Date: 10.01.2023
  Time: 05:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Главная</title>
</head>
<body>
<%@include file="templates/header.jsp" %>
<div class="container">
    <c:if test="${sessionScope.user == null}">
        <%@include file="templates/registration.jsp" %>
    </c:if>
</div>
</body>
</html>
