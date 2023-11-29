<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/login.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <title>Iniciar Sesión</title>
</head>
<body>
<jsp:include page="../../components/alerts.jsp"/>
<div class="container">
    <div class="login-box">
        <h2 class="card-title">¡Bienvenido!</h2>
        <hr>
        <div class="input-group">
            <form method="post" action="${pageContext.request.contextPath}/login">
                <input class="form-input mb-4" type="text" placeholder="username" name="username" required>
                <input class="form-input mb-4" type="password" placeholder="Contraseña" name="password" required>
                <a href="<%=request.getContextPath()%>/forgot-password" class="forgot-password">¿Olvidaste tu contraseña?</a>
                <button type="submit" class="form-button">Iniciar Sesión</button>
                <div class="form-group text-center">
                    ¿Es un usuario nuevo? <a href="<%=request.getContextPath()%>/signup" class="form-link">Registrarse</a>
                </div>
                <%--Volver al home--%>
                <div class="form-group text-center">
                    <a href="<%=request.getContextPath()%>/home" class="form-link">Volver al Home</a>
                </div>
            </form>

        </div>
    </div>
    <div class="image-container">
        <img class="image" src="<%=request.getContextPath()%>/assets/img/login/portada.png" alt="Portada">
    </div>
</div>
</body>
</html>
