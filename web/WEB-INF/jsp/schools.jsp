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
        <div class="row my-5" style="display: flex; justify-content: center; align-content: center;">
            <div class="col-lg-6 col-12">
                <table class="table table-striped">
                    <thead>
                    <th scope="col">ID</th>
                    <th scope="col">Департамент</th>
                    <th scope="col">Название</th>
                    <th scope="col">Директор</th>
                    <th scope="col"></th>
                    <th>
                        <a href="" data-bs-target="#insert-modal" data-bs-toggle="modal">Добавить</a>
                        <div class="modal fade" id="insert-modal" tabindex="-1" role="dialog"
                             aria-labelledby="insert-modalTitle"
                             aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="insertModalLongTitle">Добавление школы</h5>
                                    </div>
                                    <div class="modal-body">
                                        <form action="${pageContext.request.contextPath}/schools/insert"
                                              method="post" accept-charset="UTF-8">
                                            <div class="row form-group my-3">
                                                <label for="departmentIdInsert"
                                                       class="col-form-label col-4">Департамент</label>
                                                <div class="col-8">
                                                    <select name="department" id="departmentIdInsert"
                                                            class="form-select" required>
                                                        <c:forEach var="department"
                                                                   items="${sessionScope['USER-departments']}">
                                                            <option value="${department.id}">${department.city}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="row form-group my-3">
                                                <label for="nameIdInsert"
                                                       class="col-form-label col-4">Название</label>
                                                <div class="col-8">
                                                    <input type="text"
                                                           class="form-control"
                                                           id="nameIdInsert" name="name"
                                                           required>
                                                </div>
                                            </div>
                                            <div class="row form-group my-3">
                                                <label for="managerIdInsert"
                                                       class="col-form-label col-4">Менеджер</label>
                                                <div class="col-8">
                                                    <select name="manager" id="managerIdInsert"
                                                            class="form-select" required>
                                                        <c:forEach var="manager" items="${sessionScope['managers']}">
                                                            <option value="${manager['user']}">
                                                                    ${manager['user'].surname} ${manager['user'].name} ${manager['user'].patronymic}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                                    Отмена
                                                </button>
                                                <button type="submit" class="btn btn-success">Добавить</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </th>
                    </thead>
                    <tbody>
                    <c:if test="${sessionScope['schools-of-department'] != null}">
                        <c:forEach var="schoolOfDepartment" items="${sessionScope['schools-of-department']}">
                            <tr>
                                <th scope="col">${schoolOfDepartment.id}</th>
                                <td>${schoolOfDepartment['department'].city}</td>
                                <td>${schoolOfDepartment.name}</td>
                                <td>${schoolOfDepartment['manager']['user'].surname} ${schoolOfDepartment['manager']['user'].name} ${schoolOfDepartment['manager']['user'].patronymic}</td>
                                <td>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    <c:if test="${sessionScope['schools'] != null}">
                        <c:forEach var="school" items="${sessionScope['schools']}">
                            <tr>
                                <th scope="col">${school.id}</th>
                                <td>${school['department'].city}</td>
                                <td>${school.name}</td>
                                <td>${school['manager']['user'].surname} ${school['manager']['user'].name} ${school['manager']['user'].patronymic}</td>
                                <td>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </c:if>
</div>
</body>
</html>
