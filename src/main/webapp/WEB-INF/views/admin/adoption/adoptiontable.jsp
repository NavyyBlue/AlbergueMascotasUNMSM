<%@ page import="java.util.List" %>
<%@ page import="org.grupo12.util.Pagination" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.grupo12.models.Adoption" %>
<%@ page import="org.grupo12.util.AdoptionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin - Lista de Adopciones</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
    <link href="<%=request.getContextPath()%>/assets/css/adminUser.css" rel="stylesheet"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/navbar.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
<%
    int defaultUserPetId = 0;
    int userPetId = request.getParameter("userPetId") != null ?
            Integer.parseInt(request.getParameter("userPetId")) : defaultUserPetId;
    int adoptionStatusId = request.getParameter("adoptionStatusId") != null ?
            Integer.parseInt(request.getParameter("adoptionStatusId")) : 1;
%>
<jsp:include page="../../../components/alerts.jsp"/>
<jsp:include page="../../../components/navBar.jsp"/>


<!--Droplist to change between adoptionStatusId and not adoptionStatusId adoptions -->
<div class="dropdown ms-5 mt-2">
    <label for="adoptionStatusIdFilter" class="visually-hidden">Filtrar por estado de usuario</label>
    <select class="btn btn-secondary" id="adoptionStatusIdFilter" onchange="window.location.href=this.value">
        <option value="<%=request.getContextPath()%>/admin/adoptionsTable?adoptionStatusId=2" <%=adoptionStatusId == 2 ? "selected" : ""%> >En proceso</option>
        <option value="<%=request.getContextPath()%>/admin/adoptionsTable?adoptionStatusId=3" <%=adoptionStatusId == 3 ? "selected" : ""%> >Adoptados</option>
<%--        <option value="<%=request.getContextPath()%>/admin/adoptionsTable?adoptionStatusId=4" <%=adoptionStatusId == 4 ? "selected" : ""%> >Rechazados</option>--%>
    </select>
</div>

<div class="table-responsive mt-3 m-5">
    <table class="table">
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Nombre Adoptante</th>
            <th scope="col">Mascota</th>
            <th scope="col">Fecha de adopción</th>
            <th scope="col">Acciones</th>
        </tr>
        </thead>
        <tbody class="table-group-divider">
        <%
            List<Adoption> adoptions = (List<Adoption>) request.getAttribute("adoptions");
            for (Adoption adoption : adoptions) {
        %>
        <tr>
            <th scope="row"><%=adoption.getUserPetId()%></th>
            <td><%=adoption.getUserFullName()%></td>
            <td><%=adoption.getPetName()%></td>
            <td><%=adoption.getAdoptionDate()%></td>
            <td>

                    <% if(adoption.getStatusId() == AdoptionUtil.EN_PROCESO){%>
                <span data-bs-toggle="tooltip" data-bs-placement="top" title="Adoptar">
                        <a type="button" class="btn btn-success btn-sm" data-bs-toggle="modal" data-bs-target="#confirmAdoptionModal"
                           data-userpetid="<%=adoption.getUserPetId()%>" data-petid="<%=adoption.getPetId()%>" data-userid="<%=adoption.getUserId()%>">
                            <img src="<%=request.getContextPath()%>/assets/svg/confirm.svg" alt="confirmar">
                        </a>
                    </span>
                    <%}else if(adoption.getStatusId() == AdoptionUtil.RECHAZADO){%>
                    <span data-bs-toggle="tooltip" data-bs-placement="top" title="Restaurar mascota a la lista de adopción">
                        <a type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#restoreAdoptionModal"
                           data-userpetid="<%=adoption.getUserPetId()%>" data-petid="<%=adoption.getPetId()%>" data-userid="<%=adoption.getUserId()%>">
                            <img src="<%=request.getContextPath()%>/assets/svg/restore.svg" alt="restaurar">
                        </a>
                        </span>
                    <%}%>
            </td>
        </tr>
        <%}%>
        </tbody>
    </table>
</div>
<!-- Quitar rechazo Modal -->
<div class="modal fade" id="restoreAdoptionModal" tabindex="-1" aria-labelledby="restoreAdoptionModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="restoreAdoptionModalLabel">Colocar en adopción</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id="deleteUserForm" action="${pageContext.request.contextPath}/admin/adoptionsTable" method="post">
                <div class="modal-body">
                    ¿Desea volver a colocar en adopción a la mascota?
                </div>
                <div class="modal-footer">
                    <input type="hidden" id="restoreAdoptionUserPetId" name="restoreAdoptionUserPetId" value="">
                    <input type="hidden" id="restoreAdoptionPetId" name="restoreAdoptionPetId" value="">
                    <input type="hidden" id="restoreAdoptionUserId" name="restoreAdoptionUserId" value="">

                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    <button type="submit" class="btn btn-primary">Restaurar</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- Confirm Modal -->
<div class="modal fade" id="confirmAdoptionModal" tabindex="-1" aria-labelledby="confirmAdoptionModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="confirmAdoptionModalLabel">Confirmar Adopción</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id="restoreUserForm" action="${pageContext.request.contextPath}/admin/confirmAdoption" method="post">
                <div class="modal-body">
                    ¿Está seguro de confirmar la adopción??
                </div>
                <div class="modal-footer">
                    <input type="hidden" id="confirmAdoptionUserPetId" name="confirmAdoptionUserPetId" value="">
                    <input type="hidden" id="confirmAdoptionPetId" name="confirmAdoptionPetId" value="">
                    <input type="hidden" id="confirmAdoptionUserId" name="confirmAdoptionUserId" value="">

                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    <button type="submit" class="btn btn-primary">Confirmar adopción</button>
                </div>
            </form>
        </div>
    </div>
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
    <form id="paginationForm" action="<%=request.getContextPath()%>/admin/adoptionsTable" method="get">
        <!-- Add hidden fields for other parameters if needed -->
        <input type="hidden" name="userPetId" value="<%=userPetId%>" />
        <input type="hidden" id="currentPage" name="page" value="<%=currentPage%>" />

        <ul class="pagination">
            <li class="page-item">
                <a class="page-link" href="#" aria-label="Previous" onclick="submitPage(<%= currentPage > start ? currentPage - 1 : start%>)">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            <% for (int i = 1; i <= totalPages; i++) { %>
            <li class="page-item <%= (i == currentPage) ? "adoptionStatusId" : "" %>">
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
<%}%>
<script>


    $('#restoreAdoptionModal').on('show.bs.modal', function (event) {
        let button = $(event.relatedTarget); // Botón que activó el modal
        let userPetId = button.data('userpetid');
        let petId = button.data('petid');
        let userId = button.data('userid');

        $('#restoreAdoptionUserPetId').val(userPetId);
        $('#restoreAdoptionPetId').val(petId);
        $('#restoreAdoptionUserId').val(userId);
    });

    $('#confirmAdoptionModal').on('show.bs.modal', function (event) {
        let button = $(event.relatedTarget); // Botón que activó el modal
        let userPetId = button.data('userpetid');
        let petId = button.data('petid');
        let userId = button.data('userid');

        $('#confirmAdoptionUserPetId').val(userPetId);
        $('#confirmAdoptionPetId').val(petId);
        $('#confirmAdoptionUserId').val(userId);
    });
</script>
</body>
</html>