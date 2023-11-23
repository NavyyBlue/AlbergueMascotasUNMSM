<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reestablecer contraseña</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</head>
<body>
<div class="container mt-5">
    <h2>Reestablecer Contraseña</h2>
    <form action="${pageContext.request.contextPath}/resetpassword" method="post">
        <div class="form-group">
            <label for="password">Nueva Contraseña:</label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>
        <div class="form-group">
            <label for="password2">Confirmar Contraseña:</label>
            <input type="password" class="form-control" id="password2" name="password2" required>
        </div>
        <button type="submit" class="btn btn-primary">Reestablecer Contraseña</button>
    </form>
</div>
</body>
</html>
