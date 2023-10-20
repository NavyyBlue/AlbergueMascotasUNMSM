<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="java.util.Map" %>
<%@ page import="org.grupo12.models.User" %>
<%User user = (User) session.getAttribute("user");%>
<%
    Map<String, String> errors = (Map<String, String>) request.getAttribute("errors");
%>
<body>
<nav class="navbar navbar-expand-sm navbar-light ml-auto mx-5">
    <div class="container-fluid justify-content-between">
        <div class="d-flex">
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
               class="navbar-brand me-2 mb-1 d-flex align-items-center">
                <img alt="logo-navbar"
                     class="d-inline-block"
                     src="<%=request.getContextPath()%>/assets/img/navbar/NavBarLogo.png"
                     width="100" height="80"
                />
            </a>
        </div>

        <ul class="navbar-nav flex-row d-none d-md-flex">
            <li class="nav-item">
                <a href="#" class="nav-link active">
                    Nosotros
                </a>
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
        <ul class="navbar-nav flex-row align-items-center">
            <li class="nav-item me-3 me-lg-1">
                <a href="#" class="nav-link bttnVert m-0 me-4">
                    Donaciones
                </a>
            </li>
            <li class="nav-item me-3 me-lg-1">
                <% if (user != null) { %>
                <div class="nav-link d-sm-flex align-items-sm-center">
                    <p class="m-0 me-2">
                        Hola,<br>
                        <%= user.getFirstName()%>
                    </p>
                    <img class="imgUser" src="<%=request.getContextPath()%>/assets/img/home/download.jpeg">
                </div>
                <% } else {%>
                <a href="<%=request.getContextPath()%>/login" class="nav-link bttnBrun">
                    Iniciar Sesion
                </a>
                <% } %>
            </li>
        </ul>

    </div>
</nav>
</body>
