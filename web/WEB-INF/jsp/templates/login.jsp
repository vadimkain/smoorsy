<%--
  Created by IntelliJ IDEA.
  User: kainv
  Date: 12.01.2023
  Time: 23:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<br>
<h1 class="center">Авторизация</h1>
<br>
<form action="${pageContext.request.contextPath}/login" method="post" accept-charset="UTF-8">
    <div class="row">
        <div class="col-lg-12 col-md-12 mb-3">
            <label for="emailLoginId">Email</label>
            <input type="email" class="form-control ${not empty requestScope['invalid.email'] ? 'is-invalid' : ''}"
                   id="emailLoginId" name="email" value="${requestScope['error.email']}" required>
            <c:if test="${not empty requestScope['invalid.email']}">
                <div class="invalid-feedback">${requestScope['invalid.email']}</div>
            </c:if>
            <c:if test="${not empty requestScope['error.email']}">
                <div class="invalid-feedback">Неверно введены данные или отсутствует такой аккаунт</div>
            </c:if>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-12 col-md-12 mb-3">
            <label for="passwordLoginId">Пароль</label>
            <input type="password"
                   class="form-control ${not empty requestScope['invalid.password'] ? 'is-invalid' : ''}"
                   id="passwordLoginId" name="password"
                   required>
            <c:if test="${not empty requestScope['invalid.password']}">
                <div class="invalid-feedback">${requestScope['invalid.password']}</div>
            </c:if>
        </div>
    </div>
    <button class="btn btn-primary" type="submit">Войти</button>
</form>