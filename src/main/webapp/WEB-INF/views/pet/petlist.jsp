<%@ page import="org.grupo12.models.Pet" %>
<%@ page import="java.util.List" %>
<%@ page import="org.grupo12.util.Pagination" %>
<%@ page import="org.grupo12.models.User" %>
<%@ page import="java.util.Properties" %>
<%@ page import="org.grupo12.util.ConfigLoader" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String webSocketUrl = ConfigLoader.getWebSocketUrl();
%>
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
    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script src="https://cdn.socket.io/4.7.2/socket.io.min.js" integrity="sha384-mZLF4UVrpi/QTWPA7BjNPEnkIfRFn4ZEO3Qt/HFklTJBj/gBOV8G3HcKn4NfQblz" crossorigin="anonymous"></script>
    <script>
        // Establecer la conexión WebSocket
        let socket = new WebSocket("<%=webSocketUrl%>/petfavorites");

        // Escuchar eventos WebSocket
        socket.onmessage = function(event) {
            const data = JSON.parse(event.data);
            if (data.event === "favoriteChange") {
                updateFavoriteCount(data.petId, data.newFavoriteCount);
            }
        };

        // Función para actualizar la cantidad de favoritos en la interfaz de usuario
        function updateFavoriteCount(petId, newFavoriteCount) {
            const favoriteCountElement = document.querySelector(".favorite-count-" + petId);
            const favoriteImgElement = document.querySelector(".favorite-pet-" + petId).querySelector(".favorite-img");
            if (favoriteCountElement) {
                favoriteCountElement.textContent = newFavoriteCount;

                const isFavorite = favoriteImgElement.src.includes("favorite-2.svg");
                if (isFavorite) {
                    favoriteImgElement.src = "${pageContext.request.contextPath}/assets/svg/favorite-2.svg";
                } else {
                    favoriteImgElement.src = "${pageContext.request.contextPath}/assets/svg/favorite-1.svg";
                }
            }

        }
    </script>
