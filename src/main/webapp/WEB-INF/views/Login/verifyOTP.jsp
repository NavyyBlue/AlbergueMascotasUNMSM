<%--
  Created by IntelliJ IDEA.
  User: jj-08
  Date: 23/11/2023
  Time: 21:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Validación OTP</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</head>
<body>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header text-center">
                    <h4>Verificación de Código OTP</h4>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/resetpassword" method="post">
                        <div class="mb-3">
                            <label for="otpCode" class="form-label">Ingrese el código OTP de 4 dígitos:</label>
                            <input type="text" class="form-control" id="otpCode" name="otpCode" pattern="\d{4}" required>
                            <div class="form-text">Ingrese el código de 4 dígitos enviado a su correo.</div>
                        </div>
                        <button type="submit" class="btn btn-primary">Verificar</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
