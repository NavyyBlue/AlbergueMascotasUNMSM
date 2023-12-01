<%--
  Created by IntelliJ IDEA.
  User: migue
  Date: 16/11/2023
  Time: 21:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="org.grupo12.models.User" %>
<%@ page import="com.google.gson.Gson" %>

<%User user = (User) session.getAttribute("user");%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Usuario</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
    <link href="<%=request.getContextPath()%>/assets/css/home.css" rel="stylesheet"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/navbar.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"/>


    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
<%
    // Default speciesId if not present in the request
    User currentUser = (User) request.getSession().getAttribute("user");
    int userId = currentUser.getUserId();
    // Convert the list of users to JSON using Gson
    Gson gson = new Gson();
    String usersJson = gson.toJson(currentUser);
%>


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
                            Administrador
                            <% } else if (user.getUserRole() == 1) { %>
                            Usuario
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
                            <a href="<%=request.getContextPath()%>/logout" class="btn btn-primary">Cerrar Sesion</a>
                        </div>

                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#editModal" data-userid="<%=user.getUserId()%>">
                            <img src="<%=request.getContextPath()%>/assets/svg/edit.svg" alt="editar">
                            <a>Editar Usuario</a>
                        </button>


                        <!-- Modal -->
                        <div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title fs-5" id="editModalLabel">Editar Usuario</h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form id="editUserForm" action="${pageContext.request.contextPath}/usuario" method="post">
                                            <div class="mb-3">
                                                <label for="editFirstName" class="form-label">Nombre</label>
                                                <input type="text" class="form-control" id="editFirstName" name="editFirstName">
                                            </div>
                                            <div class="mb-3">
                                                <label for="editLastName" class="form-label">Apellido</label>
                                                <input type="text" class="form-control" id="editLastName" name="editLastName">
                                            </div>
                                            <div class="mb-3">
                                                <label for="editEmail" class="form-label">Email</label>
                                                <input type="text" class="form-control" id="editEmail" name="editEmail">
                                            </div>
                                            <div class="mb-3">
                                                <label for="editUserName" class="form-label">Nombre de Usuario</label>
                                                <input type="text" class="form-control" id="editUserName" name="editUserName">
                                            </div>
                                            <div class="mb-3">
                                                <label for="editPhoneNumber" class="form-label">Telefono</label>
                                                <input type="text" class="form-control" id="editPhoneNumber" name="editPhoneNumber">
                                            </div>
                                        <div class="modal-footer">
                                            <input type="hidden" id="editUserId" name="editUserId" value="<%=userId%>">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                            <button type="submit" class="btn btn-primary">Guardar</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
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
                                    Administrador
                                    <% } else if (user.getUserRole() == 1) { %>
                                    Usuario
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
                                <p class="mb-0 me-2">Mascotas Apadrinadas</p>
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
        </div>
    </div>


        <script>
            $('#editModal').on('show.bs.modal', function (event) {
                let button = $(event.relatedTarget); // Botón que activó el modal
                let userId = button.data('userid'); // Extraer el userId del atributo data-userid


                    // Parse the JSON data from the attribute
                    let usersJson = '<%= usersJson %>';
                    let user = JSON.parse(usersJson);
                    console.log("userjson",usersJson)
                    // Find the user with the corresponding userId

                    $('#editFirstName').val(user.firstName);
                    $('#editLastName').val(user.lastName);
                    $('#editEmail').val(user.email);
                    $('#editUserName').val(user.userName);
                    $('#editPhoneNumber').val(user.phoneNumber);
                });
        </script>


</section>
</body>
</html>
