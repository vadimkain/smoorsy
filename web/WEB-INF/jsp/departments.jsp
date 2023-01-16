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
<c:if test="${sessionScope.USER != null}">
    <div class="row" style="display: flex; justify-content: center; align-content: center;">
        <div class="col-4">
            <table class="table table-striped">
                <thead>
                <th scope="col">ID</th>
                <th scope="col">Город</th>
                <th scope="col">
                    <form>
                        <input type="submit" formaction="${pageContext.request.contextPath}/departments/insert"
                               formmethod="post" value="Добавить" class="btn btn-primary">
                    </form>
                </th>
                </thead>
                <tbody>
                <c:forEach var="department" items="${sessionScope['USER-departments']}">
                    <tr>
                        <th scope="row">${department.id}</th>
                        <td><a href="#">${department.city}</a></td>
                        <td>
                            <form>
                                <input type="submit"
                                       formaction="${pageContext.request.contextPath}/departments/delete/${department.id}"
                                       formmethod="post" value="Удалить" class="btn btn-danger">
                                <input type="submit"
                                       formaction="${pageContext.request.contextPath}/departments/edit/${department.id}"
                                       formmethod="post" value="Редактировать" class="btn btn-primary">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</c:if>
</body>
</html>