</head>
<body>
<jsp:include page="../../components/navBar.jsp"/>
<main>
    <div style="display: flex; align-items: center; justify-content: center; margin-bottom: 32.7px">
        <img class="imgMain" src="<%=request.getContextPath()%>/assets/img/petlist/petlistmain.png">
        <p class="titleText">MASCOTAS</p>

    </div>
        <%
            User user = (User) request.getSession().getAttribute("user");
            // Default speciesId if not present in the request
            int defaultSpeciesId = 0;
            int speciesId = request.getParameter("speciesId") != null ?
                    Integer.parseInt(request.getParameter("speciesId")) : defaultSpeciesId;

            String searchKeyword = (String) request.getAttribute("searchKeyword");
            if (searchKeyword == null) {
                searchKeyword = "";
            }
            request.setAttribute("searchKeyword", searchKeyword);
        %>
    <div style="align-items: center; justify-content: center; padding: 20px; text-align: -webkit-center;">
        <div class="filterContainer" id="filterContainer">
            <a href="petlist?speciesId=0" class="<%= (speciesId == 0) ? "selected" : "" %>">Todos</a>
            <a href="petlist?speciesId=1" class="<%= (speciesId == 1) ? "selected" : "" %>">Perros</a>
            <a href="petlist?speciesId=2" class="<%= (speciesId == 2) ? "selected" : "" %>">Gatos</a>
            <a href="petlist?speciesId=3" class="<%= (speciesId == 3) ? "selected" : "" %>">Otros</a>


            <form id="searchForm" action="<%= request.getContextPath() %>/petlist" method="get">
                <div class="input-wrapper">
                    <input type="search" class="input" placeholder="Buscar por nombre" name="searchKeyword" value="<%= searchKeyword %>">
                    <svg xmlns="http://www.w3.org/2000/svg" class="input-icon" width="51" height="50" viewBox="0 0 51 50"
                         fill="none">
                        <path d="M38.25 22.9166C38.25 30.9707 31.5902 37.4999 23.375 37.4999C15.1598 37.4999 8.5 30.9707 8.5 22.9166C8.5 14.8624 15.1598 8.33325 23.375 8.33325C31.5902 8.33325 38.25 14.8624 38.25 22.9166Z"
                              fill="#BBD478"/>
                        <path d="M42.5 41.6667L38.25 37.5" stroke="#BBD478" stroke-width="2" stroke-linecap="round"/>
                    </svg>
                </div>
            </form>
        </div>
    </div>

    <div class="container mx-auto mt-4">
        <div class="row" id="card-row">
            <%
                List<Pet> pets = (List<Pet>) request.getAttribute("pets");
                List<Integer> favoritePets = (List<Integer>) request.getAttribute("favoritePets");
                for (Pet pet : pets) {
                    String petInfoUrl = "petinfo?petId=" + pet.getPetId();
                    // Imagen por defecto si no tiene
                    String imgUrl = (pet.getImageUrl() == null || pet.getImageUrl().isEmpty()) ?
                                    request.getContextPath() + "/assets/img/petlist/pet_footprint.png" : pet.getImageUrl();
                    boolean isFavorite = favoritePets != null && favoritePets.contains(pet.getPetId());
            %>
            <div class="col-sm-12 col-md-6 col-lg-4 mb-4">
                <div class="card mb-4 position-relative">
                    <div class="card-link">
                        <img src="<%=request.getContextPath() + imgUrl%>" alt="<%= pet.getName() %>" class="card-img bd-placeholder-img bd-placeholder-img-lg petImg">
                        <div class="card-img-overlay infoCardImg">
                            <%if(user != null){%>
                                <a class="card-text position-absolute top-0 end-0 pe-4 pt-3 favorite-pet-<%=pet.getPetId()%>" onclick="handleFavorite('<%=pet.getPetId()%>')">
                                    <img src="<%=request.getContextPath()%>/assets/svg/favorite-<%= isFavorite ? '2' : '1'%>.svg" alt="fav" class="favorite-img">
                                </a>
                                <h5 class="card-text position-absolute top-0 end-0 pe-4 mt-5 favorite-count-<%=pet.getPetId()%>"><%=pet.getTotalFavorites()%></h5>
                            <%}%>
                            <a href="<%= petInfoUrl %>" class="text-card">
                                <h2 class="card-text"><%= pet.getName() %></h2>
                                <p class="card-text fs-6">Sexo: <%= pet.getGender() %> </p>
                                <p class="card-text fs-6">Edad: <%= pet.getAge() %> años</p>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <%
                }
            %>
        </div>
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
            if(totalPages > 0){
        %>
            <!-- Controles de paginacion -->
            <nav aria-label="Page navigation example" class="d-flex justify-content-center">
                <form id="paginationForm" action="<%=request.getContextPath()%>/petlist" method="get">
                    <!-- Add hidden fields for other parameters if needed -->
                    <input type="hidden" name="speciesId" value="<%=speciesId%>" />
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
        <%}
            if(pets.size() == 0){
        %>
            <img src="<%=request.getContextPath()%>/assets/svg/pet.svg" alt="No se encontraron mascotas" class="imgEmpty">
            <p class="textEmpty">No se encontraron mascotas</p>
        <%}%>
    </div>
    <jsp:include page="../../components/footer.jsp"/>

    <script>
           function handleFavorite(petId) {
            let xhr = new XMLHttpRequest();
            xhr.open("POST", "${pageContext.request.contextPath}/petfavorite", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = () => {
                if(xhr.readyState === 4 && xhr.status === 200) {
                    let response = JSON.parse(xhr.responseText);
                    if (response.success) {
                        let favoriteImg = document.querySelector(".favorite-pet-" + petId).querySelector(".favorite-img");
                        if (response.isFavorite) {
                            favoriteImg.src = "${pageContext.request.contextPath}/assets/svg/favorite-2.svg";
                        } else {
                            favoriteImg.src = "${pageContext.request.contextPath}/assets/svg/favorite-1.svg";
                        }
                        // Envía un mensaje de WebSocket después de actualizar el estado de favoritos
                        socket.send(JSON.stringify({ petId: petId, favoriteCount: response.favoriteCount }));
                    }
                }
            }
            xhr.send("petId=" + petId);


        }
    </script>
</body>
</html>

