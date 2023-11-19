<%@ page import="org.grupo12.models.Pet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="org.grupo12.util.Pagination" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Tabla de Mascotas</title>
    <link href="<%=request.getContextPath()%>/assets/css/petTable.css" rel="stylesheet"/>
    <link href="<%=request.getContextPath()%>/assets/css/petlist.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/navbar.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</head>
<body>
<jsp:include page="../../components/navBar.jsp"/>
<div class="divMainPetTable">
    <div style="display: flex; flex-direction: column; justify-content: center; align-items: flex-end; width: 100%; margin: 0 0 20px 0;">
        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
            Añadir Mascota
        </button>
    </div>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Edad</th>
            <th>Especie</th>
            <th>Género</th>
            <th>Raza</th>
            <th>Fecha de Ingreso</th>
            <th>Ubicación</th>
            <th>Estado de Adopción</th>
            <th>Editar</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Pet> pets = (List<Pet>) request.getAttribute("pets");
            for (Pet pet : pets) {
                String petInfoUrl = "petinfo?petId=" + pet.getPetId();
        %>

        <tr>
            <td><%= pet.getPetId() %></td>
            <td><%= pet.getName() %></td>
            <td><%= pet.getAge() %> años</td>
            <td>
                <%
                    int speciesId = pet.getSpeciesId();
                    if (speciesId == 1) {
                        out.print("Perro");
                    } else if (speciesId == 2) {
                        out.print("Gato");
                    } else if (speciesId == 3) {
                        out.print("Otro");
                    } else {
                        out.print("Desconocido");
                    }
                %>
            </td>
            <td><%= pet.getGender() %></td>
            <td><%= pet.getBreed() %></td>
            <td><%= pet.getEntryDate() %></td>
            <td>
                <%
                    int location = pet.getLocation();
                    if (location == 0) {
                        out.print("Ciudad Universitaria");
                    } else if (speciesId == 2) {
                        out.print("Gato");
                    } else if (speciesId == 3) {
                        out.print("Otro");
                    } else {
                        out.print("Desconocido");
                    }
                %>
            </td>
            <td>
                <%
                    int adoptionStatus = pet.getAdoptionStatusId();
                    if (adoptionStatus == 1) {
                        out.print("Disponible");
                    } else if (adoptionStatus == 2) {
                        out.print("Gato");
                    } else if (adoptionStatus == 3) {
                        out.print("Otro");
                    } else {
                        out.print(adoptionStatus);
                    }
                %>
            </td>
            <td>
                <img src="<%=request.getContextPath()%>/assets/icon/ic_home.svg">
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
        <form id="paginationForm" action="<%=request.getContextPath()%>/petTable" method="get">
            <!-- Add hidden fields for other parameters if needed -->
            <%--        <input type="hidden" name="speciesId" value="<%=speciesId%>" />--%>
            <input type="hidden" id="currentPage" name="page" value="<%=currentPage%>" />

            <ul class="pagination">
                <li class="page-item">
                    <a class="page-link" href="#" aria-label="Previous" onclick="submitPage(<%= currentPage > start ? currentPage - 1 : start%>)">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <% for (int i = 1; i <= totalPages; i++) { %>
                <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                    <a class="page-link" href="#" onclick="submitPage(<%=i%>)"><%=i%></a>
                </li>
                <% } %>
                <li class="page-item">
                    <a class="page-link" href="#" aria-label="Next" onclick="submitPage(<%= currentPage < end ? currentPage + 1: end %>)">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </form>
    </nav>
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
                <form action="<%=request.getContextPath()%>/petTable" method="post">
                        <div class="mb-3">
                            <label for="nameNewPet" class="form-label">Nombre</label>
                            <input type="text" class="form-control" id="nameNewPet" name="nameNewPet" aria-describedby="emailHelp" required>
                        </div>
                        <div class="mb-3">
                            <label for="ageNewPet" class="form-label">Edad</label>
                            <input type="text" class="form-control" id="ageNewPet"  name="ageNewPet" required>
                        </div>

                        <div class="mb-3">
                            <label for="especie">Especie:</label>
                            <select id="especie" name="especie" class="form-select" aria-label="Default select example" required>
                                <option selected>Seleccione una especie</option>
                                <option value="perro">Perro</option>
                                <option value="gato">Gato</option>
                                <option value="otros">Otros</option>
                            </select>
                        </div>

                    <div class="mb-3">
                        <label for="genderNewPet">Género</label>
                        <select id="genderNewPet" name="genderNewPet" class="form-select" aria-label="Default select example" required>
                            <option selected>Seleccione un género</option>
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
                        <select id="locationNewPet" name="locationNewPet" class="form-select" aria-label="Default select example" required>
                            <option selected>Seleccione un género</option>
                            <option value="1">Ciudad Universitaria</option>
                            <option value="2">San Fernando</option>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="descriptionNewPet" class="form-label">Descripción</label>
                        <textarea class="form-control" id="descriptionNewPet" name="descriptionNewPet" rows="3"></textarea>
                    </div>

                    <button type="submit" class="btn btn-primary">Submit</button>

                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
