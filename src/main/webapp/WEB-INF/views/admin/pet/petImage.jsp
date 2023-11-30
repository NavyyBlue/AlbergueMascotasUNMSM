<%@ page import="java.util.List" %>
<%@ page import="org.grupo12.models.Image" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cambiar o agregar imagen mascota</title>
    <link href="<%=request.getContextPath()%>/assets/css/petTable.css" rel="stylesheet"/>
    <link href="<%=request.getContextPath()%>/assets/css/petlist.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/navbar.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
            crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
    <jsp:include page="../../../components/alerts.jsp"/>
    <jsp:include page="../../../components/navBar.jsp"/>
    <%
        String petId = request.getParameter("petId");

        Image mainImage = (Image) request.getAttribute("mainImage");
        List<Image> images = (List<Image>) request.getAttribute("images");

        String petTableUrl = "petTable";
    %>
    <div class="container p-5">
        <form id="uploadImageForm" action="${pageContext.request.contextPath}/admin/petImage" method="post" enctype="multipart/form-data">
            <input type="hidden" id="uploadPetId" name="uploadPetId" value="<%=petId%>">
            <!-- Subir imagen principal -->
            <div class="mb-3">
                <label for="uploadImage" class="form-label">Imagen Principal</label>
                <input type="file" class="form-control" id="uploadImage" name="uploadImage">
                <%if(mainImage != null){%>
                    <img src="<%=request.getContextPath() + mainImage.getImageUrl()%>" alt="petImage" class="img-thumbnail" width="200"/>
                <%}%>
            </div>
            <div class="main-image-preview" id="main-image-preview"></div>
            <!--Subir imagenes secundarias -->
            <div class="mb-3">
                <label for="uploadImages" class="form-label">Imagenes Secundarias</label>
                <input type="file" class="form-control" id="uploadImages" name="uploadImages" multiple>
            </div>
            <%if(images != null){
                for(Image image : images){%>
                <div class="image-preview d-flex flex-wrap" id="imagePreview">
                    <div class="image-container">
                        <img src="<%=request.getContextPath() + image.getImageUrl()%>" class="img-thumbnail" width="200" height="200"/>
                        <button type="button" class="btn btn-danger btn-sm remove-image">Eliminar</button>
                    </div>
                </div>
            <%}}%>
            <div class="image-preview d-flex flex-wrap" id="imagePreview"></div>
            <div class="modal-footer">
                <a href="<%= petTableUrl %>" type="button" class="btn btn-secondary" data-bs-dismiss="modal">Volver</a>
                <button type="submit" class="btn btn-primary">Subir</button>
            </div>
        </form>
    </div>
    <%--<script>

        $(document).ready(function () {
            $('#uploadImages').on('change', function (e) {
                // Obtén el contenedor de previsualización de imágenes
                let imagePreview = $('#imagePreview');

                // Limpia la previsualización anterior
                imagePreview.html('');

                // Lee los archivos seleccionados
                let files = e.target.files;

                // Itera sobre los archivos y agrega previsualización
                $.each(files, function (i, file) {
                    let reader = new FileReader();

                    reader.onload = function (e) {
                        // Agrega la imagen al contenedor de previsualización
                        imagePreview.append('<div class="image-container"><img src="' + e.target.result + '" class="img-thumbnail" width="200" height="200"/><button type="button" class="btn btn-danger btn-sm remove-image">Eliminar</button></div>');

                        // Actualiza el valor del input
                        updateInputValue();
                    };

                    // Lee el archivo como URL de datos
                    reader.readAsDataURL(file);
                });

                // Agrega un manejador de eventos para el botón de eliminación
                imagePreview.on('click', '.remove-image', function () {
                    $(this).parent().remove();
                    updateInputValue();
                });
            });

            function updateInputValue() {
                let imagePreview = $('#imagePreview');
                let imageCount = imagePreview.find('.image-container').length;

                // Crea un nuevo objeto FileList simulado
                let simulatedFileList = [];
                for (let i = 0; i < imageCount; i++) {
                    simulatedFileList.push(new File([""], "image" + i + ".png"));
                }

                // Asigna el nuevo objeto FileList al input
                $('#uploadImages').prop('files', new DataTransfer().items.add(simulatedFileList));
            }

        });
    </script>--%>
</body>
</html>
