<%--
  Created by IntelliJ IDEA.
  User: kainv
  Date: 16.01.2023
  Time: 15:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Департаменты</title>
</head>
<body>
<%@include file="templates/header.jsp" %>
<div class="container-fluid">
    <c:if test="${sessionScope.USER != null}">
        <div class="row my-5" style="display: flex; justify-content: center; align-content: center;">
            <div class="col-lg-6 col-12">
                <table class="table table-striped">
                    <thead>
                    <th scope="col">ID</th>
                    <th scope="col">Город</th>
                    <th scope="col">
                        <!-- Button trigger modal -->
                        <a href="" data-bs-target="#insert-modal" data-bs-toggle="modal">Добавить</a>
                        <!-- Modal insert -->
                        <div class="modal fade" id="insert-modal" tabindex="-1" role="dialog"
                             aria-labelledby="insert-modalTitle"
                             aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="insertModalLongTitle">Добавление департамента</h5>
                                    </div>
                                    <div class="modal-body">
                                        <form action="${pageContext.request.contextPath}/departments/insert"
                                              method="post" accept-charset="UTF-8">
                                            <div class="row form-group my-3">
                                                <label for="cityId" class="col-form-label col-4">Город</label>
                                                <div class="col-8">
                                                    <input type="text"
                                                           class="form-control ${not empty requestScope['invalid.department.city'] ? 'is-invalid' : ''}"
                                                           id="cityId" name="city"
                                                           required>
                                                </div>
                                                <c:if test="${not empty requestScope['invalid.department.city']}">
                                                    <div class="invalid-feedback">${requestScope['invalid.department.city']}</div>
                                                </c:if>
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
                    <c:forEach var="department" items="${sessionScope['USER-departments']}">
                        <tr>
                            <th scope="row">${department.id}</th>
                            <td><a href="#">${department.city}</a></td>
                            <td>
                                <a href="" data-bs-target="#delete-${department.id}-modal" data-bs-toggle="modal"
                                   class="text-danger">Удалить</a>
                                <!-- Modal delete -->
                                <div class="modal fade" id="delete-${department.id}-modal" tabindex="-1" role="dialog"
                                     aria-labelledby="delete-modalTitle"
                                     aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="deleteModalLongTitle">Удалить департамент
                                                    <b>${department.city}</b>?</h5>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                                    Отмена
                                                </button>
                                                <form action="${pageContext.request.contextPath}/departments/delete/${department.id}"
                                                      method="post">
                                                    <button type="submit" class="btn btn-danger">Удалить</button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- Button trigger modal -->
                                <a href="" data-bs-target="#edit-${department.id}-modal" data-bs-toggle="modal"
                                   class="mx-2">Редактировать</a>
                                <!-- Modal insert -->
                                <div class="modal fade" id="edit-${department.id}-modal" tabindex="-1" role="dialog"
                                     aria-labelledby="edit-modalTitle"
                                     aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="editModalLongTitle">Редактирование
                                                    департамента <b>${department.city}</b></h5>
                                            </div>
                                            <div class="modal-body">
                                                <form action="${pageContext.request.contextPath}/departments/edit/${department.id}"
                                                      method="post" accept-charset="UTF-8">
                                                    <div class="row form-group my-3">
                                                        <label for="cityEditId"
                                                               class="col-form-label col-4">Город</label>
                                                        <div class="col-8">
                                                            <input type="text"
                                                                   class="form-control ${not empty requestScope['invalid.department.city'] ? 'is-invalid' : ''}"
                                                                   id="cityEditId" name="city"
                                                                   required>
                                                        </div>
                                                        <c:if test="${not empty requestScope['invalid.department.city']}">
                                                            <div class="invalid-feedback">${requestScope['invalid.department.city']}</div>
                                                        </c:if>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary"
                                                                data-bs-dismiss="modal">
                                                            Отмена
                                                        </button>
                                                        <button type="submit" class="btn btn-success">Изменить</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>

                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </c:if>
</div>
</body>
</html>
