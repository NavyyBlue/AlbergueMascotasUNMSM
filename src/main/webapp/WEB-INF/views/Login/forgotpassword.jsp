<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Olvidaste la contraseña</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</head>
<body style="background-color: #eeeeee">
    <div class="container d-flex flex-column">
        <div class="row align-items-center justify-content-center min-vh-100">
            <div class="col-12 col-md-8 col-lg-4">
                <div class="card shadow-sm">
                    <div
                            class="card-header h5"
                            style="background-color: #bbd478; font-weight: 700"
                    >
                        ¿Olvidaste tu Contraseña?
                    </div>
                    <div class="card-body">
                        <div class="mb-4">
                            <p class="mb-2">
                                Ingresa tu dirección de correo electrónico para recibir un
                                código de recuperación
                            </p>
                        </div>
                        <form action="${pageContext.request.contextPath}/forgot-password" method="post">
                            <div class="mb-3">
                                <input
                                        type="email"
                                        id="userEmail"
                                        class="form-control"
                                        name="userEmail"
                                        placeholder="Correo Electronico"
                                        required=""
                                />
                            </div>
                            <div class="mb-3 d-grid">
                                <button
                                        type="submit"
                                        class="btn text-white"
                                        style="background-color: #a53535"
                                >
                                    Reset Password
                                </button>
                            </div>
                            <span>¿No tienes una cuenta? <a href="<%=request.getContextPath()%>/signup">Registrate</a></span>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
