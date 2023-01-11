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
        <%--        https://getbootstrap.ru/docs/4.4/components/forms/#server-side--%>
        <br>
        <h1 class="center">Регистрация</h1>
        <br>
        <form>
            <div class="row">
                <div class="col-lg-3 col-md-12 mb-3">
                    <label for="surnameId">Фамилия</label>
                    <input type="text" class="form-control is-valid" id="surnameId" name="surname" required>
                    <div class="valid-feedback">Looks good!</div>
                </div>
                <div class="col-lg-3 col-md-12 mb-3">
                    <label for="nameId">Имя</label>
                    <input type="text" class="form-control is-valid" id="nameId" name="name" required>
                    <div class="valid-feedback">Looks good!</div>
                </div>
                <div class="col-lg-3 col-md-12 mb-3">
                    <label for="patronymicId">Отчество</label>
                    <input type="text" class="form-control is-invalid" id="patronymicId" name="patronymic" required>
                    <div class="invalid-feedback">Please choose a username.</div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-3 col-md-12 mb-3">
                    <label for="birthdayId">Дата рождения</label>
                    <input type="date" class="form-control is-valid" id="birthdayId" name="birthday" required>
                    <div class="valid-feedback">Looks good!</div>
                </div>
                <div class="col-lg-6 col-md-12 mb-3">
                    <label for="emailId">Email</label>
                    <input type="email" class="form-control is-valid" id="emailId" name="email" required>
                    <div class="valid-feedback">Looks good!</div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-9 col-md-12 mb-3">
                    <label for="passwordId">Пароль</label>
                    <input type="password" class="form-control is-invalid" id="passwordId" name="password" required>
                    <div class="invalid-feedback">Please choose a username.</div>
                </div>
            </div>
            <button class="btn btn-primary" type="submit">Зарегистрироваться</button>
        </form>
    </c:if>
</div>
</body>
</html>
