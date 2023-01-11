<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="dependencies.jsp" %>

<c:if test="${sessionScope.user == null}">
    <div class="container">
        <nav class="navbar navbar-expand-lg" style="min-height: 10vh">
            <div class="container-fluid">
                <h2>Smoorsy</h2>
                <address>
                    <b>Костыли принадлежат</b>
                    <br>
                    <code style="color: black">+380665431860</code>
                    <br>
                    <code style="color: black">kain.vadim50@gmail.com</code>
                </address>
            </div>
        </nav>
    </div>
    <nav class="navbar bg-primary" data-bs-theme="dark" style="height: 3vh">
        <!-- Navbar content -->
    </nav>
</c:if>