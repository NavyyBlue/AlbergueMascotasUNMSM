<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <%--<link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery-3.6.0.min.js"></script>
    <script src="js/bootstrap.bundle.min.js"></script>--%>
    <title>Albergue de Mascotas</title>
</head>
<body>
    <%--<c:choose>
        <c:when test="${not empty sessionScope.username}">
            <jsp:forward page="WEB-INF/views/home/home.jsp"/>
        </c:when>
        <c:otherwise>
            <jsp:forward page="WEB-INF/views/Login/login.jsp"/>
        </c:otherwise>
    </c:choose>--%>
    <jsp:forward page="WEB-INF/views/home/home.jsp"/>
</body>
</html>
