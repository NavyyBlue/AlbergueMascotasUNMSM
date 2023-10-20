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
        <button type="button"
                data-bs-toggle="collapse"
                data-bs-target="#navbarNav"
                class="navbar-toggler"
                aria-controls="navbarNav"
                aria-expanded="false"
                aria-label="Toggle navigation"
        >
            <span class="navbar-toggler-icon"></span>
        </button>
        <a href="#"
           class="navbar px-3">
            <img alt="logo-navbar"
                 class="d-inline-block"
                 src="<%=request.getContextPath()%>/assets/img/navbar/NavBarLogo.png"
                 width="100" height="80"
            />
        </a>
        <div class="collapse navbar-collapse"
             id="navbarNav">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a href="#" class="nav-link active">
                        Nosotros
                    </a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                       aria-expanded="false">
                        Mascotas
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="#">Adopcion</a></li>
                        <li><a class="dropdown-item disabled" href="#">Apadrinamiento</a></li>
                    </ul>
                </li>
                <li class="nav-item ">
                    <a href="#" class="nav-link">
                        Mascotas
                    </a>
                </li>
                <li class="nav-item">
                    <a href="#" class="nav-link">
                        Voluntariado
                    </a>
                </li>
                <li class="nav-item">
                    <a href="#" class="nav-link">
                        Mapa
                    </a>
                </li>
            </ul>
            <div class="d-flex me-5">
                <a href="#" class="bttnVert me-4">
                    Donaciones
                </a>
                <% if (user != null) { %>
                <div class="userNavbarContainer">
                    <p class="m-0 me-2">
                        Hola,<br>
                        <%= user.getFirstName()%>
                    </p>
                    <img class="imgUser" src="<%=request.getContextPath()%>/assets/img/home/download.jpeg">
                </div>
                <% } else {%>
                <a href="<%=request.getContextPath()%>/login" class="bttnBrun">
                    Iniciar Sesion
                </a>
                <% } %>
            </div>
        </div>
    </div>
</nav>
</body>
