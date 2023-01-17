<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="dependencies.jsp" %>

<div class="container">
    <nav class="navbar navbar-expand-lg" style="min-height: 10vh">
        <div class="container-fluid">
            <h2>Smoorsy</h2>
            <c:if test="${sessionScope.USER != null}">
                <address>
                    <b>${sessionScope.USER.surname} ${sessionScope.USER.name}</b>
                    <br>
                    <code>${sessionScope.USER.email}</code>
                    <br>
                    <c:forEach var="item" items="${sessionScope['USER-roles']}">
                        ${item.role}
                    </c:forEach>
                    <br>
                    <form action="${pageContext.request.contextPath}/logout" method="post">
                        <button type="submit" class="btn btn-primary">Выйти</button>
                    </form>
                </address>
            </c:if>
        </div>
    </nav>
</div>
<c:if test="${sessionScope.USER != null}">
    <nav class="navbar sticky-top bg-primary" data-bs-theme="dark"
         style="min-height: 3vh; display: flex; justify-content: center; align-content: center;">
        <!-- Navbar content -->
        <ul class="nav justify-content-center nav-pills">
            <li class="nav-item">
                <a class="nav-link ${requestScope['jakarta.servlet.forward.request_uri'] == '/' ? 'bg-white text-primary' : 'text-white'}"
                   href="${pageContext.request.contextPath}/">Главная</a>
            </li>
            <c:forEach var="item" items="${sessionScope['USER-roles']}">
                <c:if test="${item.role == 'DEVELOPER'}">
                    <li class="nav-item">
                        <a class="nav-link ${requestScope['jakarta.servlet.forward.request_uri'] == '/departments' ? 'bg-white text-primary' : 'text-white'}"
                           href="${pageContext.request.contextPath}/departments">Департаменты</a>
                    </li>
                </c:if>
            </c:forEach>
        </ul>
    </nav>
</c:if>