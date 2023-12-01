<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Validación OTP</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body style="background-color: #eeeeee">
<div class="container mt-5">
    <jsp:include page="../../components/alerts.jsp"/>
    <div class="container d-flex flex-column">
        <div class="row align-items-center justify-content-center min-vh-100">
            <div class="col-12 col-md-8 col-lg-4">
                <div class="card shadow-sm">
                    <div
                            class="card-header h5 center"
                            style="background-color: #bbd478; font-weight: 700"
                    >
                        Código de Verificación
                    </div>
                    <div class="card-body">
                        <div class="mb-4">
                            <p class="mb-2">
                                Por favor, ingresar el código de verificación enviado al
                                correo electronico <strong><%=request.getSession().getAttribute("userEmail")%></strong>
                            </p>
                        </div>
                        <form  action="${pageContext.request.contextPath}/verifyotp" method="post">
                            <div class="mb-3">
                                <input
                                        type="text"
                                        id="otpCode"
                                        class="form-control"
                                        name="otpCode"
                                        placeholder="Código de Verificación"
                                        pattern="\d{4}"
                                        required=""
                                />
                            </div>
                            <span>
                  ¿No has recibido el código de verificación?<br />
                  Revisa tu bandeja de spam o <a href="#" id="resendLink">Reenvia el Código</a>
                </span>
                            <div class="mb-3 mt-3 d-grid">
                                <button
                                        type="submit"
                                        class="btn text-white"
                                        style="background-color: #a53535"
                                >
                                    Verificar
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    document.getElementById("resendLink").addEventListener("click", function(event) {
        event.preventDefault();
        resendOtp();
    });

    function resendOtp() {
        var form = document.createElement("form");
        form.method = "post";
        form.action = "${pageContext.request.contextPath}/resendotp";

        document.body.appendChild(form);

        form.submit();
    }
</script>
</body>
</html>
