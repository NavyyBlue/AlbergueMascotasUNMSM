<%@ page import="org.grupo12.models.Pet" %>
<%@ page import="java.util.List" %>
<%@ page import="org.grupo12.util.Pagination" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Mascotas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link href="<%=request.getContextPath()%>/assets/css/petlist.css" rel="stylesheet"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/navbar.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"/>
</head>
<body>

<jsp:include page="../../components/navBar.jsp"/>
<main>
    <div style="display: flex; align-items: center; justify-content: center; margin-bottom: 32.7px">
        <img class="imgMain" src="<%=request.getContextPath()%>/assets/img/petlist/petlistmain.png">
        <p class="titleText">MASCOTAS</p>

    </div>

    <div style="align-items: center; justify-content: center; padding: 20px; text-align: -webkit-center;">
        <div class="filterContainer" id="filterContainer">
            <a href="petlist?speciesId=0" class="selected">Todos</a>
            <a href="petlist?speciesId=1">Perros</a>
            <a href="petlist?speciesId=2">Gatos</a>
            <a href="petlist?speciesId=3">Otros</a>

            <div class="input-wrapper">
                <input type="search" class="input" placeholder="Search">

                <svg xmlns="http://www.w3.org/2000/svg" class="input-icon" width="51" height="50" viewBox="0 0 51 50"
                     fill="none">
                    <path d="M38.25 22.9166C38.25 30.9707 31.5902 37.4999 23.375 37.4999C15.1598 37.4999 8.5 30.9707 8.5 22.9166C8.5 14.8624 15.1598 8.33325 23.375 8.33325C31.5902 8.33325 38.25 14.8624 38.25 22.9166Z"
                          fill="#BBD478"/>
                    <path d="M42.5 41.6667L38.25 37.5" stroke="#BBD478" stroke-width="2" stroke-linecap="round"/>
                </svg>
            </div>
        </div>
    </div>

    <div class="container mx-auto mt-4">
        <div class="row" id="card-row">
            <%
                List<Pet> pets = (List<Pet>) request.getAttribute("pets");
                for (Pet pet : pets) {
                    String petInfoUrl = "petinfo?petId=" + pet.getPetId();
            %>
            <div class="col-sm-12 col-md-6 col-lg-4 mb-4">
                <div class="card mb-4">
                    <a href="<%= petInfoUrl %>">
                    <img src="<%= pet.getImageUrl() %>" class="petImg" alt="<%= pet.getName() %>">
                    </a>
                    <div class="card-body">
                        <h5 class="card-title"><%= pet.getName() %>
                        </h5>
                        <p class="card-text">Edad: <%= pet.getAge() %>
                        </p>
                    </div>
                </div>
            </div>
            <%
                }
            %>
        </div>
        <%
            Pagination pagination = (Pagination) request.getAttribute("pagination");
            int start = pagination.getStartPage();
            int end = pagination.getEndPage();
            int totalPages = pagination.getTotalPages(); //Total de páginas
            int total = pagination.getTotal(); //Total de datos
            int currentPage = pagination.getCurrentPage();
            int offset = pagination.getOffset();
            int limit = pagination.getLimit();

            //Obtener el id de la especie del parametro de la url
            int speciesId = Integer.parseInt(request.getParameter("speciesId"));
            System.out.println("speciesId: " + speciesId);
        %>

        <!-- Controles de paginación -->
        <nav aria-label="Page navigation example" class="d-flex justify-content-center">
            <ul class="pagination">
                <%
                    if (currentPage > 1) {
                %>
                <li class="page-item">
                    <a class="page-link" href="petlist?speciesId=<%= speciesId %>&offset=<%= offset - 1 %>&limit=<%= limit %>" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <%
                    }
                %>

                <%
                    for (int i = start; i <= end; i++) {
                %>
                <li class="page-item <%= i == currentPage ? "active" : "" %>">
                    <a class="page-link" href="petlist?speciesId=<%= speciesId %>&offset=<%= (i - 1) * limit %>&limit=<%= limit %>"><%= page %></a>
                </li>
                <%
                    }
                %>

                <%
                    if (currentPage < totalPages) {
                %>
                <li class="page-item">
                    <a class="page-link" href="petlist?speciesId=<%= speciesId %>&offset=<%= offset + 1 %>&limit=<%= limit %>" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
                <%
                    }
                %>
            </ul>
        </nav>

    </div>
    <jsp:include page="../../components/footer.jsp"/>
</body>
</html>

