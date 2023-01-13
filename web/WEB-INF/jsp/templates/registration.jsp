<%--
  Created by IntelliJ IDEA.
  User: kainv
  Date: 12.01.2023
  Time: 02:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--        https://getbootstrap.ru/docs/4.4/components/forms/#server-side--%>
<br>
<h1 class="center">Регистрация</h1>
<br>
<form action="/registration" accept-charset="UTF-8" method="post">
    <div class="row">
        <div class="col-lg-4 col-md-12 mb-3">
            <label for="surnameId">Фамилия</label>
            <input type="text"
                   class="form-control ${not empty requestScope['invalid.surname'] ? 'is-invalid' : ''}"
                   id="surnameId" name="surname"
                   required>
            <c:if test="${not empty requestScope['invalid.surname']}">
                <div class="invalid-feedback">${requestScope['invalid.surname']}</div>
            </c:if>
        </div>
        <div class="col-lg-4 col-md-12 mb-3">
            <label for="nameId">Имя</label>
            <input type="text"
                   class="form-control ${not empty requestScope['invalid.name'] ? 'is-invalid' : ''}"
                   id="nameId" name="name" required>
            <c:if test="${not empty requestScope['invalid.name']}">
                <div class="invalid-feedback">${requestScope['invalid.name']}</div>
            </c:if>
        </div>
        <div class="col-lg-4 col-md-12 mb-3">
            <label for="patronymicId">Отчество</label>
            <input type="text"
                   class="form-control ${not empty requestScope['invalid.patronymic'] ? 'is-invalid' : ''}"
                   id="patronymicId" name="patronymic"
                   required>
            <c:if test="${not empty requestScope['invalid.patronymic']}">
                <div class="invalid-feedback">${requestScope['invalid.patronymic']}</div>
            </c:if>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-4 col-md-12 mb-3">
            <label for="birthdayId">Дата рождения</label>
            <input type="date"
                   class="form-control ${not empty requestScope['invalid.birthday'] ? 'is-invalid' : ''}"
                   id="birthdayId" name="birthday" required>
            <c:if test="${not empty requestScope['invalid.birthday']}">
                <div class="invalid-feedback">${requestScope['invalid.birthday']}</div>
            </c:if>
        </div>
        <div class="col-lg-8 col-md-12 mb-3">
            <label for="emailId">Email</label>
            <input type="email"
                   class="form-control ${not empty requestScope['invalid.email'] ? 'is-invalid' : ''}"
                   id="emailId" name="email" required>
            <c:if test="${not empty requestScope['invalid.email']}">
                <div class="invalid-feedback">${requestScope['invalid.email']}</div>
            </c:if>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-12 col-md-12 mb-3">
            <label for="passwordId">Пароль</label>
            <input type="password"
                   class="form-control ${not empty requestScope['invalid.password'] ? 'is-invalid' : ''}"
                   id="passwordId" name="password"
                   required>
            <c:if test="${not empty requestScope['invalid.password']}">
                <div class="invalid-feedback">${requestScope['invalid.password']}</div>
            </c:if>
        </div>
    </div>
    <button class="btn btn-primary" type="submit">Зарегистрироваться</button>
</form>