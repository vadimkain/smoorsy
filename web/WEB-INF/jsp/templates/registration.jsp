<%--
  Created by IntelliJ IDEA.
  User: kainv
  Date: 12.01.2023
  Time: 02:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--        https://getbootstrap.ru/docs/4.4/components/forms/#server-side--%>
<div class="row">
    <div class="col-lg-8">
        <br>
        <h1 class="center">Регистрация</h1>
        <br>
        <form action="/registration" accept-charset="UTF-8" method="post">
            <div class="row">
                <div class="col-lg-4 col-md-12 mb-3">
                    <label for="surnameId">Фамилия</label>
                    <input type="text" class="form-control is-invalid" id="surnameId" name="surname" required>
                    <div class="invalid-feedback">Please choose a username.</div>
                </div>
                <div class="col-lg-4 col-md-12 mb-3">
                    <label for="nameId">Имя</label>
                    <input type="text" class="form-control is-invalid" id="nameId" name="name" required>
                    <div class="invalid-feedback">Please choose a username.</div>
                </div>
                <div class="col-lg-4 col-md-12 mb-3">
                    <label for="patronymicId">Отчество</label>
                    <input type="text" class="form-control is-invalid" id="patronymicId" name="patronymic" required>
                    <div class="invalid-feedback">Please choose a username.</div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-4 col-md-12 mb-3">
                    <label for="birthdayId">Дата рождения</label>
                    <input type="date" class="form-control is-invalid" id="birthdayId" name="birthday" required>
                    <div class="invalid-feedback">Please choose a username.</div>
                </div>
                <div class="col-lg-8 col-md-12 mb-3">
                    <label for="emailId">Email</label>
                    <input type="email" class="form-control is-invalid" id="emailId" name="email" required>
                    <div class="invalid-feedback">Please choose a username.</div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12 col-md-12 mb-3">
                    <label for="passwordId">Пароль</label>
                    <input type="password" class="form-control is-invalid" id="passwordId" name="password" required>
                    <div class="invalid-feedback">Please choose a username.</div>
                </div>
            </div>
            <button class="btn btn-primary" type="submit">Зарегистрироваться</button>
        </form>

    </div>
    <div class="col-lg-4">
        <br>
        <h1 class="center">Авторизация</h1>
        <br>
        <form action="/login">
            <div class="row">
                <div class="col-lg-12 col-md-12 mb-3">
                    <label for="emailLoginId">Email</label>
                    <input type="email" class="form-control is-invalid" id="emailLoginId" name="email" required>
                    <div class="invalid-feedback">Please choose a username.</div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12 col-md-12 mb-3">
                    <label for="passwordLoginId">Пароль</label>
                    <input type="password" class="form-control is-invalid" id="passwordLoginId" name="password"
                           required>
                    <div class="invalid-feedback">Please choose a username.</div>
                </div>
            </div>
            <button class="btn btn-primary" type="submit">Войти</button>
        </form>
    </div>
</div>

<%--<c:if test="${not empty requestScope.errors}">--%>
<%--    <div style="color: red; font-weight: bold;">--%>
<%--        <c:forEach var="error" items="${requestScope.errors}">--%>
<%--            <span>${error.message}</span>--%>
<%--            <br>--%>
<%--        </c:forEach>--%>
<%--    </div>--%>
<%--</c:if>--%>