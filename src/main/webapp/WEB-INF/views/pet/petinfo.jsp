<%@ page import="org.grupo12.models.Pet" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Arrays" %>
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
<%
    String petIdParam = request.getParameter("petId");
    if (petIdParam != null && !petIdParam.isEmpty()) {
        Pet petInfo = (Pet) request.getAttribute("petInfo");
        List<Pet> petStatus = (List<Pet>) request.getAttribute("petStatus");
        List<Pet> petImages = (List<Pet>) request.getAttribute("petImages");
        String firstImageUrl = null;
        // Verificar si la lista no está vacía
        if (!petImages.isEmpty()) {
            // Obtener el primer elemento de la lista
            Pet firstPetImage = petImages.get(0);

            // Obtener la propiedad imgUrl del primer elemento
            firstImageUrl = firstPetImage.getImageUrl();
        }

        // Crear un array de strings con los valores de petStatusName
        List<String> petStatusNames = new ArrayList<>();
        for (Pet pet : petStatus) {
            petStatusNames.add(pet.getPetStatusName());
        }

        // Convertir la lista de strings a un array de strings
        String[] petStatusArray = petStatusNames.toArray(new String[0]);
%>
<div class="divMainPet">
    <div class="divDetailsPet" style="align-items: center;">
        <img style="width: 406px; height: 406px; border-radius: 30px;" src="<%=firstImageUrl%>">
        <div class="divDescriptionPet">
            <div class="divDetailsPet" style="height: 71px; align-items: center">
                <img style="width: 55.675px; height: 52.984px; transform: rotate(52.107deg); margin: 0 10px 0 0;" src="<%=request.getContextPath()%>/assets/img/petlist/pet_footprint.png">
                <p style="margin: 0; font-size: 54px;"><%= petInfo.getName() %></p>
            </div>
            <div class="animal-info" id="animalInfo">
                <p>Sexo: <span id="sexoPlaceholder"><%= petInfo.getGender() %></span></p>
                <p>Edad: <span id="edadPlaceholder"><%= petInfo.getAge() %></span></p>
                <%if(petInfo.getBreed() != null){%>
                <p>Raza: <span id="tamanioPlaceholder"><%=petInfo.getBreed()%></span></p>
                <%}%>
                <p>Descripción: <span id="descriptionPlaceholder"><%= petInfo.getDescription() %></span></p>
                <p>Ubicación: <span id="locationPlaceholder"><%= petInfo.getLocation() %></span></p>
                <p>Etiquetas:</p>
                <ul id="etiquetasMascota"></ul>
                <p id="descripcionPlaceholder"></p>
            </div>
            <script>
                const etiquetasMascota = JSON.parse('<%= Arrays.toString(petStatusArray).replaceAll("'", "\\\\'") %>');
                console.log("etiquetas: ", etiquetasMascota);

                // Obtén la referencia al elemento ul donde se mostrarán las etiquetas
                const listaEtiquetas = document.getElementById("etiquetasMascota");

                // Recorre la lista de etiquetas y crea elementos li para cada una
                etiquetasMascota.forEach(function(etiqueta) {
                    let li = document.createElement("li");
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

    <div class="text-center">
        <% for (Pet image : petImages) { %>
        <img style="width: 406px; height: 406px; border-radius: 30px;" src="<%= image.getImageUrl() %>" alt="Imagen de la mascota">
        <% } %>
    </div>

</div>
<%} else {%>
<h1>No se encontró la mascota</h1>
<%}%>
</body>
</html>
