<%@ page import="java.util.List" %>
<%@ page import="org.grupo12.models.Image" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cambiar o agregar imagen mascota</title>
    <link href="<%=request.getContextPath()%>/assets/css/petImage.css" rel="stylesheet"/>
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
    %>
    <div class="container p-5">
        <form id="uploadImageForm" action="${pageContext.request.contextPath}/admin/sendSingleImage" method="post" enctype="multipart/form-data">
            <input type="hidden" id="uploadPetId" name="uploadPetId" value="<%=petId%>">
            <input type="hidden" id="imageId" name="imageId" value="">
            <!-- Subir imagen principal -->
            <div class="mb-3">
                <label for="uploadImage" class="form-label">Imagen Principal</label>
                <div class="d-flex">
                    <input type="file" class="form-control" id="uploadImage" name="uploadImage" accept="image/*" <%=mainImage == null ? "required" : "" %>>
                    <button type="submit" class="btn btn-primary" onclick="checkFile()">Subir</button>
                </div>
            </div>
            <%if(mainImage != null){%>
                <div class="image-preview d-flex" id="imagePreview">
                    <div class="image-container position-relative">
                        <img src="<%=request.getContextPath() + mainImage.getImageUrl()%>" alt="petImage" class="img-thumbnail" width="200"/>
                        <a class="position-absolute top-0 end-0 delete-button" onclick="deleteImage('<%=mainImage.getImageId()%>', '<%=mainImage.getImageUrl()%>', '<%=mainImage.getPetId()%>')">
                            <img src="<%=request.getContextPath()%>/assets/svg/delete-red.svg" class="remove-image text-danger" width="30" height="30" alt="delete"/>
                        </a>
                    </div>
                </div>
            <%}%>
        </form>
            <!--Subir imagenes secundarias -->
        <form id="uploadMultipleImagesForm" action="${pageContext.request.contextPath}/admin/sendMultipleImage" method="post" enctype="multipart/form-data">
            <input type="hidden" id="uploadPetId2" name="uploadPetId2" value="<%=petId%>">
            <div class="mb-3">
                <label for="uploadImages" class="form-label">Imagenes Secundarias</label>
                <div class="d-flex">
                    <input type="file" class="form-control" id="uploadImages" name="uploadImages" multiple>
                    <button type="submit" class="btn btn-primary">Subir</button>
                </div>
            </div>
            <%if(images != null){%>
                <div class="grid" style="--bs-columns: 6;">
                    <%for(Image image : images){%>
                        <div class="image-preview d-flex" id="imagePreview">
                            <div class="image-container position-relative">
                                <img src="<%=request.getContextPath() + image.getImageUrl()%>" class="img-thumbnail" width="200" height="200" alt="imagenes"/>
                                <a class="position-absolute top-0 end-0 delete-button" onclick="deleteImage('<%= image.getImageId() %>', '<%=image.getImageUrl()%>', '<%=image.getPetId()%>')">
                                    <img src="<%=request.getContextPath()%>/assets/svg/delete-red.svg" class="remove-image text-danger" width="30" height="30" alt="delete"/>
                                </a>
                            </div>
                        </div>
                    <%}%>
                </div>
            <%}%>
            <div class="image-preview d-flex flex-wrap" id="imagePreview"></div>
        </form>
    </div>
    <script>
        function checkFile() {
            var fileInput = document.getElementById('uploadImage');
            var imageIdInput = document.getElementById('imageId');

            // Verifica si se ha seleccionado un archivo
            if (fileInput.files.length > 0) {
                console.log("Se ha seleccionado un archivo");
                imageIdInput.value =  "<%=mainImage != null ? mainImage.getImageId() : -1 %>";
            } else {
                console.log("No se ha seleccionado un archivo");
                imageIdInput.value = "-1";
            }
        }

        function deleteImage(imageId, imageUrl, petId) {
            $.ajax({
                url: "${pageContext.request.contextPath}/admin/deleteImage",
                type: "POST",
                data: {
                    imageId: imageId,
                    imageUrl: imageUrl,
                    petId: petId
                },
                success: function (response) {
                    console.log(response);
                    if (response == "true") {
                        location.reload();
                    }
                }
            });
        }
    </script>


</body>
</html>
