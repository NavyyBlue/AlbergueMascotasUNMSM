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
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
            crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
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

        for(Pet pet : petImages){
            if(pet.isMainImage() && pet.getImageUrl() != null && pet.isImageActive()){
                firstImageUrl = pet.getImageUrl();
                //Quitar la imagen principal de la lista
                petImages.remove(pet);
                break;
            }
        }

        if(firstImageUrl == null){
            firstImageUrl = "/assets/img/petlist/pet_footprint.png";
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
        <img style="width: 406px; height: 406px; border-radius: 30px;" src="<%=request.getContextPath() + firstImageUrl%>">
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
        <button type="button" style="margin: 0 10px 0 0; background: #94C11F; border-color: #94C11F; width: 150px;" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#deleteModal" data-petid="0">
            APADRINAR
        </button>
        <button type="button" style="margin: 0 0 0 10px; background: #94C11F; border-color: #94C11F; width: 150px;" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#deleteModal" data-petid="0">
            ADOPTAR
        </button>
    </div>
    <div style="width: 1251px; height: 70px; display: flex; align-items: center;justify-content: flex-start;">
        <div style="width: 300px; height: 47px; display: flex; flex-direction: row; align-items: center; justify-content: center; background: #BBD478; border-radius: 30px">
            <img style="width: 30px; height: 30px;" src="<%=request.getContextPath()%>/assets/img/petlist/arrow_img.png">
            <p style="font-size: 24px; color: #000; margin: 0;">Otras Imágenes</p>
        </div>


    </div>

    <div class="row text-center mx-5 mt-3">
        <% for (Pet image : petImages) {
            if(image.getImageUrl() == null || !image.isImageActive()){
                continue;
            }
        %>
            <div class="col-md-4 mb-5">
                <img style="width: 406px; height: 406px; border-radius: 30px;" src="<%=request.getContextPath() + image.getImageUrl() %>" alt="Imagen de la mascota">
            </div>
        <% } %>
    </div>

</div>


<!-- Delete Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="apadrinarModalLabel">Apadrinar Mascota</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>


            <div style="border: 1px solid #4e3838; border-radius: 30px; margin: 10px 30px 10px 30px;  padding: 10px; display: flex; flex-direction: column; justify-content: center; align-items: center;">
                <h1 style="font-size: 48px; margin: 0;"><%= petInfo.getName() %></h1>
            </div>

            <div class="divMainPet" style="margin: 0 15px 0 15px">
                <p style="text-align: center; margin: 0">Cada aporte va directo al cuidado de LA mascotas. Si sobrepasamos las necesidades de una, el excedente beneficia a las demás. Tu contribución hace la diferencia en sus vidas. ¡Gracias por ser parte de nuestro compromiso con el bienestar animal! 🐾 </p>
            </div>
            <div class="modal-body">
                <form id="deletePetForm" action="${pageContext.request.contextPath}/admin/petTable" method="post">

                    <div class="mb-3" style="justify-content: center;">
                        <label class="form-label">Monto</label>
                        <br>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadioPayment1" value="option1">
                            <label class="form-check-label" for="inlineRadioPayment1">S./10</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadioPayment2" value="option2">
                            <label class="form-check-label" for="inlineRadioPayment2">S./15</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadioPayment3" value="option3">
                            <label class="form-check-label" for="inlineRadioPayment3">S./20</label>
                        </div>
                    </div>
                    <div class="modal-footer" style="justify-content: center;">
                        <%--                    <input type="hidden" id="deletePetId" name="deletePetId" value="<%=petId%>">--%>
                        <%--                    <input type="hidden" name="_method" value="DELETE">--%>

                        <button type="submit" class="btn btn-primary" style="margin: 0 10px 0 0; background: #94C11F; border-color: #94C11F; width: 150px;">Apadrinar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%} else {%>
<h1>No se encontró la mascota</h1>
<%}%>
</body>
</html>
