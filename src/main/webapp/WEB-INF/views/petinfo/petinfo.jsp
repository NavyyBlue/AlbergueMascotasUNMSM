<%--
  Created by IntelliJ IDEA.
  User: jccr_
  Date: 16/11/2023
  Time: 22:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Detalles de la mascota</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/navbar.css">
    <link href="<%=request.getContextPath()%>/assets/css/petinfo.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"/>
</head>
<body>
<jsp:include page="../../components/navBar.jsp"/>

<div class="divMainPet">
    <div class="divDetailsPet" style="align-items: center;">
        <img style="width: 545px; height: 406px; border-radius: 30px;" src="<%=request.getContextPath()%>/assets/img/petlist/foto_petprueba.png">
        <div class="divDescriptionPet">
            <div class="divDetailsPet" style="height: 71px; align-items: center">
                <img style="width: 55.675px; height: 52.984px; transform: rotate(52.107deg); margin: 0 10px 0 0;" src="<%=request.getContextPath()%>/assets/img/petlist/pet_footprint.png">
                <p style="margin: 0; font-size: 54px;">Archie</p>
            </div>
            <div class="animal-info" id="animalInfo">
                <p>Sexo: <span id="sexoPlaceholder"></span></p>
                <p>Edad: <span id="edadPlaceholder"></span></p>
                <p>Raza: <span id="tamanioPlaceholder"></span></p>
                <p>Descripción: <span id="descriptionPlaceholder"></span></p>
                <p>Ubicación: <span id="locationPlaceholder"></span></p>
                <p>Etiquetas:</p>
                <ul id="etiquetasMascota"></ul>
                <p id="descripcionPlaceholder"></p>
            </div>
            <script>
                // Supongamos que tienes una lista de etiquetas asociadas con la mascota
                var etiquetasMascota = ["Vacunado", "Enfermo", "Recién Nacido", "Esterilizado"];

                // Obtén la referencia al elemento ul donde se mostrarán las etiquetas
                var listaEtiquetas = document.getElementById("etiquetasMascota");

                // Recorre la lista de etiquetas y crea elementos li para cada una
                etiquetasMascota.forEach(function(etiqueta) {
                    var li = document.createElement("li");
                    li.textContent = etiqueta;
                    listaEtiquetas.appendChild(li);
                });
            </script>

        </div>
    </div>
    <div class="divButtonsPet">
        <button class="buttonPetDetails">APADRINAR</button>
        <button class="buttonPetDetails">ADOPTAR</button>
    </div>
    <div style="width: 1251px; height: 70px; display: flex; align-items: center;justify-content: flex-start;">
        <div style="width: 300px; height: 47px; display: flex; flex-direction: row; align-items: center; justify-content: center; background: #BBD478; border-radius: 30px">
            <img style="width: 30px; height: 30px;" src="<%=request.getContextPath()%>/assets/img/petlist/arrow_img.png">
            <p style="font-size: 24px; color: #000; margin: 0;">Otras Imágenes</p>
        </div>


    </div>
    <div class="image-list">
        <img src="<%=request.getContextPath()%>/assets/img/petlist/image1.png" alt="Imagen 1">
        <img src="<%=request.getContextPath()%>/assets/img/petlist/image1.png"  alt="Imagen 2">
        <img src="<%=request.getContextPath()%>/assets/img/petlist/image1.png"  alt="Imagen 3">
        <!-- Agrega más imágenes según sea necesario -->
    </div>
</div>
</body>
</html>
