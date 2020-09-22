<%@ page contentType="text/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">

<jsp:include page="fragment/head.jsp"/>

<body>

<jsp:include page="fragment/navigation.jsp">
    <jsp:param name="pageName" value="Продукты"/>
</jsp:include>

<div class="container">
    <div class="row py-2">
        <div class="col-12">
            <c:url value="/new" var="newEditUrl"/>
            <a class="btn btn-primary" href="${newEditUrl}">Add product</a>
        </div>

        <div class="col-12">
            <table class="table table-bordered my-2">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Name</th>
                    <th scope="col">Description</th>
                    <th scope="col">Price</th>
                    <th scope="col">Actions</th>
                </tr>
                </thead>
                <tbody>

                <%--                <% for (Product prod : repository.findAll()) { %>--%>
                <c:forEach var="prod" items="${requestScope.products}">
                    <tr>
                        <th scope="row">
                            <c:out value="${prod.id}"/>
                                <%--                        <%= prod.getId() %>--%>
                        </th>
                        <td>
                            <c:out value="${prod.name}"/>
                                <%--                        <%= prod.getName() %>--%>
                        </td>
                        <td>
                            <c:out value="${prod.description}"/>
                                <%--                        <%= prod.getDescription() %>--%>
                        </td>
                        <td>
                                <c:out value="${prod.price}"/>
                                <%--                        $<%= prod.getPrice() %></td>--%>
                        <td>
                            <c:url value="/edit" var="productEditUrl">
                                <c:param name="id" value="${prod.id}"/>
                            </c:url>
                            <a class="btn btn-success" href="${productEditUrl}"><i class="fas fa-edit"></i></a>
                            <a class="btn btn-danger" href="#"><i class="far fa-trash-alt"></i></a>
                        </td>
                    </tr>
                </c:forEach>
                <%--                <% } %>--%>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="fragment/scripts.jsp"/>

</body>
</html>
