<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="java.util.Map" %>
<%@ page import="org.grupo12.models.User" %>
<%User user = (User) session.getAttribute("user");%>
<%
    Map<String, String> errors = (Map<String, String>) request.getAttribute("errors");
%>
<body>
<nav class="navbar navbar-expand-sm navbar-light ml-auto">

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
                    alt="logo-navbar"
                    class="d-inline-block"
                    src="<%=request.getContextPath()%>/assets/img/navbar/NavBarLogo.png"
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
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                       aria-expanded="false">
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
                <li style="padding: 10px; align-items: center;">
                    <a href="#" class="bttnVert">
                        Donaciones
                    </a>
                </li>
                <li style="padding: 10px">
                    <% if (user != null) { %>
                    <div class="userNavbarContainer">
                        <p class="nameUser" >Hola,<br/><%= user.getFirstName()%></p>
                        <img class="imgUser" src="<%=request.getContextPath()%>/assets/img/home/download.jpeg">
                    </div>
                    <% } else {%>
                    <a href="<%=request.getContextPath()%>/login" class="bttnBrun" style="white-space: nowrap">
                        Iniciar Sesion
                    </a>
                    <% } %>

                </li>
            </ul>
        </div>
    </div>
</nav>
</body>
</html>
