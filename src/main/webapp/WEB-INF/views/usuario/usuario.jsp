<%--
  Created by IntelliJ IDEA.
  User: migue
  Date: 16/11/2023
  Time: 21:07
  To change this template use File | Settings | File Templates.
--%>
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
</head>
<body>
<jsp:include page="../navBar/navBar.jsp"/>

<section style="background-color: #eee;">
    <div class="container py-5">

        <div class="row">
            <div class="col-lg-4">
                <div class="card mb-4">
                    <div class="card-body text-center">
                        <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3.webp" alt="avatar"
                             class="rounded-circle img-fluid" style="width: 150px;">
                        <h5 class="my-3">FirstName LastName</h5>
                        <p class="text-muted mb-1"> UserRole </p>
                        <p class="text-muted mb-4"> Active </p>
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
                                <p class="text-muted mb-0">FirstName LastName</p>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <p class="mb-0">Email</p>
                            </div>
                            <div class="col-sm-9">
                                <p class="text-muted mb-0">Email</p>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <p class="mb-0">Telefono </p>
                            </div>
                            <div class="col-sm-9">
                                <p class="text-muted mb-0">PhoneNumber</p>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <p class="mb-0">Estado</p>
                            </div>
                            <div class="col-sm-9">
                                <p class="text-muted mb-0">Active</p>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <p class="mb-0">Rol de usuario</p>
                            </div>
                            <div class="col-sm-9">
                                <p class="text-muted mb-0">UserRole</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

</body>
</html>
