<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Olvidaste la contraseña</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</head>
<body class="bg-light">
<div class="container">
    <div class="row justify-content-center mt-5">
        <div class="col-md-6">
            <div class="card">
                <div class="card-body">
                    <h2 class="card-title text-center">Recuperación de Contraseña</h2>
                    <form action="${pageContext.request.contextPath}/forgot-password" method="post">
                        <div class="form-group">
                            <label for="userEmail">Correo Electrónico:</label>
                            <input type="email" class="form-control" id="userEmail" name="userEmail" required>
                        </div>
                        <button type="submit" class="btn btn-primary btn-block">Recuperar Contraseña</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
