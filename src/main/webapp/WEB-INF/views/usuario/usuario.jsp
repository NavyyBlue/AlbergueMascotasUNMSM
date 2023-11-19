<%--
  Created by IntelliJ IDEA.
  User: migue
  Date: 16/11/2023
  Time: 21:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="org.grupo12.models.User" %>
<%User user = (User) session.getAttribute("user");%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Usuario</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link href="<%=request.getContextPath()%>/assets/css/home.css" rel="stylesheet"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/navbar.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"/>


    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA==" crossorigin="anonymous" referrerpolicy="no-referrer" />

</head>
<body>
<jsp:include page="../../components/navBar.jsp"/>

<section style="background-color: #eee;">
    <div class="container py-5">

        <div class="row">
            <div class="col-lg-4">
                <div class="card mb-4">
                    <div class="card-body text-center">
                        <img src="<%=request.getContextPath()%>/assets/img/home/download.jpeg" alt="avatar"
                             class="rounded-circle img-fluid" style="width: 150px;">
                        <h5 class="my-3"><%= user.getFirstName()%> <%= user.getLastName()%></h5>
                        <p class="text-muted mb-1">
                            <% if (user.getUserRole() == 0) { %>
                            Usuario
                            <% } else if (user.getUserRole() == 1) { %>
                            Administrador
                            <% }
                            %>
                        </p>
                        <p class="text-muted mb-4">
                            <% if (user.isActive()) { %>
                            Activo
                            <% } else { %>
                            Inactivo
                            <% } %>
                        </p>

                        <div class="d-flex justify-content-center mb-2">
                            <button type="button" class="btn btn-primary">Cerrar sesion</button>
                        </div>
                    </div>
                </div>

            </div>
            <div class="col-lg-8">
                <div class="card mb-4">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-sm-3">
                                <p class="mb-0">Nombre Completo</p>
                            </div>
                            <div class="col-sm-9">
                                <p class="text-muted mb-0"><%= user.getFirstName()%> <%= user.getLastName()%></p>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <p class="mb-0">Nombre de Usuario</p>
                            </div>
                            <div class="col-sm-9">
                                <p class="text-muted mb-0"><%= user.getUserName()%></p>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <p class="mb-0">Email</p>
                            </div>
                            <div class="col-sm-9">
                                <p class="text-muted mb-0"><%= user.getEmail()%></p>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <p class="mb-0">Telefono </p>
                            </div>
                            <div class="col-sm-9">
                                <p class="text-muted mb-0"><%= user.getPhoneNumber()%></p>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <p class="mb-0">Estado</p>
                            </div>
                            <div class="col-sm-9">
                                <p class="text-muted mb-0"><% if (user.isActive()) { %>
                                    Activo
                                    <% } else { %>
                                    Inactivo
                                    <% } %></p>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <p class="mb-0">Rol de usuario</p>
                            </div>
                            <div class="col-sm-9">
                                <p class="text-muted mb-0"><% if (user.getUserRole() == 0) { %>
                                    Usuario
                                    <% } else if (user.getUserRole() == 1) { %>
                                    Administrador
                                    <% }
                                    %></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-lg-6 mx-auto">
                <div class="card mb-4 mt-4 ">
                    <div class="card-body">
                        <div class="row align-items-center">
                            <div class="col-sm-3 text-center d-flex align-items-center ">
                                <p class="mb-0 me-2">Mascotas Adoptadas  </p>
                                <i class="fa-solid fa-dog align-middle "></i>

                            </div>
                            <div class="col-sm-6">
                                <p class="text-muted mb-0">10</p>
                            </div>

                        </div>

                        <hr>
                        <div class="row align-items-center">
                            <div class="col-sm-3 text-center d-flex align-items-center">
                                <p class="mb-0 me-2"">Mascotas Apadrinadas</p>
                                <i class="fas fa-bone align-middle"></i>
                            </div>
                            <div class="col-sm-6">
                                <p class="text-muted mb-0">5</p>
                            </div>
                        </div>
                        <hr>
                        <div class="row align-items-center">
                            <div class="col-sm-3 text-center d-flex align-items-center">
                                <p class="mb-0 me-2">Numero de Favoritos</p>
                                <i class="fa-solid fa-heart align-middle"></i>
                            </div>
                            <div class="col-sm-6">
                                <p class="text-muted mb-0">10</p>
                            </div>
                        </div>

                    </div>
                </div>

        </div>
    </div>



</section>

</body>
</html>
