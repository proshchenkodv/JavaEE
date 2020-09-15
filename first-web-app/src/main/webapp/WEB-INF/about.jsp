<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">

<jsp:include page="fragments/head.jsp"/>

<body>

<jsp:include page="fragments/navigator.jsp">
    <jsp:param name="pageName" value="О Компании"/>
</jsp:include>

<div class="container">
    <div class="row py-2">

        <div class="col-12">
            <table class="table table-bordered my-2">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Name</th>
                    <th scope="col">Adress</th>
                </tr>
                </thead>
                <tbody>

                <%--                <% for (Category category : repository.findAll()) { %>--%>
                <c:forEach var="company" items="${requestScope.company}">
                    <tr>
                        <th scope="row">
                            <c:out value="${company.id}"/>
                                <%--                        <%= category.getId() %>--%>
                        </th>
                        <td>
                            <c:out value="${company.name}"/>
                                <%--                        <%= category.getName() %>--%>
                        </td>
                        <td>
                            <c:out value="${company.adress}"/>
                                <%--                        <%= category.getDescription() %>--%>
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
