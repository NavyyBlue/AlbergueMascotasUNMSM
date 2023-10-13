<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="java.util.Map" %>
<%
    Map<String, String> errors = (Map<String, String>) request.getAttribute("errors");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap NavBar</title>
    <link rel="stylesheet" type='text/css'  href="style.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
</head>

<body>

    <nav class="navbar fixed-top navbar-expand-sm navbar-light ml-auto">

        <div class="container-fluid">

            <button
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#navbarNav"
                    class="navbar-toggler"
                    aria-controls="navbarNav"
                    aria-expanded="false"
                    aria-label="Toggle navigation"
            >
                <span class="navbar-toggler-icon"></span>
            </button>

            <a
                    href="#"
                    class="navbar px-3">
                <img
                        class="d-inline-block"
                        src="../../assets/img/NavBarLogo.png"
                        width="100" height="80"
                />
            </a>


            <div
                class="collapse navbar-collapse"
                id="navbarNav">

            <ul class="navbar-nav ml-auto d-flex">

                <li class="nav-item flex-grow-1">
                    <a href="#" class="nav-link active">
                        Nosotros
                    </a>
                </li>

                <li class="nav-item dropdown flex-grow-1">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Mascotas
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="#">Adopcion</a></li>
                        <li><a class="dropdown-item disabled" href="#">Apadrinamiento</a></li>
                    </ul>
                </li>

                <li class="nav-item flex-grow-1">
                    <a href="#" class="nav-link">
                        Mascotas
                    </a>
                </li>

                <li class="nav-item flex-grow-1">
                    <a href="#" class="nav-link">
                        Voluntariado
                    </a>
                </li>

                <li class="nav-item flex-grow-1">
                    <a href="#" class="nav-link">
                        Mapa
                    </a>
                </li>
            </ul>

        </div>

            <div>
                <ul class="nav navbar-nav navbar-right">
                    <li style="padding: 10px">
                        <a href="#" class="bttnVert">
                            Donaciones
                        </a>
                    </li>
                    <li style="padding: 10px">
                        <a href="#" class="bttnBrun" style="white-space: nowrap">
                            Iniciar Sesion
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>





<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>

</body>
</html>
