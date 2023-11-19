<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/login.css">
    <title>Iniciar Sesión</title>
</head>
<body>


<div class="container">
    <div class="login-box">
        <h2 class="card-title">¡Bienvenido!</h2>
        <hr>
        <div class="input-group">
            <form method="post" action="${pageContext.request.contextPath}/login">
                <input class="form-input" type="text" placeholder="username" name="username" required>
                <input class="form-input" type="password" placeholder="Contraseña" name="password" required>
                <a href="#" class="forgot-password">¿Olvidaste tu contraseña?</a>
                <button type="submit" class="form-button">Iniciar Sesión</button>
                <div class="form-group text-center">
                    ¿Es un usuario nuevo? <a href="#" class="form-link">Registrarse</a>
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
