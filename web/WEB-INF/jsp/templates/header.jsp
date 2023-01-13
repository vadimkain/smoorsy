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
                    <form action="${pageContext.request.contextPath}/logout" method="post">
                        <button type="submit" class="btn btn-primary">Выйти</button>
                    </form>
                </address>
            </c:if>
        </div>
    </nav>
</div>
<nav class="navbar bg-primary sticky-top" data-bs-theme="dark" style="min-height: 3vh">
    <!-- Navbar content -->
</nav>