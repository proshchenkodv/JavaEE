<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">

<jsp:include page="fragments/head.jsp"/>

<body>

<jsp:include page="fragments/navigator.jsp">
    <jsp:param name="pageName" value="Категория"/>
</jsp:include>

<div class="container">
    <div class="row py-2">
        <div class="col-12">
            <c:url value="/categories/upsert" var="categoryPostUrl"/>
            <form action="${categoryPostUrl}" method="post">
                <input type="hidden" id="id" name="id" value="${category.id}">
                <div class="form-group">
                    <label>Name</label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="Enter name"
                           value="${category.name}">
                </div>
                <div class="form-group">
                    <label>Description</label>
                    <input type="text" class="form-control" id="description" name="description" placeholder="Enter description"
                           value="${category.description}">
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="fragments/scripts.jsp"/>

</body>
