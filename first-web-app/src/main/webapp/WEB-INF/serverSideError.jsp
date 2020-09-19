<%@ page contentType="text/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">

<jsp:include page="fragment/head.jsp"/>

<body>

<jsp:include page="fragment/navigation.jsp">
    <jsp:param name="pageName" value="Бренды"/>
</jsp:include>

<div class="container">
    <div class="row py-2">
        <h1>Server side error</h1>
    </div>
</div>

<% response.setStatus(500); %>

<jsp:include page="fragment/scripts.jsp"/>

</body>
</html>
