<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">

<jsp:include page="fragments/head.jsp"/>

<body>

<jsp:include page="fragments/navigator.jsp">
    <jsp:param name="pageName" value="Категории"/>
</jsp:include>

<div class="container">
    <div class="row py-2">
        <div class="col-12">
            <c:url value="/categories/new" var="newEditUrl"/>
            <a class="btn btn-primary" href="${newEditUrl}">Add category</a>
        </div>

        <div class="col-12">
            <table class="table table-bordered my-2">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Name</th>
                    <th scope="col">Description</th>
                    <th scope="col">Actions</th>
                </tr>
                </thead>
                <tbody>

                <%--                <% for (Category category : repository.findAll()) { %>--%>
                <c:forEach var="category" items="${requestScope.categories}">
                    <tr>
                        <th scope="row">
                            <c:out value="${category.id}"/>
                                <%--                        <%= category.getId() %>--%>
                        </th>
                        <td>
                            <c:out value="${category.name}"/>
                                <%--                        <%= category.getName() %>--%>
                        </td>
                        <td>
                            <c:out value="${category.description}"/>
                                <%--                        <%= category.getDescription() %>--%>
                        </td>
                        <td>
                            <c:url value="/categories/edit" var="categoryEditUrl">
                                <c:param name="id" value="${category.id}"/>
                            </c:url>
                            <a class="btn btn-success" href="${categoryEditUrl}"><i class="fas fa-edit"></i></a>
                            <c:url value="/categories/delete" var="categoryDeleteUrl">
                                <c:param name="id" value="${category.id}"/>
                            </c:url>
                            <a class="btn btn-danger" href="${categoryDeleteUrl}"><i class="far fa-trash-alt"></i></a>
                        </td>
                    </tr>
                </c:forEach>
                <%--                <% } %>--%>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="fragments/scripts.jsp"/>
</body>
</html>
