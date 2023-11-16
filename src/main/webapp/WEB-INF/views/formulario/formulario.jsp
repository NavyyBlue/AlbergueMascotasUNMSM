<%--
  Created by IntelliJ IDEA.
  User: migue
  Date: 19/10/2023
  Time: 23:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Formulario</title>
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

<section class="d-flex justify-content-center align-items-center">
    <div class="card rounded shadow col-xs-12 col-sm-6 col-md-6 col-lg-4 p-4">

        <div class="bg-success text-white text-center p-2 mb-4">
            <h3>Ficha de Apadrinamiento</h3>
        </div>

        <div class="mb-4 d-flex justify-content-start align-items-center">
            <h4><i class="bi bi-chat-left-quote"></i> &nbsp; Mascota a Adoptar</h4>
        </div>

        <select class="form-select" >
            <option selected>[MASCOTA]</option>
            <option>Perros</option>
            <option>Gatos</option>
            <option>Otros</option>
        </select>

        <div class="mb-4 mt-4 d-flex justify-content-start align-items-center">
            <h4>  <i class="bi bi-chat-left-quote"></i> &nbsp; Datos Personales</h4>
        </div>
        <div class="mb-1">
            <form id = "datos" >
                <div class="mb-4 d-flex justify-content-between">
                    <div>
                        <label for="nombre"> <i class="bi bi-person-fill"></i> Nombres</label>
                        <input type="text" class="form-control" name="nombre" id="nombre" placeholder= "ej: Wambrillo" required>
                        <div class="nombre text-danger "></div>
                    </div>
                    <div >
                        <label for="apellido"> <i class="bi bi-person-bounding-box"></i> Apellidos</label>
                        <input type="text" class="form-control" name="apellido" id="apellido" placeholder= "ej: Ampuero" required>
                        <div class="apellido text-danger"></div>
                    </div>
                </div>
                <div class="mb-4 d-flex justify-content-between">
                    <div>
                        <label for="correo"><i class="bi bi-envelope-fill"></i> Correo</label>
                        <input type="email" class="form-control" name="correo" id="correo" placeholder= "ej: EsoTilin@mail.com" required>
                        <div class="correo text-danger"></div>
                    </div>
                    <div>
                        <label for="telefono"><i class="bi bi-envelope-fill"></i> Celular/Teléfono:</label>
                        <input type="number" class="form-control" name="telefono" id="telefono" placeholder= "ej: 960946343" required>
                        <div class="telefono text-danger"></div>
                    </div>
                </div>

                <div class="mb-4 d-flex justify-content-between">
                    <div>
                        <label ><i class="bi bi-envelope-fill"></i> Fecha de nacimiento</label>
                        <input type="date" class="form-control" min="1999-12-31" required>
                        <div class="fecha text-danger"></div>
                    </div>
                    <div>
                        <label for="Dni"><i class="bi bi-envelope-fill"></i> Dni:</label>
                        <input type="number" class="form-control" name="telefono" id="Dni" placeholder= "ej: 14279381" required>
                        <div class="dni text-danger"></div>
                    </div>
                </div>

                <div class="mb-4">
                    <label for="sexo"><i class="bi bi-gender-ambiguous"></i> DNI: </label>
                    <input type="radio" class="form-check-input"  name="sexo"  value="masculino" > Masculino
                    <input type="radio" class="form-check-input" name="sexo"  value="femenino" > Femenino
                    <div class="sexo text-danger"></div>
                </div>

                <div class="form-check">
                    <label class="form-check-label">
                        <input class="form-check-input" type="checkbox">
                        <div>
                            <h6>Al llenar la ficha otorgo mi consentimiento libre, previo, informado, expreso e inequívoco para que el refugio se comunique utilizando mis datos para proceder con el proceso de adopción. * </h6>
                        </div>
                    </label>

                </div>

                <div class="mb-2">
                    <button id ="botton" class="col-12 btn btn-primary d-flex justify-center ">
                        <span>Enviar </span><i id="icono" class="bi bi-cursor-fill "></i>
                    </button>
                </div>

            </form>
        </div>
    </div>
</section>

</body>
</html>
