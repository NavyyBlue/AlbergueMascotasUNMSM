<%@ page import="org.grupo12.models.News" %>
<%@ page import="java.util.List" %>
<%@ page import="org.grupo12.util.Pagination" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Tabla de Noticias</title>
    <link href="<%=request.getContextPath()%>/assets/css/petTable.css" rel="stylesheet"/>
    <link href="<%=request.getContextPath()%>/assets/css/newslist.css" rel="stylesheet"/>
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

<%
    // Default speciesId if not present in the request
    int defaultNewsId = 0;
    int newsId = request.getParameter("newsId") != null ?
            Integer.parseInt(request.getParameter("newsId")) : defaultNewsId;
    int active = request.getParameter("active") != null ?
            Integer.parseInt(request.getParameter("active")) : 1;
%>
<jsp:include page="../../../components/alerts.jsp"/>
<jsp:include page="../../../components/navBar.jsp"/>
<div class="divMainPetTable">
    <div style="display: flex; flex-direction: column; justify-content: center; align-items: flex-end; width: 100%; margin: 0 0 20px 0;">
        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#editModal" data-petid="<%=defaultNewsId%>">
            Añadir Noticia
        </button>
    </div>
    <table>
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Titulo</th>
            <th scope="col">Descripcion</th>
            <th scope="col">Fecha de Creación</th>
            <th scope="col">Tipo de Noticia</th>
            <th scope="col">Editar</th>
        </tr>
        </thead>
        <tbody class="table-group-divider">
        <%
            List<News> news = (List<News>) request.getAttribute("news");
            for (News notice : news) {
//                String petImageUrl = "petImage?petId=" + notice.getNewsId();
        %>

        <tr>
            <td><%= notice.getNewsId() %></td>
            <td><%= notice.getTitle() %></td>
            <td><%= notice.getDescription() %></td>
            <td><%= notice.getCreationDate() %></td>
            <td>
                <%
                    int newsType = notice.getNewsType();
                    if (newsType == 1) {
                        out.print("Eventos");
                    } else if (newsType == 2) {
                        out.print("Emergencias");
                    } else {
                        out.print("Otro");
                    }
                %>
            </td>

            <td>
                    <span data-bs-toggle="tooltip" data-bs-placement="top" title="Editar">
                        <a type="button" class="btn btn-warning btn-sm me-2" data-bs-toggle="modal"
                           data-bs-target="#editModal" data-petid="<%=notice.getNewsId()%>">
                            <img src="<%=request.getContextPath()%>/assets/svg/edit.svg" alt="editar">
                        </a>
                    </span>
                <span data-bs-toggle="tooltip" data-bs-placement="top" title="Subir imagen">
<%--                         <a href="<%= petImageUrl %>" class="btn btn-info btn-sm me-2">--%>
                            <img src="<%=request.getContextPath()%>/assets/svg/upload.svg" alt="upload">
                        </a>
                    </span>
                <% String titleTooltip = notice.getActive() == 1 ? "Eliminar" : "Restaurar"; %>
                <span data-bs-toggle="tooltip" data-bs-placement="top" title="<%=titleTooltip%>">
                        <% if (notice.getActive() == 1) {%>
                            <a type="button" class="btn btn-danger btn-sm" data-bs-toggle="modal"
                               data-bs-target="#deleteModal" data-petid="<%=notice.getNewsId()%>">
                                <img src="<%=request.getContextPath()%>/assets/svg/delete.svg" alt="eliminar">
                            </a>
                        <%} else {%>
                            <a type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal"
                               data-bs-target="#restoreModal" data-petid="<%=notice.getNewsId()%>">
                                <img src="<%=request.getContextPath()%>/assets/svg/restore.svg" alt="restaurar">
                            </a>
                        <%}%>
                    </span>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <script>
        function submitPage(page) {
            document.getElementById('currentPage').value = page;
            document.getElementById('paginationForm').submit();
        }
    </script>
    <%
        Pagination pagination = (Pagination) request.getAttribute("pagination");
        int start = pagination.getStartPage();
        int end = pagination.getEndPage();
        int totalPages = pagination.getTotalPages(); //Total de páginas
        int currentPage = pagination.getCurrentPage();
    %>
    <!-- Controles de paginacion -->
    <nav aria-label="Page navigation example" class="d-flex justify-content-center">
        <form id="paginationForm" action="<%=request.getContextPath()%>/admin/newsTable" method="get">
            <input type="hidden" id="currentPage" name="page" value="<%=currentPage%>"/>

            <ul class="pagination">
                <li class="page-item">
                    <a class="page-link" href="#" aria-label="Previous"
                       onclick="submitPage(<%= currentPage > start ? currentPage - 1 : start%>)">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <% for (int i = 1; i <= totalPages; i++) { %>
                <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                    <a class="page-link" href="#" onclick="submitPage(<%=i%>)"><%=i%></a>
                </li>
                <% } %>
                <li class="page-item">
                    <a class="page-link" href="#" aria-label="Next"
                       onclick="submitPage(<%= currentPage < end ? currentPage + 1: end %>)">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </form>
    </nav>
</div>


<!-- Modal -->
<div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="editModalLabel">Editar Noticia</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="editPetForm" action="${pageContext.request.contextPath}/admin/newsTable" method="post">
                    <div class="mb-3">
                        <label for="editName" class="form-label">Titulo</label>
                        <input type="text" class="form-control" id="editName" name="editName">
                    </div>
                    <div class="mb-3">
                        <label for="editAge" class="form-label">Descripcion</label>
                        <input type="text" class="form-control" id="editAge" name="editAge">
                    </div>
                    <div class="mb-3">
                        <label for="editSpeciesId">Tipo de Noticia</label>
                        <select id="editSpeciesId" name="editSpeciesId" class="form-select"
                                aria-label="Default select example" required>
                            <option value="" disabled selected>Seleccione el tipo de Noticia</option>
                            <option value="1">Evento</option>
                            <option value="2">Emergencia</option>
                            <option value="3">Otros</option>
                        </select>
                    </div>

                    <div class="mb-3" id="entryDateOption">
                        <label for="editEntryDate" class="form-label">Fecha de Publicacion</label>
                        <input type="datetime-local" class="form-control" id="editEntryDate" name="editEntryDate">
                    </div>
                    <input type="hidden" id="isNewPet" name="isNewPet" value="false">
                    <div class="modal-footer">
                        <input type="hidden" id="editPetId" name="editPetId" value="<%=newsId%>">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        <button type="submit" class="btn btn-primary">Guardar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="<%=request.getContextPath()%>/admin/petTable" method="post">
                    <div class="mb-3">
                        <label for="nameNewPet" class="form-label">Nombre</label>
                        <input type="text" class="form-control" id="nameNewPet" name="nameNewPet"
                               aria-describedby="emailHelp" required>
                    </div>
                    <div class="mb-3">
                        <label for="ageNewPet" class="form-label">Edad</label>
                        <input type="text" class="form-control" id="ageNewPet" name="ageNewPet" required>
                    </div>

                    <div class="mb-3">
                        <label for="especie">Especie:</label>
                        <select id="especie" name="especie" class="form-select" aria-label="Default select example"
                                required>
                            <option value="" disabled selected>Seleccione una especie</option>
                            <option value="1">Perro</option>
                            <option value="2">Gato</option>
                            <option value="3">Otros</option>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="genderNewPet">Género</label>
                        <select id="genderNewPet" name="genderNewPet" class="form-select"
                                aria-label="Default select example" required>
                            <option value="" disabled selected>Seleccione un género</option>
                            <option value="Macho">Macho</option>
                            <option value="Hembra">Hembra</option>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="breedNewPet" class="form-label">Raza</label>
                        <input type="text" class="form-control" id="breedNewPet" name="breedNewPet" required>
                    </div>

                    <div class="mb-3">
                        <label for="locationNewPet">Ubicación</label>
                        <select id="locationNewPet" name="locationNewPet" class="form-select"
                                aria-label="Default select example" required>
                            <option value="" disabled selected>Seleccione una ubicación</option>
                            <option value="1">Ciudad Universitaria</option>
                            <option value="2">San Fernando</option>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="descriptionNewPet" class="form-label">Descripción</label>
                        <textarea class="form-control" id="descriptionNewPet" name="descriptionNewPet"
                                  rows="3"></textarea>
                    </div>

                    <button type="submit" class="btn btn-primary">Añadir</button>

                </form>
            </div>
        </div>
    </div>
</div>

<!-- Delete Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="deleteModalLabel">Eliminar Noticia</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id="deletePetForm" action="${pageContext.request.contextPath}/admin/newsTable" method="post">
                <div class="modal-body">
                    ¿Está seguro de eliminar la noticia?
                </div>
                <div class="modal-footer">
                    <input type="hidden" id="deleteNewsId" name="deleteNewsId" value="<%=newsId%>">
                    <input type="hidden" name="_method" value="DELETE">

                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    <button type="submit" class="btn btn-primary">Eliminar</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Restore Modal -->
<div class="modal fade" id="restoreModal" tabindex="-1" aria-labelledby="restoreModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="restoreModalLabel">Restaurar Mascota</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id="restorePetForm" action="${pageContext.request.contextPath}/admin/newsTable" method="post">
                <div class="modal-body">
                    ¿Está seguro de restaurar la mascota?
                </div>
                <div class="modal-footer">
                    <input type="hidden" id="restoreNewsId" name="restoreNewsId" value="<%=newsId%>">
                    <input type="hidden" name="_method" value="PATCH">

                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    <button type="submit" class="btn btn-primary">Restaurar</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    function setIsNewUser(isNew) {
        document.getElementById('isNewPet').value = isNew;
    }

    $('#deleteModal').on('show.bs.modal', function (event) {
        let button = $(event.relatedTarget); // Botón que activó el modal
        let petId = button.data('petid'); // Extraer el userId del atributo data-userid
        $('#deletePetId').val(petId);
    });

    $('#restoreModal').on('show.bs.modal', function (event) {
        let button = $(event.relatedTarget); // Botón que activó el modal
        let petId = button.data('petid'); // Extraer el userId del atributo data-userid
        $('#restorePetId').val(petId);
    });

    $('#editModal').on('show.bs.modal', function (event) {
        let button = $(event.relatedTarget); // Botón que activó el modal
        let petId = button.data('petid'); // Extraer el userId del atributo data-userid
        if (petId === 0) {
            $('#editModalLabel').text('Agregar Mascota');
            // Clear modal fields
            $('#editPetId').val("");
            // Update modal fields with user data
            $('#editName').val("");
            $('#editAge').val("");
            $('#editSpeciesId').val("");
            $('#editGender').val("");
            $('#editDescription').val("");
            $('#editAdoptionStatus').val("");
            $('#editBreed').val("");
            $('#editLocation').val("");
            $('#entryDateOption').hide();
        } else {
            // Parse the JSON data from the attribute
            let petsJson = '<%= request.getAttribute("petsJson") %>';
            let pets = JSON.parse(petsJson);
            // Find the user with the corresponding userId
            let pet = pets.find(function (u) {
                return u.petId === petId;
            });

            $('#editModalLabel').text('Editar Mascota');
            $('#editPetId').val(pet.petId);
            // Update modal fields with user data
            $('#editName').val(pet.name);
            $('#editAge').val(pet.age);
            $('#editSpeciesId').val(pet.speciesId);
            $('#editGender').val(pet.gender);
            $('#editDescription').val(pet.description);
            $('#editAdoptionStatus').val(pet.adoptionStatusId);
            $('#editBreed').val(pet.breed);
            $('#editLocation').val(pet.location);
            $('#editEntryDate').val(pet.entryDate);

        }
        if (petId) {
            setIsNewUser(false);
        } else {
            setIsNewUser(true);
        }
    });
</script>

</body>
</html>
